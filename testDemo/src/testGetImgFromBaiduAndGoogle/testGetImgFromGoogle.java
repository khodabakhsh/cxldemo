package testGetImgFromBaiduAndGoogle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 google的貌似一次从参数【start】开始算起抓取20个图
 http://www.google.com.hk/search?q=%E4%B9%A6&um=1&hl=zh-CN&newwindow=1&safe=strict&sa=X&tbs=isz:l&biw=1540&bih=125&tbm=isch&ijn=ls&ei=XjMlTs-7N7HomAW4r-zqCQ&page=0&start=0
 * ，
 * 
 * 参数：
 * start： 开始位置
 * q： 关键字 
 * tbs :图片尺寸
 * 
 * 
 */
/**
 * 需要的主要jar包 httpclient-4.0.1jar jsoup-1.5.2.jar
 * 
 * @author caixl
 * 
 */
public class testGetImgFromGoogle {

	private static final Log log = LogFactory
			.getLog(testGetImgFromGoogle.class);

	/**
	 * 抓取图片存放目录
	 */
	private static final String PIC_DIR = "d:/temp";

	private static final String gb2312 = "gb2312";
	private static final String utf8 = "utf-8";

	private static final String big = "isz:l"; // 大尺寸
	private static final String middle = "isz:m"; // 中尺寸

	private static int pageSize = 20; // 一次装载20个图

	/**
	 * 链接超时
	 */
	private static final int TIME_OUT = 5000;

	/**
	 * 传入url获得html内容
	 */
	public static String getHtmlContent(String url) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);
		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				TIME_OUT);
		StringBuilder sBuffer = new StringBuilder();
		try {
			HttpResponse response = client.execute(get);
			InputStreamReader iStreamReader = new InputStreamReader(
					new BufferedInputStream(response.getEntity().getContent()),
					gb2312);

			BufferedReader bufferedReader = new BufferedReader(iStreamReader);
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
	}

	public static void beginGetImgs(String url, final String directory)
			throws Exception {
		System.out.println(url);
		// 1.用这个访问会抓到所有页面的连页，下面必须加入header，否则会出现403 load url error(禁止访问)
		Document doc = Jsoup
				.connect(url)
				.header("User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
				.header("Accept", "text ml,application/xhtml+xml").header(
						"Accept-Language", "zh-cn,zh;q=0.5").header(
						"Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7").get();
		// System.err.println(doc.outerHtml());

		// 2.用下面这句，只能抓到从start开始的21个图，why
		// Document doc = Jsoup.parse(getHtmlContent(url));
		Elements links = doc.select("a[href~=/imgres\\?imgurl*]");
		for (int i = 0; i < links.size(); i++) {
			Element element = links.get(i);
			String fullImgHref = element.attr("href");
			final String imgHref = fullImgHref.substring("/imgres?imgurl="
					.length(), fullImgHref.indexOf("&"));
			final String savefileName = i
					+ imgHref.substring(imgHref.lastIndexOf("."));
			Thread.sleep(500);
			new Thread(new Runnable() {
				public void run() {
					try {
						saveImg(imgHref, savefileName, directory);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	/**
	 * 保存图片
	 * 
	 * @param url
	 * @param i
	 * @throws Exception
	 */
	public static void saveImg(String url, String fileName, String directory)
			throws Exception {
		if (fileName == null || "".equals(fileName)) {
			fileName = url.substring(url.lastIndexOf("/"));
		}
		String diretoryPathString = PIC_DIR + "/" + directory + "/";
		File directoryFile = new File(diretoryPathString);
		if (!directoryFile.exists()) {
			directoryFile.mkdirs();
		}
		BufferedOutputStream out = null;
		byte[] bit = getByte(url);
		if (bit.length > 0) {
			try {
				out = new BufferedOutputStream(new FileOutputStream(
						diretoryPathString + fileName));
				out.write(bit);
				out.flush();
				System.err.println("成功创建文件【" + diretoryPathString + fileName
						+ "】" + "，源路径：【" + url + "】");
				log.info("Create File success! [" + diretoryPathString
						+ fileName + "]");
			} finally {
				if (out != null)
					out.close();
			}
		}
	}

	/**
	 * 获取图片字节流
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	static byte[] getByte(String uri) throws Exception {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(uri);
		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				TIME_OUT);
		try {
			HttpResponse resonse = client.execute(get);
			if (resonse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resonse.getEntity();
				if (entity != null) {
					return EntityUtils.toByteArray(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return new byte[0];
	}

	public static void main(String[] args) throws Exception {
		String[] words = { "何静", "张馨予", "周韦彤" };
		for (String word : words) {
			final String tempword = word;
			new Thread(new Runnable() {
				public void run() {
					try {
						beginGetImgs(genRequestUrl(tempword, "0", big),
								tempword);
						// System.out.println(genRequestUrl(tempword, "0",
						// big));
						// System.out.println(getHtmlContent(genRequestUrl(tempword,
						// "0", big)));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

		}
	}

	/**
	 * 
	 * @param word
	 *            关键字
	 * @param start
	 *            开始位置
	 * @param imgSize
	 *            尺寸
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String genRequestUrl(String word, String start, String imgSize)
			throws UnsupportedEncodingException {
		return "http://www.google.com.hk/search?hl=zh-CN&newwindow=1&safe=strict&tbm=isch&prmd=ivnsol&source=lnt&sa=X&ei=N1gmTsPxHOOfmQXl7bGOCg&ved=0CAcQpwUoAQ&biw=1540&bih=258&start="
				+ start
				+ "&q="
				+ URLEncoder.encode(word, gb2312)
				+ "&tbs="
				+ imgSize;

	}
}
package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class GetImgFromPconline {

	/**
	 * 抓取图片存放目录
	 */
	private static final String PIC_DIR = "d:/temp";

	private static final String gb2312 = "gb2312";
	private static final String utf8 = "utf-8";
	private static final String gbk = "GBK";

	/**
	 * 链接超时
	 */
	private static final int TIME_OUT = 5000;

	private static ExecutorService executor = Executors.newFixedThreadPool(200);
	private static final String baseUrl = "http://wallpaper.pconline.com.cn";

	private static final String resolution = "1680x1050";

	/**
	 * 传入url获得html内容
	 * @throws Exception 
	 */
	public static String getHtmlContent(String url) throws Exception {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);

		//pconline必须要设置header，不然会乱码
		get.setHeader("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 Greatwqs");
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		get.setHeader("Host", "www.yourdomain.com");
		get.setHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		get.setHeader("Referer", "http://www.yourdomian.com/xxxAction.html");

		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		StringBuilder sBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			HttpResponse response = client.execute(get);
			bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(response.getEntity()
					.getContent()), gbk));
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
			//			System.out.println(sBuffer);
		} catch (Exception e) {
			System.out.println("重新 尝试url --->  " + url);
			return getHtmlContent(url);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
	}

	/**
	 * 获取指定url对应图集的所有图片 
	 */
	public static void beginGetImgsByAlbum(String url, String preDirectory) throws Exception {
		//		System.out.println(url);
		Document doc = Jsoup.parse(getHtmlContent(url));
		Elements lis = doc.select("div.sPicList li");
		final String title = doc.select("h1.photoName").get(0).html();
		String number = lis.get(0).getElementsByTag("i").html();
		number = number.substring(number.lastIndexOf("/") + 1);
		int count = Integer.valueOf(number);
		List<String> list = new ArrayList<String>();
		String suffix = ".html";
		String prefix = url.substring(0, url.lastIndexOf(suffix));
		list.add(url);
		//因为其url规律是：http://wallpaper.pconline.com.cn/pic/17536_计数.html
		for (int i = 2; i <= count; i++) {
			list.add(prefix + "_" + i + suffix);
		}
		for (int j = 0; j < list.size(); j++) {
			final String htmlurl = list.get(j);
			final String fileName = String.valueOf(j + 1);
			final String listSize = String.valueOf(list.size());
//			Thread.sleep(200);
			final String directory = preDirectory + ("".equals(preDirectory)?"":"/") + title + "_" + listSize;
			executor.execute((new Runnable() {
				public void run() {
					try {
						get_1680_1050(htmlurl, listSize + "_" + fileName, directory);
					} catch (Exception e) {
						try {
							System.out.println("重新尝试下载IMG  --->  " + htmlurl);
							get_1680_1050(htmlurl, listSize + "_" + fileName, directory);
						} catch (Exception e1) {
							e1.printStackTrace();
							System.out.println("重新尝试【失败】 " + htmlurl);
						}
					}
				}
			}));
		}
	}

	public static void get_1680_1050(String url, String fileName, String directory) throws Exception {
		Document doc = Jsoup.parse(getHtmlContent(url));
		Elements imgs = doc.select("div.sPicList li.cur img");
		for (int j = 0; j < imgs.size(); j++) {
			Element element = imgs.get(j);
			//			System.out.println(element.attr("src"));
			String img_100x75 = element.attr("src");
			//图片img_100x75地址如：http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1303/06/c0/18673593_1362554875411_分辨率.jpg
			//真实地址是，不知怎么得来，http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1303/06/c0/spcgroup/18673593_1362554875411__分辨率.jpg
			String prefix = img_100x75.substring(0, img_100x75.lastIndexOf("_"));
			String suffix = img_100x75.substring(img_100x75.lastIndexOf("."));
			String img_temp = prefix + "_" + resolution + suffix;
			String prefixSplash = img_temp.substring(0, img_temp.lastIndexOf("/"));
			String suffixSplash = img_temp.substring(img_temp.lastIndexOf("/") + 1);
			//只能硬编码
			String img_wanted = (prefixSplash + "/spcgroup/" + suffixSplash).replace("img", "imgrt");
			saveImg(img_wanted, fileName + suffix, directory);
		}
	}

	/**
	 * 保存图片
	 */
	public static void saveImg(String url, String fileName, String directory) throws Exception {
		if (fileName == null || "".equals(fileName)) {
			fileName = url.substring(url.lastIndexOf("/"));
		}
		String diretoryPathString = PIC_DIR + "/" + directory + "/";
		File directoryFile = new File(diretoryPathString);
		if (!directoryFile.exists()) {
			directoryFile.mkdirs();
		}
		if (new File(diretoryPathString + fileName).exists()) {
			return;
		}
		BufferedOutputStream out = null;
		byte[] bit = getByte(url);
		if (bit.length > 0) {
			try {
				out = new BufferedOutputStream(new FileOutputStream(diretoryPathString + fileName));
				out.write(bit);
				out.flush();
			} catch (Exception e) {
				throw e;
			} finally {
				bit =null;
				if (out != null)
					out.close();
			}
			System.err.println("成功创建文件【" + diretoryPathString + fileName + "】" + "，源路径：【" + url + "】");
		}
	}

	/**
	 * 获取图片字节流
	 */
	static byte[] getByte(String uri) throws IOException {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(uri);
		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpResponse resonse;
		try {
			resonse = client.execute(get);

			if (resonse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resonse.getEntity();
				if (entity != null) {
					return EntityUtils.toByteArray(entity);
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			client.getConnectionManager().shutdown();
		}
		return new byte[0];
	}

	/**
	 * 获取首页的全部推广的图集
	 */
	private static void getAllImgsOfPconlineHomePage() throws Exception {
		Document doc = Jsoup.parse(getHtmlContent(baseUrl));
		Elements homepage_types = doc.select("div.pic-box");
		for (int h = 0; h < homepage_types.size(); h++) {
			Elements links = homepage_types.get(h).select("i.i-pic a");
			String type = homepage_types.get(h).select("div.mark a").html();
			//安照不同壁纸类别
			for (int j = 0; j < links.size(); j++) {
				beginGetImgsByAlbum(links.get(j).attr("href"), type);
			}
		}
	}

	/**
	 * 获取首页的下载排行的图集
	 */
	private static void getTopImgsOfPconlineHomePage() throws Exception {
		Document doc = Jsoup.parse(getHtmlContent(baseUrl));
		Elements top_types = doc.select("div.pic-list-a");
		for (int h = 0; h < top_types.size(); h++) {
			Elements type_items = new Elements();
			Elements links_top_1 = top_types.get(h).select("div.pf-con a");
			Elements links_top_2_10 = top_types.get(h).select("ul.u-list-a a");
			type_items.addAll(links_top_1);
			type_items.addAll(links_top_2_10);
			String type = top_types.get(h).getElementsByTag("strong").html();
			//安照不同壁纸类别排行，如动物壁纸排行
			for (int j = 0; j < type_items.size(); j++) {
				beginGetImgsByAlbum(type_items.get(j).attr("href"), type);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		//		beginGetImgsByAlbum("http://wallpaper.pconline.com.cn/pic/17536.html");
		getAllImgsOfPconlineHomePage();
		getTopImgsOfPconlineHomePage();
		executor.shutdown();
	}
}
package testGetImgFromBaidu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

/**
 * 需要的主要jar包 httpclient-4.0.1jar jsoup-1.5.2.jar
 * 
 * @author caixl
 * 
 */
public class testGetImgFromBaidu {

	private static final Log log = LogFactory.getLog(testGetImgFromBaidu.class);

	/**
	 * 抓取图片存放目录
	 */
	private static final String PIC_DIR = "c:/temp";
	
	private static final String encoding = "gb2312";

	/**
	 * 链接超时
	 */
	private static final int TIME_OUT = 5000;

	/**
	 * 传入url获得html内容
	 * 
	 * @param url
	 */
	public static void getHtmlContent(String url) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);
		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				TIME_OUT);
		try {
			HttpResponse response = client.execute(get);
			InputStreamReader iStreamReader = new InputStreamReader(
					new BufferedInputStream(response.getEntity().getContent()),encoding);
			StringBuilder sBuffer = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(iStreamReader);
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
			System.out.println(sBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

	public static void beginGetImgs(String url, final String directory)
			throws Exception {
		Connection conn = Jsoup.connect(url);
		Document doc = conn.get();
		Elements links = doc.select("script");
		for (int i = 0; i < links.size(); i++) {
			Element element = links.get(i);
			String htmlString = element.html();
			if (htmlString.indexOf("var imgdata") != -1) {

				System.out.println(htmlString);
				htmlString = htmlString.substring(0, htmlString
						.lastIndexOf("var isRightData"));
				System.out.println(htmlString);

				Context cx = Context.enter();
				Scriptable scope = cx.initStandardObjects();
				cx.evaluateString(scope, htmlString, "htmlString", 1, null);
				Scriptable imgdata = (Scriptable) scope.get("imgdata", scope);
				NativeArray scriptableArray = (NativeArray) imgdata.get("data",
						scope);
				for (int j = 0; j < scriptableArray.getLength(); j++) {
					Scriptable scriptable = (Scriptable) (((Scriptable) (scriptableArray
							.get(j, scope))));
					final String imgSrcString = scriptable.get("objURL", scope)
							.toString().trim();
					final String savefileName = j
							+ imgSrcString.substring(imgSrcString
									.lastIndexOf("."));
					Thread.sleep(500);
					new Thread(new Runnable() {
						public void run() {
							System.out.println(imgSrcString);
							try {
								saveImg(imgSrcString, savefileName, directory);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			}

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
		String[] words = { "何静","周韦彤" };
		for (String word : words) {
//			http://image.baidu.com/i?tn=baiduimage&ct=201326592&lm=-1&cl=2&width=&height=&pn=0&word=%BA%CE%BE%B2#z=3 
			beginGetImgs(
					"http://image.baidu.com/i?tn=baiduimage&ct=201326592&lm=-1&cl=2&width=&height=&pn=0&word="
							+ URLEncoder.encode(word) + "#z=3", word);
		}

	}
}
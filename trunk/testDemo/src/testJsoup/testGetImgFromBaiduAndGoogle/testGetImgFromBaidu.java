package testJsoup.testGetImgFromBaiduAndGoogle;

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
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

/**
 * 点击大图，发以下两个异步请求(都是返回json)，第一个请求加载30个，第二个请求加载60个，应该是这样的，如果第一个请求数量不足30，就不发第二个请求了。
 * http://image.baidu.com/i?tn=baiduimagejson&ct=201326592&lm=-1&cl=2&word=%D6%DC%CE%A4%CD%AE&z=3&width=&height=&pn=0&rn=30&1235511889240.0015
 * http://image.baidu.com/i?tn=baiduimagejson&ct=201326592&lm=-1&cl=2&word=%D6%DC%CE%A4%CD%AE&z=3&width=&height=&pn=30&rn=60&595510651577.3099
 * 参数：
 * z： 尺寸
 * word： 关键字 
 * pn ：开始位置
 * rn ： 加载数量
 * width :  精确尺寸时用到
 * height : 精确尺寸时用到
 * ic : 颜色
 * 
 * 
 */
/**
 * 需要的主要jar包 httpclient-4.0.1jar rhino.jar
 * 
 * @author caixl
 * 
 */
public class testGetImgFromBaidu {

	private static final Log log = LogFactory.getLog(testGetImgFromBaidu.class);

	/**
	 * 抓取图片存放目录
	 */
	private static final String PIC_DIR = "d:/temp";

	private static final String encoding = "gb2312";

	private static final String huge = "9"; // 特大尺寸
	private static final String big = "3"; // 大尺寸
	private static final String middle = "2"; // 中尺寸
	private static final String small = "1"; // 小尺寸

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
					encoding);

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
		return sBuffer.toString();
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

				Context cx = Context.enter();
				Scriptable scope = cx.initStandardObjects();
				cx.evaluateString(scope, htmlString, "htmlString", 1, null);
				Scriptable imgdata = (Scriptable) scope.get("imgdata", scope);
				NativeArray scriptableArray = (NativeArray) imgdata.get("data",
						scope);
				for (int j = 0; j < scriptableArray.getLength(); j++) {
					Scriptable scriptable = (Scriptable) (scriptableArray.get(
							j, scope));
					if (!scriptable.has("objURL", scope)) {
						continue;
					}
					final String imgSrcString = scriptable.get("objURL", scope)
							.toString().trim();
					final String savefileName = j
							+ imgSrcString.substring(imgSrcString
									.lastIndexOf("."));
					Thread.sleep(500);
					new Thread(new Runnable() {
						public void run() {
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
	 * 用此方法
	 */

	public static void finalBeginGetImgs(String url, final String directory)
			throws Exception {
		String htmlString = "var imgdata = " + getHtmlContent(url);

		System.out.println(htmlString);

		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();
		cx.evaluateString(scope, htmlString, "htmlString", 1, null);
		Scriptable imgdata = (Scriptable) scope.get("imgdata", scope);
		NativeArray scriptableArray = (NativeArray) imgdata.get("data", scope);
		for (int j = 0; j < scriptableArray.getLength(); j++) {
			Scriptable scriptable = (Scriptable) (scriptableArray.get(j, scope));
			if (!scriptable.has("objURL", scope)) {
				continue;
			}
			final String imgSrcString = scriptable.get("objURL", scope)
					.toString().trim();
			final String savefileName = j
					+ imgSrcString.substring(imgSrcString.lastIndexOf("."));
			Thread.sleep(500);
			new Thread(new Runnable() {
				public void run() {
					try {
						saveImg(imgSrcString, savefileName, directory);
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
		String[] words = { "何静","张馨予","周韦彤" };
		for (String word : words) {
			final String tempword = word;
			new Thread(new Runnable() {
				public void run() {
					try {
						finalBeginGetImgs(genRequestUrl("0", "20", tempword, big), tempword);
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
	 * @param pn
	 *            开始位置
	 * @param rn
	 *            加载数目
	 * @param word
	 *            关键字
	 * @param size
	 *            尺寸
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String genRequestUrl(String pn, String rn, String word,
			String size) throws UnsupportedEncodingException {
		return "http://image.baidu.com/i?tn=baiduimagejson&ct=201326592&lm=-1&cl=2&width=&height=&pn="
				+ pn
				+ "&rn="
				+ rn
				+ "&word="
				+ URLEncoder.encode(word,encoding)
				+ "&z="
				+ size;

	}
}
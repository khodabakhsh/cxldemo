package com.cxl;

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

public class GetImgFromZol {

	/**
	 * 抓取图片存放目录
	 */
	private static final String PIC_DIR = "d:/zol壁纸";

	private static final String gb2312 = "gb2312";
	private static final String utf8 = "utf-8";
	private static final String gbk = "GBK";

	/**
	 * 链接超时
	 */
	private static final int TIME_OUT = 5000;

	private static ExecutorService executor = Executors.newFixedThreadPool(200);
	private static final String baseUrl = "http://desk.zol.com.cn";

	private static final String[] resolutions = { "1680x1050", "1280x800", "1024x768" };

	/**
	 * 传入url获得html内容
	 * @throws Exception
	 */
	public static String getHtmlContent(String url) throws Exception {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);

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
		} catch (Exception e) {
			System.err.println("重新 尝试url --->  " + url);
			return getHtmlContent(url);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
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
				bit = null;
				if (out != null)
					out.close();
			}
			System.out.println("成功创建文件【" + diretoryPathString + fileName + "】" + "，源路径：【" + url + "】");
		}
	}

	/**
	 * 获取图片字节流
	 */
	static byte[] getByte(String url) throws IOException {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);
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
			System.err.println("重新 尝试获取图片字节流 , url--->  " + url);
			return getByte(url);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return new byte[0];
	}

	/**
	 * 获取某一分辨率、某一类型的分页数
	 */
	private static String getPageNum(String url) throws Exception {
		Document doc = Jsoup.parse(getHtmlContent(url));
		// “下一页”按钮
		Element pageNext = doc.getElementById("pageNext");
		// 1.如果没有“下一页”按钮，说明已经到了最后一页了，即为所要的页数
		if (pageNext == null) {
			try {
				return doc.select("div.page span.active").get(0).html();
			} catch (IndexOutOfBoundsException e) {
				// 抛异常，说明没有分页，即只有1页
				return "1";
			}
		} else {
			// 2.如果有“下一页”按钮，就继续遍历当前页的下一页
			Element element = doc.select("div.page span.active").get(0).nextElementSibling();
			return getPageNum(baseUrl + element.attr("href"));
		}
	}

	/**
	 * 获取选了某一分辨率、某一类型的所有图片（包含所有分页专辑）
	 */
	private static void getAllImgsByTypeAndResolution(String url, String preBaseDirectory, String albumTitle)
			throws Exception {
		Document doc = Jsoup.parse(getHtmlContent(url));
		// 找到<title></title>标题
		String headTitle = doc.getElementsByTag("title").get(0).html();
		headTitle = headTitle.substring(0, headTitle.lastIndexOf("】") + 1);
		// 优先使用自定义的标题名
		headTitle = "".equals(albumTitle) ? headTitle : albumTitle;
		// 找到页数
		int pageNum = 0;
		try {
			pageNum = Integer.parseInt(getPageNum(url));
		} catch (Exception e) {
			System.err.println("获取类型：【" + headTitle + "】分页数发生异常 ， url: " + url);
			return;
		}
		// 因其分页命名规律如：
		// 第1页：http://desk.zol.com.cn/chemo/1280x1024/
		// 第2页：http://desk.zol.com.cn/chemo/1280x1024/2.html
		// 第3页：http://desk.zol.com.cn/chemo/1280x1024/3.html
		// 循环每一页
		for (int k = 1; k <= pageNum; k++) {
			String eachPagePredictory = headTitle + "_共" + pageNum + "页" + "/" + headTitle + "_第" + k + "页";
			if (!"".equals(preBaseDirectory)) {
				eachPagePredictory = preBaseDirectory + "/" + eachPagePredictory;
			}
			String eachPageUrl = url + (k == 1 ? "" : k + ".html");
			Document eachPageDoc = Jsoup.parse(getHtmlContent(eachPageUrl));
			Elements links = eachPageDoc.select("li.photo-list-padding >a");
			final int linksSize = links.size();
			for (int i = 0; i < links.size(); i++) {
				Element element = links.get(i);
				final String fullImgHref = element.attr("href");
				beginGetImgsByAlbum(baseUrl + fullImgHref, eachPagePredictory);
			}
		}
	}

	/**
	 * 获取指定url对应图片专辑下的所有图片
	 */
	public static void beginGetImgsByAlbum(String url, final String preDirectory) throws Exception {
		Document doc = Jsoup.parse(getHtmlContent(url));
		Elements links = doc.select("li[id~=img] > a");
		final String title = doc.getElementsByTag("title").get(0).html();
		final int linksSize = links.size();
		for (int i = 0; i < links.size(); i++) {
			final String fileName = String.valueOf(i + 1);
			Element element = links.get(i);
			final String fullImgHref = element.attr("href");
			final String directory = preDirectory + ("".equals(preDirectory) ? "" : "/") + title + "_" + linksSize;
			// Thread.sleep(300);
			executor.execute((new Runnable() {
				public void run() {
					try {
						getWantedImg(fullImgHref, String.valueOf(linksSize) + "_" + fileName, directory);
					} catch (Exception e) {
						try {
							System.err.println("重新尝试下载IMG  --->  " + fullImgHref);
							getWantedImg(fullImgHref, String.valueOf(linksSize) + "_" + fileName, directory);
						} catch (Exception e1) {
							e1.printStackTrace();
							System.err.println("重新尝试【失败】 " + fullImgHref);
						}
					}
				}
			}));
		}
	}

	/**
	 * 获得对应分辨率的图片
	 */
	public static void getWantedImg(String url, String saveFileName, String saveDirectory) throws Exception {
		Document doc = Jsoup.parse(getHtmlContent(baseUrl + url));
		Elements links = null;
		for (int i = 0; i < resolutions.length; i++) {
			links = doc.select("a[id=" + resolutions[i] + "]");
			if (links.size() > 0) {
				break;
			}
		}
		if (links.size() == 0) {
			System.err.println("不存在1280x1024、1280x800、1024x768的图片" + "url : " + url);
			return;
		}
		try {
			Document doc_1028_1024 = Jsoup.parse(getHtmlContent(baseUrl + links.get(0).attr("href")));
			Elements links_1028_1024 = doc_1028_1024.getElementsByTag("img");
			String src = links_1028_1024.attr("src");
			System.out.println("保存图片："+src);
			saveImg(links_1028_1024.attr("src"), saveFileName + src.substring(src.lastIndexOf(".")), saveDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取选了某一分辨率下所有类型的所有图片（包含所有类型的所有分页专辑）
	 */
	private static void getAllImgsByResolution(String url, String preBaseDirectory) throws Exception {
		Document eachPageDoc = Jsoup.parse(getHtmlContent(url));
		Elements links = eachPageDoc.select("dd.brand-sel-box").get(0).select("a[href]");
		// 遍历某一分辨率下所有类型
		for (int j = 0; j < links.size(); j++) {
			Element link = links.get(j);
			getAllImgsByTypeAndResolution(baseUrl + link.attr("href"), preBaseDirectory, link.html());
		}
	}

	public static void main(String[] args) throws Exception {
		getAllImgsByResolution("http://desk.zol.com.cn/1280x1024/", "【1280x1024高清壁纸下载】");
		executor.shutdown();
	}
}
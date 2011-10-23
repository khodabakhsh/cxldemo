package com.cxl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 中草药查询
 * 
 */
public class HerbUtil {

	public static String getHerbDetailUrl = "http://zhongcaoyao.supfree.net/eat.asp?id=4617";//
	public static String getHerbUrl = "http://zhongcaoyao.supfree.net/search.asp";//
	public static String Referer = "http://zhongcaoyao.supfree.net/search.asp";
	public static String Host = "zhongcaoyao.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";
	/**
	 * 链接超时
	 */
	private static final int TIME_OUT = 5000;

	private String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getHerbDetailUrl);
		for (Map.Entry<String, String> entry : queryParams.entrySet()) {
			if (returnString.lastIndexOf("?") == -1) {
				returnString.append("?" + entry.getKey() + "="
						+ entry.getValue());
			} else {
				returnString.append("&" + entry.getKey() + "="
						+ entry.getValue());
			}
		}
		return returnString.toString();
	}

//	private Document getDocument(String url) {
//		try {
//			// return addHeader(Jsoup.connect(url).timeout(5000)).get();
//			return Jsoup
//					.connect(url)
//					.header("Referer", Referer)
//					.header("Host", Host)
//					.header("User-Agent",
//							"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
//					.header("Accept", "text ml,application/xhtml+xml")
//					.header("Accept-Language", "zh-cn,zh;q=0.5")
//					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
//					.header("Connection", "keep-alive")
//					.header("Cache-Control", "max-age=0")
//					.header("Accept-Encoding", "gzip, deflate").get();
//		} catch (IOException e) {
//
//			e.printStackTrace();
//			return null;
//		}
//	}

	private Document getDocumentByPost(String url,
			Map<String, String> queryParams) {
		try {
			// return addHeader(Jsoup.connect(url).timeout(5000)).get();
			return Jsoup
					.connect(url)
					.header("Referer", Referer)
					.header("Host", Host)
					.header("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.header("Accept-Language", "zh-cn,zh;q=0.5")
					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
					.header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0")
					.header("Accept-Encoding", "gzip, deflate")
					.userAgent("Mozilla")
 .timeout(3000)
					.data("B1","%B2%E9%D1%AF")
					.data("title", "%DC%F2%DC%DF")
//					?title=%DC%F2%DC%DF&B1=%B2%E9%D1%AF
					.post();
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}
	public String getDocumentContentByPost(String url,
			Map<String, String> queryParams) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpPost post = new HttpPost(url);
		post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				TIME_OUT);
		
        List<NameValuePair> nvps=new ArrayList<NameValuePair>();  
//需要通过POST提交的参数  
		for (Map.Entry<String, String> entry : queryParams.entrySet()){
			 nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
		}
        try {
			post.setEntity(new UrlEncodedFormEntity(nvps,GB2312));
		} catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}  
        
		StringBuilder sBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		InputStreamReader iStreamReader = null;
		try {
			HttpResponse response = client.execute(post);
			iStreamReader = new InputStreamReader(new BufferedInputStream(
					response.getEntity().getContent()), GB2312);

			bufferedReader = new BufferedReader(iStreamReader);
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
			System.out.println(sBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				iStreamReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
	}

//	public String getConstellationDetail(String id, String day) {
//		Map<String, String> queryParams = new HashMap<String, String>();
//		queryParams.put("id", id);
//		queryParams.put("day", day);
//		Document doc;
//		try {
//			doc = getDocument(addQueryParams(queryParams));
//			Elements element = doc.getElementsByClass("entry");
//			// System.out.println(element.get(0).outerHtml());
//			return replaceLuckyNumber(element.get(0).outerHtml());
//		} catch (Exception e) {
//			e.printStackTrace();
//			doc = getDocument(addQueryParams(queryParams));
//			Elements element = doc.getElementsByClass("entry");
//			// System.out.println(element.get(0).outerHtml());
//			return replaceLuckyNumber(element.get(0).outerHtml());
//			// return "";
//		}
//	}

	public List<String> getHerb(String content) {
		List<String> returnList = new ArrayList<String>();
		
		Document doc;
		try {
			doc = Jsoup.parse(content);
			Elements elements = doc.select("a[href^=eat.asp?id=]");
			for (Element element : elements) {
				returnList.add(element.attr("href").substring(
						"eat.asp?id=".length()));
			}
			return returnList;
		} catch (Exception e) {
			e.printStackTrace();
			// return replaceLuckyNumber(element.get(0).outerHtml());
			return returnList;
		}

	}

	public String replaceLuckyNumber(String orign) {
		return orign.replace("<img src=\"images/h_0.gif\" />", "0")
				.replace("<img src=\"images/h_1.gif\" />", "1")
				.replace("<img src=\"images/h_2.gif\" />", "2")
				.replace("<img src=\"images/h_3.gif\" />", "3")
				.replace("<img src=\"images/h_4.gif\" />", "4")
				.replace("<img src=\"images/h_5.gif\" />", "5")
				.replace("<img src=\"images/h_6.gif\" />", "6")
				.replace("<img src=\"images/h_7.gif\" />", "7")
				.replace("<img src=\"images/h_7.gif\" />", "7")
				.replace("<img src=\"images/h_8.gif\" />", "8")
				.replace("<img src=\"images/h_9.gif\" />", "9");

	}

	public List<Herb> GetConstellationList() {
		List<Herb> returnList = new ArrayList<Herb>();
		returnList.add(new Herb("1", "白羊座", "3.21-4.20", ""));
		returnList.add(new Herb("2", "金牛座", "4.21-5.20", ""));
		returnList.add(new Herb("3", "双子座", "5.21-6.21", ""));
		returnList.add(new Herb("4", "巨蟹座", "6.22-7.22", ""));
		returnList.add(new Herb("5", "狮子座", "7.23-8.22", ""));
		returnList.add(new Herb("6", "处女座", "8.23-9.22", ""));
		returnList.add(new Herb("7", "天秤座", "9.23-10.22", ""));
		returnList.add(new Herb("8", "天蝎座", "10.23-11.21", ""));
		returnList.add(new Herb("9", "射手座", "11.22-12.21", ""));
		returnList.add(new Herb("10", "摩羯座", "12.22-1.19", ""));
		returnList.add(new Herb("11", "水瓶座", "1.20-2.19", ""));
		returnList.add(new Herb("12", "双鱼座", "2.20-3.20", ""));
		return returnList;
	}

	public static void main(String[] args) {
		// for (Constellation constellation : GetConstellation
		// .GetConstellationList()) {
		// System.out.println(constellation + ";\n");
		// }
		// System.out.println(GetConstellation.GetConstellationList());
		// System.out.println(GetConstellation.getConstellationDetail("12",
		// "20111022"));
		Map<String, String> queryParams = new HashMap<String, String>();
		try {
			queryParams.put("B1", URLEncoder.encode("查询", GB2312));
			queryParams.put("title", URLEncoder.encode("茯苓", GB2312));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HerbUtil herbUtil = new HerbUtil();
		System.out.println(herbUtil.getHerb(herbUtil.getDocumentContentByPost(getHerbUrl, queryParams)));
	}

}
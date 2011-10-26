package com.cxl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 生日密码查询
 * 
 */
public class ConstellationPairUtil {

	public static String getBirthSecretDetailUrl = "http://birth.supfree.net/wocao.asp";//
	public static String Referer = "http://birth.supfree.net/";
	public static String Host = "birth.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";

	private String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getBirthSecretDetailUrl);
		for (Map.Entry<String, String> entry : queryParams.entrySet())
			if (returnString.lastIndexOf("?") == -1) {
				returnString.append("?" + entry.getKey() + "=" + entry.getValue());
			} else {
				returnString.append("&" + entry.getKey() + "=" + entry.getValue());
			}
		return returnString.toString();
	}

	private Document getDocument(String url) {
		try {
			// return addHeader(Jsoup.connect(url).timeout(5000)).get();
			return Jsoup.connect(url).header("Referer", Referer).header("Host", Host)
					.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text ml,application/xhtml+xml").header("Accept-Language", "zh-cn,zh;q=0.5")
					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7").header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0").header("Accept-Encoding", "gzip, deflate").get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getConstellationPairDetail(String yue, String ri) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("yue", yue);
		queryParams.put("ri", ri);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			element.get(0).select("p").last().remove();
			return element.get(0).outerHtml();
		} catch (Exception e) {
			return getConstellationPairDetail(yue, ri);
		}
	}

	public static void main(String[] args) {
		System.out.println(new ConstellationPairUtil().getConstellationPairDetail("3", "6"));
		;
		//		System.out.println(new ConstellationPairUtil().getConstellationPair());;

	}

}
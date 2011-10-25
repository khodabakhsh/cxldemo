package com.cxl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 星座配对
 * 
 */
public class ConstellationPairUtil {

	public static String getConstellationPairDetailUrl = "http://xingpei.supfree.net/qot.asp";//
	public static String getConstellationPairUrl = "http://xingpei.supfree.net/";//
	public static String Referer = "http://www.supfree.net/";
	public static String Host = "xingpei.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";

	private String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getConstellationPairDetailUrl);
		for (Map.Entry<String, String> entry : queryParams.entrySet())
			if (returnString.lastIndexOf("?") == -1) {
				returnString.append("?" + entry.getKey() + "="
						+ entry.getValue());
			} else {
				returnString.append("&" + entry.getKey() + "="
						+ entry.getValue());
			}
		return returnString.toString();
	}

	private Document getDocument(String url) {
		try {
			// return addHeader(Jsoup.connect(url).timeout(5000)).get();
			return Jsoup
					.connect(url)
					.header("Referer", Referer)
					.header("Host", Host)
					.header("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text ml,application/xhtml+xml")
					.header("Accept-Language", "zh-cn,zh;q=0.5")
					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
					.header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0")
					.header("Accept-Encoding", "gzip, deflate").get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String,String>  getConstellationPair() {
		Map<String,String> map = new HashMap<String, String>();
		Document document = getDocument(getConstellationPairUrl);
		Elements elements  = document.select("a[href^=qot.asp?id=]");
		for(Element element :elements){
			map.put(element.text(),element.attr("href").substring("qot.asp?id=".length()));
		}
		return map;
	}
	
	public String getConstellationPairDetail(String id) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("id", id);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			return element.get(0).outerHtml();
		} catch (Exception e) {
			return getConstellationPairDetail( id);
		}
	}


	public static void main(String[] args) {
//		System.out.println(new ConstellationPairUtil().getConstellationPairDetail("hu"));;
		System.out.println(new ConstellationPairUtil().getConstellationPair());;
		
	}

}
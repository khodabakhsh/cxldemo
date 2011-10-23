package com.cxl;

import com.cxl.constellation.R;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 每日星座运势
 * 
 */
public class ConstellationUtil {

	public static String getConstellationDetailUrl = "http://xingzuo.supfree.net/pie.asp";//
	public static String Referer = "http://www.supfree.net/";
	public static String Host = "xingzuo.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";

	private String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getConstellationDetailUrl);
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

	private Connection addHeader(Connection connection) {
		return connection
				.header("Referer", Referer)
				.header("Host", Host)
				.header("User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
				.header("Accept", "text ml,application/xhtml+xml")
				.header("Accept-Language", "zh-cn,zh;q=0.5")
				.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
				.header("Connection", "keep-alive")
				.header("Cache-Control", "max-age=0")
				.header("Accept-Encoding", "gzip, deflate");
	}

	public String getConstellationDetail(String id, String day) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("id", id);
		queryParams.put("day", day);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			// System.out.println(element.get(0).outerHtml());
			return replaceLuckyNumber(element.get(0).outerHtml());
		} catch (Exception e) {
			e.printStackTrace();
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			// System.out.println(element.get(0).outerHtml());
			return replaceLuckyNumber(element.get(0).outerHtml());
			// return "";
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

	public List<Constellation> GetConstellationList() {
		List<Constellation> returnList = new ArrayList<Constellation>();
		returnList.add(new Constellation("1", "白羊座", "3.21-4.20", ""));
		returnList.add(new Constellation("2", "金牛座", "4.21-5.20", ""));
		returnList.add(new Constellation("3", "双子座", "5.21-6.21", ""));
		returnList.add(new Constellation("4", "巨蟹座", "6.22-7.22", ""));
		returnList.add(new Constellation("5", "狮子座", "7.23-8.22", ""));
		returnList.add(new Constellation("6", "处女座", "8.23-9.22", ""));
		returnList.add(new Constellation("7", "天秤座", "9.23-10.22", ""));
		returnList.add(new Constellation("8", "天蝎座", "10.23-11.21", ""));
		returnList.add(new Constellation("9", "射手座", "11.22-12.21", ""));
		returnList.add(new Constellation("10", "摩羯座", "12.22-1.19", ""));
		returnList.add(new Constellation("11", "水瓶座", "1.20-2.19", ""));
		returnList.add(new Constellation("12", "双鱼座", "2.20-3.20", ""));
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
	}

}
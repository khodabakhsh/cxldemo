package fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

	private static String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getConstellationDetailUrl);
		for (Map.Entry<String, String> entry : queryParams.entrySet())
			if (returnString.lastIndexOf("?") == -1) {
				returnString.append("?" + entry.getKey() + "=" + entry.getValue());
			} else {
				returnString.append("&" + entry.getKey() + "=" + entry.getValue());
			}
		return returnString.toString();
	}

	private static Document getDocument(String url) {
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

	private static Connection addHeader(Connection connection) {
		return connection.header("Referer", Referer).header("Host", Host)
				.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
				.header("Accept", "text ml,application/xhtml+xml").header("Accept-Language", "zh-cn,zh;q=0.5")
				.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7").header("Connection", "keep-alive")
				.header("Cache-Control", "max-age=0").header("Accept-Encoding", "gzip, deflate");
	}

	public static String getConstellationDetail(String id, String day) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("id", id);
		queryParams.put("day", day);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			element.get(0).select("script").remove();
			element.get(0).select("p").get(1).remove();
			return replaceLuckyNumber(element.get(0).outerHtml());
		} catch (Exception e) {
			e.printStackTrace();
			return getConstellationDetail(id, day);
		}

	}

	public static String replaceLuckyNumber(String orign) {
		return orign.replace("<img src=\"images/h_0.gif\" />", "0").replace("<img src=\"images/h_1.gif\" />", "1")
				.replace("<img src=\"images/h_2.gif\" />", "2").replace("<img src=\"images/h_3.gif\" />", "3")
				.replace("<img src=\"images/h_4.gif\" />", "4").replace("<img src=\"images/h_5.gif\" />", "5")
				.replace("<img src=\"images/h_6.gif\" />", "6").replace("<img src=\"images/h_7.gif\" />", "7")
				.replace("<img src=\"images/h_7.gif\" />", "7").replace("<img src=\"images/h_8.gif\" />", "8")
				.replace("<img src=\"images/h_9.gif\" />", "9");

	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String currentDay = new java.text.SimpleDateFormat("yyyyMMdd")
				.format(calendar.getTime());
		
		for (int i = 1; i <= 12; i++) {
				CommonUtil.WriteFile(CommonUtil.writeFileBasePath + "\\星座每日运势\\content_" + i+"_"+currentDay + ".txt", getConstellationDetail(String.valueOf(i), currentDay));
		
		}
	}

}
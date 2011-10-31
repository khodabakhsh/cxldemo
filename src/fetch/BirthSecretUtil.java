package fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 生日密码查询
 * 
 */
public class BirthSecretUtil {

	public static String getBirthSecretDetailUrl = "http://birth.supfree.net/wocao.asp";//
	public static String Referer = "http://birth.supfree.net/";
	public static String Host = "birth.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";

	private static Map<String, List<String>> initYueRi() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> dayListOf29 = new ArrayList<String>();
		for (int i = 1; i <= 29; i++) {
			dayListOf29.add(String.valueOf(i));
		}
		List<String> dayListOf30 = new ArrayList<String>();
		for (int i = 1; i <= 30; i++) {
			dayListOf30.add(String.valueOf(i));
		}
		List<String> dayListOf31 = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			dayListOf31.add(String.valueOf(i));
		}
		map.put("1", dayListOf31);
		map.put("2", dayListOf29);
		map.put("3", dayListOf31);
		map.put("4", dayListOf30);
		map.put("5", dayListOf31);
		map.put("6", dayListOf30);
		map.put("7", dayListOf31);
		map.put("8", dayListOf31);
		map.put("9", dayListOf30);
		map.put("10", dayListOf31);
		map.put("11", dayListOf30);
		map.put("12", dayListOf31);
		return map;
	}

	private static String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getBirthSecretDetailUrl);
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

	public static String getConstellationPairDetail(String yue, String ri) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("yue", yue);
		queryParams.put("ri", ri);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			element.get(0).select("p").last().remove();
			element.get(0).select("script").remove();
			return element.get(0).outerHtml();
		} catch (Exception e) {
			return getConstellationPairDetail(yue, ri);
		}
	}

	public static void writeFile(String yue, String ri, String content) {
		CommonUtil.WriteFile(CommonUtil.writeFileBasePath + "\\生日查询\\content_" + yue + "_" + ri + ".txt", content);
	}

	public static void main(String[] args) {
		Map<String, List<String>> map = initYueRi();
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			final String yue = entry.getKey();
			for (final String ri : entry.getValue()) {
				new Thread() {
					public void run() {
						writeFile(yue, ri, getConstellationPairDetail(yue, ri));
						System.out.println(" 【"+yue+","+ri+"】");
					};
				}.start();
			}
		}

	}

}
package com.cxl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 随机笑话
 * 
 */
public class RandomJokeUtil {

	public static String getRandomJokeDetailUrl = "http://www.faydo.com/xiaohua/le.asp?rd=1319630956794";//
	public static String Referer = "http://www.faydo.com/xiaohua/";
	public static String Host = "www.faydo.com";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";

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

	public String getRandomJokeDetail(int number) {
		Document doc;
		StringBuilder returnString = new StringBuilder();
		try {
			for (int i = 0; i <= number; i++) {
				doc = getDocument(getRandomJokeDetailUrl);
				returnString
						.append(doc
								.outerHtml()
								.replace(
										"<head></head>",
										"<head><style>.ics{display:none;} .lebiaoti{font-size:20px;color:#4169E1;}</style></head>")
								+ "<br /><br /><br />");
			}
			System.out.println(returnString.toString());
			return returnString.toString();
		} catch (Exception e) {
			return "<html><body>【连接网络出现错误】</body></html>";
		}

	}

	public static void main(String[] args) {
		System.out.println(new RandomJokeUtil().getRandomJokeDetail(5));
		;
		// System.out.println(new RandomJokeUtil().getConstellationPair());;

	}

}
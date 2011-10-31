package fetch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

	public static String getRandomJokeDetail(int number) {
		Document doc;
		StringBuilder returnString = new StringBuilder("");
		try {
			for (int i = 0; i <= number; i++) {
				doc = getDocument(getRandomJokeDetailUrl);
				returnString.append(doc.outerHtml().replace("<head></head>",
						"<head><style>.ics{display:none;} .lebiaoti{font-size:20px;color:#4169E1;}</style></head>")
						+ "<br /><br /><br />");
			}
			return returnString.toString();
		} catch (Exception e) {
			return getRandomJokeDetail(number);
		}

	}

	public static void main(String[] args) {
		for (int i = 1; i <= 200; i++) {
			final int index = i;
			new Thread() {
				public void run() {
					CommonUtil.WriteFile(CommonUtil.writeFileBasePath + "\\幽默笑话\\content_" + index + ".txt",
							RandomJokeUtil.getRandomJokeDetail(10));
					System.out.println(" 【" + index + "】");
				};
			}.start();
		}
	}

}
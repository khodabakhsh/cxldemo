package fetch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 名族风俗
 * 
 */
public class NationCustomUtil {

	public static String getDetailUrl = "http://fengsu.supfree.net/right.asp";//
	public static String Referer = "http://fengsu.supfree.net/";
	public static String Host = "fengsu.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";
	public static String GBK = "GBK";

	private static String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getDetailUrl);
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

	private static Document getDocument(String url) {
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

	public static String getDetail(String id) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("id", id);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			element.get(0).select("script").remove();
			// element.get(0).select("p").get(1).remove();
			return element.get(0).outerHtml();
		} catch (Exception e) {
			e.printStackTrace();
			return getDetail(id);
		}

	}

	public static void main(String[] args) {

		String nationString = "回族 羌族 景颇族 纳西族 赫哲族 藏族 怒族 门巴族 苗族 佤族 仫佬族 珞巴族 彝族 京族 布朗族 基诺族 壮族 汉族 撒拉族 维吾尔族 傣族 蒙古族 毛南族 哈萨克族 满族 朝鲜族 锡伯族 俄罗斯族 侗族 土家族 阿昌族 瑶族 哈尼族 普米族 塔吉克族 白族 布依族 仡佬族 塔塔尔族 黎族 傈僳族 德昂族 鄂温克族 畲族 高山族 保安族 鄂伦春族 水族 拉祜族 裕固族 乌孜别克族 土族 东乡族 独龙族 柯尔克孜族";
		String[] nationArray = nationString.split(" ");
		for (int i = 0; i < nationArray.length; i++) {
			try {
//				System.out.println(getConstellationDetail(URLEncoder.encode(
//						nationArray[i], GBK)));
				CommonUtil.WriteFile(CommonUtil.writeFileBasePath
						+ "\\名族风俗查询\\content_" + i + ".txt",
						getDetail(URLEncoder.encode(
								nationArray[i], GBK)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
package fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.istack.internal.FinalArrayList;

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
	
	public static Map<String, String> Name_Id_Map =  getConstellationPair() ;

	private static String addQueryParams(Map<String, String> queryParams) {
		StringBuffer returnString = new StringBuffer(getConstellationPairDetailUrl);
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

	public static Map<String, String> getConstellationPair() {
		Map<String, String> map = new HashMap<String, String>();
		Document document = getDocument(getConstellationPairUrl);
		Elements elements = document.select("a[href^=qot.asp?id=]");
		for (Element element : elements) {
			map.put(element.text(), element.attr("href").substring("qot.asp?id=".length()));
		}
		return map;
	}

	public static String getConstellationPairDetail(String id) {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("id", id);
		Document doc;
		try {
			doc = getDocument(addQueryParams(queryParams));
			Elements element = doc.getElementsByClass("entry");
			element.get(0).select("script").remove();
			return element.get(0).outerHtml();
		} catch (Exception e) {
			return getConstellationPairDetail(id);
		}
	}

	private static String[] getConstellationArray() {
		String[] array = new String[] { "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座" };
		return array;
	}
	private static List<String> getConstellationCombination() {
		List<String> returnList = new ArrayList<String>(144);
		String[] array  = getConstellationArray();
		for(String firstItem : array){
			for(String secondItem : array){
				returnList.add((firstItem+"和"+secondItem));
			}
		}
		return returnList;
	}

	public static void main(String[] args) {
		List<String> returnList= getConstellationCombination();
		for(final String combination : returnList){
			new Thread() {
				public void run() {
					CommonUtil.WriteFile(CommonUtil.writeFileBasePath + "\\星座配对\\content_" + Name_Id_Map.get(combination) + ".txt", getConstellationPairDetail(Name_Id_Map.get(combination)) );
					System.out.println(" 【" + combination + "】");
				};
			}.start();
		}
	}

}
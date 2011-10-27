package com.cxl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;

/**
 * 
 * 
 */
public class DictionaryUtil {

	public static String getDetailUrl = "http://dict.youdao.com/m/search?keyfrom=dict.mindex&q=";//
	public static String Referer = "http://dict.youdao.com/m";
	public static String Host = "dict.youdao.com";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";


	private static Document getDocument(String url, String keyword) {
		try {
			return Jsoup.connect(url+keyword).header("Referer", Referer).header("Host", Host)
					.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text ml,application/xhtml+xml").header("Accept-Language", "zh-cn,zh;q=0.5")
					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7").header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0").header("Accept-Encoding", "gzip, deflate")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDetail(String keyword) {
		Document doc;
		try {
			doc = getDocument(getDetailUrl, keyword);
			System.out.println(doc.outerHtml());
			
			doc.select("form").remove();
			 Elements allDivs = doc.select("div");
//			 allDivs.remove(1);
//			 allDivs.remove(0);
			 allDivs.first().remove();
			 allDivs.last().remove();
			 doc.getElementsByClass("title").remove();
			
//			Elements element = doc.getElementsByClass("entry");
//			element.get(0).select("p").remove();
//			
//			element.get(0).select("form").remove();
//			element.get(0).select("table").removeAttr("width");
			return doc.outerHtml();
		} catch (Exception e) {
			return "<html><body>【网络连接异常】</body></html>";
		}
	}

	public static void main(String[] args) {
		System.out.println(DictionaryUtil.getDetail("高兴"));
	}
}
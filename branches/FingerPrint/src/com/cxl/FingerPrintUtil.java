package com.cxl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 指纹
 * 
 */
public class FingerPrintUtil {

	public static String getFingerPrintDetailUrl = "http://zhiwen.supfree.net/index.asp";//
	public static String Referer = "http://zhiwen.supfree.net/index.asp";
	public static String Host = "zhiwen.supfree.net";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";

	private Document getDocument(String url, String wo1, String wo2, String wo3, String wo4, String wo5) {
		try {
			return Jsoup.connect(url).header("Referer", Referer).header("Host", Host)
					.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text ml,application/xhtml+xml").header("Accept-Language", "zh-cn,zh;q=0.5")
					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7").header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0").header("Accept-Encoding", "gzip, deflate").data("wo1", wo1)
					.data("wo2", wo2).data("wo3", wo3).data("wo4", wo4).data("wo5", wo5).post();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getFingerPrintDetail(String wo1, String wo2, String wo3, String wo4, String wo5) {
		Document doc;
		try {
			doc = getDocument(getFingerPrintDetailUrl, wo1, wo2, wo3, wo4, wo5);
			Elements element = doc.getElementsByClass("entry");
			element.get(0).select("p").remove();
			element.get(0).select("form").remove();
			element.get(0).select("table").removeAttr("width");
			return element.get(0).outerHtml();
		} catch (Exception e) {
			return getFingerPrintDetail(wo1, wo2, wo3, wo4, wo5);
		}
	}

	public static void main(String[] args) {
		System.out.println(new FingerPrintUtil().getFingerPrintDetail("0", "1", "1", "0", "1"));
	}
}
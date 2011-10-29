package com.cxl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
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
	private static final int TIME_OUT = 5000;
	
//	public String getDocumentContentByGet(String url) {
//		HttpClient client = new DefaultHttpClient();
//		client.getParams().setParameter(
//				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
//		HttpGet get = new HttpGet(url);
//		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
//				TIME_OUT);
//		
//        
//		StringBuilder sBuffer = new StringBuilder();
//		BufferedReader bufferedReader = null;
//		InputStreamReader iStreamReader = null;
//		try {
//			HttpResponse response = client.execute(get);
//			iStreamReader = new InputStreamReader(new BufferedInputStream(
//					response.getEntity().getContent()), GB2312);
//
//			bufferedReader = new BufferedReader(iStreamReader);
//			String strLine;
//			while ((strLine = bufferedReader.readLine()) != null) {
//				sBuffer.append(strLine + "\n");
//			}
//			System.out.println(sBuffer);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				iStreamReader.close();
//				bufferedReader.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			client.getConnectionManager().shutdown();
//		}
//		return sBuffer.toString();
//	}
	public static String getDocumentContentByPost(String url,
			Map<String, String> queryParams) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpPost post = new HttpPost(url);
		post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				TIME_OUT);
		
        List<NameValuePair> nvps=new ArrayList<NameValuePair>();  
//需要通过POST提交的参数  
		for (Map.Entry<String, String> entry : queryParams.entrySet()){
			 nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
		}
        try {
			post.setEntity(new UrlEncodedFormEntity(nvps,GB2312));
		} catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}  
        
		StringBuilder sBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		InputStreamReader iStreamReader = null;
		try {
			HttpResponse response = client.execute(post);
			iStreamReader = new InputStreamReader(new BufferedInputStream(
					response.getEntity().getContent()), GB2312);

			bufferedReader = new BufferedReader(iStreamReader);
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
			System.out.println(sBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				iStreamReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
	}
	private Document getDocumentByPost(String url,
			Map<String, String> queryParams) {
		try {
			// return addHeader(Jsoup.connect(url).timeout(5000)).get();
			return Jsoup
					.connect(url)
					.header("Referer", Referer)
					.header("Host", Host)
					.header("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.header("Accept-Language", "zh-cn,zh;q=0.5")
					.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
					.header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0")
					.header("Accept-Encoding", "gzip, deflate")
					.userAgent("Mozilla")
 .timeout(3000)
					.data("B1","%B2%E9%D1%AF")
					.data("title", "%DC%F2%DC%DF")
//					?title=%DC%F2%DC%DF&B1=%B2%E9%D1%AF
					.post();
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}
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

	public static String getFingerPrintDetail(String wo1, String wo2, String wo3, String wo4, String wo5) {
		Document doc;
		Map<String, String> queryParams = new HashMap<String,String>();
		queryParams.put("wo1",wo1);
		queryParams.put("wo2",wo2);
		queryParams.put("wo3",wo3);
		queryParams.put("wo4",wo4);
		queryParams.put("wo5",wo5);
		
		
		try {
			doc = Jsoup.parse(getDocumentContentByPost(getFingerPrintDetailUrl,queryParams));
//			doc = getDocument(getFingerPrintDetailUrl, wo1, wo2, wo3, wo4, wo5);
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
//		System.out.println(new FingerPrintUtil().getFingerPrintDetail("0", "1", "1", "0", "1"));
		String [] arrays = new String[]{"0","1"};
		StringBuilder result = new StringBuilder("");
		int i = 1;
		for(String wo1 : arrays){
			result.delete(0, result.length());
			for(String wo2 : arrays){
				for(String wo3 : arrays){
					for(String wo4 : arrays){
						for(String wo5 : arrays){
							System.out.println("【"+(i++)+"】"+result.append(wo1).append(wo2).append(wo3).append(wo4).append(wo5));
						}
					}
				}
			}
		}
	}
}
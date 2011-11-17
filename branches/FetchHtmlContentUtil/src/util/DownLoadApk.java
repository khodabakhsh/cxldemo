package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.sun.istack.internal.FinalArrayList;

import fetch.CommonUtil;

public class DownLoadApk {
	private static final int TIME_OUT = 5000;
	public static String host = "http://apk.gfan.com";
	public static String SaveFileBaseDir = "d:/自动下载apk/";
	//346
	public static String SteveJobs_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=732mBl0add0lWe7SOsog3VFuQG9lqOmQgV04dcF/Apv5Fg9C3CrTUnWCOzLY0rRIkAxjOK8LmlXmrKil0add0lDfekNJ6uluqrjnYJln/dwNJF5l0add0lQSCNoVglmVRJSyZxnQ==";
	public static String SteveJobs_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/14/191535_129d3374-9f28-4ed6-b090-76b137363478.apk";
	//1024
	public static String TangShi_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=oDqYDw6T5lJpK5BQKUESIDlBmhqiacJFfnRr2dX4kflBZpTy0pIJl0add0lHCGIKePyc/6pp3Ebo5fYzslpZXtoewk76Jqx0v/iXjrykpJf4B3cl0add0lj21KcRKaNYDw==";
	public static String TangShi_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/14/191508_23fdb297-5be5-4616-91ba-bb2ca472b141.apk";
	//255
	public static String Speak_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=Sq8oK2twRDRn54eBIzFLsKCyurfwhRHlFLVh3K84Fg4b4QkKIq4A4kOieOPSvT//aTJdSRM7yAk12IqGg5LuR/ajSGySZdxB/qrI8xreqp4H8KdhAMQYVA==";
	public static String Speak_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/14/191511_8b99c57e-71f8-4a53-9158-8f1db0c50c40.apk";

	//716
	public static String CarKnowledge_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=L3upL/1Mj/fYfEGWz322l0add0lpUbl0add0lWtttUJZX3qU82B2l0add0lKIbnl0add0lmHtRxS0S3v8eMJrgLDXHVnfKll0add0lPGImj4WJ8NaHJBDQadetpc3NRiAYPpqv5/GRTeCxIjpOWg==";
	public static String CarKnowledge_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/14/191517_bf8528c9-2ab9-4a1d-8e9e-8f913a007e23.apk";

	//
	public static String BrainFun_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=lwC5kqImNKv2gF6FerWrbb7uRtuvfYAgkzFt/EarT8Sd7eOCoGmTBE6KzHdjZnget1ZJIvZTSg2B7vKx9deTbyYI9HWdg1KUslqgx1AwHt9BOi0vAB9JLQ==";
	public static String BrainFun_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/16/193015_ee585987-8d4b-4a19-8cd9-d01f75ed64b5.apk";

	//
	public static String SeeAnybody_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=96apdVgEgtWN70k67Vbxqi3rbwDeU7aGbNfq92pPjwNgnGaQv1WCVZeLiEvl0add0l3l3tUMZW5oLpZuIu9sr0SVDQcUXezQJSECo9dW96Q0TFs6KsBVaPFStJRg==";
	public static String SeeAnybody_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/17/193100_c605e335-b90d-4553-801d-fa220efda393.apk";

	//
	public static String Car_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=neWJ/cOLEJVXLC0cVIdV9aJ3AbLpAx8lZucdCXMcgX/d45syZS3KOiLVH7xDXfJnyQv6VQNV/IVt9LAku4H6pS21oq3au10GRXXnUBvqnHl0add0lqF4V9MrAO/A==";
	public static String Car_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/10/190563_bc01cbcd-a2a2-4514-b964-1cc3e80e2dc5.apk";

	
	//
	public static String OralEnglish_ViewPageUrl = "/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=dZgql0add0lqzXHKzcEvBl0add0l84TRszvSFQhUOr3y7d0RQyHgxt1ol0add0lgZFa9Ut0IwXSTDn/vhiXbtbdkr8qIP4NJlBcqeG7kw8UKhq2hpWt3OMal0add0lC9YtPOEuqMj3S4FQ==";
	public static String OralEnglish_DownLoadUrl = "http://down.apk.gfan.com/asdf/Pfiles/2011/11/17/190279_4b5c5dc7-1e53-4558-9e00-4d51ef537548.apk";
	
	
			
	public static int countSteveJobs = 0;
	public static int countTangShi = 0;
	public static int countSpeak = 0;
	public static int CarKnowledge = 0;
	public static Map<String, Integer> countMap = new HashMap<String, Integer>();
	static {
		countMap.put("SteveJobs", 0);
		countMap.put("TangShi", 0);
		countMap.put("Speak", 0);
		countMap.put("CarKnowledge", 0);
	}

	public static void main(String[] args) {
		//				System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + SteveJobs_ViewPageUrl)));
		//		downLoadApk(SteveJobs_DownLoadUrl,"SteveJobs.apk");
		//				System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + TangShi_ViewPageUrl)));
		//		downLoadApk(TangShi_DownLoadUrl,"TangShi.apk");
		//				System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + Speak_ViewPageUrl)));
		//		downLoadApk(Speak_DownLoadUrl,"Speak.apk");
		//				System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + CarKnowledge_ViewPageUrl)));
		//		downLoadApk(CarKnowledge_DownLoadUrl,"CarKnowledge.apk");
		//				System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + BrainFun_ViewPageUrl)));
		//		downLoadApk(CarKnowledge_DownLoadUrl,"CarKnowledge.apk");
//		System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + SeeAnybody_ViewPageUrl)));
		
//		System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + Car_ViewPageUrl)));
//		System.out.println(getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + OralEnglish_ViewPageUrl)));
		

		//		long begin = System.currentTimeMillis();
				for (int i = 0; i < 20000; i++) {
					System.out.println(i+1);
					startThread();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		/**
		 * 因为是多线程，在下面统计执行时间可能不准确
		 */
		//		long end = System.currentTimeMillis();
		//		System.out.println("执行时间------>   "+(end-begin)/1000+" 秒");

	}

	private static void startThread() {
		new Thread(new Runnable() {
			public void run() {
				//				downLoadApk(downLoadUrl, saveFileDir, saveFileName);
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + SteveJobs_ViewPageUrl));
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + TangShi_ViewPageUrl));
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + Speak_ViewPageUrl));
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + CarKnowledge_ViewPageUrl));
				
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + BrainFun_ViewPageUrl));
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + SeeAnybody_ViewPageUrl));
				
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + Car_ViewPageUrl));
				getDownLoadUrl(CommonUtil.getDocumentContentByGet(host + OralEnglish_ViewPageUrl));
				
			}
		}).start();
	}


	public static String getDownLoadUrl(String html) {
		int begin = html.indexOf("<script>javascript:window.location='")
				+ "<script>javascript:window.location='".length();
		int end = html.indexOf("';</script></form>");
		return html.substring(begin, end);
	}

	public static void downLoadApk(String url, String saveFileDir, String saveFileName) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);
		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		BufferedReader bufferedReader = null;
		BufferedInputStream bufferedInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(SaveFileBaseDir + saveFileDir, saveFileName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fileOutputStream = new FileOutputStream(file);
			HttpResponse response = client.execute(get);
			bufferedInputStream = new BufferedInputStream(response.getEntity().getContent());
			int size = 1024 * 1024;
			byte[] read = new byte[size];
			int readNumber;
			while ((readNumber = bufferedInputStream.read(read)) != -1) {
				fileOutputStream.write(read, 0, readNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			downLoadApk(url, saveFileDir, saveFileName);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (bufferedInputStream != null)
					bufferedInputStream.close();
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		int count = countMap.get(saveFileDir).intValue();
		countMap.put(saveFileDir, ++count);
		System.out.println(saveFileDir + " , 下载数目 -----> " + count);

	}
}

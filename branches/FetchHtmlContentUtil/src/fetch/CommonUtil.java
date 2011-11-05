package fetch;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public class CommonUtil {
	private static final int TIME_OUT = 5000;
	public static String GB2312 = "GB2312";
	public static String writeFileBasePath = "d:\\抓取内容\\";

	public static void WriteFile(String filepath, String content) {
		File file = new File(filepath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(filepath, false);
			PrintWriter printwriter = new PrintWriter(filewriter);
			printwriter.println(content);
			printwriter.flush();
			printwriter.close();
			filewriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 写入多行

	}

	public static String getDocumentContentByPost(String url, Map<String, String> queryParams) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpPost post = new HttpPost(url);
		post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : queryParams.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps, GB2312));
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}

		StringBuilder sBuffer = new StringBuilder("");
		BufferedReader bufferedReader = null;
		InputStreamReader iStreamReader = null;
		try {
			HttpResponse response = client.execute(post);
			iStreamReader = new InputStreamReader(new BufferedInputStream(response.getEntity().getContent()), GB2312);

			bufferedReader = new BufferedReader(iStreamReader);
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
			// System.out.println(sBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (iStreamReader != null)
					iStreamReader.close();
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
	}

	public static String getDocumentContentByGet(String url) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpGet get = new HttpGet(url);
		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		StringBuilder sBuffer = new StringBuilder("");
		BufferedReader bufferedReader = null;
		InputStreamReader iStreamReader = null;
		try {
			HttpResponse response = client.execute(get);
			iStreamReader = new InputStreamReader(new BufferedInputStream(response.getEntity().getContent()), GB2312);

			bufferedReader = new BufferedReader(iStreamReader);
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
			// System.out.println(sBuffer);
		} catch (Exception e) {
			e.printStackTrace();
			return getDocumentContentByGet(url);
		} finally {
			try {
				if (iStreamReader != null)
					iStreamReader.close();
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
	}

}

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class FingerprintUtil
{
	public static String getFingerPrintDetailUrl = "http://zhiwen.supfree.net/index.asp";//
	private static final int TIME_OUT = 5000;
	public static String GB2312 = "GB2312";

	public static void main(String[] args)
	{
		try
		{
			Map<String, String> resultMap = fetchPageContent(fetchKey());
			// for (Map.Entry<String, String> entry : resultMap.entrySet())
			// {
			// WriteFile("d:\\FetchPageContentUtil\\" + entry.getKey()
			// + ".txt", filterContent(entry.getValue()));
			// }

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void WriteFile(String filepath, String content)
	{
		File file = new File(filepath);
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		FileWriter filewriter;
		try
		{
			filewriter = new FileWriter(filepath, false);
			PrintWriter printwriter = new PrintWriter(filewriter);
			printwriter.println(content);
			printwriter.flush();
			printwriter.close();
			filewriter.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 写入多行

	}

	public static String filterContent(String content)
	{
		Document doc;
		try
		{
			doc = Jsoup.parse(content);
			Elements element = doc.getElementsByClass("entry");
			element.get(0).select("p").remove();
			element.get(0).select("form").remove();
			element.get(0).select("table").removeAttr("width");
			return element.get(0).outerHtml();
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public static String getDocumentContentByPost(String url,
			Map<String, String> queryParams)
	{
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		HttpPost post = new HttpPost(url);
		post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				TIME_OUT);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : queryParams.entrySet())
		{
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try
		{
			post.setEntity(new UrlEncodedFormEntity(nvps, GB2312));
		} catch (UnsupportedEncodingException e1)
		{

			e1.printStackTrace();
		}

		StringBuilder sBuffer = new StringBuilder("");
		BufferedReader bufferedReader = null;
		InputStreamReader iStreamReader = null;
		try
		{
			HttpResponse response = client.execute(post);
			iStreamReader = new InputStreamReader(new BufferedInputStream(
					response.getEntity().getContent()), GB2312);

			bufferedReader = new BufferedReader(iStreamReader);
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null)
			{
				sBuffer.append(strLine + "\n");
			}
			// System.out.println(sBuffer);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				iStreamReader.close();
				bufferedReader.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
		}
		return sBuffer.toString();
	}

	public static Set<String> fetchKey()
	{
		String[] arrays = new String[] { "0", "1" };
		StringBuilder result = new StringBuilder("");
		Set<String> set = new HashSet<String>();
		int i = 1;
		for (String wo1 : arrays)
		{
			for (String wo2 : arrays)
			{
				for (String wo3 : arrays)
				{
					for (String wo4 : arrays)
					{
						for (String wo5 : arrays)
						{
							System.out.println("【"
									+ (i++)
									+ "】"
									+ result.append(wo1).append(wo2)
											.append(wo3).append(wo4)
											.append(wo5));
							set.add(result.toString());
							result.delete(0, result.length());
						}
					}
				}
			}
		}
		return set;
	}

	public static Map<String, String> fetchPageContent(Set<String> keySet)
	{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> queryParams = new HashMap<String, String>();
		int i = 1;
		for (String key : keySet)
		{
			try
			{
				System.out.println("启动第" + (i++) + "个");
				queryParams.put("wo1", String.valueOf(key.charAt(0)));
				queryParams.put("wo2", String.valueOf(key.charAt(1)));
				queryParams.put("wo3", String.valueOf(key.charAt(2)));
				queryParams.put("wo4", String.valueOf(key.charAt(3)));
				queryParams.put("wo5", String.valueOf(key.charAt(4)));
				String content = getDocumentContentByPost(
						getFingerPrintDetailUrl, queryParams);
				map.put(key, content);
				if (content != null && !"".equals(content))
				{
					WriteFile("d:\\FetchPageContentUtil\\【" + (i - 1) + "】"
							+ key + ".txt", filterContent(content));
				}

				Thread.sleep(5000);
			} catch (Exception e)
			{
				e.printStackTrace();
				System.err.println("【" + (i - 1) + "】" + key);
			}
		}
		return map;
	}
}

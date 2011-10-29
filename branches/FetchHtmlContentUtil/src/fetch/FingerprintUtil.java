package fetch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FingerprintUtil
{
	public static String getFingerPrintDetailUrl = "http://zhiwen.supfree.net/index.asp";//

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
				String content = CommonUtil.getDocumentContentByPost(
						getFingerPrintDetailUrl, queryParams);
				map.put(key, content);
				if (content != null && !"".equals(content))
				{
					CommonUtil.WriteFile("d:\\FetchPageContentUtil\\【"
							+ (i - 1) + "】" + key + ".txt",
							filterContent(content));
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

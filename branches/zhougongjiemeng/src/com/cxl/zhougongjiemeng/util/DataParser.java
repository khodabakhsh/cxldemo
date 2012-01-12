package com.cxl.zhougongjiemeng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class DataParser {

	private static Hashtable hs = new Hashtable();
	private Context context;

	@SuppressWarnings("unchecked")
	public DataParser(Context paramContext) {
		this.context = paramContext;
		hs.put("情爱", "qingai.a");
		hs.put("鬼神", "guishen.a");
		hs.put("建筑", "jianzhu.a");
		hs.put("动植物", "dongzhiwu.a");
		hs.put("人物", "renwu.a");
		hs.put("生活", "shenghuo.a");
		hs.put("身体", "shenti.a");
		hs.put("物品", "wupin.a");
		hs.put("自然", "ziran.a");
	}

	private String parser(String paramString1, String paramString2) {
		String divString = "<div name=";
		Pattern localPattern = Pattern.compile("<div name=" + paramString2 + ">.*?<----div--allove--->");
		String str2;
		try {
			str2 = inputStream2String(this.context.getAssets().open(paramString1));
			Matcher localMatcher = localPattern.matcher(str2);
			if (localMatcher.find()) {
				System.out.println("marther ....................");
				System.out.println(localMatcher.group());
				divString = localMatcher.group().replace("<----div--allove--->", "");
				String str4 = "<div name=" + paramString2 + ">";
				return divString.replace(str4, "");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	public String getResult(String paramString1, String paramString2) {
		String str = (String) hs.get(paramString1);
		return parser(str, paramString2);
	}

	public String inputStream2String(InputStream paramInputStream) throws IOException {
		InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream, "utf-8");
		BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
		String str = "";
		StringBuffer localStringBuffer = new StringBuffer();
		while (true) {
			str = localBufferedReader.readLine();
			if (str == null) {
				localBufferedReader.close();
				return localStringBuffer.toString();
			}
			localStringBuffer.append(str);
		}
	}
}
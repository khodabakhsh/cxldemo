package com.cxl.zhougongjiemeng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class IndexParser {
	private static IndexParser instance;
	private Context context;

	private Hashtable hs;

	private IndexParser(Context paramContext) {
		this.context = paramContext;
		get();
	}

	private void get() {
			InputStream localInputStream;
			try {
				localInputStream = this.context.getAssets().open("index.xml");
				this.hs = parser(inputStream2String(localInputStream));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static IndexParser getInstance(Context paramContext) {
		if (instance == null)
			instance = new IndexParser(paramContext);
		return instance;
	}


	private Hashtable parser(String paramString) {
		String empty = "";
		Pattern typePattern = Pattern.compile("<type.*?</type>");
		Pattern typeNamePattern = Pattern.compile("'.*?'");
		Hashtable returnHashtable = new Hashtable();
		Matcher typeMatcher = typePattern.matcher(paramString);
		while (typeMatcher.find()) {
			String str2 = typeMatcher.group();
			String typeName = "";
			Matcher typeNameMatcher = typeNamePattern.matcher(str2);
			if (typeNameMatcher.find())
				typeName = typeNameMatcher.group().replaceAll("'", empty);
			String typeDetail = str2.replaceAll("<type.*?>", empty).replace("</type>", empty).replaceAll("\t", empty);
			System.out.println(typeName);
			System.out.println(typeDetail);
			returnHashtable.put(typeName, typeDetail);
		}
		return returnHashtable;
	}

	public Hashtable getResult() {
		return this.hs;
	}

	public String inputStream2String(InputStream paramInputStream) throws IOException {
		InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream, "utf-8");
		BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
		String str = "";
		StringBuffer localStringBuffer = new StringBuffer();
		while (true) {
			str = localBufferedReader.readLine();
			if (str == null)
				return localStringBuffer.toString();
			localStringBuffer.append(str);
		}
	}
}
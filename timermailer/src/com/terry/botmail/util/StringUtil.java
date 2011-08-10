package com.terry.botmail.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xinghuo.yao Email: yaoxinghuo at 126 dot com
 * @version create: Jul 20, 2009 5:50:14 PM
 */
public class StringUtil {
	public static String unicodeEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}

	public static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	public static String HTMLToTEXT(String html) {
		// html=html.replaceAll("<([^<>]+)>","");
		// html=StringUtils.replace(html, "&nbsp;"," ");
		// html=StringUtils.replace(html, "&#160;"," ");
		// html=StringUtils.replace(html, "&lt;","<");
		// html=StringUtils.replace(html, "&gt;",">");
		// html=StringUtils.replace(html, "&quot;","\"");
		// html=StringUtils.replace(html, "&amp;","&");

		return html.replaceAll("<([^<>]+)>", "");

	}

	public static boolean isEmptyOrWhitespace(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		return false;
	}

	public static boolean isDigital(String s) {
		String pattern = "[0-9]+(.[0-9]+)?";
		// 对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
		// (.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public static boolean validateEmail(String email) {
		if (email == null)
			return false;
		Pattern p = Pattern
				.compile("^[\\w-]+(?:\\.[\\w-]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean validateUrl(String url) {
		if (url == null || url.equals(""))
			return false;
		// Pattern p = Pattern
		// .compile(
		// "(http|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?",
		// Pattern.CASE_INSENSITIVE);
		Pattern p = Pattern.compile(
				"(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w-   ./?%&=]*)?",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}

	public static boolean isChinaMobile(String s) {
		if (s == null)
			return false;

		String regMobileStr = "^1(([3][456789])|([5][0124789])|([8][78]))[0-9]{8}$";
		String regMobile3GStr = "^((157)|(18[78]))[0-9]{8}$";
		if (s.matches(regMobileStr) || s.matches(regMobile3GStr))
			return true;
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isChinaMobile("18912345678"));
	}
}

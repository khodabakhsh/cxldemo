package com.bincode.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理函数
 * @author bincode
 * @email	5235852@qq.com
 */
public class DateUtils {
	public static final String getDateTime(String aMask, Date aDate) {
		if (aDate == null) {
			return "";
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(aMask);
			return dateFormat.format(aDate);
		}
	}
}

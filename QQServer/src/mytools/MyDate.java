package mytools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * 这是一个产生各种日期或时间数据的工具类
 * @author Devon
 *
 */
public class MyDate {
	
	/**
	 * 指示所产生的时间格式为"yyyyMMdd"
	 */
	public static final int FORMAT_YMD = 1;
	
	/**
	 * 指示所产生的时间格式为"[yyyy-MM-dd HH:mm:ss]"
	 */
	public static final int FORMAT_YMDHMS = 2;
	
	/**
	 * "HH:mm:ss"
	 */
	public static final int FORMAT_HMS = 3;

	/**
	 * 产生关于年份的一个Vector<Integer>，主要是用于设置JComboBox中的选项
	 * @return		关于年份的一个Vector<Integer>
	 */
	public static Vector<Integer> year() {
		Vector<Integer> vector_Year = new Vector<Integer>();
		for (int i = 2010; i <= 2100; i++) {
			vector_Year.add(i);
		}

		return vector_Year;
	}

	/**
	 * 产生关于月份的一个Vector<Integer>，主要是用于设置JComboBox中的选项
	 * @return		关于月份的一个Vector<Integer>
	 */
	public static Vector<Integer> month() {
		Vector<Integer> vector_Month = new Vector<Integer>();
		for (int i = 1; i <= 12; i++) {
			vector_Month.add(i);
		}

		return vector_Month;
	}

	/**
	 * 产生关于属于大月月份中的天数的一个Vector<Integer>，主要是用于设置JComboBox中的选项
	 * @return		关于属于大月月份中的天数的一个Vector<Integer>，即31天
	 */
	public static Vector<Integer> dateOf31() {
		Vector<Integer> vector_Date = new Vector<Integer>();
		for (int i = 1; i <= 31; i++) {
			vector_Date.add(i);
		}

		return vector_Date;
	}

	/**
	 * 产生关于属于小月月份中的天数的一个Vector<Integer>，主要是用于设置JComboBox中的选项
	 * @return		关于属于小月月份中的天数的一个Vector<Integer>，即30天
	 */
	public static Vector<Integer> dateOf30() {
		Vector<Integer> vector_Date = new Vector<Integer>();
		for (int i = 1; i <= 30; i++) {
			vector_Date.add(i);
		}

		return vector_Date;
	}

	/**
	 * 产生关于属于闰年2月份中的天数的一个Vector<Integer>，主要是用于设置JComboBox中的选项
	 * @return		关于属于闰年2月份中的天数的一个Vector<Integer>，即29天
	 */
	public static Vector<Integer> dateOf29() {
		Vector<Integer> vector_Date = new Vector<Integer>();
		for (int i = 1; i <= 29; i++) {
			vector_Date.add(i);
		}

		return vector_Date;
	}

	/**
	 * 产生关于属于平年2月份中的天数的一个Vector<Integer>，主要是用于设置JComboBox中的选项
	 * @return		关于属于平年2月份中的天数的一个Vector<Integer>，即28天
	 */
	public static Vector<Integer> dateOf28() {
		Vector<Integer> vector_Date = new Vector<Integer>();
		for (int i = 1; i <= 28; i++) {
			vector_Date.add(i);
		}

		return vector_Date;
	}

	/**
	 * 判断是否为闰年
	 * @param year		要判断的年份
	 * @return		该年属闰年则返回true；否则返回false
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取得当前系统时间的Vector<Integer>。其中有3项内容，分别是年，月，日
	 * @return
	 */
	public static Vector<Integer> currentDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String strDate = simpleDateFormat.format(date);
		Vector<Integer> currentDate = new Vector<Integer>();
		String[] arrDate = strDate.split("-");
		for (String string : arrDate) {
			currentDate.add(Integer.parseInt(string));
		}
		
		return currentDate;
	}
	
	/**
	 * 取得当前系统时间的字符串表示形式。
	 * @param format	要定制的格式，具体参数可参考本类静态常量值
	 * @return		根据定制要求返回当前系统时间的字符串表示形式
	 */
	public static String dateFormat(int format){
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfYMDHMS = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
		SimpleDateFormat sdfHMS = new SimpleDateFormat("HH:mm:ss");
		
		Date date = new Date();
		String strYMD = sdfYMD.format(date);
		String strYMDHMS = sdfYMDHMS.format(date);
		String strHMS = sdfHMS.format(date);
		
		if(format == 1){
			return strYMD;
		}else if(format == 2){
			return strYMDHMS;
		}else if(format == 3){
			return strHMS;
		}
		
		return null;
	}

}

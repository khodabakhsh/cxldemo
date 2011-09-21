package cn.jcenterhome.taglib;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.jcenterhome.util.Common;
public class Function{
	public static String urlEncode(String s) {
		return Common.urlEncode(s);
	}
	public static String htmlSpecialChars1(String string) {
		return Common.htmlSpecialChars(string);
	}
	public static String htmlSpecialChars2(String text, int quotestyle) {
		return Common.htmlSpecialChars(text,quotestyle);
	}
	public static boolean in_array(Object source, Object ext) {
		return Common.in_array(source, ext);
	}
	public static String avatar1(Integer uid, Map<String, Object> sGlobal, Map<String, Object> sConfig) {
		return Common.avatar(uid, sGlobal, sConfig);
	}
	public static String avatar2(Integer uid, String sizeType, boolean returnUrl, Map<String, Object> sGlobal,Map<String, Object> sConfig) {
		return Common.avatar(uid, sizeType, returnUrl, sGlobal, sConfig);
	}
	public static boolean checkPerm(HttpServletRequest request, HttpServletResponse response, String permType) {
		return Common.checkPerm(request, response, permType);
	}
	public static int rand(int min, int max) {
		return Common.rand(min,max);
	}
	public static String sgmdate(HttpServletRequest request, String dateformat, int timestamp, boolean format) {
		return Common.sgmdate(request, dateformat, timestamp,format);
	}
}

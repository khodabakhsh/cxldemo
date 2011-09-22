package cn.jcenterhome.util;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;/** * 提供全局常量，如编码、版本号、数据库表名、web根目录实际磁盘地址 *  * @author caixl , Sep 22, 2011 * */
public final class JavaCenterHome {
	public static final boolean IN_JCHOME = true;
	public static final String JCH_CHARSET = "UTF-8";
	public static final String JCH_VERSION = "2.0";
	public static final int JCH_RELEASE = 20110324;	/**	 * web根目录实际磁盘地址	 */
	public static String jchRoot = null;
	public static Map<String, String> jchConfig = new HashMap<String, String>();
	private static Map<String, String> tableNames = new HashMap<String, String>();
	public static String getTableName(String name) {
		String tableName = tableNames.get(name);
		if (tableName == null) {
			tableName = jchConfig.get("tablePre") + name;
			tableNames.put(name, tableName);
		}
		return tableName;
	}
	public static void clearTableNames() {
		tableNames.clear();
	}
	public static void setJchRoot(HttpServletRequest request) {
		jchRoot = request.getSession().getServletContext().getRealPath("/");
		if (jchRoot == null) {
			try {
				jchRoot = request.getSession().getServletContext().getResource("/").getPath();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!jchRoot.endsWith("/"))jchRoot = jchRoot + "/";
	}
}
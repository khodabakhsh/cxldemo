package tool;


import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * 输出系统属性和环境变量
 * @author caixl
 */
public class SystemPropertyAndEnvironment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Properties
		System.err.println("系统属性");
		Properties properties = System.getProperties();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			System.out.println("【"+entry.getKey() + "】 : " + entry.getValue());
		}
		//environment
		System.err.println("环境变量");
		Map<String, String> envMap = System.getenv();
		for (Entry<String, String> entry : envMap.entrySet()) {
			System.out.println("【"+entry.getKey() + "】 : " + entry.getValue());
		}
	}

}

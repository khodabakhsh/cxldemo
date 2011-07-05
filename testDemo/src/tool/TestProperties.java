package tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * 1。使用java.util.Properties类的load()方法 示例： InputStream in = lnew
 * BufferedInputStream(new FileInputStream(name)); Properties p = new
 * Properties(); p.load(in);
 * 
 * 2。使用java.util.ResourceBundle类的getBundle()方法 示例： ResourceBundle rb =
 * ResourceBundle.getBundle(name, Locale.getDefault());
 * 
 * 3。使用java.util.PropertyResourceBundle类的构造函数 示例： InputStream in = new
 * BufferedInputStream(new FileInputStream(name)); ResourceBundle rb = new
 * PropertyResourceBundle(in);
 * 
 * 4。使用class变量的getResourceAsStream()方法 示例： InputStream in =
 * JProperties.class.getResourceAsStream(name); Properties p = new Properties();
 * p.load(in);
 * 
 * 5。使用class.getClassLoader()所得到的java.lang.ClassLoader的getResourceAsStream()方法
 * 示例： InputStream in =
 * JProperties.class.getClassLoader().getResourceAsStream(name); Properties p =
 * new Properties(); p.load(in);
 * 
 * 6。使用java.lang.ClassLoader类的getSystemResourceAsStream()静态方法 示例： InputStream in =
 * ClassLoader.getSystemResourceAsStream(name); Properties p = new Properties();
 * p.load(in);
 * 
 * 补充
 * 
 * Servlet中可以使用javax.servlet.ServletContext的getResourceAsStream()方法
 * 示例：InputStream in = context.getResourceAsStream(path); Properties p = new
 * Properties(); p.load(in);
 * 
 * @author caixl
 * 
 */
public class TestProperties {
	public static void main(String[] args) throws IOException {
		TestProperties loadProp = new TestProperties();
		InputStream in = loadProp.getClass().getResourceAsStream(
				"/" + TestProperties.class.getPackage().getName()
						+ "/TestProperties.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			in.close();
		}
		// loop entry
		for (Entry entry : prop.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.err.println("-------------------");
		// loop enumeration
		Enumeration enumeration = prop.elements();
		while (enumeration.hasMoreElements()) {
			String value = (String) enumeration.nextElement();
			System.out.println(value);
		}
		// getProperty
		prop.getProperty("age", "not found");
	}
}
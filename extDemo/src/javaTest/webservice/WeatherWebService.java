package javaTest.webservice;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 */
public class WeatherWebService {

	final static Logger log = Logger.getLogger(WeatherWebService.class);

	/**
	 * @TODO 获取天气预报
	 */

	@SuppressWarnings("unchecked")
	public static void getWeatherFromWS(String theCityName, int theDayFlag) {
		SAXReader reader = new SAXReader();
		try {

			Document doc = reader
					.read("http://www.ayandy.com/Service.asmx/getWeatherbyCityName?theCityName="
							+ EscapeUnescape.escape(theCityName)
							+ "&theDayFlag=" + theDayFlag);
			Element root = doc.getRootElement();
			List<Element> nodeList = root.elements("string");
			for (Element element : nodeList) {
				System.out.println(element.getStringValue());
			}
			log.debug("获取天气预报数据完成！");
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error("获取天气预报数据异常：" + e.getMessage());
		}
	}

	public static int switchWeatherGif(String weather) {
		if (weather.contains("晴")) {
			return 0;
		} else if (weather.contains("雨")) {
			return 3;
		} else if (weather.contains("云")) {
			return 1;
		} else if (weather.contains("阴")) {
			return 2;
		} else if (weather.contains("雪")) {
			return 13;
		} else if (weather.contains("雾")) {
			return 18;
		} else if (weather.contains("沙")) {
			return 20;
		} else if (weather.contains("浮尘")) {
			return 30;
		} else {
			return 2;
		}
	}

	public static void main(String[] args) {
		getWeatherFromWS("广州", 1);
	}

}

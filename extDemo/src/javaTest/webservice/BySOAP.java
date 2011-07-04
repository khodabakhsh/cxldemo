package javaTest.webservice;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 类作用调用webservice得到天气预报服务
 * 
 * @author qsw-Myonlystar 2010-1-13上午09:59:45
 * 
 * 用到了XPath，需要额外引入jaxen-1.1.1.jar包，否则程序会报org/jaxen/JaxenException的错误
 */
public class BySOAP {
	/**
	 * 获取soap请求头，并替换其中的标志符号为用户的输入符号
	 * 
	 * @param city
	 *            用户输入城市名
	 * @return 用户将要发送给服务器的soap请求
	 */
	private static String getSoapRequest(String city) {
		StringBuilder sb = new StringBuilder();
		sb
				.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
						+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
						+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
						+ "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+ "<soap:Body>	<getWeatherbyCityName xmlns=\"http://WebXml.com.cn/\">"
						+ "<theCityName>" + city
						+ "</theCityName>	</getWeatherbyCityName>"
						+ "</soap:Body></soap:Envelope>");
		return sb.toString();
	}

	/**
	 * 用户把SOAP请求发送给服务器端，并返回服务器点返回的输入流
	 * 
	 * @param city
	 *            用户输入的城市名称
	 * @return 服务器端返回的输入流，供客户端读取
	 * @throws Exception
	 */
	public static InputStream getSoapInputStream(String city) throws Exception {
		try {
			String soap = getSoapRequest(city);
			if (soap == null) {
				return null;
			}
			URL url = new URL(
					"http://www.webxml.com.cn/WebServices/WeatherWebService.asmx");
			URLConnection conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", Integer.toString(soap
					.length()));
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction",
					"http://WebXml.com.cn/getWeatherbyCityName");
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			osw.write(soap);
			osw.flush();
			osw.close();
			InputStream is = conn.getInputStream();
			// System.out.println(is.toString());
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过dom4j对服务器端返回的XML进行解析
	 * 
	 * @param city
	 *            用户输入的城市名称
	 * @return 符串 用,分割
	 */
	@SuppressWarnings("unchecked")
	public static String getWeather(String city) {
		Document document = null;
		SAXReader reader = new SAXReader();
		String s = "";
		Map map = new HashMap();
		map.put("design", "http://WebXml.com.cn/");
		reader.getDocumentFactory().setXPathNamespaceURIs(map);
		try {
			InputStream is = getSoapInputStream(city);// 得到输入流
			document = reader.read(is);// 将输入流转化为document
//			System.out.println(document.asXML());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List nodes = document.selectNodes("//design:string");
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			s = s + elm.getText() + "\n";
		}
		return s;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		System.out.println(BySOAP.getWeather("广州"));
	}
}

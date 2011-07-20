package tool.getProxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Proxy {
	private static final String url = "http://www.proxycn.com/html_proxy/30fastproxy-1.html";
	private static Map<String, String> ipMap = new HashMap<String, String>();
	private static final String filePath = "D:/sts/workspace-sts-2.5.1_t1/sf3a/src/main/resources/proxy.properties";

	public static Map<String, String> getProxyMap() {
		Document doc =null;
				 try {
					doc = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
					.header("Accept", "text ml,application/xhtml+xml").header(
							"Accept-Language", "zh-cn,zh;q=0.5").header(
							"Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7").get();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

		Elements trs = doc.select("tr[onDblClick]");
		for (Element e : trs) {
			String ip = e.attr("onDblClick").replaceAll("clip", "")
					.replaceAll("已拷贝到剪贴板!", "").replaceAll("alert", "")
					.replaceAll("'", "").replaceAll(";", "")
					.replaceAll("\\(", "").replaceAll("\\)", "");
			String[] ipArray = ip.split(":");
			ipMap.put(ipArray[0], ipArray[1]);
		}
		return ipMap;
	}

	public static void writeValidProxy() {
		Map<String, String> ipMap = getProxyMap();
		System.out.println("本次共获取到的:" + ipMap.size() + "个代理");
		for (String ip : ipMap.keySet()) {
			String port = ipMap.get(ip);
			System.out.println("获取新的待检验的:" + ip + "=" + port);
			boolean flag = HttpclientUtil.checkProxy(ip, port);
			if (flag) {
				System.out.println("写入有效:" + ip + "=" + port);
//				PropertiesUtil.writeProperties(filePath, ip, port);
			} else {
				System.out.println("移除失效:" + ip + "=" + port);
//				PropertiesUtil.removeProperties(filePath, ip);
			}
		}
	}

//	public static Map<String, String> getValidProxyMap() {
//		return PropertiesUtil.readProperties(filePath);
//	}
//
//	public static void removeInvalidProxy() {
//		Map<String, String> ipMap = getValidProxyMap();
//		for (String ip : ipMap.keySet()) {
//			String port = ipMap.get(ip);
//			System.out.println("校验原有:" + ip + "=" + port);
//			boolean flag = HttpclientUtil.checkProxy(ip, port);
//			if (!flag) {
//				System.out.println("移除失效:" + ip + "=" + port);
//				PropertiesUtil.removeProperties(filePath, ip);
//			}
//		}
//	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {
		while (true) {
			try {
//				removeInvalidProxy();
				writeValidProxy();
				Thread.sleep(1000 * 60 * 30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

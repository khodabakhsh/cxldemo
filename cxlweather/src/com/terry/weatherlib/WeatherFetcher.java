package com.terry.weatherlib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-23 下午05:20:54
 */
public class WeatherFetcher {

	private static Log log = LogFactory.getLog(WeatherFetcher.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("M月d日",
			Locale.CHINA);

	private static Calendar calendar = Calendar.getInstance(TimeZone
			.getTimeZone("GMT+08:00"), Locale.CHINA);

	static {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	}

	public static Weather fetchWeather(String loc) {
		if (loc.endsWith("市") && loc.length() > 1) {
			loc = loc.substring(0, loc.length() - 1);
		}
		String unicodeLoc = null;
		try {
			unicodeLoc = URLEncoder.encode(loc, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		String data = fetchData("http://search.weather.com.cn/wap/search.php",
				"city=" + unicodeLoc);
		log.debug("fetch data:" + loc + data);
		if (data == null)
			return null;
		Pattern p = Pattern.compile("\"([^\"]*)\"", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(data);
		if (matcher.find()) {
			String redirectURL = matcher.group(1);
			if (redirectURL.endsWith("101010100.shtml")) {// 默认找不到都是写是北京
				if (!loc.contains("北京") && !loc.contains("beijing")) {
					return null;
				}
			}
			data = fetchData(redirectURL, null);
		} else
			return null;
		log.debug("fetch data:" + loc + data);
		if (data == null)
			return null;
		return parserWeather(data, loc);
	}

	private static String fetchData(String url, String param) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url)
					.openConnection();
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
			con
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.1 (KHTML, like Gecko) Chrome/5.0.322.2 Safari/533.1,gzip(gfe),gzip(gfe)");
			if (param != null) {
				con.setDoOutput(true);
				con.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				con.setRequestMethod("POST");
				con.connect();
				DataOutputStream out = new DataOutputStream(con
						.getOutputStream());
				out.writeBytes(param);
				out.flush();
				out.close();
			} else
				con.setRequestMethod("GET");

			int code = con.getResponseCode();
			if (code >= 200 && code < 300) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "UTF-8")); // 读取结果
				StringBuffer sb = new StringBuffer("");
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\r\n");
				}
				reader.close();
				con.disconnect();
				return sb.toString();
			}
			con.disconnect();
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static Weather parserWeather(String html, String loc) {
		Parser parser = Parser.createParser(html.replace("<br />", "\r\n")
				.replace("&nbsp;", " "), "utf8");

		NodeFilter contentFilter = new NodeClassFilter(Div.class);
		NodeList nodelist = null;
		try {
			nodelist = parser.extractAllNodesThatMatch(contentFilter);
		} catch (ParserException e) {
			return null;
		}

		Node[] nodes = nodelist.toNodeArray();

		Weather weather = new Weather();

		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if (node instanceof Div) {
				Div div = (Div) node;
				if ("weather".equals(div.getAttribute("class"))) {
					NodeList wn = div.getChildren();
					Node[] wnNode = wn.toNodeArray();
					for (int j = 0; j < wnNode.length; j++) {
						Node n = wnNode[j];
						if (n instanceof HeadingTag) {
							HeadingTag ht = (HeadingTag) n;
							if ("H2".equalsIgnoreCase(ht.getTagName())) {
								weather.setCity(ht.toPlainTextString().replace(
										"天气预报", ""));
								log.debug("city:" + weather.getCity());
							} else if ("H3".equalsIgnoreCase(ht.getTagName())) {
								weather.setDesc(ht.toPlainTextString());
							}
						} else if (n instanceof Div) {
							Div days = (Div) n;
							if ("days".equals(days.getAttribute("class"))) {
								StringBuilder sb = new StringBuilder();
								NodeList dl = days.getChildren();
								Node[] dlNode = dl.toNodeArray();
								int cdays = 0;
								for (int k = 0; k < dlNode.length; k++) {
									String c = dlNode[k].toPlainTextString();
									if (c != null && !c.trim().equals("")) {
										cdays++;
										if (cdays == 1) {// 第一天把日期换成“今天”或“明天”或者周几
											String[] s = c.trim().split("\r\n",
													2);
											if (s.length == 2) {
												Date date = new Date();
												if (s[0].trim().equals(
														sdf.format(date))) {
													c = "今天 " + s[1].trim();
												} else if (s[0]
														.trim()
														.equals(
																sdf
																		.format(new Date(
																				date
																						.getTime() + 24 * 3600 * 1000))))
													c = "明天 " + s[1].trim();
												else {
													String weekName = getWeekName(s[0]);
													if (weekName != null)
														c = weekName + " "
																+ s[1].trim();
												}
											}
										} else if (cdays > 3) {// 第4天开始不要风力等情况了
											String[] s = c.trim().split("\r\n");
											if (s.length >= 3)
												c = s[0].trim() + " "
														+ s[1].trim();
											sb.append("\r\n");
										} else {
											String[] s = c.trim().split("\r\n",
													2);
											if (s.length == 2) {
												String weekName = getWeekName(s[0]);
												if (weekName != null)
													c = weekName + " "
															+ s[1].trim();
											}
											sb.append("\r\n");
										}
										sb.append(c.replace("\r\n", " ")
												.replace("    ", " ").trim());
									}
								}
								if (sb.length() != 0)
									weather.setContent(sb.toString());
							}
						}
					}
				}

			}
		}
		if (weather.getCity() == null)
			weather.setCity(loc);
		if (weather.checkComplete()) {
			weather.setUdate(new Date());
			return weather;
		}
		return null;
	}

	/*
	 * date 格式要是类似5月1日
	 */
	private static String getWeekName(String dateS) {
		try {
			Date date = sdf.parse(dateS.trim());
			calendar.setTime(date);
			calendar.set(Calendar.YEAR, Calendar.getInstance(
					TimeZone.getTimeZone("GMT+08:00"), Locale.CHINA).get(
					Calendar.YEAR));
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case 1:
				return "周日";
			case 2:
				return "周一";
			case 3:
				return "周二";
			case 4:
				return "周三";
			case 5:
				return "周四";
			case 6:
				return "周五";
			case 7:
				return "周六";
			default:
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		Weather w = WeatherFetcher.fetchWeather("上海");
		if (w != null)
			System.out.println(w.getReport());
	}
}

package com.terry.weatherlib.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.terry.weatherlib.Weather;
import com.terry.weatherlib.WeatherCache;
import com.terry.weatherlib.util.XMPPSender;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-10 下午12:01:48
 */
public class XMPPServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823820537706460688L;

	private XMPPService xmpp = XMPPServiceFactory.getXMPPService();

	private static final String ERROR = "对不起，程序出现错误，请稍候再试";

	private static final String HELP = "直接输入tq+城市名称或拼音将直接显示天气预报，"
			+ "\r\n若要定制或管理每天定时邮件提醒，" + "请登录http://tianqiyubao.org.ru/，"
			+ "机器人只接受查询，管理功能已取消。";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		Message message = xmpp.parseMessage(req);

		JID jid = message.getFromJid();
		String jids = jid.getId();
		if (jids.indexOf("/") != -1)
			jids = jids.substring(0, jids.indexOf("/"));

		XMPPSender.sendXMPP(jid, getResponse(message.getBody()));
	}

	private String getResponse(String body) {
		if (body.toLowerCase().startsWith("tq")) {
			if (body.trim().length() < 3) {
				return "请在tq后面输入城市名称或者拼音";
			} else {
				String city = body.substring(2).trim();
				if (city.length() > 12)
					return ERROR;
				Weather w = WeatherCache.queryWeather(city, city);
				if (w == null)
					return "对不起，无法获取“" + city + "”的天气预报，请稍候再试！";
				return w.getReport() + "\r\n" + w.getDesc();
			}
		} else
			return HELP;
	}
}

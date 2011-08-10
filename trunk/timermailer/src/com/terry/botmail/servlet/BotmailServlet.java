package com.terry.botmail.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.terry.botmail.data.impl.AccountDaoImpl;
import com.terry.botmail.data.impl.ScheduleDaoImpl;
import com.terry.botmail.data.intf.IAccountDao;
import com.terry.botmail.data.intf.IScheduleDao;
import com.terry.botmail.model.Account;
import com.terry.botmail.model.Schedule;
import com.terry.botmail.util.MailSender;
import com.terry.botmail.util.StringUtil;
import com.terry.botmail.util.XMPPSender;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-2 下午04:03:50
 */
public class BotmailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4449085355159990305L;

	private XMPPService xmpp = XMPPServiceFactory.getXMPPService();

	private IScheduleDao scheduleDao = new ScheduleDaoImpl();
	private IAccountDao accountDao = new AccountDaoImpl();

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.CHINA);

	private Cache cache;

	private static final String HELP = "直接添加或发送邮件（默认139邮箱）请输入标准格式（机器人会提示您接下来的操作）："
			+ "手机号或Email[空格]标题[空格]内容"
			+ "\r\n输入list或00查看已设置定时列表"
			+ "\r\n输入account或000查看或修改账户信息" + "\r\n任何时候输入0取消当前操作";
	private static final String HELP_TYPE_CHOICE = "输入1即时发送，2指定时间发送，3定时每天发送，"
			+ "4每周发送，5每月发送，6每年发送，输入0取消";
	private static final String ERROR = "对不起，程序出现错误，请稍候再试";
	private static final int DEFAULT_SCHEDULES_LIMIT = 10;

	@Override
	public void init() throws ServletException {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		sdf2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		Map<Integer, Integer> props = new HashMap<Integer, Integer>();
		props.put(GCacheFactory.EXPIRATION_DELTA, 600);
		try {
			cache = CacheManager.getInstance().getCacheFactory().createCache(
					props);
		} catch (CacheException e) {
		}
	}

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

		XMPPSender.sendXMPP(jid, getResponse(jids, message.getBody()));
	}

	@SuppressWarnings("unchecked")
	private String getResponse(String account, String body) {
		if (cache == null)
			return ERROR;

		Object o = cache.get(account);
		if (body.equals("0")) {
			if (o != null) {
				cache.remove(account);
				return "已取消。" + HELP;
			} else
				return HELP;
		}
		if (o != null && o instanceof Schedule) {
			return setScheduleResponse(account, body, (Schedule) o);
		}
		if (o != null && o instanceof HashMap<?, ?>) {
			HashMap<Integer, String> map = (HashMap<Integer, String>) o;
			if (!StringUtil.isDigital(body)) {
				return "请重新输入有效的序号以删除定时设置";
			}
			int index = Integer.parseInt(body);
			String id = map.get(index);
			if (id == null)
				return "对不起，找不到该序号的定时设置，请重新输入序号";
			else {
				if (scheduleDao.deleteScheduleById(id))
					return "您已成功删除定时设置，输入序号继续删除，按0返回";
				else
					return "对不起，找不到该序号的定时设置，可能已经删除";
			}
		}
		if (o != null && o instanceof Account) {
			if (body.length() > 20)
				return "请重新输入少于20个字的邮件发送人昵称，输入0返回";
			else {
				Account acc = (Account) o;
				acc.setNickname(body.trim());
				cache.remove(account);
				if (accountDao.updateAccountNickname(account, body))
					return "您已成功设置邮件发送人昵称为：" + body + "，输入0返回";
				else
					return ERROR;
			}
		}

		if (StringUtil.isEmptyOrWhitespace(body))
			return HELP;
		if (body.equalsIgnoreCase("list") || body.equalsIgnoreCase("00")) {
			return generateScheduleListResponse(account, scheduleDao
					.getSchedulesByAccount(account));
		}
		if (body.equalsIgnoreCase("account") || body.equalsIgnoreCase("000")) {
			Account acc = checkAndGetAccount(account);
			cache.put(account, acc);
			StringBuffer sb = new StringBuffer("");
			if (acc.getCdate().getTime() == acc.getUdate().getTime())
				sb.append("您从未设置过定时邮件");
			else {
				sb.append("您共有" + scheduleDao.getScheduleCount(account)
						+ "条定时邮件设置，");
				sb.append("最近一次设置定时邮件时间为：").append(sdf2.format(acc.getUdate()));
			}
			sb.append("\r\n邮件发送人昵称为：").append(acc.getNickname()).append(
					"\r\n设置定时邮件的限额为：少于").append(acc.getSlimit()).append("条");
			sb.append("\r\n").append(
					"请直接输入昵称以修改现有的昵称（少于20个字）：" + acc.getNickname());
			sb.append("\r\n输入0返回");
			return sb.toString();
		}
		String[] parts = body.split("\\s", 3);
		if (parts.length < 3)
			return HELP;

		Account acc = checkAndGetAccount(account);
		if (scheduleDao.getScheduleCount(account) >= acc.getSlimit())
			return "对不起，您设置的定时数目已经达到上限，请删除一些定时设置后再试";

		String email = parts[0];
		if (StringUtil.isDigital(parts[0])) {
			if (!StringUtil.isChinaMobile(parts[0]))
				return parts[0] + "不是有效的中国移动手机号码";
			else
				email = parts[0] + "@139.com";
		} else {
			if (!StringUtil.validateEmail(parts[0]))
				return parts[0] + "不是有效的邮件地址";
		}

		Schedule schedule = new Schedule();
		schedule.setSdate(null);
		schedule.setAccount(account);
		schedule.setAdate(null);
		schedule.setCdate(new Date());
		schedule.setEmail(email);
		schedule.setSubject(parts[1]);
		schedule.setContent(parts[2]);
		schedule.setNickname(acc.getNickname());
		schedule.setType(0);

		cache.put(account, schedule);

		return "您将设置标题为：" + parts[1] + "的邮件发送至：" + email + "\r\n"
				+ HELP_TYPE_CHOICE;
	}

	@SuppressWarnings("unchecked")
	private String generateScheduleListResponse(String account,
			List<Schedule> schedules) {
		if (schedules == null || schedules.size() == 0)
			return "您尚未设置定时";
		StringBuffer sb = new StringBuffer("显示您现有的定时列表，直接输入序号数字将删除该定时设置，输入0返回");
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		int count = 0;
		for (Schedule schedule : schedules) {
			count++;
			map.put(count, schedule.getId());
			sb.append("\r\n").append(count).append(" ");
			if (schedule.getType() == 2)
				sb.append("指定：").append(sdf2.format(schedule.getSdate()))
						.append("发送一次主题为：").append(schedule.getSubject())
						.append("的邮件至：" + schedule.getEmail());
			else {
				sb.append("定时每").append(changeTypeToString(schedule.getType()))
						.append("发送主题为：").append(schedule.getSubject()).append(
								"的邮件至：" + schedule.getEmail());
				if (schedule.getAdate() != null) {
					sb.append("，最近一次发送时间：" + sdf2.format(schedule.getAdate()));
				}
				sb.append("，下一次发送时间：" + sdf2.format(schedule.getSdate()));
			}
		}
		cache.put(account, map);
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private String setScheduleResponse(String account, String body,
			Schedule schedule) {
		boolean scheduleSaved = false;
		if (schedule.getType() > 1) {
			try {
				Date sdate = null;
				if (body.length() == 5) {
					sdate = sdf.parse(body.replace("：", ":"));
					Calendar c = Calendar.getInstance();
					c.setTime(sdate);
					Calendar now = Calendar.getInstance();
					c.set(Calendar.YEAR, now.get(Calendar.YEAR));
					c.set(Calendar.MONTH, now.get(Calendar.MONTH));
					c
							.set(Calendar.DAY_OF_MONTH, now
									.get(Calendar.DAY_OF_MONTH));
					sdate = c.getTime();
				} else
					sdate = sdf2.parse(body.replace("：", ":"));
				if (sdate.getTime() <= new Date().getTime())
					return "您输入的：" + sdf2.format(sdate) + "已超过现在时间，请重新输入";
				schedule.setSdate(sdate);
				scheduleSaved = scheduleDao.saveSchedule(schedule);
				accountDao.updateAccountUdate(account);
				cache.remove(account);
			} catch (ParseException e) {
				return "请输入有效的时间日期，如"
						+ sdf2.format(new Date(new Date().getTime() + 3600000))
						+ "，如果是当天发送，也可只输入时间，如09:08";
			}
		}

		switch (schedule.getType()) {
		case 0:
			String t = null;
			if (body.equals("1")) {
				cache.remove(account);
				try {
					MailSender.sendMail(schedule.getEmail(), schedule
							.getNickname(), schedule.getSubject(), schedule
							.getContent());
					return "标题为：" + schedule.getSubject() + "的邮件已即时发送至："
							+ schedule.getEmail();
				} catch (Exception e) {
					return "对不起，邮件未发送：" + schedule.getEmail() + "，错误："
							+ e.getMessage();
				}
			} else if (body.equals("2")) {
				schedule.setType(2);
				cache.put(account, schedule);
				return "请输入要发送邮件的日期时间，如"
						+ sdf2.format(new Date(new Date().getTime() + 3600000))
						+ "，如果是当天发送，也可只输入时间，如20:08";
			} else if (body.equals("3")) {
				t = "天";
				schedule.setType(3);
			} else if (body.equals("4")) {
				t = "周";
				schedule.setType(4);
			} else if (body.equals("5")) {
				t = "月";
				schedule.setType(5);
			} else if (body.equals("6")) {
				t = "年";
				schedule.setType(6);
			}
			if (t == null) {
				return HELP_TYPE_CHOICE;
			} else {
				cache.put(account, schedule);
				return "您已成功设置定时每" + t + "发送，请输入第一次发送邮件的日期时间，如"
						+ sdf2.format(new Date(new Date().getTime() + 3600000))
						+ "，如果是当天，也可只输入时间，如09:08";

			}
		case 2:
			if (scheduleSaved)
				return "您已成功设置指定：" + sdf2.format(schedule.getSdate())
						+ "发送主题为：" + schedule.getSubject() + "的邮件至："
						+ schedule.getEmail();
			else
				return ERROR;
		default:
			if (scheduleSaved)
				return "您已成功设置定时每" + changeTypeToString(schedule.getType())
						+ "发送主题为：" + schedule.getSubject() + "的邮件至："
						+ schedule.getEmail() + "，下一次发送时间为："
						+ sdf2.format(schedule.getSdate());
			else
				return ERROR;
		}
	}

	private String changeTypeToString(int type) {
		switch (type) {
		case 3:
			return "天";
		case 4:
			return "周";
		case 5:
			return "月";
		default:
			return "年";
		}
	}

	private Account checkAndGetAccount(String a) {
		Account account = accountDao.getAccountByAccount(a);
		Date now = new Date();
		if (account == null) {
			account = new Account();
			account.setSlimit(DEFAULT_SCHEDULES_LIMIT);
			account.setAccount(a);
			account.setNickname(a.substring(0, a.indexOf("@")));
			account.setCdate(now);
			account.setUdate(now);
			accountDao.saveAccount(account);
			return account;
		} else {
			return account;
		}
	}

}

package com.terry.weatherlib.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.terry.weatherlib.Weather;
import com.terry.weatherlib.WeatherCache;
import com.terry.weatherlib.data.impl.AccountDaoImpl;
import com.terry.weatherlib.data.impl.ScheduleDaoImpl;
import com.terry.weatherlib.data.intf.IAccountDao;
import com.terry.weatherlib.data.intf.IScheduleDao;
import com.terry.weatherlib.model.Account;
import com.terry.weatherlib.model.Schedule;
import com.terry.weatherlib.util.StringUtil;
import com.terry.weatherlib.util.WeatherMailSender;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: Mar 1, 2010 12:36:37 AM
 */
public class WebManagerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -419330133682830958L;

	private static final String ERROR = "对不起，程序出现错误，请稍候再试";

	private MemcacheService cache = MemcacheServiceFactory.getMemcacheService();

	private static final String CACHE_COUNT_NAME = "cache-count";

	private static final String CACHE_TOTAL_COUNT_NAME = "cache-total-count";

	private static final String CACHE_TEST_EMAIL_NAME = "cache-test-email";

	private static final int DEFAULT_SCHEDULES_LIMIT = 10;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.CHINA);
	private SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd HH:mm",
			Locale.CHINA);

	private IScheduleDao scheduleDao = new ScheduleDaoImpl();
	private IAccountDao accountDao = new AccountDaoImpl();
	private UserService userService = UserServiceFactory.getUserService();

	@Override
	public void init() throws ServletException {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		sdf2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		sdf3.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");

		JSONObject jo = null;
		if (userService.isUserLoggedIn()) {
			String action = req.getParameter("action");
			if (action != null) {
				if (action.equals("saveSchedule")) {
					jo = saveSchedule(req);
				} else if (action.equals("deleteSchedules")) {
					jo = deleteSchedules(req);
				} else if (action.equals("updateNickname")) {
					jo = updateNickanme(req);
				} else if (action.equals("getAccountInfo")) {
					jo = getAccountInfo();
				} else if (action.equals("getTotalCount")) {
					jo = getTotalCount();
				} else if (action.equals("testEmail")) {
					jo = testEmail(req);
				} else
					jo = schedulesList(req);
			}
		} else
			jo = getTotalCount();

		if (jo != null)
			resp.getWriter().println(jo.toString());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	private JSONObject schedulesList(HttpServletRequest req) {
		// 得到当前页数
		int page = Integer.parseInt(req.getParameter("page"));
		// 得到每页显示行数
		int limit = Integer.parseInt(req.getParameter("rp"));
		int start = (page - 1) * limit;

		String account = userService.getCurrentUser().getEmail();
		JSONObject jo = createDefaultJo();
		List<Schedule> schedules = scheduleDao.getSchedulesByAccount(account,
				start, limit);
		JSONArray rows = new JSONArray();
		for (Schedule s : schedules) {
			JSONObject jso = new JSONObject();
			JSONArray ja = new JSONArray();
			ja.put(sdf2.format(s.getCdate()));
			ja.put(s.getCity());
			String days;
			if (s.getDays() == 0) {
				if (s.getType() == 1)
					days = "5[系统默认]";
				else if (s.getType() == 2)
					days = "3[系统默认]";
				else
					days = "[系统默认]";
			} else
				days = String.valueOf(s.getDays());
			ja.put(days);
			ja.put(sdf.format(s.getSdate()));
			ja.put(s.getEmail());
			if (s.getType() == 0)
				ja.put("暂时停用");
			else if (s.getType() == 1)
				ja.put("天气内容放正文");
			else
				ja.put("天气内容放主题");
			String adate = "";
			if (s.getAdate() == null) {
				if (s.getType() == 0)
					adate = "从未发送[已暂停]";
				else
					adate = "待发送" + sdf3.format(s.getSdate());
			} else
				adate = sdf2.format(s.getAdate());
			ja.put(adate);
			ja.put(StringUtil.isEmptyOrWhitespace(s.getRemark()) ? "[无]" : s
					.getRemark());
			try {
				jso.put("id", s.getId());
				jso.put("cell", ja);
			} catch (JSONException e) {
			}
			rows.put(jso);
		}
		try {
			jo.put("total", getAccountScheduleCount(account));
			jo.put("page", page);
			jo.put("rows", rows);
			jo.put("result", true);
			jo.put("message", "ok");
		} catch (JSONException e) {
		}
		return jo;
	}

	private JSONObject testEmail(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();
		String email = req.getParameter("email");
		String city = req.getParameter("city");
		String typeS = req.getParameter("type");
		String daysS = req.getParameter("days");

		if (StringUtil.isEmptyOrWhitespace(email) || email.length() > 100
				|| StringUtil.isEmptyOrWhitespace(city) || city.length() > 12
				|| StringUtil.isEmptyOrWhitespace(typeS)
				|| StringUtil.isEmptyOrWhitespace(daysS)) {
			try {
				jo.put("message", "请检查必填栏位或是否符合长度规定");
			} catch (JSONException e) {
			}
			return jo;
		}

		if (!StringUtil.validateEmail(email)) {
			try {
				jo.put("message", "请检查邮件格式");
			} catch (JSONException e) {
			}
			return jo;
		}

		if (!StringUtil.isDigital(typeS)) {
			try {
				jo.put("message", "请检查状态");
			} catch (JSONException e) {
			}
			return jo;
		}
		int type = Integer.parseInt(typeS);
		if (type == 1 || type == 2)
			;
		else
			type = 1;

		if (!StringUtil.isDigital(daysS)) {
			try {
				jo.put("message", "请检查预报天数");
			} catch (JSONException e) {
			}
			return jo;
		}
		int days = Integer.parseInt(daysS);
		if (days < 0)
			days = 0;

		String account = userService.getCurrentUser().getEmail();
		Account a = accountDao.getAccountByAccount(account);
		if (a == null) {
			try {
				jo.put("message", "非法操作");
			} catch (JSONException e) {
			}
			return jo;
		}

		Weather w = WeatherCache.queryWeather(city.trim());
		if (w == null) {
			try {
				jo.put("message", "无法获取“" + city + "”的天气，请检查您的输入并稍候再试");
			} catch (JSONException e) {
			}
			return jo;
		} else
			city = w.getCity();

		String key = CACHE_TEST_EMAIL_NAME + "-" + email + "-" + city + "-"
				+ type;
		Long last = (Long) cache.get(key);
		if (last != null && System.currentTimeMillis() - last < 5 * 60 * 1000) {
			try {
				jo.put("message", "您对“" + email + "”发送的测试邮件过于频繁，请过5分钟后再试");
			} catch (JSONException e) {
			}
			return jo;
		}

		boolean result = WeatherMailSender.sendWeatherMail(w, email, type,
				days, "[测试]" + a.getNickname(), true);
		if (result)
			cache.put(key, System.currentTimeMillis());

		try {
			jo.put("result", result);
			jo.put("message", result ? "“" + city + "”的测试邮件已发送至" + email
					: "对不起，暂时无法发送测试邮件，请稍候再试");
			jo.put("city", city);
			jo.put("count", getAccountScheduleCount(account));
			jo.put("total", getScheduleCount());
		} catch (JSONException e) {
		}

		return jo;
	}

	private JSONObject saveSchedule(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();

		String email = req.getParameter("email");
		String sdateS = req.getParameter("sdate");
		String city = req.getParameter("city");
		String remark = req.getParameter("remark");
		String typeS = req.getParameter("type");
		String daysS = req.getParameter("days");
		String sid = req.getParameter("sid");

		boolean update = !StringUtil.isEmptyOrWhitespace(sid);

		if (StringUtil.isEmptyOrWhitespace(email) || email.length() > 100
				|| StringUtil.isEmptyOrWhitespace(city) || city.length() > 12
				|| StringUtil.isEmptyOrWhitespace(sdateS)
				|| StringUtil.isEmptyOrWhitespace(typeS)
				|| StringUtil.isEmptyOrWhitespace(daysS) || remark == null
				|| remark.length() > 100) {
			try {
				jo.put("message", "请检查必填栏位或是否符合长度规定");
			} catch (JSONException e) {
			}
			return jo;
		}

		if (!StringUtil.validateEmail(email)) {
			try {
				jo.put("message", "请检查邮件格式");
			} catch (JSONException e) {
			}
			return jo;
		}

		if (!StringUtil.isDigital(typeS)) {
			try {
				jo.put("message", "请检查状态");
			} catch (JSONException e) {
			}
			return jo;
		}
		int type = Integer.parseInt(typeS);
		if (type == 1 || type == 2)
			;
		else
			type = 0;

		if (!StringUtil.isDigital(daysS)) {
			try {
				jo.put("message", "请检查预报天数");
			} catch (JSONException e) {
			}
			return jo;
		}
		int days = Integer.parseInt(daysS);
		if (days < 0)
			days = 0;

		String account = userService.getCurrentUser().getEmail();
		Account a = accountDao.getAccountByAccount(account);
		if (a == null) {
			try {
				jo.put("message", "非法操作");
			} catch (JSONException e) {
			}
			return jo;
		}

		if (!update && getScheduleCount() >= 2000) {
			try {
				jo.put("message", "本站总定制数目已经达到上限:2000，后续会通过开分站的形式为您提供服务。");
			} catch (JSONException e) {
			}
			return jo;
		}

		if (!update && getAccountScheduleCount(account) >= a.getSlimit()) {
			try {
				jo
						.put(
								"message",
								"设置的定制数目已经达到上限:"
										+ a.getSlimit()
										+ "，请删除一些定制设置后再试，或<a target=\"_blank\" href=\"http://xinghuo.org.ru/\">联系站长</a>");
			} catch (JSONException e) {
			}
			return jo;
		}

		Date sdate = null;
		try {
			sdate = sdf.parse(sdateS);
			Calendar c = Calendar.getInstance();
			c.setTime(sdate);
			Calendar now = Calendar.getInstance();
			c.set(Calendar.YEAR, now.get(Calendar.YEAR));
			c.set(Calendar.MONTH, now.get(Calendar.MONTH));
			c.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
			if (c.getTimeInMillis() < now.getTimeInMillis())
				c.add(Calendar.DAY_OF_YEAR, 1);// 如果小于现在时间，就再加一天，第二天再发
			sdate = c.getTime();
		} catch (ParseException e1) {
			try {
				jo.put("message", "请检查发送时间");
			} catch (JSONException e) {
			}
			return jo;
		}

		Weather w = WeatherCache.queryWeather(city.trim());
		if (w == null) {
			try {
				jo.put("message", "无法获取“" + city
						+ "”的天气，所以未能保存您的定制，请检查您的输入并稍候再试");
			} catch (JSONException e) {
			}
			return jo;
		} else
			city = w.getCity();
		boolean result = false;
		if (update) {
			result = scheduleDao.updateScheduleById(sid, email, city, sdate,
					type, days, remark);
		} else {
			Schedule s = new Schedule();
			s.setAccount(account);
			s.setAdate(null);
			s.setCdate(new Date());
			s.setCity(city);
			s.setEmail(email);
			s.setRemark(remark.trim());
			s.setSdate(sdate);
			s.setType(type);
			s.setDays(days);
			result = scheduleDao.saveSchedule(s);
			if (result) {
				updateAccountScheduleCount(account, 1);
				accountDao.updateAccountUdate(account);
			}
		}
		String message = result ? "已成功保存“" + city + "”的天气预报邮件定制" : ERROR;

		try {
			jo.put("result", result);
			jo.put("message", message);
			jo.put("count", getAccountScheduleCount(account));
			jo.put("total", getScheduleCount());
		} catch (JSONException e) {
		}

		return jo;
	}

	private JSONObject deleteSchedules(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();
		String[] ids = req.getParameter("ids").split(",");
		String account = userService.getCurrentUser().getEmail();
		int total = 0;
		for (String id : ids) {
			if (!StringUtil.isEmptyOrWhitespace(id))
				if (scheduleDao.deleteScheduleByIdAndAccount(id, account))
					total++;
		}
		updateAccountScheduleCount(account, -total);
		try {
			jo.put("result", total > 0);
			String message = total > 0 ? "您已成功删除" + total + "条天气预报定制设置" : ERROR;
			jo.put("message", message);
			jo.put("count", getAccountScheduleCount(account));
			jo.put("total", getScheduleCount());
		} catch (JSONException e) {

		}
		return jo;
	}

	private JSONObject getAccountInfo() {
		JSONObject jo = createDefaultJo();
		String account = userService.getCurrentUser().getEmail();
		Account a = accountDao.getAccountByAccount(account);
		Date now = new Date();
		if (a == null) {
			a = new Account();
			a.setSlimit(DEFAULT_SCHEDULES_LIMIT);
			a.setAccount(account);
			a.setNickname("天气预报");
			a.setCdate(now);
			a.setUdate(now);
			if (!accountDao.saveAccount(a))
				return jo;
		}
		try {
			jo.put("result", true);
			jo.put("message", "ok");
			jo.put("nickname", a.getNickname());
			jo.put("slimit", a.getSlimit());
			jo.put("cdate", sdf2.format(a.getCdate()));
			jo.put("udate", sdf2.format(a.getUdate()));
			jo.put("count", getAccountScheduleCount(account));
			jo.put("total", getScheduleCount());
		} catch (JSONException e) {
		}
		return jo;
	}

	private JSONObject getTotalCount() {
		JSONObject jo = createDefaultJo();
		try {
			jo.put("result", true);
			jo.put("message", "ok");
			jo.put("total", getScheduleCount());
		} catch (JSONException e) {
		}
		return jo;
	}

	private JSONObject updateNickanme(HttpServletRequest req) {
		String account = userService.getCurrentUser().getEmail();
		JSONObject jo = createDefaultJo();
		String nickname = req.getParameter("nickname");
		if (StringUtil.isEmptyOrWhitespace(nickname) || nickname.length() > 12) {
			try {
				jo.put("message", "请检查您的输入");
			} catch (JSONException e) {
			}
			return jo;
		}
		if (accountDao.updateAccountNickname(account, nickname)) {
			try {
				jo.put("result", true);
				jo.put("message", "已成功更新邮件发送时昵称为：" + nickname);
				jo.put("count", getAccountScheduleCount(account));
				jo.put("total", getScheduleCount());
			} catch (JSONException e) {
			}
		}
		return jo;
	}

	private JSONObject createDefaultJo() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("result", false);
			jo.put("message", ERROR);
		} catch (JSONException e) {
		}
		return jo;
	}

	private int getAccountScheduleCount(String account) {
		String name = account + "-" + CACHE_COUNT_NAME;
		Object o = cache.get(name);
		if (o != null && o instanceof Integer) {
			return (Integer) o;
		}
		int count = scheduleDao.getScheduleCountByAccount(account);
		cache.put(name, count);
		return count;
	}

	private int getScheduleCount() {
		Object o = cache.get(CACHE_TOTAL_COUNT_NAME);
		if (o != null && o instanceof Integer) {
			return (Integer) o;
		}
		int count = scheduleDao.getScheduleCount();
		cache.put(CACHE_TOTAL_COUNT_NAME, count);
		return count;
	}

	private void updateAccountScheduleCount(String account, int change) {
		if (change == 0)
			return;
		String name = account + "-" + CACHE_COUNT_NAME;
		cache.increment(name, change);
		cache.increment(CACHE_TOTAL_COUNT_NAME, change);
	}

}

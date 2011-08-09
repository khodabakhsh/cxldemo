package com.terry.weatherlib.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.terry.weatherlib.Weather;
import com.terry.weatherlib.WeatherCache;
import com.terry.weatherlib.model.Account;
import com.terry.weatherlib.model.Schedule;
import com.terry.weatherlib.util.EMF;
import com.terry.weatherlib.util.StringUtil;
import com.terry.weatherlib.util.WeatherMailSender;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-3 下午04:35:50
 */
public class SendMailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -843840894946959108L;

	private static Log log = LogFactory.getLog(SendMailServlet.class);

	private static MemcacheService cacheService = MemcacheServiceFactory
			.getMemcacheService();

	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.CHINA);

	@Override
	public void init() throws ServletException {
		sdf2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String id = req.getParameter("id");
		if (StringUtil.isEmptyOrWhitespace(id))
			return;
		Key key = KeyFactory.stringToKey(id);
		if (key == null || !key.isComplete())
			return;

		EntityManager em = EMF.get().createEntityManager();

		Schedule schedule = em.find(Schedule.class, key);
		if (schedule == null)
			return;
		if (schedule.getType() == 0)
			return;
		if (schedule.getSdate().getTime() > System.currentTimeMillis())
			return;
		String a = schedule.getAccount();
		Query query = em.createQuery("SELECT a FROM " + Account.class.getName()
				+ " a where a.account=:account");
		query.setParameter("account", a);
		Account account = (Account) query.getSingleResult();
		Weather weather = WeatherCache.queryWeather(schedule.getCity());
		if (weather == null) {
			log.warn("can not fetch weather for: " + schedule.getCity()
					+ ", account: " + schedule.getAccount());
			return;
		}
		if (!WeatherMailSender.sendWeatherMail(weather, schedule,
				account == null ? null : account.getNickname()))
			return;
		log.debug("mail sent:" + schedule.getEmail() + " for account:"
				+ account.getAccount() + ", schedule data:"
				+ sdf2.format(schedule.getSdate()));
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Date now = new Date();
			Calendar c_sdate = Calendar.getInstance();
			c_sdate.setTime(schedule.getSdate());
			if (now.getTime() - schedule.getSdate().getTime() > 24 * 3600 * 1000) {
				Calendar nowC = Calendar.getInstance();
				c_sdate.set(Calendar.YEAR, nowC.get(Calendar.YEAR));
				c_sdate.set(Calendar.MONTH, nowC.get(Calendar.MONTH));
				c_sdate.set(Calendar.DAY_OF_MONTH, nowC
						.get(Calendar.DAY_OF_MONTH));
			}
			if (c_sdate.getTimeInMillis() <= now.getTime())
				c_sdate.add(Calendar.DAY_OF_YEAR, 1);
			schedule.setSdate(c_sdate.getTime());
			// schedule.setCity(weather.getCity());
			schedule.setAdate(now);
			em.persist(schedule);
			tx.commit();
		} catch (Exception e) {
		}

		cacheService.delete(CheckScheduleServlet.SCHEDULE_ID_KEY + "-" + id);
	}
}

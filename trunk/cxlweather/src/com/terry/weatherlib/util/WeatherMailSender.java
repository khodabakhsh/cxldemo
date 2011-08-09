package com.terry.weatherlib.util;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.terry.weatherlib.Weather;
import com.terry.weatherlib.model.Schedule;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: Mar 5, 2010 8:24:39 PM
 */
@SuppressWarnings("unchecked")
public class WeatherMailSender {
	private static final String HELP = "\r\n管理订阅请登录http://www.tianqiyubao.org.ru/\r\n请勿直接回复";

	private static SimpleDateFormat sdf2 = new SimpleDateFormat("M月d日H:mm",
			Locale.CHINA);

	private static Log log = LogFactory.getLog(WeatherMailSender.class);

	private static Cache cache;

	private static final String SCHEDULE_ID_KEY2 = "schedule-id2";

	private static int mailerCounter = 0;

	static {
		sdf2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		try {
			cache = CacheManager.getInstance().getCacheFactory().createCache(
					Collections.emptyMap());
		} catch (CacheException e) {
		}
	}

	public static boolean sendWeatherMail(Weather weather, String email,
			int type, int days, String nickname, boolean fetch) {
		String subject = null;
		String content = null;
		if (type == 2) {
			if (days == 0)// 放主题的默认天数为3
				days = 3;
			subject = weather.getReport(days).replace("\r\n", " ");
			content = "如题。" + weather.getDesc() + HELP;
		} else {
			subject = weather.getCity() + "天气预报--" + weather.getDesc();
			content = weather.getReport(days) + HELP;
		}
		if (fetch) {
			return MailSender.fetchToSendMail(null, email, nickname, subject,
					content);
		}
		Random r = new Random();
		if (r.nextInt(5) == 0 && Constants.BACKUP_MAILERS != null
				&& Constants.BACKUP_MAILERS.length != 0) {
			String url = "http://"
					+ Constants.BACKUP_MAILERS[r
							.nextInt(Constants.BACKUP_MAILERS.length)]
					+ "/mail";
			mailerCounter++;
			return MailSender.fetchToSendMail(url, email, nickname, subject,
					content);
		}
		try {
			MailSender.sendMail(email, nickname, subject, content);
			return true;
		} catch (OverQuotaException e) {
			if (Constants.BACKUP_MAILERS == null
					|| Constants.BACKUP_MAILERS.length == 0)
				return false;
			if (mailerCounter >= Constants.BACKUP_MAILERS.length)
				mailerCounter = 0;
			String url = "http://" + Constants.BACKUP_MAILERS[mailerCounter]
					+ "/mail";
			mailerCounter++;
			return MailSender.fetchToSendMail(url, email, nickname, subject,
					content);
		} catch (Exception e) {
			return false;
		}
	}

	public static synchronized boolean sendWeatherMail(Weather weather,
			Schedule schedule, String nickname) {
		String key = SCHEDULE_ID_KEY2 + "-" + schedule.getId();
		if (cache.containsKey(key)) {
			Date sdate = (Date) cache.get(key);
			if (schedule.getSdate().getTime() <= sdate.getTime()) {
				log.warn("email already sent with schedule id: "
						+ schedule.getId());
				return true;
			}
		}
		boolean result = sendWeatherMail(weather, schedule.getEmail(), schedule
				.getType(), schedule.getDays(), nickname, false);
		if (result) {
			cache.put(key, schedule.getSdate());
		}
		return result;
	}
}

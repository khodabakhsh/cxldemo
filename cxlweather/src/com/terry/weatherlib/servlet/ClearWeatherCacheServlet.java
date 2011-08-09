package com.terry.weatherlib.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.terry.weatherlib.Weather;
import com.terry.weatherlib.WeatherCache;
import com.terry.weatherlib.WeatherFetcher;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: Feb 27, 2010 8:50:27 AM
 */
public class ClearWeatherCacheServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1883179443719794954L;

	private static Log log = LogFactory.getLog(ClearWeatherCacheServlet.class);

	private static final String testCity = "上海";

	private Cache cache;

	@Override
	public void init() throws ServletException {
		try {
			cache = CacheManager.getInstance().getCacheFactory().createCache(
					Collections.emptyMap());
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

		String forceS = req.getParameter("force");
		boolean force = forceS != null && forceS.equals("true");

		if (!force && !checkShouldClearCache()) {
			log.debug("there is no need to clear weather cache.");
			return;
		}

		cache.clear();
	}

	private boolean checkShouldClearCache() {
		if (cache == null)
			return false;
		long nowTime = System.currentTimeMillis();
		Weather cacheTestWeather = WeatherCache.queryWeather(testCity);
		if (cacheTestWeather == null
				|| cacheTestWeather.getUdate().getTime() >= nowTime)
			return false;// 如果是刚刚从网上取的，就不用检查了

		String hourS = getHour(cacheTestWeather.getDesc());
		if (hourS == null)
			return false;
		int cacheHour = Integer.parseInt(hourS);
		int nowHour = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"),
				Locale.CHINA).get(Calendar.HOUR_OF_DAY);

		if (nowHour >= 0 && nowHour < 8) {
			if (cacheHour == 18)
				return false;
		} else if (nowHour >= 8 && nowHour < 12) {
			if (cacheHour == 8)
				return false;
		} else if (nowHour >= 12 && nowHour < 18) {
			if (cacheHour == 11)
				return false;
		} else {
			if (cacheHour == 18)
				return false;
		}

		Weather nowTestWeather = WeatherFetcher.fetchWeather(testCity);

		/*
		 * 如果从cache取出来的和直接从网上取到的desc（类似2010-05-01 11时发布）不一样，就要清空缓存
		 */
		if (nowTestWeather == null || nowTestWeather.getDesc() == null
				|| nowTestWeather.getDesc().equals(cacheTestWeather.getDesc()))
			return false;
		else
			return true;
	}

	private String getHour(String source) {
		if (source == null)
			return null;
		Pattern p = Pattern.compile("[^ ]* ([0-9]+)时发布");
		Matcher m = p.matcher(source);
		if (m.find())
			return m.group(1);
		return null;
	}

}

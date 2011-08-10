package com.terry.botmail.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.terry.botmail.data.impl.ScheduleDaoImpl;
import com.terry.botmail.data.intf.IScheduleDao;
import com.terry.botmail.model.Schedule;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-3 下午04:42:38
 */
public class CheckScheduleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -302297776402317937L;

	private IScheduleDao scheduleDao = new ScheduleDaoImpl();

	private Queue queue = QueueFactory.getDefaultQueue();

	private Cache cache;

	private static final String KEY = "check-status";

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

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		if (req.getParameter("recheck") != null) {
			if (cache != null) {
				if (cache.get(KEY) == null)
					return;
			}
		}
		if (cache != null)
			cache.put(KEY, Boolean.TRUE);
		List<Schedule> schedules = scheduleDao.getReadyToToSchedules();
		if (schedules == null || schedules.size() == 0)
			return;
		for (Schedule schedule : schedules) {
			queue.add(TaskOptions.Builder.withUrl("/cron/send").param("id",
					schedule.getId()));
		}
		if (cache != null)
			cache.remove(KEY);
	}

}

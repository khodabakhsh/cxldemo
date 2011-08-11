package com.terry.weddingphoto.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;
import com.terry.weddingphoto.constants.Constants;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-3-16 下午05:31:25
 */
public class AdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1854946647850514930L;

	private static Cache cache;

	@Override
	public void init() throws ServletException {
		if (cache == null) {
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			map.put(GCacheFactory.EXPIRATION_DELTA,
					Constants.UPLOAD_SESSION_TIME);
			try {
				cache = CacheManager.getInstance().getCacheFactory()
						.createCache(map);
			} catch (CacheException e) {
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uuid = generateUUID();
		req.setAttribute("uuid", uuid);
		req.getRequestDispatcher("/admin.jsp").forward(req, resp);
	}

	@SuppressWarnings("unchecked")
	public static String generateUUID() {
		String uuid = UUID.randomUUID().toString();
		if (cache != null) {
			Object o = cache.get(Constants.UPLOAD_SESSION_CACHE);
			ArrayList<String> al = null;
			if (o != null) {
				al = (ArrayList<String>) o;
			} else
				al = new ArrayList<String>();
			al.add(uuid);
			cache.put(Constants.UPLOAD_SESSION_CACHE, al);
		}
		return uuid;
	}
}

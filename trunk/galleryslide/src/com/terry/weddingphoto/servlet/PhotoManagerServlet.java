package com.terry.weddingphoto.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.terry.weddingphoto.constants.Constants;
import com.terry.weddingphoto.data.impl.PhotoDaoImpl;
import com.terry.weddingphoto.data.intf.IPhotoDao;
import com.terry.weddingphoto.model.Comment;
import com.terry.weddingphoto.model.Photo;
import com.terry.weddingphoto.util.MailSender;
import com.terry.weddingphoto.util.PhotoCache;
import com.terry.weddingphoto.util.StringUtil;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-3-17 下午12:49:41
 */
public class PhotoManagerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4006164308610992222L;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.CHINA);
	private UserService userService = UserServiceFactory.getUserService();
	private MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
	private IPhotoDao photoDao = new PhotoDaoImpl();
	private static final String ERROR = "对不起，程序出现错误，请稍候再试";

	@Override
	public void init() throws ServletException {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");

		JSONObject jo = null;

		String action = req.getParameter("action");
		if (action != null) {
			if (action.equals("saveComment")) {
				jo = saveComment(req);
			} else if (action.equals("deleteComment")) {
				jo = deleteComment(req);
			} else if (action.equals("updatePhoto")) {
				jo = updatePhoto(req);
			} else if (action.equals("photosList")) {
				jo = photosList(req);
			} else if (action.equals("deletePhotos")) {
				jo = deletePhotos(req);
			}
		}

		if (jo != null)
			resp.getWriter().println(jo.toString());
	}

	private JSONObject saveComment(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();
		String pid = req.getParameter("pid");
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");
		String content = req.getParameter("content");
		if (StringUtil.isEmptyOrWhitespace(pid)
				|| StringUtil.isEmptyOrWhitespace(nickname)
				|| StringUtil.isEmptyOrWhitespace(content)
				|| (!StringUtil.isEmptyOrWhitespace(email) && !StringUtil
						.validateEmail(email)) || nickname.length() > 12
				|| content.length() > 500) {
			try {
				jo.put("message", "请检查您的输入");
			} catch (JSONException e) {
			}
			return jo;
		}

		String ip = getIpAddr(req);
		if (getIpCommentCount(pid, ip) >= Constants.COMMENT_LIMIT) {
			try {
				jo.put("message", "您的评论频率太高");
			} catch (JSONException e) {
			}
			return jo;
		}

		Photo p = photoDao.getPhotoById(pid);
		if (p.getComment() == -1) {
			try {
				jo.put("message", "该照片不允许评论");
			} catch (JSONException e) {
			}
			return jo;
		}

		Comment comment = new Comment();
		comment.setCdate(new Date());
		comment.setIp(ip);
		comment.setEmail(email);
		comment.setName(nickname);
		comment.setPid(pid);
		comment.setContent(content);
		int count = photoDao.saveComment(comment);
		if (count != -1) {
			updateIpCommentCount(pid, ip);
			try {
				jo.put("result", true);
				jo.put("message", "已成功发表评论");
				jo.put("count", count);
				jo.put("cdate", sdf.format(comment.getCdate()));
				jo.put("nickname", comment.getName());
				jo.put("content", comment.getContent());
				jo.put("cid", comment.getId());
			} catch (JSONException e) {
			}
			if (Constants.COMMENT_NOTIFICATION_EMAILS != null) {
				String c = comment.getName() + "于 "
						+ sdf.format(comment.getCdate()) + " 对“"
						+ p.getFilename();
				if (!StringUtil.isEmptyOrWhitespace(p.getRemark()))
					c += ("(" + p.getRemark() + ")");
				c += ("”发表了新评论：\r\n" + comment.getContent());
				c += ("\r\n网址：http://" + SystemProperty.applicationId.get() + ".appspot.com/");
				try {
					MailSender.sendMail(Constants.COMMENT_NOTIFICATION_EMAILS,
							"照片评论提醒", p.getFilename() + "有新评论", c);
				} catch (Exception e) {
				}
			}
		}
		return jo;
	}

	private JSONObject deleteComment(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();
		if (!userService.isUserLoggedIn() || !userService.isUserAdmin()) {
			try {
				jo.put("message", "您无权删除该评论");
			} catch (JSONException e) {
			}
			return jo;
		}
		String cid = req.getParameter("cid");
		if (StringUtil.isEmptyOrWhitespace(cid)) {
			try {
				jo.put("message", "请检查您的输入");
			} catch (JSONException e) {
			}
			return jo;
		}
		int count = photoDao.deleteCommentById(cid);
		if (count != -1) {
			try {
				jo.put("result", true);
				jo.put("message", "已成功删除评论");
				jo.put("count", count);
			} catch (JSONException e) {
			}
		}
		return jo;
	}

	private JSONObject updatePhoto(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();
		if (!userService.isUserLoggedIn() || !userService.isUserAdmin()) {
			try {
				jo.put("message", "您无权更新照片信息");
			} catch (JSONException e) {
			}
			return jo;
		}
		String pid = req.getParameter("pid");
		String remark = req.getParameter("remark");
		String comment = req.getParameter("comment");
		if (StringUtil.isEmptyOrWhitespace(pid) || remark == null
				|| remark.length() > 500 || comment == null) {
			try {
				jo.put("message", "请检查您的输入");
			} catch (JSONException e) {
			}
			return jo;
		}
		if (photoDao.updatePhoto(pid, remark, comment.equals("false") ? false
				: true)) {
			try {
				jo.put("result", true);
				jo.put("message", "已成功更新照片信息");
			} catch (JSONException e) {
			}
		}
		return jo;
	}

	private JSONObject photosList(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();
		String spage = req.getParameter("page");// 得到当前页数
		String slimit = req.getParameter("rp");// 得到每页显示行数
		if (!StringUtil.isDigital(spage) || !StringUtil.isDigital(slimit)) {
			try {
				jo.put("message", "请检查您的输入");
			} catch (JSONException e) {
			}
			return jo;
		}
		int page = Integer.parseInt(req.getParameter("page"));
		int limit = Integer.parseInt(req.getParameter("rp"));
		int start = (page - 1) * limit;

		List<Photo> photos = photoDao.getPhotos(start, limit);
		JSONArray rows = new JSONArray();
		for (Photo p : photos) {
			String pid = p.getId();
			JSONObject jso = new JSONObject();
			JSONArray ja = new JSONArray();
			ja.put(sdf.format(p.getCdate()));
			ja
					.put("<a title=\"评论|详情\" href=\"detail.jsp?pid="
							+ pid
							+ "&admin=true&keepThis=true&TB_iframe=true&height=580&width=700\" class=\"thickbox\">"
							+ p.getFilename() + "</a>");
			ja.put(p.getRemark());
			int comment = p.getComment() == -1 ? p.getComments().size() : p
					.getComment();
			ja.put(comment > 0 ? "<span class='red'>" + comment + "</span>"
					: "0");
			ja.put(p.getComment() == -1 ? "<span class='red'>评论已关闭</span>"
					: "<span class='green'>允许评论</span>");
			ja.put("<img src='view?id=" + p.getId() + "&w=100&h=80'/>");
			try {
				jso.put("id", pid);
				jso.put("cell", ja);
			} catch (JSONException e) {
			}
			rows.put(jso);
		}
		try {
			jo.put("total", getPhotosCount());
			jo.put("page", page);
			jo.put("rows", rows);
			jo.put("result", true);
			jo.put("message", "ok");
		} catch (JSONException e) {
		}
		return jo;
	}

	private JSONObject deletePhotos(HttpServletRequest req) {
		JSONObject jo = createDefaultJo();
		if (!userService.isUserLoggedIn() || !userService.isUserAdmin()) {
			try {
				jo.put("message", "您无权删除该评论");
			} catch (JSONException e) {
			}
			return jo;
		}
		String ids = req.getParameter("ids");
		if (StringUtil.isEmptyOrWhitespace(ids)) {
			try {
				jo.put("message", "请检查您的输入");
			} catch (JSONException e) {
			}
			return jo;
		}
		String[] id = ids.split(",");
		int total = id.length;
		int count = 0;
		for (String pid : id) {
			if (pid.equals(""))
				continue;
			if (photoDao.deletePhotoById(pid)) {
				count++;
				PhotoCache.clearPhotoCache(pid);
			}
		}
		try {
			jo.put("total", id.length);
			jo.put("count", count);
			jo.put("result", total == count);
			updatePhotosCount(count);
			String message = "您已成功删除 <strong>" + total + "</strong> 张照片";
			if (total != count) {
				if (count == 0)
					message = "对不起，程序出现错误，没有删除照片，请稍候再试";
				else
					message = "您计划删除 <strong>" + total
							+ "</strong> 张照片，有 <strong>" + (total - count)
							+ "<strong> 张未删除，请稍候再试";
			}
			jo.put("message", message);
			updatePhotosCount(count);
		} catch (JSONException e) {
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

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private int getIpCommentCount(String pid, String ip) {
		String key = Constants.IP_COUNT_CACHE + "-" + pid + "-" + ip;
		Object o = cache.get(key);
		if (o != null && o instanceof Integer) {
			return (Integer) o;
		}
		return 0;
	}

	private void updateIpCommentCount(String pid, String ip) {
		String key = Constants.IP_COUNT_CACHE + "-" + pid + "-" + ip;
		Object o = cache.get(key);
		if (o != null && o instanceof Integer) {
			cache.increment(key, 1);
		} else
			cache.put(key, 1, Expiration
					.byDeltaSeconds(Constants.IP_CACHE_SESSION_TIME));
	}

	private int getPhotosCount() {
		Object o = cache.get(Constants.PHOTOS_COUNT_CACHE);
		if (o != null && o instanceof Integer) {
			return (Integer) o;
		}
		int count = photoDao.getPhotosCount();
		cache.put(Constants.PHOTOS_COUNT_CACHE, count);
		return count;
	}

	private void updatePhotosCount(int change) {
		if (change == 0)
			return;
		cache.increment(Constants.PHOTOS_COUNT_CACHE, change);
	}
}

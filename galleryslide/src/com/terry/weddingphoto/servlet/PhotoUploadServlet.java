package com.terry.weddingphoto.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.terry.weddingphoto.constants.Constants;
import com.terry.weddingphoto.data.impl.PhotoDaoImpl;
import com.terry.weddingphoto.data.intf.IPhotoDao;
import com.terry.weddingphoto.util.PhotoCache;
import com.terry.weddingphoto.util.StringUtil;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-3-15 上午10:23:18
 */
public class PhotoUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3274040408352222112L;

	private IPhotoDao photoDao = new PhotoDaoImpl();

	private MemcacheService cache = MemcacheServiceFactory.getMemcacheService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");
		JSONObject jo = new JSONObject();
		try {
			jo.put("result", false);
			jo.put("message", "对不起，未能保存上传的文件，请稍候再试！");
		} catch (JSONException e1) {
		}
		String id = req.getParameter("id");
		if (!checkUploadPermission(id)) {
			try {
				jo.put("message", "你无权上传文件，若您长时间未操作，请刷新页面后再试");
			} catch (JSONException e) {
			}
		} else {
			String[] ids = id.split("/");
			try {
				ServletFileUpload upload = new ServletFileUpload();
				upload.setSizeMax(Constants.PHOTO_BYTES_LIMIT);

				try {
					FileItemIterator iterator = upload.getItemIterator(req);
					while (iterator.hasNext()) {
						FileItemStream item = iterator.next();
						InputStream in = item.openStream();

						if (item.isFormField()) {
						} else {
							String fileName = item.getName();
							try {
								if (ids.length == 2
										&& !StringUtil
												.isEmptyOrWhitespace(ids[1])) {
									boolean result = photoDao.updatePhoto(
											ids[1], IOUtils.toByteArray(in));
									jo.put("result", result);
									jo
											.put(
													"message",
													"照片："
															+ fileName
															+ (result ? " 已成功更新，因浏览器缓存，可能旧图片会存在一段时间"
																	: " 未能更新，请稍候再试"));
									if (result) {
										PhotoCache.clearPhotoCache(ids[1]);
									}
								} else {
									int result = photoDao.saveOrUpdatePhoto(
											fileName, IOUtils.toByteArray(in));
									if (result != -1) {
										jo.put("result", true);
										jo
												.put(
														"message",
														"照片："
																+ fileName
																+ (result == 0 ? " 存在服务器，已成功覆盖原照片"
																		: " 已成功存入相册"));
										updatePhotosCount(result);
									}
								}

							} finally {
								IOUtils.closeQuietly(in);
							}

						}
					}
				} catch (SizeLimitExceededException e) {
					jo.put("message", "您最多允许上传 (" + e.getPermittedSize()
							+ ") 实际已上传 (" + e.getActualSize() + ")");
				}
			} catch (Exception ex) {
				try {
					jo.put("message", "对不起，程序错误，请稍候再试！" + ex.getMessage());
				} catch (JSONException e) {
				}
			}
		}
		PrintWriter out = resp.getWriter();
		out.print(jo.toString());
	}

	private void updatePhotosCount(int change) {
		if (change == 0)
			return;
		cache.increment(Constants.PHOTOS_COUNT_CACHE, change);
	}

	@SuppressWarnings("unchecked")
	private boolean checkUploadPermission(String id) {
		if (StringUtil.isEmptyOrWhitespace(id))
			return false;
		String[] ids = id.split("/");
		Object o = cache.get(Constants.UPLOAD_SESSION_CACHE);
		if (o == null)
			return false;
		ArrayList<String> al = (ArrayList<String>) o;
		if (al == null || al.size() == 0)
			return false;
		for (String s : al) {
			if (ids[0].equals(s))
				return true;
		}
		return false;
	}

}

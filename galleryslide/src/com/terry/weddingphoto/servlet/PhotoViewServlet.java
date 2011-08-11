package com.terry.weddingphoto.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.terry.weddingphoto.util.PhotoCache;
import com.terry.weddingphoto.util.StringUtil;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-3-15 下午01:55:56
 */
public class PhotoViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4072411700253346694L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		byte[] data = getImageData(req);
		if (data == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND,
					"can not find photo");
			return;
		}
		Image image = ImagesServiceFactory.makeImage(data);
		resp.setContentType("image/"
				+ image.getFormat().toString().toLowerCase());
		ServletOutputStream responseOutputStream = resp.getOutputStream();
		IOUtils.write(data, responseOutputStream);
	}

	private byte[] getImageData(HttpServletRequest req) {
		String id = req.getParameter("id");
		if (!StringUtil.isEmptyOrWhitespace(id)) {
			return PhotoCache.getPhotoData(id, req.getParameter("w"), req
					.getParameter("h"));
		}
		return null;
	}

}

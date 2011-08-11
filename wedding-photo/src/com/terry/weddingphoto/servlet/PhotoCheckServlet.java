package com.terry.weddingphoto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.terry.weddingphoto.data.impl.PhotoDaoImpl;
import com.terry.weddingphoto.data.intf.IPhotoDao;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: Mar 18, 2010 8:38:16 PM
 */
public class PhotoCheckServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 488527160312532329L;

	private IPhotoDao photoDao = new PhotoDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");
		JSONArray ja = new JSONArray();
		Enumeration<String> en = req.getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			if (!name.equals("folder")) {
				String value = req.getParameter(name);
				if (photoDao.checkPhotoExists(value)) {
					ja.put(value);
				}
			}

		}
		PrintWriter out = resp.getWriter();
		out.print(ja.toString());
	}

}

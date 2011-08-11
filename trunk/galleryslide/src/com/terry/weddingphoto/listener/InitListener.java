package com.terry.weddingphoto.listener;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;

import com.terry.weddingphoto.constants.Constants;
import com.terry.weddingphoto.util.StringUtil;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: Mar 20, 2010 10:42:16 PM
 */
public class InitListener implements ServletContextListener {

	
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	
	public void contextInitialized(ServletContextEvent contextEvent) {
		String basePath = contextEvent.getServletContext().getRealPath("/");
		try {
			Constants.PHOTO_DELETED_DATA = IOUtils
					.toByteArray(new FileInputStream(new File(basePath
							+ File.separator + "images" + File.separator
							+ "deleted.png")));
		} catch (Exception e) {
		}

		String str = contextEvent.getServletContext().getInitParameter(
				"commentNotificationEmails");
		if (!StringUtil.isEmptyOrWhitespace(str))
			Constants.COMMENT_NOTIFICATION_EMAILS = str.split(",");

	}
}

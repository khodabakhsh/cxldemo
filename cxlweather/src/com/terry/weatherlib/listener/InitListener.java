package com.terry.weatherlib.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.terry.weatherlib.util.Constants;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: Nov 10, 2009 5:38:54 PM
 */
public class InitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		Constants.BACKUP_MAILERS = contextEvent.getServletContext()
				.getInitParameter("backupMailers").split(",");

		test();
	}

	private void test() {

	}

}

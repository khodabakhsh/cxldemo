package web.listener.countOnLineUser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/*

HttpSessionListener,触发取决于 session 本身的产生和消亡
HttpSessionAttributeListener,触发取决于 session 中的属性变化；
 */

public class HttpSessionAttributeCreate implements ServletContextListener,
		HttpSessionListener, HttpSessionAttributeListener {
	// 声明一个ServletContext对象
	private ServletContext application = null;

	public void contextInitialized(ServletContextEvent sce) {
		// 容器初始化时，向application中存放一个空的容器
		this.application = sce.getServletContext();
		this.application.setAttribute("alluser", new ArrayList());
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void sessionCreated(HttpSessionEvent se) {
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		// 将用户名称从列表中删除
		List l = (List) this.application.getAttribute("alluser");
		String value = (String) se.getSession().getAttribute("uname");
		l.remove(value);
		this.application.setAttribute("alluser", l);
	}

	public void attributeAdded(HttpSessionBindingEvent se) {
		// 如果登陆成功，则将用户名保存在列表之中
		List l = (List) this.application.getAttribute("alluser");
		l.add(se.getValue());
		this.application.setAttribute("alluser", l);
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
	}
}

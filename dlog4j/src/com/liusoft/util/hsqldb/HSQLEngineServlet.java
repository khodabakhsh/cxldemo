/*
 *  HSQLEngineServlet.java
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *  
 *  Author: Winter Lau (javayou@gmail.com)
 *  http://dlog4j.sourceforge.net
 */
package com.liusoft.util.hsqldb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 用于启动HSQLEngine的小服务程序，由于数据库引擎必须在应用启动前初始化
 * 因此不能使用Struts的插件来启动HSQLDB
 * @author Winter Lau
 */
public class HSQLEngineServlet extends HttpServlet {

	String path = "/WEB-INF/db";
	String database = "mydlog";
	int port = 9001;
	HSQLEngine engine;
	
	/**
	 * 执行HSQLDB数据库库引擎初始化过程
	 */
	public void init() throws ServletException {
		String tPath = getInitParameter("path");
		if(StringUtils.isNotEmpty(tPath))
			path = tPath;
		String tDatabase = getInitParameter("database");
		if(StringUtils.isNotEmpty(tDatabase))
			database = tDatabase;
		String tPort = getInitParameter("port");
		if(StringUtils.isNumeric(tPort)){
			int nPort = Integer.parseInt(tPort);
			if(nPort>1024 && nPort<65536)
				port = nPort;
		}
		String dataPath;
		if(path.startsWith("/"))
			dataPath = getServletContext().getRealPath(path);
		else
			dataPath = path;
		//启动引擎
		engine = HSQLEngine.getEngine(dataPath,port,database);
		engine.start();
		while(!engine.isRunning()){
			try{
				Thread.sleep(200);
			}catch(Exception e){}
		}
		log("HSQLEngine of DLOG4J started.");
	}
	
	/**
	 * 输出HSQLEngine的状态信息
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		if(engine==null)
			out.println("HSQLDB Engine initialize failed.");
		else
		if(engine.isRunning())
			out.println("HSQLDB Engine is running...");
		else
			out.println("HSQLDB Engine is stopped.");
	}
	
	/**
	 * 停止数据库引擎
	 */
	public void destroy() {
		try{
			engine.stop();
		}catch(Exception e){}
	}
	
}

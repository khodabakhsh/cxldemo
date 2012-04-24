/*
 *  HSQLEngine.java
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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hsqldb.Server;

public class HSQLEngine {

	private static HSQLEngine engine;
	private static Server hsqldb;
	
	private HSQLEngine(){}
	/**
	 * 获得一个数据库引擎的实例，需要一个数据路径的参数 
	 * 该参数必须为物理存在的路径
	 * @param dataPath
	 * @param dbn 数据库名
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */	
	public synchronized static HSQLEngine getEngine(String dataPath, int port, String dbn)
	{
		if(engine!=null)
			return engine;		
		HSQLEngine engine = new HSQLEngine();
		if(!dataPath.endsWith(File.separator))			
			dataPath += File.separator;
		hsqldb = new Server();		
		if(port>0)
			hsqldb.setPort(port);		
		if (dbn != null){
			hsqldb.setDatabaseName(0, dbn);
			dataPath+=dbn;
		}
		hsqldb.setDatabasePath(0, dataPath);
		
		hsqldb.setSilent(true);
		hsqldb.setTrace(false);
		return engine;
	}
	
	public void start(){
		//调用HSQLDB的服务入口
		hsqldb.start();
	}
	
	public void stop(){
		hsqldb.stop();
		int i=0;
		while(i<10 && isRunning()){
			i++;
			try{
				Thread.sleep(500);
			}catch(Exception e){}
		}
	}
	
	public boolean isRunning(){
		try{
			hsqldb.checkRunning(true);
			return true;
		}catch(RuntimeException  e){
			return false;
		}
	}
	
	public String getDatabaseName() {
		return hsqldb.getDatabaseName(0,false);
	}

	public String getDataPath() {
		return hsqldb.getDatabasePath(0,false);
	}

	public int getPort() {
		return hsqldb.getPort();
	}
	
	public static void main(String[] args) throws Exception{
		HSQLEngine engine = HSQLEngine.getEngine("D:\\TEST",9001,null);
		engine.start();
		try{
			testCreateTable();
		}finally{
			engine.stop();
		}
	}
	
	public static void testCreateTable() throws Exception{
		Class.forName("org.hsqldb.jdbcDriver");
		Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost","sa","");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			//ps = conn.prepareStatement("create table dlog_bookmark (markid INTEGER,logid INTEGER,siteid INTEGER,userid INTEGER,marktype INTEGER,createTime DATE,markorder INTEGER);");
			//ps.executeUpdate();
			
			ps = conn.prepareStatement("SELECT * FROM dlog_user");
			rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("displayName"));
			}
		}finally{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
	}
}

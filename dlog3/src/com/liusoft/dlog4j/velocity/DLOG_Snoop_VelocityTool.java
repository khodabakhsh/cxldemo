/*
 *  DLOG_Snoop_VelocityTool.java
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
 *  Author: Winter Lau
 *  http://dlog4j.sourceforge.net
 *  2006-9-14
 */
package com.liusoft.dlog4j.velocity;

import java.sql.DatabaseMetaData;
import java.util.Properties;

import com.liusoft.dlog4j.dao.DAO;

/**
 * 用于获取DLOG4J运行时信息的toolbox
 * @author liudong
 */
public class DLOG_Snoop_VelocityTool{

	/**
	 * 获取数据库的元信息
	 * @return
	 */
	public DatabaseMetaData getDatabaseMetadata(){
		return DAO.metadata();
	}

	public String env(String key){
		return System.getProperty(key);		
	}
	
	public Properties envs(){
		return System.getProperties();
	}

}

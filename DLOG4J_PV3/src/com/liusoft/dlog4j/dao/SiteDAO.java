/*
 *  SiteDAO.java
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
package com.liusoft.dlog4j.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.liusoft.dlog4j.base.FunctionStatus;
import com.liusoft.dlog4j.beans.SiteBean;
import com.liusoft.dlog4j.util.StringUtils;

/**
 * 个人站点对应的数据库接口
 * SiteBean的url字段必须做唯一索引,url字段不应该由用户进行手工修改
 * @author liudong
 */
public class SiteDAO extends DAO {

	/**
	 * 搜索个人网记
	 * @param key
	 * @return
	 */
	public static List searchSite(String key){
		String pattern = '%' + key + '%';
		return executeNamedQuery("SEARCH_SITE", -1, 20, new Object[]{SiteBean.I_STATUS_NORMAL, pattern,pattern});
	}

	/**
	 * 列出最新注册的site
	 * @param fromIdx
	 * @param count
	 * @return
	 */
	public static List listNewestSites(int fromIdx , int count){
		return executeNamedQuery("LIST_NEW_SITES", fromIdx, count, SiteBean.STATUS_NORMAL);
	}
	
	/**
	 * 列出推荐的网站
	 * 也就是site_level>1的网站
	 * @param fromIdx
	 * @param count
	 * @return
	 */
	public static List listRecommendSites(int fromIdx ,int count){
		return executeNamedQuery("LIST_RECOMMEND_SITES", fromIdx, count, null);
	}
	
	/**
	 * 返回注册的站点总数
	 * @param site
	 * @return
	 */
	public static int getSiteCount(){
		return executeNamedStat("SITE_COUNT", SiteBean.STATUS_NORMAL).intValue();
	}

	/**
	 * 开通个人网记
	 * @param site
	 */
	public static void createSite(SiteBean site){
		site.setFunctionStatus(new FunctionStatus());
		Session ssn = getSession();
		try{
			beginTransaction();
			ssn.save(site);
			site.getOwner().setOwnSiteId(site.getId());
			ssn.update(site.getOwner());
			commit();			
		}catch(HibernateException e){
			rollback();
			throw e;
		}
	}
	
	/**
	 * 更新网站资料
	 * @param site
	 */
	public static void updateSite(SiteBean site){	
		flush();
	}
	
	/**
	 * 根据网站编号获取详细信息
	 * @param site_id
	 * @return
	 * @throws HibernateException
	 */
	public static SiteBean getSiteByID(int site_id){
		if(site_id<1) 
			return null;
		return (SiteBean)getBean(SiteBean.class, site_id);
	}
	
	/**
	 * 根据网站的唯一标识来获取对应网站的详细信息
	 * @param site_name
	 * @return
	 */
	public static SiteBean getSiteByName(String site_name){
		if(StringUtils.isEmpty(site_name))
			return null;
		return (SiteBean)namedUniqueResult("GET_SITE_BY_NAME", site_name);
	}

	/**
	 * 根据网站名来获取对应网站的详细信息
	 * @param site_name
	 * @return
	 */
	public static SiteBean getSiteByFriendlyName(String site_name){
		if(StringUtils.isEmpty(site_name))
			return null;
		return (SiteBean)namedUniqueResult("GET_SITE_BY_FRIENDLYNAME", site_name);
	}
	
}

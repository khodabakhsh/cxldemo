/*
 *  DAO.java
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

import java.io.Serializable;
import java.sql.DatabaseMetaData;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.liusoft.dlog4j.db.HibernateUtils;

/**
 * 所有数据库访问接口的基类
 * 
 * 人之患，在于好为人师
 * 
 * @author liudong
 */
public abstract class DAO extends _DAOBase{

	public final static int MAX_TAG_COUNT = 5;//限制每篇文章的标签最多五个
	public final static int MAX_TAG_LENGTH = 20;//标签最大长度,字节

	/**
	 * 获取数据库的元信息 
	 * @return
	 */
	public static DatabaseMetaData metadata(){
		try{
			return getSession().connection().getMetaData();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加对象
	 * @param cbean
	 */
	public static void save(Object cbean){
		try{
			Session ssn = getSession();
			beginTransaction();
			ssn.save(cbean);
			commit();
		}catch(HibernateException e){
			rollback();
			throw e;
		}
	}

	/**
	 * 添加对象
	 * @param cbean
	 */
	protected static void saveOrUpdate(Object cbean){
		try{
			Session ssn = getSession();
			beginTransaction();
			ssn.saveOrUpdate(cbean);
			commit();
		}catch(HibernateException e){
			rollback();
			throw e;
		}
	}

	/**
	 * 删除对象
	 * @param cbean
	 */
	protected static void delete(Object cbean){
		try{
			Session ssn = getSession();
			beginTransaction();
			ssn.delete(cbean);
			commit();
		}catch(HibernateException e){
			rollback();
			throw e;
		}
	}
	
	/**
	 * 根据主键删除某个对象
	 * @param objClass
	 * @param key
	 * @return
	 */
	protected static int delete(Class objClass, Serializable key){
		StringBuffer hql = new StringBuffer("DELETE FROM ");
		hql.append(objClass.getName());
		hql.append(" AS t WHERE t.id=?");
		return commitUpdate(hql.toString(), new Object[]{key});
	}

	protected static int delete(Class objClass, int key){
		return delete(objClass, new Integer(key));
	}
	
	/**
	 * 写脏数据到数据库
	 */
	public static void flush(){
		try{
			Session ssn = getSession();
			if(ssn.isDirty()){
				beginTransaction();
				ssn.flush();
				commit();
			}
		}catch(HibernateException e){
			rollback();
			throw e;
		}
	}
	
	/**
	 * 根据主键加载对象
	 * @param beanClass
	 * @param ident
	 * @return
	 */
	protected static Object getBean(Class beanClass, int id){
		return getSession().get(beanClass, new Integer(id));
	}

	/**
	 * 执行统计查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static Number executeStat(String hql, Object[] args){
		return (Number)uniqueResult(hql, args);
	}

	/**
	 * 执行统计查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static int executeStatAsInt(String hql, Object[] args){
		return (executeStat(hql, args)).intValue();
	}

	protected static int executeStatAsInt(String hql, int parm1){
		return executeStatAsInt(hql, new Object[]{new Integer(parm1)});
	}

	protected static int executeStatAsInt(String hql, int parm1, int parm2){
		return executeStatAsInt(hql, new Object[]{new Integer(parm1), new Integer(parm2)});
	}

	protected static int executeStatAsInt(String hql, int parm1, int parm2, int parm3, int parm4){
		return executeStatAsInt(hql, new Object[]{new Integer(parm1), new Integer(parm2),new Integer(parm3), new Integer(parm4)});
	}

	/**
	 * 执行统计查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static long executeStatAsLong(String hql, Object[] args){
		return (executeStat(hql, args)).longValue();
	}

	/**
	 * 执行普通查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static List findAll(String hql, Object[] args){
		return executeQuery(hql, -1, -1, args);
	}
	
	/**
	 * 执行普通查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static List executeQuery(String hql, int fromIdx, int fetchCount, Object[] args){
		Session ssn = getSession();
		Query q = ssn.createQuery(hql);
		for(int i=0;args!=null&&i<args.length;i++){
			q.setParameter(i, args[i]);
		}
		if(fromIdx > 0)
			q.setFirstResult(fromIdx);
		if(fetchCount > 0)
			q.setMaxResults(fetchCount);
		return q.list();
	}

	protected static List executeQuery(String hql, int fromIdx, int fetchCount, int parm1){
		return executeQuery(hql, fromIdx, fetchCount, new Object[]{new Integer(parm1)});
	}
	
	protected static List executeQuery(String hql, int fromIdx, int fetchCount, int parm1, int parm2){
		return executeQuery(hql, fromIdx, fetchCount, new Object[]{new Integer(parm1),new Integer(parm2)});
	}

	protected static List executeQuery(String hql, int fromIdx, int fetchCount, int parm1, int parm2, int parm3){
		return executeQuery(hql, fromIdx, fetchCount, new Object[]{new Integer(parm1),new Integer(parm2),new Integer(parm3)});
	}
	
	/**
	 * 执行更新语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static int executeUpdate(String hql, Object[] args){
		Session ssn = getSession();
		Query q = ssn.createQuery(hql);
		for(int i=0;args!=null&&i<args.length;i++){
			q.setParameter(i, args[i]);
		}
		return q.executeUpdate();
	}
	
	protected static int executeUpdate(String hql, int parm1){
		return executeUpdate(hql, new Object[]{new Integer(parm1)});
	}

	/**
	 * 执行更新语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static int commitUpdate(String hql, Object[] args){
		try{
			Session ssn = getSession();
			beginTransaction();
			Query q = ssn.createQuery(hql);
			for(int i=0;args!=null&&i<args.length;i++){
				q.setParameter(i, args[i]);
			}
			int er = q.executeUpdate();
			commit();
			return er;
		}catch(HibernateException e){
			rollback();
			throw e;
		}
	}

	protected static int commitUpdate(String hql, int parm1, int parm2){
		return commitUpdate(hql, new Object[]{new Integer(parm1),new Integer(parm2)});
	}
	
	/**
	 * 执行返回单一结果的查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static Object uniqueResult(String hql, Object[] args){
		Session ssn = getSession();
		Query q = ssn.createQuery(hql);
		for(int i=0;args!=null&&i<args.length;i++){
			q.setParameter(i, args[i]);
		}
		q.setMaxResults(1);
		return q.uniqueResult();
	}

	/**
	 * 执行统计查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static Number executeNamedStat(String hql, Object[] args){
		return (Number)namedUniqueResult(hql, args);
	}

	protected static Number executeNamedStat(String hql, int parm1){
		return executeNamedStat(hql, new Object[]{new Integer(parm1)});
	}
	
	protected static Number executeNamedStat(String hql, int parm1, int parm2){
		return executeNamedStat(hql, new Object[]{new Integer(parm1),new Integer(parm2)});
	}
	
	/**
	 * 执行统计查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static int executeNamedStatAsInt(String hql, Object[] args){
		return (executeNamedStat(hql, args)).intValue();
	}

	/**
	 * 执行统计查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static int executeNamedStatAsInt(String hql, int parm1){
		return executeNamedStatAsInt(hql, new Object[]{new Integer(parm1)});
	}
	protected static int executeNamedStatAsInt(String hql, String parm1){
		return executeNamedStatAsInt(hql, new Object[]{parm1});
	}

	protected static int executeNamedStatAsInt(String hql, int parm1, int parm2){
		return executeNamedStatAsInt(hql, new Object[]{new Integer(parm1), new Integer(parm2)});
	}

	protected static int executeNamedStatAsInt(String hql, int parm1, int parm2, int parm3){
		return executeNamedStatAsInt(hql, new Object[]{new Integer(parm1), new Integer(parm2), new Integer(parm3)});
	}

	/**
	 * 执行统计查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static long executeNamedStatAsLong(String hql, Object[] args){
		return (executeNamedStat(hql, args)).longValue();
	}

	/**
	 * 执行普通查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static List executeNamedQuery(String hql, int fromIdx, int fetchCount, Object[] args){
		Session ssn = getSession();
		Query q = ssn.getNamedQuery(hql);
		for(int i=0;args!=null&&i<args.length;i++){
			q.setParameter(i, args[i]);
		}
		if(fromIdx > 0)
			q.setFirstResult(fromIdx);
		if(fetchCount > 0)
			q.setMaxResults(fetchCount);
		return q.list();
	}

	protected static List executeNamedQuery(String hql, int fromIdx, int fetchCount, int parm1){
		return executeNamedQuery(hql, fromIdx, fetchCount, new Object[]{new Integer(parm1)});
	}

	protected static List executeNamedQuery(String hql, int fromIdx, int fetchCount, int parm1, int parm2){
		return executeNamedQuery(hql, fromIdx, fetchCount, new Object[]{new Integer(parm1), new Integer(parm2)});
	}

	protected static List executeNamedQuery(String hql, int fromIdx, int fetchCount, int parm1, int parm2, int parm3){
		return executeNamedQuery(hql, fromIdx, fetchCount, new Object[]{new Integer(parm1), new Integer(parm2), new Integer(parm3)});
	}

	/**
	 * 执行普通查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static List findNamedAll(String hql, Object[] args){
		return executeNamedQuery(hql, -1, -1, args);
	}
	
	protected static List findNamedAll(String hql, Object arg){
		return findNamedAll(hql, new Object[]{arg});
	}

	protected static List findNamedAll(String hql, int arg){
		return findNamedAll(hql, new Object[]{new Integer(arg)});
	}

	protected static List findNamedAll(String hql, int parm1, int parm2){
		return findNamedAll(hql, new Object[]{new Integer(parm1),new Integer(parm2)});
	}

	protected static List findNamedAll(String hql, int parm1, int parm2, int parm3){
		return findNamedAll(hql, new Object[]{new Integer(parm1),new Integer(parm2),new Integer(parm3)});
	}
	
	/**
	 * 执行返回单一结果的查询语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static Object namedUniqueResult(String hql, Object[] args){
		Session ssn = getSession();
		Query q = ssn.getNamedQuery(hql);
		for(int i=0;args!=null&&i<args.length;i++){
			q.setParameter(i, args[i]);
		}
		q.setMaxResults(1);
		return q.uniqueResult();
	}

	protected static Object namedUniqueResult(String hql, int parm1){
		return namedUniqueResult(hql, new Object[]{new Integer(parm1)});
	}

	protected static Object namedUniqueResult(String hql, String parm1){
		return namedUniqueResult(hql, new Object[]{parm1});
	}

	protected static Object namedUniqueResult(String hql, int parm1, int parm2){
		return namedUniqueResult(hql, new Object[]{new Integer(parm1),new Integer(parm2)});
	}

	protected static Object namedUniqueResult(String hql, int parm1, int parm2, int parm3){
		return namedUniqueResult(hql, new Object[]{new Integer(parm1),new Integer(parm2),new Integer(parm3)});
	}

	/**
	 * 执行更新语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static int executeNamedUpdate(String hql, Object[] args){
		Session ssn = getSession();
		Query q = ssn.getNamedQuery(hql);
		for(int i=0;args!=null&&i<args.length;i++){
			q.setParameter(i, args[i]);
		}
		return q.executeUpdate();
	}

	protected static int executeNamedUpdate(String hql, int parm1){
		return executeNamedUpdate(hql, new Object[]{new Integer(parm1)});
	}

	protected static int executeNamedUpdate(String hql, int parm1, int parm2){
		return executeNamedUpdate(hql, new Object[]{new Integer(parm1), new Integer(parm2)});
	}

	protected static int executeNamedUpdate(String hql, int parm1, int parm2, int parm3) {
		return executeNamedUpdate(hql, new Object[] { new Integer(parm1),
				new Integer(parm2), new Integer(parm3) });
	}

	/**
	 * 执行更新语句
	 * @param hql
	 * @param args
	 * @return
	 */
	protected static int commitNamedUpdate(String hql, Object[] args){
		try{
			Session ssn = getSession();
			beginTransaction();
			Query q = ssn.getNamedQuery(hql);
			for(int i=0;args!=null&&i<args.length;i++){
				q.setParameter(i, args[i]);
			}
			int er = q.executeUpdate();
			commit();
			return er;
		}catch(HibernateException e){
			rollback();
			throw e;
		}
	}

	protected static int commitNamedUpdate(String hql, int parm1, int parm2){
		return commitNamedUpdate(hql, new Object[]{new Integer(parm1), new Integer(parm2)});
	}
	protected static int commitNamedUpdate(String hql, int parm1, int parm2, int parm3){
		return commitNamedUpdate(hql, new Object[]{new Integer(parm1), new Integer(parm2), new Integer(parm2)});
	}
}

/**
 * 用于操作Hibernate的一些方法
 * @author Winter Lau
 */
abstract class _DAOBase {

	/**
	 * Get a instance of hibernate's session
	 * @return
	 * @throws HibernateException
	 */
	protected static Session getSession(){
		return HibernateUtils.getSession();
	}

	/**
	 * Start a new database transaction.
	 */
	protected static void beginTransaction(){
		HibernateUtils.beginTransaction();
	}

	/**
	 * Commit the database transaction.
	 */
	protected static void commit(){
		HibernateUtils.commit();
	}

	/**
	 * Rollback the database transaction.
	 */
	protected static void rollback(){
		HibernateUtils.rollback();
	}
	
}
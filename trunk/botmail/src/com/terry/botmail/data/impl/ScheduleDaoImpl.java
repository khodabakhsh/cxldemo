package com.terry.botmail.data.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.terry.botmail.data.intf.IScheduleDao;
import com.terry.botmail.model.Schedule;
import com.terry.botmail.util.EMF;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-3 下午04:32:47
 */
public class ScheduleDaoImpl implements IScheduleDao {

	EntityManager em = EMF.get().createEntityManager();

	
	public boolean saveSchedule(Schedule schedule) {
		try {
			EntityManager em = EMF.get().createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(schedule);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	
	public List<Schedule> getReadyToToSchedules() {
		Query query = em.createQuery("SELECT s FROM "
				+ Schedule.class.getName() + " s where s.sdate<=:sdate");
		Date date = new Date();
		date.setTime(date.getTime() + 180000l);
		query.setParameter("sdate", date);
		query.setMaxResults(500);
		return query.getResultList();
	}

	
	public Schedule getScheduleById(String id) {
		Key key = KeyFactory.stringToKey(id);
		if (key == null || !key.isComplete())
			return null;

		return em.find(Schedule.class, key);
	}

	
	public boolean deleteScheduleById(String id) {
		Key key = KeyFactory.stringToKey(id);
		if (key == null || !key.isComplete())
			return false;

		EntityManager em = EMF.get().createEntityManager();

		Schedule schedule = em.find(Schedule.class, key);
		if (schedule == null)
			return false;
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.remove(schedule);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	public int getScheduleCount(String account) {
		Query query = em.createQuery("SELECT count(s) FROM "
				+ Schedule.class.getName() + " s where s.account=:account");
		query.setHint("datanucleus.query.resultSizeMethod", "count");
		query.setParameter("account", account);
		return (Integer) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	
	public List<Schedule> getSchedulesByAccount(String account) {
		Query query = em.createQuery("SELECT s FROM "
				+ Schedule.class.getName() + " s where s.account=:account");
		query.setParameter("account", account);
		return query.getResultList();
	}

}

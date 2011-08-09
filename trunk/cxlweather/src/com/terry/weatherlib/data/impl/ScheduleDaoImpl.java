package com.terry.weatherlib.data.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.terry.weatherlib.data.intf.IScheduleDao;
import com.terry.weatherlib.model.Schedule;
import com.terry.weatherlib.util.EMF;

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
				+ Schedule.class.getName()
				+ " s where s.sdate<=:sdate and s.type in(1,2)");
		Date date = new Date();
		date.setTime(date.getTime() - 50000l);
		query.setParameter("sdate", date);
		query.setParameter("type", 0);
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

	public boolean deleteScheduleByIdAndAccount(String id, String account) {
		Key key = KeyFactory.stringToKey(id);
		if (key == null || !key.isComplete())
			return false;

		EntityManager em = EMF.get().createEntityManager();

		Schedule schedule = em.find(Schedule.class, key);
		if (schedule == null)
			return false;
		if (!schedule.getAccount().equals(account))
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

	public int getScheduleCountByAccount(String account) {
		Query query = em.createQuery("SELECT count(s) FROM "
				+ Schedule.class.getName() + " s where s.account=:account");
		query.setHint("datanucleus.query.resultSizeMethod", "count");
		query.setParameter("account", account);
		return (Integer) query.getSingleResult();
	}

	public int getScheduleCount() {
		Query query = em.createQuery("SELECT count(s) FROM "
				+ Schedule.class.getName() + " s");
		query.setHint("datanucleus.query.resultSizeMethod", "count");
		return (Integer) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Schedule> getSchedulesByAccount(String account, int start,
			int limit) {
		Query query = em.createQuery("SELECT s FROM "
				+ Schedule.class.getName() + " s where s.account=:account");
		query.setParameter("account", account);
		query.setFirstResult(start);
		if (limit != 0)
			query.setMaxResults(limit);
		return query.getResultList();
	}

	public boolean updateScheduleById(String id, String email, String city,
			Date sdate, int type, int days, String remark) {
		Key key = KeyFactory.stringToKey(id);
		if (key == null || !key.isComplete())
			return false;

		EntityManager em = EMF.get().createEntityManager();

		Schedule schedule = em.find(Schedule.class, key);
		if (schedule == null)
			return false;
		schedule.setEmail(email);
		schedule.setCity(city);
		schedule.setSdate(sdate);
		schedule.setType(type);
		schedule.setDays(days);
		schedule.setRemark(remark);
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(schedule);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

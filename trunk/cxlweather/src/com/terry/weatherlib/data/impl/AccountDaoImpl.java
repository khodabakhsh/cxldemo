package com.terry.weatherlib.data.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.terry.weatherlib.data.intf.IAccountDao;
import com.terry.weatherlib.model.Account;
import com.terry.weatherlib.util.EMF;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-5 上午08:58:13
 */
public class AccountDaoImpl implements IAccountDao {

	EntityManager em = EMF.get().createEntityManager();

	
	public Account getAccountByAccount(String a) {
		Query query = em.createQuery("SELECT a FROM " + Account.class.getName()
				+ " a where a.account=:account");
		query.setParameter("account", a);
		Account account = null;
		try {
			account = (Account) query.getSingleResult();
		} catch (NoResultException e) {
		}
		return account;
	}

	
	public boolean saveAccount(Account account) {
		try {
			EntityManager em = EMF.get().createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(account);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	public boolean updateAccountNickname(String a, String nickname) {
		try {
			EntityManager em = EMF.get().createEntityManager();
			Query query = em.createQuery("SELECT a FROM "
					+ Account.class.getName() + " a where a.account=:account");
			query.setParameter("account", a);
			Account account = (Account) query.getSingleResult();
			if (account == null) {
				return false;
			}
			account.setNickname(nickname);
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(account);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	public boolean updateAccountUdate(String a) {
		try {
			EntityManager em = EMF.get().createEntityManager();
			Query query = em.createQuery("SELECT a FROM "
					+ Account.class.getName() + " a where a.account=:account");
			query.setParameter("account", a);
			Account account = (Account) query.getSingleResult();
			if (account == null) {
				return false;
			}
			account.setUdate(new Date());
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(account);
			tx.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

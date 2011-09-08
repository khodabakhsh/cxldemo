package com.jeecms.bbs.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.bbs.dao.BbsUserGroupDao;
import com.jeecms.bbs.entity.BbsUserGroup;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;

@Repository
public class BbsUserGroupDaoImpl extends
		HibernateBaseDao<BbsUserGroup, Integer> implements BbsUserGroupDao {

	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@SuppressWarnings("unchecked")
	public List<BbsUserGroup> getList(Integer siteId) {
		String hql = "select bean from BbsUserGroup bean where bean.site.id=? order by bean.type, bean.point";
		return find(hql, siteId);
	}

	public BbsUserGroup getRegDef() {
		String hql = "select bean from BbsUserGroup bean where bean.default=true";
		return (BbsUserGroup) findUnique(hql);
	}

	public BbsUserGroup findById(Integer id) {
		BbsUserGroup entity = get(id);
		return entity;
	}

	public BbsUserGroup save(BbsUserGroup bean) {
		getSession().save(bean);
		return bean;
	}

	public BbsUserGroup deleteById(Integer id) {
		BbsUserGroup entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<BbsUserGroup> getEntityClass() {
		return BbsUserGroup.class;
	}
}
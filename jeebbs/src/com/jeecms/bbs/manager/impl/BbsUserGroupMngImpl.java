package com.jeecms.bbs.manager.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.bbs.dao.BbsUserGroupDao;
import com.jeecms.bbs.entity.BbsUserGroup;
import com.jeecms.bbs.manager.BbsUserGroupMng;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.entity.CmsSite;

@Service
@Transactional
public class BbsUserGroupMngImpl implements BbsUserGroupMng {

	@Transactional(readOnly = true)
	public BbsUserGroup findById(Integer id) {
		BbsUserGroup entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public BbsUserGroup getRegDef() {
		return dao.getRegDef();
	}

	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		return dao.getPage(pageNo, pageSize);
	}

	public void updateRegDef(Integer regDefId, Integer siteId) {
		if (regDefId != null) {
			for (BbsUserGroup g : getList(siteId)) {
				if (g.getId().equals(regDefId)) {
					g.setDefault(true);
				} else {
					g.setDefault(false);
				}
			}
		}
	}

	public BbsUserGroup save(BbsUserGroup bean) {
		dao.save(bean);
		return bean;
	}

	public BbsUserGroup update(BbsUserGroup bean) {
		Updater<BbsUserGroup> updater = new Updater<BbsUserGroup>(bean);
		BbsUserGroup entity = dao.updateByUpdater(updater);
		return entity;
	}

	public BbsUserGroup deleteById(Integer id) {
		BbsUserGroup bean = dao.deleteById(id);
		return bean;
	}

	public BbsUserGroup[] deleteByIds(Integer[] ids) {
		BbsUserGroup[] beans = new BbsUserGroup[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public void saveOrUpdateGroups(Integer siteId, short groupType,
			String[] name, String[] imgPath, Integer[] id, Long[] point) {
		List<BbsUserGroup> list = getList(siteId);
		if (id != null && id.length > 0) {
			for (int i = 0; i < id.length; i++) {
				BbsUserGroup group = findById(id[i]);
				if (list.contains(group)) {
					group.setName(name[i]);
					group.setImgPath(imgPath[i]);
					group.setPoint(point[i]);
					update(group);
				}
			}
			for (int i = id.length; i < name.length; i++) {
				if (name[i] == null || StringUtils.isBlank(name[i])) {
					continue;
				}
				BbsUserGroup group = new BbsUserGroup();
				group.setName(name[i]);
				group.setImgPath(imgPath[i]);
				group.setPoint(point[i]);
				group.setSite(new CmsSite(siteId));
				group.setType(groupType);
				group.setDefault(false);
				group.setGradeNum(0);
				save(group);
			}
		} else {
			for (int i = 0; i < name.length; i++) {
				if (name[i] == null || StringUtils.isBlank(name[i])) {
					continue;
				}
				BbsUserGroup group = new BbsUserGroup();
				group.setName(name[i]);
				group.setImgPath(imgPath[i]);
				group.setPoint(point[i]);
				group.setSite(new CmsSite(siteId));
				group.setType(groupType);
				group.setDefault(false);
				group.setGradeNum(0);
				save(group);
			}
		}
	}

	public List<BbsUserGroup> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	public List<BbsUserGroup> getList(Integer siteId, short groupType) {
		List<BbsUserGroup> groupList = getList(siteId);
		int i = 0;
		while (i < groupList.size()) {
			BbsUserGroup group = groupList.get(i);
			if (group.getType() != groupType) {
				groupList.remove(i);
			} else {
				i++;
			}
		}
		return groupList;
	}

	private BbsUserGroupDao dao;

	@Autowired
	public void setDao(BbsUserGroupDao dao) {
		this.dao = dao;
	}

}

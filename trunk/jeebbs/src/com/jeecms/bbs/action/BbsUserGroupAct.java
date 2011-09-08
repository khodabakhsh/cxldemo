package com.jeecms.bbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.bbs.entity.BbsUserGroup;
import com.jeecms.bbs.manager.BbsUserGroupMng;
import com.jeecms.bbs.web.CmsUtils;
import com.jeecms.core.entity.CmsSite;

@Controller
public class BbsUserGroupAct {
	private static final Logger log = LoggerFactory
			.getLogger(BbsUserGroupAct.class);

	@RequestMapping("/group/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<BbsUserGroup> list = manager.getList(site.getId());
		model.addAttribute("list", list);
		model.addAttribute("groupType", 1);
		return "group/list";
	}

	@RequestMapping("/group/v_add.do")
	public String add(ModelMap model) {
		return "group/add";
	}

	@RequestMapping("/group/v_edit.do")
	public String edit(Integer groupId, short groupType, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		BbsUserGroup group = manager.findById(groupId);
		model.put("bbsUserGroup", group);
		model.put("groupType", groupType);
		return "group/edit";
	}

	@RequestMapping("/group/o_save.do")
	public String save(String[] name, String[] imgPath, Integer[] id,
			Long[] point, short groupType, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		manager.saveOrUpdateGroups(site.getId(), groupType, name, imgPath, id,
				point);
		return "redirect:v_list.do";
	}

	@RequestMapping("/group/o_update.do")
	public String update(BbsUserGroup bean, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		bean = manager.update(bean);
		log.info("update BbsUserGroup id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequestMapping("/group/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		BbsUserGroup[] beans = manager.deleteByIds(ids);
		for (BbsUserGroup bean : beans) {
			log.info("delete BbsUserGroup id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}

	@Autowired
	private BbsUserGroupMng manager;
}

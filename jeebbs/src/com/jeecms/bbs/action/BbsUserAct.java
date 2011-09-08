package com.jeecms.bbs.action;

import static com.jeecms.common.page.SimplePage.cpn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.bbs.entity.BbsUser;
import com.jeecms.bbs.entity.BbsUserExt;
import com.jeecms.bbs.entity.BbsUserGroup;
import com.jeecms.bbs.manager.BbsUserGroupMng;
import com.jeecms.bbs.manager.BbsUserMng;
import com.jeecms.bbs.web.CmsUtils;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.core.entity.CmsSite;

@Controller
public class BbsUserAct {
	private static final Logger log = LoggerFactory
			.getLogger(BbsUserGroupAct.class);

	@RequestMapping("/user/v_list.do")
	public String list(String username, Integer groupId, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(username, null, groupId, null,
				false, cpn(pageNo), CookieUtils.getPageSize(request));
		CmsSite site = CmsUtils.getSite(request);
		List<BbsUserGroup> groupList = bbsUserGroupMng.getList(site.getId());
		model.addAttribute("groupList", groupList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("username", username);
		model.addAttribute("groupId", groupId);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pagination.getPageNo());
		return "user/list";
	}

	@RequestMapping("/user/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<BbsUserGroup> groupList = bbsUserGroupMng.getList(site.getId());
		model.addAttribute("groupList", groupList);
		return "user/add";
	}

	@RequestMapping("/user/v_edit.do")
	public String edit(Integer userId, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<BbsUserGroup> groupList = bbsUserGroupMng.getList(site.getId());
		model.addAttribute("groupList", groupList);
		model.addAttribute("cmsMember", manager.findById(userId));
		return "user/edit";
	}

	@RequestMapping("/user/o_save.do")
	public String save(BbsUser bean, BbsUserExt ext, String username,
			String email, String password, Integer groupId,
			HttpServletRequest request, ModelMap model) {
		String ip = RequestUtils.getIpAddr(request);
		bean = manager.registerMember(username, email, password, ip, groupId,
				ext);
		return "redirect:v_list.do";
	}

	@RequestMapping("/user/o_update.do")
	public String update(Integer id, String email, String password,
			Boolean disabled, BbsUserExt ext, Integer groupId,
			HttpServletRequest request, ModelMap model) {
		manager.updateMember(id, email, password, disabled, null, null, ext,
				groupId);
		return "redirect:v_list.do";
	}

	@RequestMapping("/user/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		BbsUser[] beans = manager.deleteByIds(ids);
		for (BbsUser bean : beans) {
			log.info("delete BbsUser id={}", bean.getId());
		}
		return "redirect:v_list.do";
	}

	@Autowired
	private BbsUserGroupMng bbsUserGroupMng;
	@Autowired
	private BbsUserMng manager;
}

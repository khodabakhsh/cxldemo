package com.jeecms.cms.action.admin.assist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsAcquisition;
import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.cms.manager.assist.CmsAcquisitionMng;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.CmsLogMng;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.cms.service.AcquisitionSvc;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.WebErrors;

@Controller
public class CmsAcquisitionAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsAcquisitionAct.class);

	@RequestMapping("/acquisition/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsAcquisition> list = manager.getList(site.getId());
		model.addAttribute("list", list);
		return "acquisition/list";
	}

	@RequestMapping("/acquisition/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 栏目列表
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		model.addAttribute("channelList", channelList);
		model.addAttribute("typeList", typeList);
		return "acquisition/add";
	}

	@RequestMapping("/acquisition/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 栏目列表
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		model.addAttribute("channelList", channelList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("cmsAcquisition", manager.findById(id));
		return "acquisition/edit";
	}

	@RequestMapping("/acquisition/o_save.do")
	public String save(CmsAcquisition bean, Integer channelId, Integer typeId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Integer siteId = CmsUtils.getSiteId(request);
		Integer userId = CmsUtils.getUserId(request);
		bean = manager.save(bean, channelId, typeId, userId, siteId);
		log.info("save CmsAcquisition id={}", bean.getId());
		cmsLogMng.operating(request, "cmsAcquisition.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/acquisition/o_update.do")
	public String update(CmsAcquisition bean, Integer channelId,
			Integer typeId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean, channelId, typeId);
		log.info("update CmsAcquisition id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsAcquisition.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequestMapping("/acquisition/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsAcquisition[] beans = manager.deleteByIds(ids);
		for (CmsAcquisition bean : beans) {
			log.info("delete CmsAcquisition id={}", bean.getId());
			cmsLogMng.operating(request, "cmsAcquisition.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	@RequestMapping("/acquisition/o_start.do")
	public String start(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		acquisitionSvc.start(id);
		log.info("start CmsAcquisition id={}", id);
		return "redirect:v_list.do";
	}

	@RequestMapping("/acquisition/o_end.do")
	public String end(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		manager.end(id);
		log.info("end CmsAcquisition id={}", id);
		return "redirect:v_list.do";
	}

	@RequestMapping("/acquisition/o_pause.do")
	public String pause(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		manager.pause(id);
		log.info("pause CmsAcquisition id={}", id);
		return "redirect:v_list.do";
	}

	private WebErrors validateSave(CmsAcquisition bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsAcquisition entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsAcquisition.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsAcquisition.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private AcquisitionSvc acquisitionSvc;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsAcquisitionMng manager;
}
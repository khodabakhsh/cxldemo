package com.jeecms.bbs.action.front;

import static com.jeecms.bbs.Constants.TPLDIR_POST;
import static com.jeecms.bbs.Constants.TPLDIR_TOPIC;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeecms.bbs.cache.TopicCountEhCache;
import com.jeecms.bbs.entity.BbsForum;
import com.jeecms.bbs.entity.BbsGrade;
import com.jeecms.bbs.entity.BbsPost;
import com.jeecms.bbs.entity.BbsTopic;
import com.jeecms.bbs.entity.BbsUser;
import com.jeecms.bbs.entity.BbsUserGroup;
import com.jeecms.bbs.manager.BbsConfigMng;
import com.jeecms.bbs.manager.BbsGradeMng;
import com.jeecms.bbs.manager.BbsPostMng;
import com.jeecms.bbs.manager.BbsTopicMng;
import com.jeecms.bbs.manager.BbsUserMng;
import com.jeecms.bbs.web.CmsUtils;
import com.jeecms.bbs.web.FrontUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.core.entity.CmsSite;

@Controller
public class BbsPostAct {
	private static final Logger log = LoggerFactory.getLogger(BbsPostAct.class);

	public static final String TPL_POSTADD = "tpl.postadd";
	public static final String TPL_POSTEDIT = "tpl.postedit";
	public static final String TPL_NO_LOGIN = "tpl.nologin";
	public static final String TPL_NO_URL = "tpl.nourl";
	public static final String TPL_POST_QUOTE = "tpl.postquote";
	public static final String TPL_GUANSHUI = "tpl.guanshui";
	public static final String TPL_POST_GRADE = "tpl.postgrade";
	public static final String TPL_NO_VIEW = "tpl.noview";

	@RequestMapping("/post/v_add{topicId}.jspx")
	public String add(@PathVariable Integer topicId, Integer tid,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		if (topicId == null && tid == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_POST, TPL_NO_URL);
		}
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		BbsTopic topic = null;
		if (topicId != null) {
			model.put("topicId", topicId);
			topic = bbsTopicMng.findById(topicId);
		} else if (tid != null) {
			model.put("topicId", tid);
			topic = bbsTopicMng.findById(tid);
		}
		String msg = checkReply(topic.getForum(), user, site);
		if (msg != null) {
			model.put("msg", msg);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_VIEW);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_POST, TPL_POSTADD);
	}

	@RequestMapping("/post/v_edit{id}.jspx")
	public String edit(@PathVariable Integer id, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		if (id != null) {
			String msg = checkEdit(manager.findById(id).getTopic().getForum(),
					manager.findById(id), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			model.addAttribute("post", manager.findById(id));
		} else {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_POST, TPL_NO_URL);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_POST, TPL_POSTEDIT);
	}

	@RequestMapping("/post/o_save.jspx")
	public String save(BbsPost bean, Integer topicId, String title,
			String content,
			@RequestParam(value = "code", required = false) List<String> code,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		String msg = checkReply(bbsTopicMng.findById(topicId).getForum(), user,
				site);
		if (msg != null) {
			model.put("msg", msg);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_VIEW);
		}
		boolean flag = topicCountEhCache.getLastReply(user.getId(), user
				.getGroup().getPostInterval());
		if (!flag) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_GUANSHUI);
		}
		String ip = RequestUtils.getIpAddr(request);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		bean = manager.reply(user.getId(), site.getId(), topicId, title,
				content, ip, multipartRequest.getFiles("attachment"), code);
		log.info("save BbsPost id={}", bean.getId());
		return "redirect:" + bean.getRedirectUrl();
	}

	@RequestMapping("/post/o_update.jspx")
	public String update(BbsPost bean, Integer postId, String title,
			String content, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		String msg = checkEdit(manager.findById(postId).getTopic().getForum(),
				manager.findById(postId), user, site);
		if (msg != null) {
			model.put("msg", msg);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_VIEW);
		}
		String ip = RequestUtils.getIpAddr(request);
		bean = manager.updatePost(postId, title, content, user, ip);
		log.info("update BbsPost id={}.", bean.getId());
		return "redirect:" + bean.getRedirectUrl();
	}

	@RequestMapping("/post/v_quote{postId}.jspx")
	public String quote(@PathVariable Integer postId, Integer pid,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		BbsPost post = null;
		if (postId != null) {
			post = manager.findById(postId);
		} else if (pid != null) {
			post = manager.findById(pid);
		}
		if (post != null) {
			String msg = checkReply(post.getTopic().getForum(), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			model.put("post", post);
			model.put("topicId", post.getTopic().getId());
		}
		model.put("otype", 1);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_POST, TPL_POSTADD);
	}

	@RequestMapping("/post/v_reply{postId}.jspx")
	public String reply(@PathVariable Integer postId, Integer pid,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		BbsPost post = null;
		if (postId != null) {
			post = manager.findById(postId);
		} else if (pid != null) {
			post = manager.findById(pid);
		}
		if (post != null) {
			String msg = checkReply(post.getTopic().getForum(), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			model.put("post", post);
			model.put("topicId", post.getTopic().getId());
		}
		model.put("otype", 2);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_POST, TPL_POSTADD);
	}

	@RequestMapping("/post/v_grade{postId}.jspx")
	public String grade(@PathVariable Integer postId, Integer pid,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		BbsPost post = null;
		if (postId != null) {
			post = manager.findById(postId);
		} else if (pid != null) {
			post = manager.findById(pid);
		}
		if (post != null) {
			String msg = checkGrade(post.getTopic().getForum(), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			model.put("post", post);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_POST, TPL_POST_GRADE);
	}

	@RequestMapping("/post/o_grade.jspx")
	public String gradeSubmit(BbsGrade bean, Integer postId,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		if (postId != null) {
			BbsPost post = manager.findById(postId);
			String msg = checkGrade(post.getTopic().getForum(), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			bbsGradeMng.saveGrade(bean, user, post);
			return "redirect:" + post.getRedirectUrl();
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_POST, TPL_POSTADD);
	}

	@RequestMapping("/post/v_shield{postId}.jspx")
	public String shield(@PathVariable Integer postId, Integer pid,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		BbsPost post = null;
		if (postId != null) {
			String msg = checkShield(manager.findById(postId).getTopic()
					.getForum(), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			post = manager.shield(postId, null, user);
		} else if (pid != null) {
			String msg = checkShield(manager.findById(pid).getTopic()
					.getForum(), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			post = manager.shield(pid, null, user);
		}
		return "redirect:" + post.getRedirectUrl();
	}

	@RequestMapping("/post/o_shield.jspx")
	public String shieldSubmit(Integer postId, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		BbsPost post = null;
		if (postId != null) {
			post = manager.findById(postId);
		}
		if (post != null) {
			String msg = checkShield(post.getTopic().getForum(), user, site);
			if (msg != null) {
				model.put("msg", msg);
				return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TPL_NO_VIEW);
			}
			model.put("post", post);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_POST, TPL_POSTADD);
	}

	@RequestMapping("/member/o_prohibit.jspx")
	public String prohibit(Integer postId, Integer userId,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		BbsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_LOGIN);
		}
		if (!user.getModerator()) {
			model.put("msg", "您所在会员组没有访问该页面的权限");
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TPL_NO_VIEW);
		}
		BbsUser bbsuser = bbsUserMng.findById(userId);
		BbsPost post = manager.findById(postId);
		bbsuser.setProhibitPost(BbsUser.PROHIBIT_FOREVER);
		return "redirect:" + post.getRedirectUrl();
	}

	@RequestMapping("/post/o_delete.jspx")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		BbsPost[] beans = manager.deleteByIds(ids);
		for (BbsPost bean : beans) {
			log.info("delete BbsPost id={}", bean.getId());
		}
		return null;
	}

	// public String checkIp(String ip){
	//		
	// return "";
	// }

	private String checkReply(BbsForum forum, BbsUser user, CmsSite site) {
		if (forum.getGroupReplies() == null) {
			return "您所在会员组没有访问该地址的权限";
		} else {
			BbsUserGroup group = null;
			if (user == null) {
				group = bbsConfigMng.findById(site.getId()).getDefaultGroup();
			} else {
				if (user.getProhibit()) {
					return "您已经被禁言，无权访问该页面";
				}
				group = user.getGroup();
			}
			if (group == null) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (!group.allowReply()) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (forum.getGroupReplies().indexOf("," + group.getId() + ",") == -1) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (!group.checkPostToday(user.getPostToday())) {
				return "您所在会员组今日发帖量已经达到最大";
			}
		}
		return null;
	}

	private String checkEdit(BbsForum forum, BbsPost post, BbsUser user,
			CmsSite site) {
		if (forum.getGroupReplies() == null) {
			return "您所在会员组没有访问该地址的权限";
		} else {
			if (user == null) {
				return "您所在会员组没有访问该地址的权限";
			}
			BbsUserGroup group = user.getGroup();
			if (!post.getCreater().equals(user)) {
				return "不能编辑别人的帖子";
			}
			if (forum.getGroupReplies().indexOf("," + group.getId() + ",") == -1) {
				return "您所在会员组没有访问该地址的权限";
			}
		}
		return null;
	}

	private String checkGrade(BbsForum forum, BbsUser user, CmsSite site) {
		if (forum.getGroupReplies() == null) {
			return "您所在会员组没有访问该地址的权限";
		} else {
			BbsUserGroup group = null;
			if (user == null) {
				group = bbsConfigMng.findById(site.getId()).getDefaultGroup();
			} else {
				group = user.getGroup();
			}
			if (group == null) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (forum.getGroupReplies().indexOf("," + group.getId() + ",") == -1) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (user.getGradeToday() != null
					&& user.getGradeToday() >= group.getGradeNum()) {
				return "今天您已经不能评分了";
			}
		}
		return null;
	}

	private String checkShield(BbsForum forum, BbsUser user, CmsSite site) {
		if (forum.getGroupTopics() == null) {
			return "您所在会员组没有访问该地址的权限";
		} else {
			BbsUserGroup group = null;
			if (user == null) {
				group = bbsConfigMng.findById(site.getId()).getDefaultGroup();
			} else {
				group = user.getGroup();
			}
			if (group == null) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (!group.allowTopic()) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (forum.getGroupTopics().indexOf("," + group.getId() + ",") == -1) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (!group.hasRight(forum, user)) {
				return "您所在会员组没有访问该地址的权限";
			}
			if (!group.topicShield()) {
				return "您所在会员组没有访问该地址的权限";
			}
		}
		return null;
	}

	@Autowired
	private BbsPostMng manager;
	@Autowired
	private BbsTopicMng bbsTopicMng;
	@Autowired
	private BbsGradeMng bbsGradeMng;
	@Autowired
	private BbsUserMng bbsUserMng;
	@Autowired
	private BbsConfigMng bbsConfigMng;
	@Autowired
	private TopicCountEhCache topicCountEhCache;
}

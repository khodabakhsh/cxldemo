package com.jeecms.bbs.manager.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jeecms.bbs.cache.BbsConfigEhCache;
import com.jeecms.bbs.dao.BbsTopicDao;
import com.jeecms.bbs.entity.BbsForum;
import com.jeecms.bbs.entity.BbsPost;
import com.jeecms.bbs.entity.BbsTopic;
import com.jeecms.bbs.entity.BbsUser;
import com.jeecms.bbs.manager.BbsForumMng;
import com.jeecms.bbs.manager.BbsOperationMng;
import com.jeecms.bbs.manager.BbsPostMng;
import com.jeecms.bbs.manager.BbsTopicMng;
import com.jeecms.bbs.manager.BbsUserMng;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.manager.CmsSiteMng;

@Service
@Transactional
public class BbsTopicMngImpl implements BbsTopicMng {

	public void move(Integer[] ids, Integer forumId, String reason,
			BbsUser operator) {
		BbsTopic topic;
		BbsForum origForum;
		BbsForum currForum;
		for (Integer id : ids) {
			topic = dao.findById(id);
			origForum = topic.getForum();
			if (!origForum.getId().equals(forumId)) {
				currForum = bbsForumMng.findById(forumId);
				topic.setForum(currForum);
				origForum.setTopicTotal(origForum.getTopicTotal() - 1);
				currForum.setTopicTotal(currForum.getTopicTotal() + 1);
			}
			bbsOperationMng.saveOpt(topic.getSite(), operator, "移动主题", reason,
					topic);
		}
	}

	public void shieldOrOpen(Integer[] ids, boolean shield, String reason,
			BbsUser operator) {
		BbsTopic topic;
		for (Integer id : ids) {
			topic = dao.findById(id);
			short status = topic.getStatus();
			if (shield) {
				if (status == BbsTopic.NORMAL) {
					topic.setStatus(BbsTopic.SHIELD);
				}
				bbsOperationMng.saveOpt(topic.getSite(), operator, "屏蔽主题",
						reason, topic);
			} else {
				if (status == BbsTopic.SHIELD) {
					topic.setStatus(BbsTopic.NORMAL);
				}
				bbsOperationMng.saveOpt(topic.getSite(), operator, "解除主题",
						reason, topic);
			}
		}
	}

	public void lockOrOpen(Integer[] ids, boolean lock, String reason,
			BbsUser operator) {
		BbsTopic topic;
		for (Integer id : ids) {
			topic = dao.findById(id);
			short status = topic.getStatus();
			if (lock) {
				if (status == BbsTopic.NORMAL) {
					topic.setStatus(BbsTopic.LOCKED);
				}
				bbsOperationMng.saveOpt(topic.getSite(), operator, "锁定主题",
						reason, topic);
			} else {
				if (status == BbsTopic.LOCKED) {
					topic.setStatus(BbsTopic.NORMAL);
				}
				bbsOperationMng.saveOpt(topic.getSite(), operator, "解除主题",
						reason, topic);
			}
		}
	}

	public void upOrDown(Integer[] ids, Date time, String reason,
			BbsUser operator) {
		BbsTopic topic;
		for (Integer id : ids) {
			topic = dao.findById(id);
			topic.setSortTime(time);
			bbsOperationMng.saveOpt(topic.getSite(), operator, "提升/下沉主题",
					reason, topic);
		}
	}

	public void prime(Integer[] ids, short primeLevel, String reason,
			BbsUser operator) {
		BbsTopic topic;
		for (Integer id : ids) {
			topic = dao.findById(id);
			topic.setPrimeLevel(primeLevel);
			bbsOperationMng.saveOpt(topic.getSite(), operator, "精华", reason,
					topic);
		}
	}

	public void upTop(Integer[] ids, short topLevel, String reason,
			BbsUser operator) {
		BbsTopic topic;
		for (Integer id : ids) {
			topic = dao.findById(id);
			topic.setTopLevel(topLevel);
			bbsOperationMng.saveOpt(topic.getSite(), operator, "置顶", reason,
					topic);
		}
	}

	public void highlight(Integer[] ids, String color, boolean bold,
			boolean italic, Date time, String reason, BbsUser operator) {
		BbsTopic topic;
		for (Integer id : ids) {
			topic = dao.findById(id);
			topic.setStyleColor(color);
			topic.setStyleTime(time);
			topic.setStyleBold(bold);
			topic.setStyleItalic(italic);
			bbsOperationMng.saveOpt(topic.getSite(), operator, "高亮", reason,
					topic);
		}
	}

	public BbsTopic updateTitle(Integer id, String title, BbsUser editor) {
		BbsTopic topic = dao.findById(id);
		topic.setTitle(title);
		bbsOperationMng.saveOpt(topic.getSite(), editor, "修改主题标题", null, topic);
		return topic;
	}

	public BbsTopic updateTopic(Integer id, String title, String content,
			BbsUser editor, String ip) {
		BbsTopic topic = dao.findById(id);
		topic.setTitle(title);
		bbsPostMng.updatePost(topic.getFirstPost().getId(), title, content,
				editor, ip);
		return topic;
	}

	public BbsTopic postTopic(Integer userId, Integer siteId, Integer forumId,
			String title, String content, String ip, List<MultipartFile> file,
			List<String> code) {
		BbsForum forum = bbsForumMng.findById(forumId);
		BbsUser user = bbsUserMng.findById(userId);
		BbsTopic topic = new BbsTopic();
		topic.setSite(siteMng.findById(siteId));
		topic.setForum(forum);
		topic.setCreater(user);
		topic.setLastReply(user);
		topic.setTitle(title);
		if (file != null && file.size() > 0) {
			topic.setAffix(true);
		} else {
			topic.setAffix(false);
		}
		topic.init();
		save(topic);
		BbsPost post = bbsPostMng.post(userId, siteId, topic.getId(), title,
				content, ip, file, code);
		topic.setFirstPost(post);
		updateTopicCount(topic, user);
		bbsConfigEhCache.setBbsConfigCache(1, 1, 0, 0, null, siteId);
		return topic;
	}

	@Transactional(readOnly = true)
	public Pagination getForTag(Integer siteId, Integer forumId, Short status,
			Short primeLevel, String keyWords, String creater,
			Integer createrId, Short topLevel, int pageNo, int pageSize) {
		return dao.getForTag(siteId, forumId, status, primeLevel, keyWords,
				creater, createrId, topLevel, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Pagination getMemberTopic(Integer webId, Integer memberId,
			int pageNo, int pageSize) {
		return dao.getMemberTopic(webId, memberId, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<BbsTopic> getTopTopic(Integer webId, Integer ctgId,
			Integer forumId) {
		return dao.getTopTopic(webId, ctgId, forumId);
	}

	@Transactional(readOnly = true)
	public Pagination getMemberReply(Integer webId, Integer memberId,
			int pageNo, int pageSize) {
		return dao.getMemberReply(webId, memberId, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Pagination getTopicByTime(Integer webId, int pageNo, int pageSize) {
		return dao.getTopicByTime(webId, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Pagination getForSearchDate(Integer siteId, Integer forumId,
			Short primeLevel, Integer day, int pageNo, int pageSize) {
		return dao.getForSearchDate(siteId, forumId, primeLevel, day, pageNo,
				pageSize);
	}

	public BbsTopic save(BbsTopic topic) {
		initTopic(topic);
		dao.save(topic);
		return topic;
	}

	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public BbsTopic findById(Integer id) {
		BbsTopic entity = dao.findById(id);
		return entity;
	}

	public BbsTopic update(BbsTopic bean) {
		Updater<BbsTopic> updater = new Updater<BbsTopic>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public BbsTopic deleteById(Integer id) {
		BbsTopic bean = dao.findById(id);
		bean.setFirstPost(null);
		bean.setLastPost(null);
		List<BbsPost> postList = bbsPostMng.getPostByTopic(id);
		for (BbsPost post : postList) {
			if (post.equals(bean.getForum().getLastPost())) {
				BbsPost post1 = bbsPostMng.getLastPost(bean.getForum().getId(),
						id);
				bean.getForum().setLastPost(post1);
				bean.getForum().setLastReply(post1.getCreater());
				bean.getForum().setLastTime(post1.getCreateTime());
			}
			bbsPostMng.deleteById(post.getId());
		}
		dao.deleteById(id);
		return bean;
	}

	public BbsTopic[] deleteByIds(Integer[] ids) {
		BbsTopic[] beans = new BbsTopic[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private void initTopic(BbsTopic topic) {
		Date now = new Timestamp(System.currentTimeMillis());
		topic.setCreateTime(now);
		topic.setLastTime(now);
		topic.setSortTime(now);
		topic.setViewCount(0L);
		topic.setReplyCount(0);
		topic.setStatus(BbsTopic.NORMAL);
		if (topic.getTopLevel() == null) {
			topic.setTopLevel((short) 0);
		}
		if (topic.getPrimeLevel() == null) {
			topic.setPrimeLevel((short) 0);
		}
		if (topic.getStyleBold() == null) {
			topic.setStyleBold(false);
		}
		if (topic.getStyleItalic() == null) {
			topic.setStyleItalic(false);
		}
	}

	public void updateTopicCount(BbsTopic topic, BbsUser user) {
		BbsForum forum = topic.getForum();
		forum.setLastPost(topic.getFirstPost());
		forum.setLastReply(topic.getCreater());
		forum.setLastTime(topic.getSortTime());
		forum.setPostToday(forum.getPostToday() + 1);
		forum.setPostTotal(forum.getPostTotal() + 1);
		forum.setTopicTotal(forum.getTopicTotal() + 1);
		user.setPoint(user.getPoint() + forum.getPointTopic());
		user.setTopicCount(user.getTopicCount() + 1);
		user.setPostToday(user.getPostToday() + 1);
	}

	private BbsPostMng bbsPostMng;
	private BbsForumMng bbsForumMng;
	private BbsOperationMng bbsOperationMng;
	private CmsSiteMng siteMng;
	private BbsUserMng bbsUserMng;
	private BbsConfigEhCache bbsConfigEhCache;

	private BbsTopicDao dao;

	@Autowired
	public void setDao(BbsTopicDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setBbsPostMng(BbsPostMng bbsPostMng) {
		this.bbsPostMng = bbsPostMng;
	}

	@Autowired
	public void setBbsForumMng(BbsForumMng bbsForumMng) {
		this.bbsForumMng = bbsForumMng;
	}

	@Autowired
	public void setBbsOperationMng(BbsOperationMng bbsOperationMng) {
		this.bbsOperationMng = bbsOperationMng;
	}

	@Autowired
	public void setSiteMng(CmsSiteMng siteMng) {
		this.siteMng = siteMng;
	}

	@Autowired
	public void setBbsUserMng(BbsUserMng bbsUserMng) {
		this.bbsUserMng = bbsUserMng;
	}

	@Autowired
	public void setBbsConfigEhCache(BbsConfigEhCache bbsConfigEhCache) {
		this.bbsConfigEhCache = bbsConfigEhCache;
	}

}

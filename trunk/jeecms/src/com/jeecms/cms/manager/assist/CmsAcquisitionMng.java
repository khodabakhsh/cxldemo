package com.jeecms.cms.manager.assist;

import java.util.List;

import com.jeecms.cms.entity.assist.CmsAcquisition;
import com.jeecms.cms.entity.main.Content;

public interface CmsAcquisitionMng {
	public List<CmsAcquisition> getList(Integer siteId);

	public CmsAcquisition findById(Integer id);

	public void stop(Integer id);

	public void pause(Integer id);

	public CmsAcquisition start(Integer id);

	public void end(Integer id);

	public boolean isNeedBreak(Integer id, int currNum, int currItem,
			int totalItem);

	public CmsAcquisition save(CmsAcquisition bean, Integer channelId,
			Integer typeId, Integer userId, Integer siteId);

	public CmsAcquisition update(CmsAcquisition bean, Integer channelId,
			Integer typeId);

	public CmsAcquisition deleteById(Integer id);

	public CmsAcquisition[] deleteByIds(Integer[] ids);

	public Content saveContent(String title, String txt, Integer acquId);
}
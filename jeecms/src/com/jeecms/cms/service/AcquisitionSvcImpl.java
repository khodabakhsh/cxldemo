package com.jeecms.cms.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecms.cms.entity.assist.CmsAcquisition;
import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.manager.assist.CmsAcquisitionMng;

@Service
public class AcquisitionSvcImpl implements AcquisitionSvc {
	private Logger log = LoggerFactory.getLogger(AcquisitionSvcImpl.class);

	public boolean start(Integer id) {
		CmsAcquisition acqu = cmsAcquisitionMng.findById(id);
		if (acqu == null || acqu.getStatus() == CmsAcquisition.START) {
			return false;
		}
		Thread thread = new AcquisitionThread(acqu);
		thread.start();
		return true;
	}

	private CmsAcquisitionMng cmsAcquisitionMng;

	@Autowired
	public void setCmsAcquisitionMng(CmsAcquisitionMng cmsAcquisitionMng) {
		this.cmsAcquisitionMng = cmsAcquisitionMng;
	}

	private class AcquisitionThread extends Thread {
		private CmsAcquisition acqu;

		public AcquisitionThread(CmsAcquisition acqu) {
			super(acqu.getClass().getName() + "#" + acqu.getId());
			this.acqu = acqu;
		}

		@Override
		public void run() {
			if (acqu == null) {
				return;
			}
			acqu = cmsAcquisitionMng.start(acqu.getId());
			String[] plans = acqu.getAllPlans();
			HttpClient client = new DefaultHttpClient();
			CharsetHandler handler = new CharsetHandler(acqu.getPageEncoding());
			List<String> contentList;
			String url;
			int currNum = acqu.getCurrNum();
			int currItem = acqu.getCurrItem();
			Integer acquId = acqu.getId();
			for (int i = plans.length - currNum; i >= 0; i--) {
				url = plans[i];
				contentList = getContentList(client, handler, url, acqu
						.getLinksetStart(), acqu.getLinksetEnd(), acqu
						.getLinkStart(), acqu.getLinkEnd());
				String link;
				for (int j = contentList.size() - currItem; j >= 0; j--) {
					if (cmsAcquisitionMng.isNeedBreak(acqu.getId(),
							plans.length - i, contentList.size() - j,
							contentList.size())) {
						client.getConnectionManager().shutdown();
						log.info("Acquisition#{} breaked", acqu.getId());
						return;
					}
					if (acqu.getPauseTime() > 0) {
						try {
							Thread.sleep(acqu.getPauseTime());
						} catch (InterruptedException e) {
							log.warn(null, e);
						}
					}
					link = contentList.get(j);
					saveContent(client, handler, acquId, link, acqu
							.getTitleStart(), acqu.getTitleEnd(), acqu
							.getContentStart(), acqu.getContentEnd());
				}
				currItem = 1;
			}
			client.getConnectionManager().shutdown();
			cmsAcquisitionMng.end(acqu.getId());
			log.info("Acquisition#{} complete", acqu.getId());
		}

		private List<String> getContentList(HttpClient client,
				CharsetHandler handler, String url, String linksetStart,
				String linksetEnd, String linkStart, String linkEnd) {
			List<String> list = new ArrayList<String>();
			try {
				HttpGet httpget = new HttpGet(new URI(url));
				String base = url.substring(0, url.indexOf("/", url
						.indexOf("//") + 2));
				String html = client.execute(httpget, handler);
				int start = html.indexOf(linksetStart);
				if (start == -1) {
					return list;
				}
				start += linksetStart.length();
				int end = html.indexOf(linksetEnd, start);
				if (end == -1) {
					return list;
				}
				String s = html.substring(start, end);
				start = 0;
				String link;
				log.info(s);
				while ((start = s.indexOf(linkStart, start)) != -1) {
					start += linkStart.length();
					end = s.indexOf(linkEnd, start);
					if (end == -1) {
						return list;
					} else {
						link = s.substring(start, end);
						if (!link.startsWith("http")) {
							link = base + link;
						}
						log.debug("content link: {}", link);
						list.add(link);
						start = end + linkEnd.length();
					}
				}
			} catch (Exception e) {
				log.warn(null, e);
			}
			return list;
		}

		private Content saveContent(HttpClient client, CharsetHandler handler,
				Integer acquId, String url, String titleStart, String titleEnd,
				String contentStart, String contentEnd) {
			try {
				int start, end;
				HttpGet httpget = new HttpGet(new URI(url));
				String html = client.execute(httpget, handler);
				start = html.indexOf(titleStart);
				if (start == -1) {
					return null;
				}
				start += titleStart.length();
				end = html.indexOf(titleEnd, start);
				if (end == -1) {
					return null;
				}
				String title = html.substring(start, end);

				start = html.indexOf(contentStart);
				if (start == -1) {
					return null;
				}
				start += contentStart.length();
				end = html.indexOf(contentEnd, start);
				if (end == -1) {
					return null;
				}
				String txt = html.substring(start, end);
				return cmsAcquisitionMng.saveContent(title, txt, acquId);
			} catch (Exception e) {
				log.warn(null, e);
				return null;
			}
		}
	}

	private class CharsetHandler implements ResponseHandler<String> {
		private String charset;

		public CharsetHandler(String charset) {
			this.charset = charset;
		}

		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if (!StringUtils.isBlank(charset)) {
					return EntityUtils.toString(entity, charset);
				} else {
					return EntityUtils.toString(entity);
				}
			} else {
				return null;
			}
		}
	}
}

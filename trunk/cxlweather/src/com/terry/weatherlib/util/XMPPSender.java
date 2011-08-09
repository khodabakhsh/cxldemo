package com.terry.weatherlib.util;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: Feb 4, 2010 10:06:10 PM
 */
public class XMPPSender {
	private static XMPPService xmpp = XMPPServiceFactory.getXMPPService();

	public static boolean sendXMPP(String account, String content) {
		JID jid = new JID(account);
		return sendXMPP(jid, content);
	}

	public static boolean sendXMPP(JID jid, String content) {
		try {
			if (!xmpp.getPresence(jid).isAvailable()
					&& jid.getId().indexOf("@appspot.com") == -1)
				return false;
			SendResponse status = xmpp.sendMessage(new MessageBuilder()
					.withRecipientJids(jid).withBody(content).build());
			return (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
		} catch (Exception e) {
			return false;
		}
	}
}

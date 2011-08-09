package com.terry.weatherlib.util;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-3 下午04:38:15
 */
public class MailSender {

	private static Log log = LogFactory.getLog(MailSender.class);

	public static void sendMail(String email, String sender, String subject,
			String body) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		javax.mail.Message msg = new MimeMessage(session);
		if (StringUtil.isEmptyOrWhitespace(sender))
			sender = "service";
		/**
		 * 下面的邮件地址请改成你的Gmail帐号，根据官方文档
		 * http://code.google.com/intl/zh-CN/appengine
		 * /docs/java/mail/overview.html#Email_Messages
		 * 
		 * 不能随意设置发件人
		 */
		msg.setFrom(new InternetAddress("cxdragon@gmail.com", MimeUtility
				.encodeText(sender, "UTF-8", "b")));
		msg.addRecipient(javax.mail.Message.RecipientType.TO,
				new InternetAddress(email));
		msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "b"));
		msg.setText(body);
		Transport.send(msg);
	}

	public static boolean fetchToSendMail(String urlString, String email,
			String sender, String subject, String body) {
		try {
			sendMail(email, sender, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			if (urlString != null)
//				log.warn("using backup mailer: " + urlString);
//			URL postUrl = new URL(
//					urlString == null ? "http://service.appmail.org.ru/mail"
//							: urlString);
//			HttpURLConnection connection = (HttpURLConnection) postUrl
//					.openConnection();
//			connection.setConnectTimeout(30000);
//			connection.setReadTimeout(30000);
//			connection.setDoOutput(true);
//			connection.setRequestMethod("POST");
//			connection.setUseCaches(false);
//			connection.setInstanceFollowRedirects(true);
//			connection.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
//			connection.connect();
//			DataOutputStream out = new DataOutputStream(connection
//					.getOutputStream());
//			String content = "email=" + email + "&sender="
//					+ URLEncoder.encode(sender, "UTF-8") + "&subject="
//					+ URLEncoder.encode(subject, "UTF-8") + "&body="
//					+ URLEncoder.encode(body, "UTF-8");
//			out.writeBytes(content);
//
//			out.flush();
//			out.close();
//
//			int responseCode = connection.getResponseCode();
//			connection.disconnect();
//			if (responseCode == 200) {
//				return true;
//			} else {
//				log.warn("can not send mail, responseCode:" + responseCode);
//				return false;
//			}
//		} catch (Exception e) {
//			log.warn("error fetchToSendMail, exception:" + e.getMessage());
//		}
		return false;
	}
}

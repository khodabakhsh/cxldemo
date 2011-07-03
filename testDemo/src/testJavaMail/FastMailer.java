package testJavaMail;

import getDnsIp.GetDnsIp;

import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;


/**
 * 【实现邮件的特快专递功能】
 * Foxmail有个邮件特快专递的功能，其实原理很简单，就是直接通过目标邮件地址查找到该邮箱所在的服务器的地址，然后直接通过SMTP发送邮件到这台服务器上。下面代码演示了如何在Java中实现该功能
 * 
 * 
 * 经过测试发现有少数邮件服务器会对发件者进行地址和域名的匹配验证，例如21CN，也就是说如果是要给21CN发邮件必须保证发件人邮箱的后缀与所在的机器IP地址想匹配，否则会出现如下的异常：
 * 
 * Send mail to mta.21cn.com. ...Exception in thread "main"
 * javax.mail.SendFailedException: Sending failed; nested exception is: class
 * javax.mail.MessagingException: 554 Connection refused(mx). MAIL FROM
 * [javayou@gmail.com] mismatches client IP [218.19.72.248].
 * 
 * at javax.mail.Transport.send0(Transport.java:218) at
 * javax.mail.Transport.send(Transport.java:80) at
 * com.clickcom.mail.Mailer.sendMail(Mailer.java:87) at
 * com.clickcom.mail.Mailer.main(Mailer.java:53)
 * 
 * 其他的服务基本正常，例如163.com。
 * 
 * 这东西可用来发匿名邮件
 */
public class FastMailer {

	public static void main(String[] args) throws NamingException,
			MessagingException {

		// DNS服务器，看看本机的DNS配置
		String dns = GetDnsIp.getDnsIp();
		String maitFrom = "wvdeee@gmail.com";
		String[] mailToArray = { "469399609@qq.com", "denew2000@163.com" };
		String subject = "这是猪蹄";
		String content = "邮件内容来的";

		for (String mailTo :  mailToArray) {
			quickSendMail(dns, maitFrom, mailTo, subject, content);
		}

	}

	/**
	 * @param dns
	 *            DNS服务器，具体看看本机的DNS配置
	 * @param maitFrom
	 * @param mailTo
	 * @param subject
	 * @param content
	 * @throws NamingException
	 * @throws MessagingException
	 */
	protected static void quickSendMail(String dns, String maitFrom,
			String mailTo, String subject, String content)
			throws NamingException, MessagingException {
		String domain = mailTo.substring(mailTo.indexOf('@') + 1);
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.dns.DnsContextFactory");
		env.put(Context.PROVIDER_URL, dns);
		DirContext ctx = new InitialDirContext(env);
		Attributes attr = ctx.getAttributes(domain, new String[] { "MX" });
		NamingEnumeration servers = attr.getAll();
		while (servers.hasMore()) {
			Attribute hosts = (Attribute) servers.next();
			for (int i = 0; i < hosts.size(); i++) {
				String host = (String) hosts.get(i);
				host = host.substring(host.indexOf(' ') + 1);
				System.out.print("Send mail to " + host + " ...");
				sendMail(host, maitFrom, mailTo, subject, content);
				System.out.println("OK");
				//下面这里不break的话，会连着发几封。如下，qq会发3封，网易会发4封
				 break;
				// Send mail to mx1.qq.com. ...OK
				// Send mail to mx3.qq.com. ...OK
				// Send mail to mx2.qq.com. ...OK
				// Send mail to 163mx02.mxmail.netease.com. ...OK
				// Send mail to 163mx03.mxmail.netease.com. ...OK
				// Send mail to 163mx00.mxmail.netease.com. ...OK
				// Send mail to 163mx01.mxmail.netease.com. ...OK
			}
		}
	}

	protected static void sendMail(String smtpHost, String maitFrom,
			String emailTo, String subject, String content)
			throws MessagingException {
		Properties mailProperties = System.getProperties();
		mailProperties.put("mail.smtp.host", smtpHost);
		mailProperties.put("mail.smtp.port", "25");
		mailProperties.put("mail.smtp.auth", "false");
		Session mailSession = Session.getInstance(mailProperties, null);
		// mailSession.setDebug(true);
		MimeMessage mailMessage = new MimeMessage(mailSession);

		Multipart multipart = new MimeMultipart();

		// 设置邮件的文本内容
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(content);
		multipart.addBodyPart(messageBodyPart);

		mailMessage.setContent(multipart);

		mailMessage.setSentDate(new Date());
		mailMessage.setFrom(new InternetAddress(maitFrom));
		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(
				emailTo));
		mailMessage.setSubject(subject);

		Transport.send(mailMessage);
	}
}
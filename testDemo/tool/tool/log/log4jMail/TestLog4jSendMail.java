package tool.log.log4jMail;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SMTPAppender;
/**
 * 调用log4j的api 发送邮件
 *
 */
public class TestLog4jSendMail {
	static Logger logger = Logger.getLogger(TestLog4jSendMail.class);
	SMTPAppender appender = new SMTPAppender();
	public TestLog4jSendMail() {
		try {
			appender.setSMTPUsername("username");
			appender.setSMTPPassword("password");
			appender.setTo("xxx@xxx.com");
			appender.setFrom("yyy@yyy.com");
			// SMTP服务器 smtp.163.com
			appender.setSMTPHost("smtp.163.com");
			appender.setLocationInfo(true);
			appender.setSubject("Test Mail From Log4J");
			appender.setLayout(new PatternLayout());
			appender.activateOptions();
			logger.addAppender(appender);
			logger.error("Hello World");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Printing ERROR Statements", e);
		}
	}
	public static void main(String args[]) {
		new TestLog4jSendMail();
	}
}

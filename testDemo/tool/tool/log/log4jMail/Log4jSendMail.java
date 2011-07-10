
package tool.log.log4jMail;
import org.apache.log4j.Logger;
/**
 * 查看log4j.properties
 *,发送邮件
 *准备log4j-1.2.15.jar，注意只有log4j-1.2.14.jar后的版本方支持邮件发送功能。以及邮件发送的jar包activation.jar、mail.jar
 */
public class Log4jSendMail {
	private static final Logger logger = Logger.getLogger(Log4jSendMail.class);

	public static void main(String args[]) {
		logger.error("这是来自log4j邮件测试内容");
	}
}


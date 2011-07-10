package tool.log.gtalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
/**
 * http://botalk.imincloud.com/apps
 *
 *Log4j插件

当Log4j Appender的一种实现，当日志记录异常时，可以直接发送给Gtalk帐号。如果你手机上安装了Gtalk客户端，那就相当于短信提醒哦。

首先用你的Gtalk帐号添加 log4j@imincloud.com为联系人!

log4j.properties配置:

log4j.appender.gtalk=com.imincloud.gtalkplant.GtalkAppender
log4j.appender.gtalk.to=yourgtalk@gmail.com

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] %n <%m>%n

log4j.rootLogger = error,stdout,gtalk

 */
public class GtalkAppender extends AppenderSkeleton {

	/**
	 * server
	 */
	private String server = "http://botalk.imincloud.com";

	/**
	 * api key
	 */
	private String apikey="0ccb1fdd5aa8431a82efa02d831c9198";

	/**
	 * recive user
	 */
	private String to;

	/**
	 * message title
	 */
	private String title = "Log4j Notify";

	/**
	 * The last send message time. use it to control send frz
	 */
	private long lastSendTime = 0;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private ExecutorService threadPool;

	public GtalkAppender() {
		threadPool = Executors.newSingleThreadExecutor();
	}

	protected void append(LoggingEvent event) {
		long now = System.currentTimeMillis();
		if ((now - lastSendTime) > 1000) {//igron some message
			String content = sdf.format(new Date()) + " " + event.getLevel()
					+ " [" + event.getLoggerName() + "]\n"
					+ event.getRenderedMessage() + "\n";

			if (event.getThrowableInformation() != null) {
				ThrowableInformation ti = event.getThrowableInformation();
				String[] throwableStrRep = ti.getThrowableStrRep();
				if (throwableStrRep != null) {
					int k = throwableStrRep.length;
					if (k > 3) {
						k = 3;
					}
					for (int i = 0; i < k; i++) {
						content += throwableStrRep[i] + "\n";
					}
				}
			}
			final String message = content;
			threadPool.submit(new Runnable() {
				public void run() {
					sendMessage(message);

				}
			});
		}
	}

	private void sendMessage(String content) {
		if (content.length() > 400) {
			content = content.substring(0, 400);
		}
		try {
			content = URLEncoder.encode(title + "\n" + content, "utf-8");

			String urlAddress = server + "/api/pushMsg?key=" + apikey + "&to="
					+ to + "&msg=" + content;

			String r = httpGet(urlAddress);
			if (r != null) {
				r = r.trim();
			}

			if ("403:FAILE".equals(r)) {
				System.err.println(to + " need add robot log4j@imincloud.com");
			}

			lastSendTime = System.currentTimeMillis();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getIntValue(String string) {
		return Integer.parseInt(getValue(string));
	}

	private String getValue(String string) {
		int i = string.indexOf("=");
		if (i > 0) {
			return string.substring(i + 1, string.length()).trim();
		} else {
			return null;
		}
	}

	public String httpGet(String urlAddress) throws IOException {
		URL url = new URL(urlAddress);

		InputStream in = null;
		StringBuilder sb = new StringBuilder();
		try {
			URLConnection c = url.openConnection();
			in = c.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public void close() {
		this.closed = true;
		threadPool.shutdown();
	}

	public boolean requiresLayout() {
		return false;
	}

	// ///////////////// getter and setter ////////////////////

	public String getTo() {
		return to;
	}

	public void setTo(String mids) {
		this.to = mids;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

package testFormLogin;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * httpclient用来演示登录表单的示例
 * 
 */

public class FormLoginDemo {
	static final String LOGON_SITE = "localhost";
	static final int LOGON_PORT = 8888;
	static final String WEB_ROOT_NAME = "/jiangzhong";

	public static void main(String[] args) throws Exception {
		// imitate login
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);

		PostMethod postMethod = new PostMethod(WEB_ROOT_NAME
				+ "/j_spring_security_check");
		NameValuePair name = new NameValuePair("j_username", "admin");
		NameValuePair pass = new NameValuePair("j_password", "123");
		postMethod.setRequestBody(new NameValuePair[] { name, pass });
		client.executeMethod(postMethod);

		System.err
				.println("*****************************   Request Headers   ****************************");
		for (Header reqHeader : postMethod.getRequestHeaders()) {
			System.out
					.println(reqHeader.getName() + ":" + reqHeader.getValue());
		}
		System.err
				.println("*****************************  Response StatusLine   ****************************");
		System.out.println("StatusText : " + postMethod.getStatusText()
				+ " , StatusCode : " + "" + postMethod.getStatusCode());
		System.err
				.println("*****************************   Response Headers   ****************************");
		for (Header respHeader : postMethod.getResponseHeaders()) {
			System.out.println(respHeader.getName() + ":"
					+ respHeader.getValue());
		}

		// System.out.println(post.getResponseBodyAsString());
		postMethod.releaseConnection();

		// 查看 cookie 信息
		CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
		Cookie[] cookies = cookiespec.match(LOGON_SITE, LOGON_PORT,
				WEB_ROOT_NAME, false, client.getState().getCookies());
		System.err
				.println("*****************************   cookies   ****************************");
		if (cookies.length == 0) {
			System.out.println("None");
		} else {
			for (int i = 0; i < cookies.length; i++) {
				System.out.println(cookies[i].toString());
			}
		}

		// 访问所需的页面
		GetMethod getMethod = new GetMethod(WEB_ROOT_NAME
				+ "/OA/webReport/receivedoc.jsp?processId=30");
		client.executeMethod(getMethod);
		System.err
				.println("*****************************    ResponseBody   ****************************");
		// use this
		// System.out.println(get.getResponseBodyAsString());
		// or below ,both work
		InputStreamReader iStreamReader = new InputStreamReader(
				new BufferedInputStream(getMethod.getResponseBodyAsStream()),
				"utf-8");
		StringBuilder sBuffer = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(iStreamReader);
		String strLine;
		while ((strLine = bufferedReader.readLine()) != null) {
			sBuffer.append(strLine + "\n");
		}
		System.out.println(sBuffer);

		getMethod.releaseConnection();
	}
}

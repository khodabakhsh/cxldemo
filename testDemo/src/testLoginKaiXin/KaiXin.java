package testLoginKaiXin;

import java.util.*;

import org.apache.http.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
/**
 * 登陆开心并获得一个文章页面的信息(登陆时密码加密用到服务端js，rhino)
 * @author caixl
 *
 */
public class KaiXin {

    private String userName = "469399609@qq.com";
    private String password = "6688470";

    // After you login, access the following url
    private String url = "http://www.kaixin001.com/!repaste/detail.php?uid=1058342&urpid=1717403893";

    // Don't change the following URL
    private static String kaiXinLoginURL = "http://www.kaixin001.com/login/login_api.php";

    // The HttpClient is used in one session
    private HttpResponse response;
    private DefaultHttpClient httpclient = new DefaultHttpClient();

    private String getEncryptKey() {
        HttpPost httpost = new HttpPost(kaiXinLoginURL);

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("email", userName));

        String encryptKey = "";
        try {
            httpost.setEntity(new UrlEncodedFormEntity(formparams, HTTP.UTF_8));
            response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                if (responseBody.length() > 0) {
                    String[] s = responseBody.split(":");
                    encryptKey = s[1].replace("\"", "").replace("}", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptKey;
    }

    private String getEncryptPassword(String encryptKey) {
        KaiXinJS jsExploration = new KaiXinJS();
        return jsExploration.getPassword(password, encryptKey);
    }

    public boolean login() {
        HttpPost httpost = new HttpPost(kaiXinLoginURL);

        String encryptKey = getEncryptKey();
        // All the parameters post to the web site
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("ver", "1"));
        nvps.add(new BasicNameValuePair("email", userName));
        nvps.add(new BasicNameValuePair("rpasswd",
                getEncryptPassword(encryptKey)));
        nvps.add(new BasicNameValuePair("encypt", encryptKey));
        nvps.add(new BasicNameValuePair("invisible_mode", "0"));
        nvps.add(new BasicNameValuePair("url", "/home/"));
        nvps.add(new BasicNameValuePair("remember", "1"));
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            response = httpclient.execute(httpost);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            httpost.abort();
        }
        return true;
    }

    public String getText() {
        HttpGet httpget = new HttpGet(url);

        // Create a response handler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = "";
        try {
            responseBody = httpclient.execute(httpget, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
            responseBody = null;
        } finally {
            httpget.abort();
        }
        return responseBody;
    }

    public static void main(String[] args) {
        KaiXin kaiXin = new KaiXin();
        if (kaiXin.login()) {
            System.out.println(kaiXin.getText());
        } else {
            System.out.println("Failed to login!");
        }
    }

}
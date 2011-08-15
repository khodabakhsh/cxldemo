package qqrobot;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import atg.taglib.json.JsonEntity;
import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import atg.taglib.json.util.JSONStringer;

/** 
 * QQ MINI 客户端
 * @author mrlans E-mail:mrlans@qq.com 
 * @version create Time：Dec 11, 2010 8:54:38 PM 
 *	
 */
@SuppressWarnings({"unused","deprecation"})
public class MiniQQClient
{
	
	private static int qq = 469399609;
	private static int farwardQQ = 551604104;//转发到的qq
	private String password = null;
	
	private int clientid = 73937875;
	private String psessionid = "";
	private String ptwebqq;
	private String vfwebqq;
	private String skey;
	
	private final String host = "http://d.web2.qq.com";
	
	private String refer = this.host+"/proxy.html?v=20101025002";
	
	private String cookie = "";
	
	private Map<Long, User> firends = new HashMap<Long, User>();
	private Map<Long, User> firends2 = new HashMap<Long, User>();
	
	public enum METHOD {GET, POST}
	
	private boolean run = false;
	private PollMessageThread poll = new PollMessageThread();
	public Thread getPoolThread()
	{
		return poll;
	}
	
	public MiniQQClient(int qq, String password)
	{ 
		this.qq = qq;
		this.password = password;
		
		try
		{
			boolean login = login();
			if(login)
			{
				//fetchAllOnlineFriends();
				fetchAllFriends();
				run = true;
				getPoolThread().start();
				log("QQ START SUCESS.......");
				
				sendMsgToQQ(farwardQQ, "哥上线了！");
			}
		}
		catch (Exception e)
		{
			log("QQ发生异常退出\t"+e.getMessage());
			Thread.currentThread().stop();
		}
	}
	
	public static void main(String[] args)
	{
		MiniQQClient clinet = new MiniQQClient(qq, "1111");
		try
		{
			clinet.getPoolThread().join();
		}
		catch (Exception e)
		{
			System.out.println("QQ异常退出\t"+e.getMessage());
		}
	}
	/**
	 * 读取验证码。返回验证码文件保存的路径
	 */
	private String readCheckImage(String url){
		try{ 
			System.out.println("get>>>"+url);
		 
			URL serverUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection(); 
	        conn.setRequestMethod("GET");//"POST" ,"GET" 
	        
	        conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312,
	        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
	        conn.connect();
	        //返回的cookie
	        if(conn.getHeaderFields().get("Set-Cookie") != null)
		        for(String s:conn.getHeaderFields().get("Set-Cookie")){
		        	cookie += s;
		        }
	        
	        InputStream ins =  conn.getInputStream();
	        
//	        BufferedImage bi = ImageIO.read(ins);
	        File f =new File("qqimg.jpg");
//	        ImageIO.write(bi, "jpg", f);
	        
	        return f.getAbsolutePath();
		}catch(Exception e){
			e.printStackTrace(); 
		} 
		return null;
	}
	private boolean login()
	{
		//login 1
		String checkQQUrl = "http://ptlogin2.qq.com/check?appid=1003903&uin="+qq;
		String result = sendHttpMessage(checkQQUrl, METHOD.GET.name(), null);
		Pattern p = Pattern. compile("\\,\\'([!\\w]+)\\'");
		Matcher m = p. matcher(result);
		String checkType = "";
		if(m.find())
		{
			checkType = m.group(1); 
		}
		String check = ""; 
		if(!checkType.startsWith("!"))
		{
			//需要输入验证码,生成图片验证码 
			String getCheckImageUrl = "http://captcha.qq.com/getimage?aid=1003903&uin="+qq+"&vc_type="+checkType;
			String file = readCheckImage(getCheckImageUrl);
			log("请打开"+file+"，并且在这里输入其中的字符串，然后回车：");
			InputStreamReader ins = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ins);
			try {
				check = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else
		{
			check = checkType;
		}
		
		//login 2
		String loginUrl = "http://ptlogin2.qq.com/login?u="+qq+"&" +
				"p=" +encodePass(this.password, check)+
				"&verifycode="+check+"&remember_uin=1&aid=1003903" +
				"&u1=http%3A%2F%2Fweb2.qq.com%2Floginproxy.html%3Fstrong%3Dtrue" +
				"&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert";
		
		result = sendHttpMessage(loginUrl, METHOD.GET.name(), null);
		
		p = Pattern.compile("登录成功！");
		m = p. matcher(result);
		if(m.find())
		{
			log("Welcome QQ : "+this.qq+" Login Success！"); 
		}
		else
		{
			log(checkType);
			return false;
		}
		
		//从cookie中提取ptwebqq,skey
		p = Pattern.compile("ptwebqq=(\\w+);");
		m = p.matcher(cookie);
		if(m.find())
		{
			this.ptwebqq = m.group(1);
		}
		p = Pattern.compile("skey=(@\\w+);");
		m = p.matcher(cookie);
		if(m.find())
		{
			this.skey = m.group(1);
		}
		//log("ptwebqq="+ptwebqq+",skey="+skey);
		
		//login 3
		String channelLoginUrl = this.host+"/channel/login2";
		String content = "{\"status\":\"\",\"ptwebqq\":\""+ptwebqq+"\",\"passwd_sig\":\"\",\"clientid\":\""+clientid+"\"}";
		try
		{
			content = URLEncoder.encode(content,"UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
		}
		content = "r="+content;//post的数据
		result = sendHttpMessage(channelLoginUrl, METHOD.POST.name(), content);
		
		p = Pattern.compile("\"vfwebqq\":\"(\\w+)\"");
		m = p.matcher(result);
		if(m.find())
			this.vfwebqq = m.group(1);
		p = Pattern.compile("\"psessionid\":\"(\\w+)\"");
		m = p.matcher(result);
		if(m.find())
			psessionid = m.group(1);
		//log("vwebqq="+vfwebqq);
		//log("psessionid="+psessionid);

		return true;
	}
	
	//登陆成功 取QQ好友
	public void fetchAllFriends()
	{
		String getFriendsurl = "http://web2-b.qq.com/api/get_user_friends2";
		String getFriendsurl2 = "http://web2-b.qq.com/api/get_user_friends";
		String result = fetchAllFriends(getFriendsurl);
		String result2 = fetchAllFriends(getFriendsurl2);
		//firends
		Map<String, User> user = getFriendInfo(result);  //
		Map<String, User> user2 = getFriendInfo(result2); //真正的QQ号码
		
		if(user!=null && user2!=null && user.size() == user2.size())
		{
			Set<Map.Entry<String, User>> set = user.entrySet();
			for(Iterator<Entry<String, User>> it = set.iterator(); it.hasNext();)
			{
				Entry<String, User> e = it.next();
				User u = e.getValue();
				u.setQq(user2.get(e.getKey()).getUin());
				log(u.getQq()+"\t"+u.getNick()+"\t"+u.getUin());
				firends.put(u.getQq(), u);
				firends2.put(u.getUin(), u);
			}
		}
	}
	
	//在线用户 
	public void fetchAllOnlineFriends()
	{
		String onlineUserURL = host+"/channel/get_online_buddies2";
		onlineUserURL = onlineUserURL+ "?clientid="+clientid+"&psessionid="+psessionid; 
		String  result = sendHttpMessage(onlineUserURL, METHOD.GET.name(), null);
		System.out.println(result);
	}
	
	@SuppressWarnings("unchecked")
	public  Map<String, User>  getFriendInfo(String result)
	{
		Map<String, User> users = new HashMap<String, User>(500);
		try
		{
			JSONObject retJson = new JSONObject(result);
			if( retJson.getInt("retcode") == 0)
			{
				JSONArray  infos = retJson.getJSONObject("result").getJSONArray("info");
				for(ListIterator<JSONObject> it = infos.listIterator(); it.hasNext();)
				{
					JSONObject obj = it.next();
					User user = new User(obj.getLong("uin"), obj.getString("nick"), obj.getInt("face"), obj.getLong("flag"));
					users.put(user.getNick()+user.getFlag(), user);
				}
			}
		}
		catch (Exception e)
		{
			log("getFriendInfo failure  "+e.getMessage());
		}
		return users;
	}
	
	public String fetchAllFriends(String getFriendsurl)
	{
		//{"h":"hello","vfwebqq":"7fe84931db23dc5a0351d759905642bcf5d09632e001bbfc8822809067538431d4da9dd1e8e653a0"}
		String content = "{\"h\":\"hello\",\"vfwebqq\":\""+vfwebqq+"\"}";
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
			content = "r="+content;
			String  result = sendHttpMessage(getFriendsurl, METHOD.POST.name(), content);
			//log("AllFriends= "+result);
			return result;
		}
		catch (Exception e)
		{
			log("fetchAllFriends failure.............\t"+e.getMessage());
			return null;
		}
	}
	
	public User getFriend(long qq)
	{
		return this.firends.get(qq);
	}
	

	public boolean sendMsg(long toQQ, String message)
	{
		try 
		{
			JSONObject json = new JSONObject();
			json.put("to", toQQ);//要发送的人
			json.put("face", 330);
			
			JSONArray msg = new JSONArray();
			msg.add(message);
			JSONArray font = new JSONArray();
			font.add("font");
			
			JSONObject font1 = new JSONObject().put("name", "宋体").put("size", "10");
			
			JSONArray style = new JSONArray();
			style.add(0);
			style.add(0);
			style.add(0);		
			font1.put("style", style);
			font1.put("color", "000000");
			
			font.add(font1);	 
			msg.add(font);
			
			json.put("content", msg.toString());
			json.put("msg_id", new Random().nextInt(10000000));
			json.put("clientid", this.clientid);
			json.put("psessionid", this.psessionid);//需要这个才能发送
			String sendMsgUrl = this.host+"/channel/send_msg2";
			String content = json.toString();
			 
			try
			{
				content = URLEncoder.encode(content,"UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
			}//他要需要编码
			content ="r="+content;
			//发送
			String res = sendHttpMessage(sendMsgUrl, METHOD.POST.name(), content);
			//不出意外，这是返回结果：{"retcode":0,"result":"ok"}
			if(null == res || !res.contains("result"))  return false;
			JSONObject rh = new JSONObject(res);
			if("ok".equals(rh.getString("result")))
			{
				return true;
			} 
		} 
		catch (Exception e) 
		{ 
			log("send message to "+toQQ+" failure......\n"+e.getMessage());
		}
		return false;
	}
	
	public boolean sendMsgToQQ(long qq, String message)
	{
		return sendMsg(getFriend(qq).getUin(), message);
	}
	
	//HTTP 消息发送
	public String sendHttpMessage(String url, String method, String contents)
	{
		try
		{
			log("request="+url);
			URL serverUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection(); 
			conn.setConnectTimeout(20000);
	        conn.setRequestMethod(method);//"POST" ,"GET"
	        if(null != refer)
	        	conn.addRequestProperty("Referer", refer);
	        
	        conn.addRequestProperty("Cookie", cookie);
        	conn.addRequestProperty("Connection", "Keep-Alive");
	        conn.addRequestProperty("Accept-Language", "zh-cn");
	        conn.addRequestProperty("Accept-Encoding", "gzip, deflate");
	        conn.addRequestProperty("Cache-Control", "no-cache");
	        conn.addRequestProperty("Accept-Charset", "UTF-8;");
	        conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");	        
	        if(method.equalsIgnoreCase(METHOD.GET.name()))
	        {
	        	conn.connect();
	        }
	        else if(method.equalsIgnoreCase(METHOD.POST.name()))
	        {
	        	
		        conn.setDoOutput(true); 
		        conn.connect();
		        conn.getOutputStream().write(contents.getBytes());
	        }
	        else
	        	throw new RuntimeException("your method is not implement");
	        
	       
	        if(conn.getHeaderFields().get("Set-Cookie") != null)
	        {
		        for(String s:conn.getHeaderFields().get("Set-Cookie"))
		        {
		        	cookie += s;
		        }
	        }
	        
	        InputStream ins =  conn.getInputStream();
	        
	        //处理GZIP压缩的
	        if(null != conn.getHeaderField("Content-Encoding") && conn.getHeaderField("Content-Encoding").equals("gzip"))
	        {
	        	byte[] b = null;
			    GZIPInputStream gzip = new GZIPInputStream(ins);
			    byte[] buf = new byte[1024*8];
			    int num = -1;
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    while ((num = gzip.read(buf, 0, buf.length)) != -1) 
			    {
			    	baos.write(buf, 0, num);
			    }
			    b = baos.toByteArray();
			    baos.flush();
			    baos.close();
			    gzip.close();
			    ins.close();
			    return new String(b).trim();
	        }
	        
	        String charset = "UTF-8"; 
	        InputStreamReader inr = new InputStreamReader(ins, charset);
	        BufferedReader br = new BufferedReader(inr);
	       
	        String line = "";
	        StringBuffer sb = new StringBuffer(); 
	        do
	        {
	        	sb.append(line);
	    	    line = br.readLine();
	        }while(line != null);
	        log("response="+sb);
	        return sb.toString();
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	//加密密码
	public  String encodePass(String pass, String code)
	{
		try
		{
			ScriptEngineManager m = new ScriptEngineManager();
			ScriptEngine se = m.getEngineByName("javascript");
			se.eval(new FileReader(new File(this.getClass().getClassLoader().getResource("qqrobot/1.js").getPath())));
			Object t = se.eval("md5(md5_3(\""+pass+"\")+\""+code.toUpperCase()+"\");");
			return t.toString();
		}catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return null;
	}
	

	// 记录日志
	private void log(String msg)
	{
		System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+" : "+ msg);
	}
	
	public String numToIp(Long num)
	{
		String aaa = Long.toHexString(num);
		String n1 = Integer.parseInt(aaa.substring(0,2),16)+"";
		String n2 = Integer.parseInt(aaa.substring(2,4),16)+"";
		String n3 = Integer.parseInt(aaa.substring(4,6),16)+"";
		String n4 = Integer.parseInt(aaa.substring(6),16)+"";
		
		return n1+"."+n2+"."+n3+"."+n4;
	}
	
	public void receiveMsg(JSONObject value) throws Exception
	{
		String content = value.getJSONArray("content").getString(1);
		long from_uin = value.getLong("from_uin");
		long reply_ip = value.getLong("reply_ip");
		
		sendMsg(from_uin, "此乃QQ聊天机器人程序测试中，消息会转发到QQ："+farwardQQ+" 上");
		Thread.sleep(2000);
		User u = firends2.get(from_uin);
		if(null == u)
			sendMsgToQQ(farwardQQ, "[qq = "+from_uin+"] send message  :\r\n"+content);
		else
		{
			log("receive [qq = "+u.getQq()+" and name ="+u.getNick()+"] message {"+content+"}  ~~~");
			sendMsgToQQ(farwardQQ, "[qq = "+u.getQq()+" and name ="+u.getNick()+"] send message  :\r\n"+content);
		}
	}
	
	public void changeStatus(JSONObject value) throws Exception
	{
		long from_uin = value.getLong("uin");
		String status = value.getString("status");
		User u = firends2.get(from_uin);
		log("用户："+u.getNick()+"\t"+status);
	}
	@SuppressWarnings("unchecked")
//	public Map<Long, Group> fetchAllGroups() {
//		Map<Long, Group> groups = new HashMap<Long, Group>();
//		String getGroupsurl = "http://web2-b.qq.com/api/get_group_name_list_mask2";
//		String content = "{\"vfwebqq\":\"" + vfwebqq + "\"}";
//		try {
//			content = URLEncoder.encode(content, "UTF-8");
//			content = "r=" + content;
//			String result = sendHttpMessage(getGroupsurl, METHOD.POST.name(), content);
//			JSONObject retJson = new JSONObject(result);
//			//System.out.println(result);
//			if (retJson.getInt("retcode") == 0) {
//				Iterator it = retJson.getJSONObject("result").getJSONArray("gnamelist").listIterator();
//				while(it.hasNext()){
//					JSONObject obj = (JSONObject)it.next();
//					Group group = new Group();
//					group.setGid(obj.getLong("gid"));
//					group.setCode(obj.getLong("code"));
//					group.setFlag(obj.getLong("flag"));
//					group.setName(obj.getString("name"));
//					groups.put(group.getGid(), group);
//				}
//			}
//		} catch (Exception e) {
//			log("fetchAllGroups failure.............\t" + e.getMessage());
//		}
//		return groups;
//	}

	
	class PollMessageThread extends Thread
	{
		@Override
		public void run()
		{
				String pollUrl = host+ "/channel/poll2?clientid="+clientid+"&psessionid="+psessionid; 
				while(run)
				{
					try
					{
						String ret= sendHttpMessage(pollUrl, METHOD.GET.name(), null);
						JSONObject retJ = new JSONObject(ret);
						int retcode = retJ.getInt("retcode");
						if(retcode == 0)
						{
							JSONArray result = retJ.getJSONArray("result");
							String poll_type = result.getJSONObject(0).getString("poll_type");
							JSONObject value = result.getJSONObject(0).getJSONObject("value");
							if("message".equals(poll_type))
							{//好友消息
								try
								{
									receiveMsg(value);
								}
								catch (Exception e)
								{
								}
							}
							else if("buddies_status_change".equals(poll_type))
							{//好友上下线
								changeStatus(value);
							}
							else if("group_message".equals(poll_type))
							{//群消息
								
							}
							//system_message 是系统消息
						}
						else if(retcode == 121)
						{
							run = false;
							log("QQ已经在别处登录！");
						}
					}
					catch (Exception e)
					{
						// TODO: handle exception
						log("Response PollMessage failure = "+e.getMessage());
					}
				}
		}
	}
}

@SuppressWarnings("serial")
class User implements Serializable
{
	private long uin;
	
	private long qq;
	
	private String nick;
	
	private int face;
	
	private long flag;
	
	public User()
	{
		super();
	}
	
	public User(long uin, String nick, int face, long flag)
	{
		super();
		this.uin = uin;
		this.nick = nick;
		this.face = face;
		this.flag = flag;
	}
	
	public long getUin()
	{
		return uin;
	}

	public void setUin(long uin)
	{
		this.uin = uin;
	}

	public long getQq()
	{
		return qq;
	}

	public void setQq(long qq)
	{
		this.qq = qq;
	}

	public String getNick()
	{
		return nick;
	}

	public void setNick(String nick)
	{
		this.nick = nick;
	}

	public int getFace()
	{
		return face;
	}

	public void setFace(int face)
	{
		this.face = face;
	}

	public long getFlag()
	{
		return flag;
	}

	public void setFlag(long flag)
	{
		this.flag = flag;
	}

	@Override
	public String toString()
	{
		String user = this.uin+"\t\t"+this.qq+"\t\t"+this.nick+"\t\t"+this.flag;
		return user;
	}
}


package com.bincode.util;

import com.bincode.email.MailSenderEntity;
import com.bincode.email.SimpleMailSender;

/**
 * 发送Email
 * @author bincode
 * @email	5235852@qq.com
 */
public class MailUtils {
	//这个类主要是设置邮件   
	private static MailSenderEntity zxbInfo = new MailSenderEntity(); 
	
	//这个类主要来发送邮件   
	private static SimpleMailSender sms = new SimpleMailSender();
	
	static{
		zxbInfo.setMailServerHost("smtp.qq.com");    
		zxbInfo.setMailServerPort("465");    
		zxbInfo.setValidate(true);    
		zxbInfo.setUserName("*******@qq.com");  //QQ号  
		zxbInfo.setPassword("*******");			//您的邮箱密码    
		zxbInfo.setFromAddress("*******@qq.com");    
		zxbInfo.setToAddress("*******@qq.com");    
		zxbInfo.setSubject("火车票预订通知");    
		zxbInfo.setContent("你的火车票还没有订上哦！");   
	}
		
	public static void sendMail(String title, String content){
		zxbInfo.setSubject("火车票预订通知-"+title);    
		zxbInfo.setContent(content);  
		sms.sendTextMail(zxbInfo);//发送文体格式    
	}
}

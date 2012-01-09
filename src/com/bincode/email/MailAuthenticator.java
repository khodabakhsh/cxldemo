package com.bincode.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


/**
 * 用户名密码验证
 * @author bincode
 * @email	5235852@qq.com
 */
public class MailAuthenticator extends Authenticator{   
	private String userName=null;   
	private String password=null;   

	public MailAuthenticator(String username, String password) {    
	    this.userName = username;    
	    this.password = password;    
	}
	
	protected PasswordAuthentication getPasswordAuthentication(){   
	    return new PasswordAuthentication(userName, password);   
	}   
}   

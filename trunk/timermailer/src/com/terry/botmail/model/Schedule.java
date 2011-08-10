package com.terry.botmail.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.datanucleus.jpa.annotations.Extension;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-3 下午03:35:27
 */
@Entity
public class Schedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2282192443072555696L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Enumerated
	private String account;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getAdate() {
		return adate;
	}

	public void setAdate(Date adate) {
		this.adate = adate;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	@Enumerated
	@Temporal(TemporalType.TIMESTAMP)
	private Date adate;
	@Enumerated
	@Temporal(TemporalType.TIMESTAMP)
	private Date cdate;
	@Enumerated
	@Temporal(TemporalType.TIMESTAMP)
	private Date sdate;
	@Enumerated
	private int type;// 即时发送请输入1，指定时间发送输入2，
	// 定时每天发送输入3，每周发送输入4，每月发送输入5，每年发送输入6，输入0取消
	@Enumerated
	private String subject;
	@Enumerated
	private String content;
	@Enumerated
	private String email;
	@Transient
	private String nickname;

}

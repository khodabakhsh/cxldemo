package com.terry.weatherlib.model;

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

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getDays() {
		return days;
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
	private String city;
	@Enumerated
	private String email;
	@Transient
	private String nickname;
	private int type;// 0 暂停，2 天气预报内容写在主题里，1写在正文里
	private int days;// 预报天数，0有多少报多少 其他按照数据预报
	private String remark;

}

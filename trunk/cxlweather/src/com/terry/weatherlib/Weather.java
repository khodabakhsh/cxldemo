package com.terry.weatherlib;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-24 上午09:56:08
 */
public class Weather implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3528444236459210859L;
	private String city;
	private String content;
	private String desc;

	private Date udate;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUdate() {
		return udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

	public String getReport() {
		return getReport(0);
	}

	public String getReport(int days) {
		if (!checkComplete())
			return "没有天气预报信息";
		StringBuffer sb = new StringBuffer("");
		sb.append(city).append("\r\n");
		if (days == 0)
			sb.append(content);
		else {
			String[] dc = content.split("\r\n");
			if (days >= dc.length)
				sb.append(content);
			else {
				for (int i = 0; i < days; i++) {
					sb.append(dc[i]);
					if (i != days - 1)
						sb.append("\r\n");
				}
			}
		}
		return sb.toString();
	}

	public boolean checkComplete() {
		if (content == null || city == null)
			return false;
		return true;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}

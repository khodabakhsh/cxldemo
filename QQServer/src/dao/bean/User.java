package dao.bean;

/**
 * 用户信息的封装类
 * @author Devon
 *
 */
public class User {
	
	/**
	 * QQ编号
	 */
	private String sid;

	/**
	 * 名字
	 */
	private String sname;

	/**
	 * 密码
	 */
	private String spassword;

	/**
	 * 年龄
	 */
	private String nage;

	/**
	 * 性别
	 */
	private String ssex;

	/**
	 * 地址
	 */
	private String saddress;

	/**
	 * 是否在线
	 */
	private String nisonlin;

	/**
	 * 注册时间
	 */
	private String dregtime;

	/**
	 * 获得用户的注册时间
	 * @return		用户的注册时间
	 */
	public String getDregtime() {
		return dregtime;
	}

	/**
	 * 设置用户的注册时间
	 * @param		用户的注册时间     
	 */
	public void setDregtime(String dregtime) {
		this.dregtime = dregtime;
	}

	/**
	 * 获得用户的年龄
	 * @return		用户的年龄
	 */
	public String getNage() {
		return nage;
	}

	/**
	 * 设置用户的年龄
	 * @param		用户的年龄
	 */
	public void setNage(String nage) {
		this.nage = nage;
	}

	/**
	 * 获得用户是否在线
	 * @return		是否在线
	 */
	public String getNisonlin() {
		return nisonlin;
	}

	/**
	 * 设置用户是否在线
	 * @param		是否在线
	 */
	public void setNisonlin(String nisonlin) {
		this.nisonlin = nisonlin;
	}

	/**
	 * 获得用户的地址
	 * @return		用户的地址
	 */
	public String getSaddress() {
		return saddress;
	}

	/**
	 * 设置用户的地址
	 * @param		用户的地址
	 */
	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}

	/**
	 * 获得用户的id
	 * @return		用户的id
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * 设置用户的id
	 * @param		用户的id
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * 获得用户的姓名
	 * @return用户的姓名
	 */
	public String getSname() {
		return sname;
	}

	/**
	 * 设置用户的姓名
	 * @param		用户的姓名
	 */
	public void setSname(String sname) {
		this.sname = sname;
	}

	/**
	 * 获得用户的密码
	 * @return		用户的密码
	 */
	public String getSpassword() {
		return spassword;
	}

	/**
	 * 设置用户的密码
	 * @param		用户的密码
	 */
	public void setSpassword(String spassword) {
		this.spassword = spassword;
	}

	/**
	 * 获得用户的性别
	 * @return		用户的性别
	 */
	public String getSsex() {
		return ssex;
	}

	/**
	 * 设置用户的性别
	 * @param		用户的性别
	 */
	public void setSsex(String ssex) {
		this.ssex = ssex;
	}
	
}

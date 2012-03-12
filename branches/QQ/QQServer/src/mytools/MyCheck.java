package mytools;

/**
 * 这是一个验证注册用户时输入数据是否合法的工具类
 * @author Devon
 *
 */
public class MyCheck {

	/**
	 * 验证密码
	 * @param pass
	 * @return 是否合法
	 */
	public static boolean checkPassword(String pass) {
		boolean f = false;
		String regex = "^([0-9]|[a-zA-Z]|_){3,16}$";//\\w--数字字母下划线
		if (pass != null) {
			if (pass.matches(regex)) {
				f = true;
			}
		}

		return f;
	}
	/**
	 * 验证名字
	 * @param sname
	 * @return 是否合法
	 */
	public static boolean checkSname(String sname) {
		boolean f = false;
		String regex = "^([a-zA-Z]{2,10})|([一-龥]{2,10})$";//全中文或全英文
		
		String chinese = "[^\u4e00-\u9fa5]+";//非中文
		String check = "^[一-龥]{2,10}+$";
	/*	Pattern pattern = Pattern.compile(check);
		Matcher matcher = pattern.matcher(sname);
		boolean m = matcher.matches();*/
		if (sname != null) {
			if (sname.matches(regex)) {
				f = true;
			}		
		}

		return f;
	}
	/**
	 * 验证年龄
	 * @param nage
	 * @return 是否合法
	 */
	public static boolean checkNage(String nage) {
		boolean f = false;
		String regex = "^[1-9][0-9]{1,2}$";
		if (nage != null) {
			nage = nage.trim();
			if (nage.matches(regex)) {
				int i = Integer.parseInt(nage);
				if(20<=i&&i<=150){
					f = true;
				}				
			}
		}
		return f;
	}
	
	/**
	 * 验证地址  不可以输入包含逗号(以便后面存入文本分隔使用)
	 * @param saddress
	 * @return 是否合法
	 */
	public static boolean checkSaddress(String saddress) {
		boolean f = false;
		String regex = "[^,]{0,100}";//可以不输入但是不可以输入逗号
		if (saddress != null) {
			if (saddress.matches(regex)) {
				f = true;				
			}
		}
		return f;
	}
}

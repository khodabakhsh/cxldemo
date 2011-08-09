package com.terry.weatherlib.data.intf;

import com.terry.weatherlib.model.Account;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-5 上午08:56:49
 */
public interface IAccountDao {
	public boolean saveAccount(Account account);

	public boolean updateAccountNickname(String a, String nickname);
	
	public boolean updateAccountUdate(String a);

	public Account getAccountByAccount(String a);
}

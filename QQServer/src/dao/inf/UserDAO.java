package dao.inf;

import java.util.Vector;

import dao.bean.User;

/**
 * 这是一个接口;用于定义 操作用户信息的方法.
 * 
 * @author Devon
 * 
 */
public interface UserDAO {

	/**
	 * 根据条件查询.
	 * 
	 * @param sid
	 *            用户编号;
	 * @param sname
	 *            用户名;
	 * @param state
	 *            决定显示所有用户或只显示在线的用户;
	 *            
	 * @return 用户信息集.
	 */
	public Vector<Vector<String>> selectList(String sid, String sname, int state);

	/**
	 * 根据条件查询.
	 * 
	 * @param sid
	 *            用户编号;
	 * @return 用户对象.
	 */
	public User selectList(String sid);


	/**
	 * 插入用户.
	 * 
	 * @param user
	 *            用户对象.
	 * @return 操作成功与否
	 */
	public boolean insertUser(User user);

	/**
	 * 删除用户.
	 * 
	 * @param sid
	 *            用户编号.
	 * @return 操作是否成功.
	 */
	public boolean deleteUser(String sid);

	/**
	 * 修改用户.
	 * 
	 * @param user
	 *            用户对象.
	 * @return 操作是否成功.
	 */
	public boolean updateUser(User user);

	/**
	 * 重置选中用户的密码.
	 * 
	 * @param sid
	 *            用户编号.
	 * @return 操作是否成功.
	 */
	public boolean resetPWD(String sid);
	
	/**
	 * 修改选中用户的密码.
	 * 
	 * @param sid
	 *            用户编号.
	 * @param newPWD
	 *            新密码.
	 * @return 操作是否成功.
	 */
	public boolean resetPWD(String sid, String newPWD);

	/**
	 * 重置所有用户密码.
	 * 
	 * @return 操作是否成功.
	 */
	public boolean resetAllPWD();

	/**
	 * 修改上下线.
	 * 
	 * @param sid
	 *            用户编号.
	 * @param tag
	 *            标识状态; up:上线;down下线
	 * @return 操作是否成功.
	 */
	public boolean upOrDown(String sid, String tag);

	/**
	 * 修改所有用户上下线状态.
	 * 
	 * @param tag
	 *            标识状态; up:上线;down下线
	 * @return 操作是否成功.
	 */
	public boolean upOrDown(String tag);

	/**
	 * @return 获取下一个用户编号.
	 * 
	 */
	public String getNextSid();
}
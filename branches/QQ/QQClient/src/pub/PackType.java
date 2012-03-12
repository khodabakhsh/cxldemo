package pub;
/**
 * 
 * @author Devon
 * 包类型 
 */
public enum PackType {
	
	/**
	 * 登陆申请
	 */
	loginApply,
	
	/**
	 * 登陆成功
	 */
	loginSuccess,
	
	/**
	 * 登陆失败
	 */
	loginFail,
	
	/**
	 * 私聊
	 */
	privateChat,
	
	/**
	 * 群聊
	 */
	publicChat,
	
	/**
	 * 公告
	 */
	post,
	
	/**
	 * 在线用户列表
	 */
	onlineuser,
	
	/**
	 * 强制下线
	 */
	enforceDown,
	
	/**
	 * 停止服务
	 */
	stopServer,

	/**
	 * 用户下线
	 */
	userOffline,

	/**
	 * 修改密码
	 */
	resetPassword,
}
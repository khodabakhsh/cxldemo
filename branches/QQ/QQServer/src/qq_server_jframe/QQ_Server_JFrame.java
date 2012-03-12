package qq_server_jframe;

import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.Map;


import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import qq_server_thread.Server_Read_Thread;

/**
 * 这是服务端主窗体
 * @author Devon
 *
 */
public class QQ_Server_JFrame extends JFrame{
	private ServerSocket serverSocket = null;
	
	/**
	 * 保存着id与读取线程的键值对
	 */
	private Map<String, Server_Read_Thread> serverread_Thread_Map = new Hashtable<String, Server_Read_Thread>();
	
	private ServerManager_JPanel serverManager = new ServerManager_JPanel(this);
	private UsersManager_JPanel usersManager = new UsersManager_JPanel(this);
	private LogsManager_JPanel logsManager = new LogsManager_JPanel(this);
	
	/**
	 * 创建一个服务端主窗体
	 * @param serverSocket	在登录界面创建的ServerSocekt对象
	 */
	public QQ_Server_JFrame(ServerSocket serverSocket) {
		super("QQ服务端 - Made By Devon | QQ:93785732");
		this.serverSocket = serverSocket;
//		this.usersManager.getUserInfoModify_JDialog().setVisible(false);
//		this.usersManager.getUserInfoModify_JDialog().setModal(true);

		this.makeAll();
		this.pack();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	/**
	 * 集成服务器管理，用户管理和日志管理
	 */
	private void makeAll(){
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("服务器管理",null,this.serverManager,"服务器管理");
		tabbedPane.addTab("用户管理",null,this.usersManager,"用户管理");
		tabbedPane.addTab("日志管理",null,this.logsManager,"日志管理");
		
		this.getContentPane().add(tabbedPane);
	}
	
	/**
	 * 获得服务器管理板
	 * @return
	 */
	public ServerManager_JPanel getServerManager() {
		return serverManager;
	}

	/**
	 * 获得用户管理面板
	 * @return	用户管理面板
	 */
	public UsersManager_JPanel getUsersManager() {
		return usersManager;
	}

	/**
	 * 获得日志管理面板
	 * @return	日志管理面板
	 */
	public LogsManager_JPanel getLogsManager() {
		return logsManager;
	}

	/**
	 * 获得创建的ServerSocket
	 * @return	创建的ServerSocket
	 */
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	/**
	 * 设置一个新的ServerSocket
	 * @param serverSocket
	 */
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	/**
	 * 获得id与读取线程的键值对集合
	 * @return	id与读取线程的键值对集合
	 */
	public Map<String, Server_Read_Thread> getServerread_Thread_Map() {
		return serverread_Thread_Map;
	}
}

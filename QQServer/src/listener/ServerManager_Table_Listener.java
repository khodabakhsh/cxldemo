package listener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import qq_server_jframe.QQ_Server_JFrame;
import qq_server_jframe.ServerManager_JPanel;
import qq_server_jframe.UsersManager_JPanel;

/**
 * 这是一个服务管理面板JTable选择事件的监听器，它实现了javax.swing.event.ListSelectionListener接口
 * @author Devon
 *
 */
public class ServerManager_Table_Listener implements ListSelectionListener{
	private ServerManager_JPanel serverManager_JPanel;
	private QQ_Server_JFrame qq_Server_JFrame;

	public ServerManager_Table_Listener(ServerManager_JPanel serverManager_JPanel,
			QQ_Server_JFrame qq_Server_JFrame) {
		this.serverManager_JPanel = serverManager_JPanel;
		this.qq_Server_JFrame = qq_Server_JFrame;
	}

	/**
	 * 定义了一些JTable选中状态或清除选中状态时的响应
	 */
	public void valueChanged(ListSelectionEvent e) {
		int selectRow = serverManager_JPanel.getQQUsersInfo_JTable().getSelectedRow();
		if(selectRow == -1){
			serverManager_JPanel.getButton_Offline().setEnabled(false);
		}else{
			serverManager_JPanel.getButton_Offline().setEnabled(true);
		}
		
	}

}

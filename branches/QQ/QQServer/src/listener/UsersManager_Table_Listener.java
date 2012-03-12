package listener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import qq_server_jframe.QQ_Server_JFrame;
import qq_server_jframe.UsersManager_JPanel;

/**
 * 这是一个用户面板JTablet选择事件的监听器，它实现了javax.swing.event.ListSelectionListener接口
 * @author Devon
 *
 */
public class UsersManager_Table_Listener implements ListSelectionListener{
	private UsersManager_JPanel usersManager_JPanel;
	private QQ_Server_JFrame qq_Server_JFrame;

	public UsersManager_Table_Listener(UsersManager_JPanel usersManager_JPanel,
			QQ_Server_JFrame qq_Server_JFrame) {
		this.usersManager_JPanel = usersManager_JPanel;
	}

	/**
	 * 定义了一些JTable选中状态或清除选中状态时的响应
	 */
	public void valueChanged(ListSelectionEvent e) {
		int selectRow = usersManager_JPanel.getQQUsersInfo_JTable().getSelectedRow();
		if(selectRow == -1){
			this.usersManager_JPanel.getButton_Del().setEnabled(false);
			this.usersManager_JPanel.getButton_Mod().setEnabled(false);
			this.usersManager_JPanel.getButton_Sel().setEnabled(false);
		}else{
			this.usersManager_JPanel.getButton_Del().setEnabled(true);
			this.usersManager_JPanel.getButton_Mod().setEnabled(true);
			this.usersManager_JPanel.getButton_Sel().setEnabled(true);
		}
		
	}

}

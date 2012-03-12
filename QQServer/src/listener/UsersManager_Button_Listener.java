package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import qq_server_jdialog.UserInfo_JDialog;
import qq_server_jframe.QQ_Server_JFrame;
import qq_server_jframe.UsersManager_JPanel;

import dao.impl.UserDaoImpl;

/**
 * 这是一个用户管理面板按钮的监听器，它实现了java.awt.event.ActionListener接口
 * @author Devon
 *
 */
public class UsersManager_Button_Listener implements ActionListener {
	private UsersManager_JPanel usersManager_JPanel;
	private QQ_Server_JFrame qq_Server_JFrame;

	public UsersManager_Button_Listener(
			UsersManager_JPanel usersManager_JPanel,
			QQ_Server_JFrame qq_Server_JFrame) {
		this.usersManager_JPanel = usersManager_JPanel;
		this.qq_Server_JFrame = qq_Server_JFrame;
	}

	/**
	 * 定义了一些按钮按下时做出的响应
	 */
	public void actionPerformed(ActionEvent e) {

		// 添加用户
		if (usersManager_JPanel.getButton_Add() == e.getSource()) {
			new UserInfo_JDialog(usersManager_JPanel, qq_Server_JFrame,
					UserInfo_JDialog.USER_INFO_ADD);
		// 重置所有用户密码
		} else if (usersManager_JPanel.getButton_All() == e.getSource()) {
			if(qq_Server_JFrame.getServerread_Thread_Map().size() != 0){
				JOptionPane.showMessageDialog(qq_Server_JFrame, "系统当前有用户在线,请先强制所有在线用户下线后再试!");
				return;
			}
			int result = JOptionPane.showConfirmDialog(usersManager_JPanel,
					"确定要重置所有用户密码吗？", "重置所有用户密码", JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				UserDaoImpl.getInstance().resetAllPWD();
				usersManager_JPanel.getQQUsersInfo_JTable().clearSelection();
				usersManager_JPanel.getButton_Del().setEnabled(false);
				usersManager_JPanel.getButton_Mod().setEnabled(false);
				usersManager_JPanel.getButton_Sel().setEnabled(false);
				JOptionPane.showMessageDialog(usersManager_JPanel, "密码重置成功！");
			} else {
				return;
			}
		// 查询
		} else if (usersManager_JPanel.getButton_Query() == e.getSource()) {
			String str_ID = usersManager_JPanel.getTextField_ID().getText()
					.replaceAll(" ", "");
			String str_Name = usersManager_JPanel.getTextField_Name().getText()
					.replaceAll(" ", "");
			Vector<Vector<String>> data = UserDaoImpl.getInstance().selectList(str_ID,
					str_Name, UserDaoImpl.UP_AND_DOWN);
			usersManager_JPanel.updateQQUsersInfo_JTable(data);
			usersManager_JPanel.getTextField_ID().setText(str_ID);
			usersManager_JPanel.getTextField_Name().setText(str_Name);

		} else {
			// 先获得用户的在线状态
			int row = usersManager_JPanel.getQQUsersInfo_JTable()
					.getSelectedRow();
			String sidSelect = usersManager_JPanel.getQQUsersInfo_JTable()
					.getValueAt(row, 0).toString();
			String name = UserDaoImpl.getInstance().selectList(sidSelect)
					.getSname();
			String message = "用户" + name + "(" + sidSelect + ")"
					+ "已登录,请强制其下线后再试!";
			String isOnline = UserDaoImpl.getInstance().selectList(sidSelect)
					.getNisonlin();

			// 用户在线时 提示其不可修改
			if (isOnline.equals("在线")) {
				JOptionPane.showMessageDialog(usersManager_JPanel, message);
				return;
			}
			// 删除用户
			if (usersManager_JPanel.getButton_Del() == e.getSource()) {
				int result = JOptionPane.showConfirmDialog(usersManager_JPanel,
						"确定要删除用户" + sidSelect + "吗？", "删除用户",
						JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					UserDaoImpl.getInstance().deleteUser(sidSelect);
					usersManager_JPanel.updateQQUsersInfo_JTable();
					JOptionPane.showMessageDialog(usersManager_JPanel, "用户" + sidSelect + "已删除！");
					usersManager_JPanel.getQQUsersInfo_JTable().clearSelection();
				} else {
					return;
				}
			// 修改用户
			} else if (usersManager_JPanel.getButton_Mod() == e.getSource()) {
				new UserInfo_JDialog(usersManager_JPanel, qq_Server_JFrame,
						UserInfo_JDialog.USER_INFO_MOD);
			// 重置所选用户密码
			} else if (usersManager_JPanel.getButton_Sel() == e.getSource()) {
				int result = JOptionPane.showConfirmDialog(usersManager_JPanel,"确定要重置用户" + sidSelect + "的密码吗？", "重置所选用户密码",JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					UserDaoImpl.getInstance().resetPWD(sidSelect);
					usersManager_JPanel.getQQUsersInfo_JTable().clearSelection();
					JOptionPane.showMessageDialog(usersManager_JPanel,"密码重置成功！");
				} else {
					return;
				}

			}

		}

	}
}

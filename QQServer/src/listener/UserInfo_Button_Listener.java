package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import mytools.MyCheck;

import qq_server_jdialog.UserInfo_JDialog;
import qq_server_jframe.QQ_Server_JFrame;

import dao.bean.User;
import dao.impl.UserDaoImpl;

/**
 * 这是一个添加用户和修改用户对话框的监听器，它实现了java.awt.event.ActionListener接口
 * @author Devon
 *
 */
public class UserInfo_Button_Listener implements ActionListener{
	private UserInfo_JDialog userInfo_JDialog;
	private QQ_Server_JFrame qq_Server_JFrame;
	
	public UserInfo_Button_Listener(UserInfo_JDialog userInfo_JDialog,
			QQ_Server_JFrame qq_Server_JFrame) {
		this.userInfo_JDialog = userInfo_JDialog;
		this.qq_Server_JFrame = qq_Server_JFrame;
	}
	
	/**
	 * 定义了一些按钮按下时做出的响应
	 */
	public void actionPerformed(ActionEvent e) {
		if (this.userInfo_JDialog.getButton_Save() == e.getSource()) {
			// 从对话框中获得各项信息
			String str_ID = this.userInfo_JDialog.getTextField_ID().getText();
			char[] ch_Password = this.userInfo_JDialog.getPasswordField_Password().getPassword();
			String str_Password = String.valueOf(ch_Password);
			char[] ch_PasswordAgn = this.userInfo_JDialog.getPasswordField_PasswordAgn().getPassword();
			String str_PasswordAgn = String.valueOf(ch_PasswordAgn);
			String str_Name = this.userInfo_JDialog.getTextField_Name().getText();
			String str_Sex;
			if (this.userInfo_JDialog.getRadioButton_Male().isSelected()) {
				str_Sex = "男";
			} else {
				str_Sex = "女";
			}
			String str_Age = this.userInfo_JDialog.getTextField_Age().getText();
			String str_Address = this.userInfo_JDialog.getTextField_Address().getText();
			String str_IsOnline = this.userInfo_JDialog.getComboBox_IsOnline().getSelectedItem().toString();
			String str_RegTime = this.userInfo_JDialog.getTextField_RegTime().getText();
			// 判断注册信息是否符合要求 ,为true时符合要求
			boolean flag_Password, flag_Name, flag_Age, flag_Address;
			if (str_Password.matches("[a-z|A-Z|0-9|_]{3,16}")) {
				if (str_Password.equals(str_PasswordAgn)) {
					flag_Password = true;
				} else {
					flag_Password = false;
				}
			} else {
				flag_Password = false;
			}
			if(flag_Password == false){
				this.userInfo_JDialog.getLabel_Message().setText("密码长度必须在 3~16 位之间 (只允许是数字,字母或 _)");
				return;
			}

			if (str_Name.matches("[\\u4e00-\\u9fa5]{2,10}")) {
				flag_Name = true;
			} else {
				flag_Name = false;
			}
			if(flag_Name == false){
				this.userInfo_JDialog.getLabel_Message().setText("真实姓名字数需在 2~10 个字之间 (必须是中文)");
				return;
			}

			if (str_Age.matches("\\d{2,3}") && Integer.parseInt(str_Age) >= 20
					&& Integer.parseInt(str_Age) <= 150) {
				flag_Age = true;
			} else {
				flag_Age = false;
			}
			if(flag_Age == false){
				this.userInfo_JDialog.getLabel_Message().setText("年龄只能为数字且必须在 20~150 之间 ");
				return;
			}
			
			if (MyCheck.checkSaddress(str_Address)) {
				flag_Address = true;
			} else {
				flag_Address = false;
			}
			if(flag_Address == false){
				this.userInfo_JDialog.getLabel_Message().setText("地址字数不能大于 100 个,且不能包含\",\"");
				return;
			}

/*			// 验证失败，给出提示
			if (flag_Password == false || flag_Name == false
					|| flag_Age == false || flag_Address == false) {
				// 定制消息内容，false返回相关错误消息，true返回空字符串
				String msg_Password = (flag_Password == true) ? ""
						: "密码长度必须在 3~16 位之间 (只允许是数字，字母 ， _ )" + "\n";
				String msg_Name = (flag_Name == true) ? ""
						: "真实姓名字数需在 2~10 个字之间 (必须是中文)" + "\n";
				String msg_Age = (flag_Age == true) ? ""
						: "年龄只能为数字且必须在 20~150 之间 " + "\n";
				String msg_Address = (flag_Address == true) ? ""
						: "地址字数不能大于 100 个";
				JOptionPane.showMessageDialog(userInfoAdd_JDialog, "失败原因:"
						+ "\n" + msg_Password + msg_Name + msg_Age
						+ msg_Address, "注册失败", JOptionPane.ERROR_MESSAGE);
				// 验证成功，将用户添加到数据库
			} else {*/
				User user = new User();
				user.setSid(str_ID);
				user.setSpassword(str_Password);
				user.setSname(str_Name);
				user.setSsex(str_Sex);
				user.setNage(str_Age);
				user.setSaddress(str_Address);
				user.setNisonlin("离线");
				user.setDregtime(str_RegTime);
				if(this.userInfo_JDialog.getTitle().equals("添加用户")){
					UserDaoImpl.getInstance().insertUser(user);
					JOptionPane.showMessageDialog(userInfo_JDialog, "注册成功！","注册成功", JOptionPane.INFORMATION_MESSAGE);
				}else{
					UserDaoImpl.getInstance().updateUser(user);
					JOptionPane.showMessageDialog(userInfo_JDialog, "修改成功！","修改成功", JOptionPane.INFORMATION_MESSAGE);
				}
				
				this.qq_Server_JFrame.getUsersManager().updateQQUsersInfo_JTable();
				this.userInfo_JDialog.dispose();
//			}

		} else if (this.userInfo_JDialog.getButton_Cancel() == e.getSource()) {
			this.userInfo_JDialog.dispose();
		}
		
	}

}

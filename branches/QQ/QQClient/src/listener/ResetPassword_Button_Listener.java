package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JLabel;

import pub.PackType;
import pub.QQPackage;

import mytools.MyCheck;

import qq_client_jdialog.ResetPassword_JDialog;
import qq_client_jframe.QQ_Chat_JFrame;
/**
 * 这是一个按钮事件的监听器类，它实现了java.awt.event.ActionListener接口
 * @author Devon
 *
 */
public class ResetPassword_Button_Listener implements ActionListener{
	
	private ResetPassword_JDialog resetPassword_JDialog = null;
	private  QQ_Chat_JFrame qq_Chat_JFrame = null;

	/**
	 * 构造一个按钮事件的监听器
	 * @param resetPassword_JDialog		使监听器可以对resetPassword_JDialog中的组件进行操作
	 * @param qq_Chat_JFrame	使监听器可以对qq_Chat_JFrame中的组件进行操作
	 */
	public ResetPassword_Button_Listener(ResetPassword_JDialog resetPassword_JDialog, QQ_Chat_JFrame qq_Chat_JFrame) {
		this.resetPassword_JDialog = resetPassword_JDialog;
		this.qq_Chat_JFrame = qq_Chat_JFrame;
	}

	/**
	 * 定义了各个按钮按下时做的响应
	 */
	public void actionPerformed(ActionEvent e) {
		
		//确定
		if(e.getSource() == resetPassword_JDialog.getButton_Confirm()){
			JLabel message = resetPassword_JDialog.getLabel_Message();
			char[] oldChar = resetPassword_JDialog.getPasswordField_Old().getPassword();
			String oldPsw = String.valueOf(oldChar);
			char[] newChar = resetPassword_JDialog.getPasswordField_New().getPassword();
			String newPsw = String.valueOf(newChar);
			char[] newAgnChar = resetPassword_JDialog.getPasswordField_NewAgn().getPassword();
			String newPswAgn = String.valueOf(newAgnChar);
			//未输入原密码
			if(oldPsw == null){
				message.setText("请输入原密码!");
				return;
			}
			//未输入新密码
			if(oldPsw == null){
				message.setText("请输入新密码!");
				return;
			}
			//未输入确认新密码
			if(oldPsw == null){
				message.setText("请确认新密码!");
				return;
			}
			//排除非法新密码
			if (!MyCheck.checkPassword(newPsw)){
				message.setText("密码长度必须在3~16位之间(只允许是数字,字母或 _)");
				return;
			}
			//排除非法确认新密码
			if (!MyCheck.checkPassword(newPswAgn)){
				message.setText("密码长度必须在3~16位之间(只允许是数字,字母或 _)");
				return;
			}
			//排除两次新密码不一致
			if (!newPsw.equals(newPswAgn)){
				message.setText("您两次输入的新密码不一致!");
				return;
			}
			//封装修改密码包
			Vector<String> psw = new Vector<String>();
			psw.add(oldPsw);
			psw.add(newPsw);
			QQPackage qqPackageResPsw = new QQPackage();
			qqPackageResPsw.setPackType(PackType.resetPassword);
			qqPackageResPsw.setFrom(qq_Chat_JFrame.getID());
			qqPackageResPsw.setData(psw);
			//发包
			ObjectOutputStream objectOutputStream = qq_Chat_JFrame.getObjectOutputStream();
			try {
				objectOutputStream.writeObject(qqPackageResPsw);
				objectOutputStream.flush();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			
		//取消
		}else if(e.getSource() == resetPassword_JDialog.getButton_Cancle()){
			resetPassword_JDialog.dispose();
		}
		
	}

}

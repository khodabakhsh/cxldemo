package qq_client_thread;

import java.io.IOException;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import pub.PackType;
import pub.QQPackage;

import qq_client_jframe.QQ_Chat_JFrame;
/**
 * 这是一个客户端的读取线程，它继承自java.lang.Thread
 * @author Devon
 *
 */
public class Client_Read_Thread extends Thread {
	private QQ_Chat_JFrame qq_Chat_JFrame = null;
	
	/**
	 * 创建一个客户端读取线程
	 * @param qq_Chat_JFrame	所对应的聊天窗体
	 * @param socket		与服务器连接的socket
	 */
	public Client_Read_Thread(QQ_Chat_JFrame qq_Chat_JFrame, Socket socket) {
		this.qq_Chat_JFrame = qq_Chat_JFrame;
		
	}

	/**
	 * 重写父类中的run方法
	 */
	public void run() {
		
		QQPackage qqPackageRec = null;
		PackType packType = null;
		Object object = null;
		
		while (!Thread.interrupted()) {
			try {
				qqPackageRec = (QQPackage) qq_Chat_JFrame.getObjectInputStream().readObject();
				packType = qqPackageRec.getPackType();
				object = qqPackageRec.getData();
			} catch (IOException e) {
				//对方断电等意外情况
				JOptionPane.showMessageDialog(qq_Chat_JFrame, "服务器关闭,您已被迫下线!");
				System.exit(0);
				
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
			}

			// 聊天包
			if (packType == PackType.publicChat) {
				String message = object.toString();
				qq_Chat_JFrame.getTextArea_Dsp().append(message);
				qq_Chat_JFrame.getTextArea_ChatLogs().append(message);
			// 在线用户列表包
			} else if (packType == PackType.onlineuser) {
				DefaultListModel defaultListModel = (DefaultListModel) qqPackageRec.getData();
				qq_Chat_JFrame.getList_OnlineUsers().setModel(defaultListModel);
				qq_Chat_JFrame.validate();
			// 服务器停止包
			} else if (packType == PackType.stopServer){
				String message = qqPackageRec.getData().toString();
				JOptionPane.showMessageDialog(qq_Chat_JFrame, message);
				qq_Chat_JFrame.dispose();
				System.exit(0);
			// 公告包
			} else if (packType == PackType.post){
				String message = qqPackageRec.getData().toString();
				qq_Chat_JFrame.getTextArea_Notice().setText(message);
			// 下线包
			} else if (packType == PackType.enforceDown){
				String message = qqPackageRec.getData().toString();
				JOptionPane.showMessageDialog(qq_Chat_JFrame, message);
				System.exit(0);
			// 密码修改回复包
			} else if (packType == PackType.resetPassword){
				String message = qqPackageRec.getData().toString();
				if("密码修改成功!请牢记新密码!".equals(message)){
					qq_Chat_JFrame.getResetPassword_JDialog().getLabel_Message().setText("");
					JOptionPane.showMessageDialog(qq_Chat_JFrame, message);
					qq_Chat_JFrame.getResetPassword_JDialog().dispose();
				}else{
					qq_Chat_JFrame.getResetPassword_JDialog().getLabel_Message().setText(message);
				}
			}
		}
	}
}

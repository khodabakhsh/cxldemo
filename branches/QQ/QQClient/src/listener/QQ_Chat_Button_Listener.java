package listener;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mytools.MyDate;

import pub.PackType;
import pub.QQPackage;

import qq_client_jdialog.ResetPassword_JDialog;
import qq_client_jframe.QQ_Chat_JFrame;
/**
 * 这是一个按钮监听类，它实现了java.awt.event.ActionListener接口。
 * @author Devon
 *
 */
public class QQ_Chat_Button_Listener implements ActionListener {
	
	/**
	 * 一个QQ_Chat_JFrame的实例
	 */
	private QQ_Chat_JFrame qq_Chat_JFrame = null;

	/**
	 * 构造一个监听器，该方法要求传入一个QQ_Chat_JFrame的实例
	 * @param qq_Chat_JFrame 使监听器可以对qq_Chat_JFrame中的组件进行操作
	 */
	public QQ_Chat_Button_Listener(QQ_Chat_JFrame qq_Chat_JFrame) {
		this.qq_Chat_JFrame = qq_Chat_JFrame;
		// 窗口关闭监听
		this.qq_Chat_JFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}

	/**
	 * 实现接口中的方法，根据各个按钮做出不同的响应。
	 */
	public void actionPerformed(ActionEvent e) {
		// 聊天记录
		if (e.getSource() == qq_Chat_JFrame.getButton_ChatLogs()) {
			if (qq_Chat_JFrame.getPanel_ChatLogs().isVisible() == true) {
				qq_Chat_JFrame.getPanel_ChatLogs().setVisible(false);
				qq_Chat_JFrame.getButton_ChatLogs().setText("聊天记录↓");
				
				//如果当前为最大化状态,聊天记录面板在不可视后仍然以最大化状显示
				if(JFrame.MAXIMIZED_BOTH == qq_Chat_JFrame.getExtendedState()){
					qq_Chat_JFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				//否则根据当前大小进行设大小:宽度不变,高度-130
				}else{
					int width = (int)qq_Chat_JFrame.getSize().getWidth();
					int height = (int)qq_Chat_JFrame.getSize().getHeight();
					qq_Chat_JFrame.setSize(new Dimension(width, height - 130));
				}
				qq_Chat_JFrame.validate();
				
			} else {
				qq_Chat_JFrame.getPanel_ChatLogs().setVisible(true);
				qq_Chat_JFrame.getButton_ChatLogs().setText("聊天记录↑"); 
				
				//如果当前为最大化状态,聊天记录面板在不可视后仍然以最大化状显示
				if(JFrame.MAXIMIZED_BOTH == qq_Chat_JFrame.getExtendedState()){
					qq_Chat_JFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					//否则根据当前大小进行设大小:宽度不变,高度+130
				}else{
					int width = (int)qq_Chat_JFrame.getSize().getWidth();
					int height = (int)qq_Chat_JFrame.getSize().getHeight();
					qq_Chat_JFrame.setSize(new Dimension(width, height + 130));
				}
				qq_Chat_JFrame.validate();
			}
		// 发送
		} else if (e.getSource() == qq_Chat_JFrame.getButton_Send()) {
			QQPackage qqPackage_Chat = new QQPackage();
			Object select = qq_Chat_JFrame.getList_OnlineUsers()
					.getSelectedValue();
			// 没有选择用户
			if (select == null) {
				JOptionPane.showMessageDialog(qq_Chat_JFrame, "您还未选择消息接收者!");
				return;
			}
			String to = select.toString();
			int index = to.indexOf("(");
			String toID = to.substring(index + 1, to.length() - 1);
			String from = qq_Chat_JFrame.getName_ID();
			String input = qq_Chat_JFrame.getTextArea_Input().getText();
			// 给自己发送消息
			if (from.equals(to)) {
				JOptionPane.showMessageDialog(qq_Chat_JFrame, "无法给自己发送消息!");
				return;

			// 给所有人或其他用户发送消息
			} else {
				// 空消息
				if (input == null || "".equals(input)) {
					JOptionPane.showMessageDialog(qq_Chat_JFrame, "消息不能为空!");
					return;
				// 超过2000
				} else if (input.length() > 2000) {
					JOptionPane.showMessageDialog(qq_Chat_JFrame,"消息长度不能超过2000个字!");
					return;
				}
				// 合法消息--所有人
				if ("所有人".equals(to)) {
					String time = MyDate.dateFormat(MyDate.FORMAT_HMS);
					String self = "我" + " " + time + " 对" + to + "说:" + "\n" + "    " + input + "\n";
					qqPackage_Chat.setPackType(PackType.publicChat);
					qqPackage_Chat.setFrom(from);
					qq_Chat_JFrame.getTextArea_Dsp().append(self);
					qq_Chat_JFrame.getTextArea_ChatLogs().append(self);

				// 合法消息--私聊
				} else {
					qqPackage_Chat.setPackType(PackType.privateChat);
					qqPackage_Chat.setFrom(from);
					qqPackage_Chat.setTo(toID);
					String time = MyDate.dateFormat(MyDate.FORMAT_HMS);
					String self = "我" + " " + time + " 对" + to + "说:" + "\n" + "    " + input + "\n";
					qq_Chat_JFrame.getTextArea_Dsp().append(self);
					qq_Chat_JFrame.getTextArea_ChatLogs().append(self);
				}

			}

			ObjectOutputStream objectOutputStream = null;
			try {
				objectOutputStream = qq_Chat_JFrame.getObjectOutputStream();
				qqPackage_Chat.setData(input);
				objectOutputStream.writeObject(qqPackage_Chat);
				objectOutputStream.flush();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			qq_Chat_JFrame.getTextArea_Input().setText("");
		// 关闭
		} else if (e.getSource() == qq_Chat_JFrame.getButton_Close()) {

			close();
		// 修改密码
		}else if (e.getSource() == qq_Chat_JFrame.getButton_ResPsw()){
			new ResetPassword_JDialog(qq_Chat_JFrame);
			
		}
	}
	/**
	 * 该方法定义了窗口关闭时应做出的响应
	 *
	 */
	private void close() {
		int result = JOptionPane.showConfirmDialog(qq_Chat_JFrame, "确定要退出吗？", "退出确认", JOptionPane.YES_NO_OPTION);
		if (result != 0){
			return;
		}
		QQPackage qqPackage_offLine = new QQPackage();
		qqPackage_offLine.setPackType(PackType.userOffline);
		qqPackage_offLine.setFrom(qq_Chat_JFrame.getID());
		qqPackage_offLine.setData(qq_Chat_JFrame.getName_ID());

		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = qq_Chat_JFrame.getObjectOutputStream();
			objectOutputStream.writeObject(qqPackage_offLine);
			objectOutputStream.flush();
		} catch (IOException e1) {
			//e1.printStackTrace();
		}

		System.exit(0);
	}
}
package listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import mytools.MyDate;
import pub.PackType;
import pub.QQPackage;

import qq_client_jframe.QQ_Chat_JFrame;
/**
 * 这是一个键盘监听器类，它继承自KeyAdapter
 * @author Devon
 *
 */
public class QQ_Chat_Key_Listener extends KeyAdapter {
	
	/**
	 * 一个QQ_Chat_JFrame的实例
	 */
	private QQ_Chat_JFrame qq_Chat_JFrame = null;
	
	/**
	 * 构造一个键盘监听器
	 * @param qq_Chat_JFrame	使监听器可以对qq_Chat_JFrame中的组件进行操作
	 */
	public QQ_Chat_Key_Listener(QQ_Chat_JFrame qq_Chat_JFrame) {
		this.qq_Chat_JFrame = qq_Chat_JFrame;
	}

	@Override
	/**
	 * 定义了按下同时按下ctrl键与enter键时做出的响应，即发送消息
	 */
	public void keyTyped(KeyEvent e) {
		// 发送
		if (e.isControlDown() && e.getKeyChar() == KeyEvent.VK_ENTER) {

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
			String from = qq_Chat_JFrame.getTitle();
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
					JOptionPane.showMessageDialog(qq_Chat_JFrame,
							"消息长度不能超过2000个字!");
					return;
				}
				// 合法消息--所有人
				if ("所有人".equals(to)) {
					String time = MyDate.dateFormat(MyDate.FORMAT_HMS);
					String self = "我" + " " + time + " 对" + to + "说:" + "\n"
							+ "    " + input + "\n";
					qqPackage_Chat.setPackType(PackType.publicChat);
					qqPackage_Chat.setFrom(from);
					qq_Chat_JFrame.getTextArea_Dsp().append(self);
					qq_Chat_JFrame.getTextArea_ChatLogs().append(self);
					qq_Chat_JFrame.getTextArea_ChatLogs().append(self);
					qq_Chat_JFrame.getTextArea_ChatLogs().setCaretPosition(
							qq_Chat_JFrame.getTextArea_ChatLogs().getDocument()
									.getLength());

					// 合法消息--私聊
				} else {
					qqPackage_Chat.setPackType(PackType.privateChat);
					qqPackage_Chat.setFrom(from);
					qqPackage_Chat.setTo(toID);
					String time = MyDate.dateFormat(MyDate.FORMAT_HMS);
					String self = "我" + " " + time + " 对" + to + "说:" + "\n"
							+ "    " + input + "\n";
					qq_Chat_JFrame.getTextArea_Dsp().append(self);
					qq_Chat_JFrame.getTextArea_Dsp().setCaretPosition(
							qq_Chat_JFrame.getTextArea_Dsp().getDocument()
									.getLength());
					qq_Chat_JFrame.getTextArea_ChatLogs().append(self);
					qq_Chat_JFrame.getTextArea_ChatLogs().setCaretPosition(
							qq_Chat_JFrame.getTextArea_ChatLogs().getDocument()
									.getLength());
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

		}
	}
}

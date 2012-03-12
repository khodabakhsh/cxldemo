package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import dao.impl.UserDaoImpl;

import pub.PackType;
import pub.QQPackage;

import mytools.LogsReaderWriter;
import mytools.MyDate;

import qq_server_jframe.QQ_Server_JFrame;
import qq_server_jframe.ServerManager_JPanel;
import qq_server_thread.Accept_Thread;
import qq_server_thread.Server_Read_Thread;

/**
 * 这是一个服务面板按钮事件的监听器，它实现了java.awt.event.ActionListener接口
 * @author Devon
 *
 */
public class ServerManager_Button_Listener implements ActionListener {
	private ServerManager_JPanel serverManager_JPanel;
	private QQ_Server_JFrame qq_Server_JFrame;
	private Accept_Thread accept_Thread = null;
	
	/**
	 * 标记进入服务器界面后，是否开启过服务
	 */
	private boolean isAccepted = false;
	
	public ServerManager_Button_Listener(
			ServerManager_JPanel serverManager_JPanel, final QQ_Server_JFrame qq_Server_JFrame) {

		this.serverManager_JPanel = serverManager_JPanel;
		this.qq_Server_JFrame = qq_Server_JFrame;

		// 窗口关闭监听
		this.qq_Server_JFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}

	/**
	 * 定义了一些按钮按下时做出的响应
	 */
	public void actionPerformed(ActionEvent e) {
		// 启动服务
		if (serverManager_JPanel.getButton_Start() == e.getSource()) {
			serverManager_JPanel.getLabel_Image().setIcon(new ImageIcon("./Image/serverstart.gif"));
			serverManager_JPanel.getTextArea_NoticeSend().setEditable(true);
			serverManager_JPanel.getButton_NoticeSend().setEnabled(true);
			// 写日志
			String ymd = MyDate.dateFormat(MyDate.FORMAT_YMD);
			String ymdhms = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
			String log = ymdhms + " 启动服务成功!" + "\n";
			String filePath = "log/" + ymd + ".log" ;
			serverManager_JPanel.getTextArea_CommunicationInfo().append(log);
			LogsReaderWriter.writeIntoFile(log, filePath, true);
			// 设按钮
			serverManager_JPanel.getButton_Start().setEnabled(false);
			serverManager_JPanel.getButton_End().setEnabled(true);
			serverManager_JPanel.getButton_Offline().setEnabled(false);
			// new Accept_Thread 线程
			//首次开启服务
			if(isAccepted == false){
				Accept_Thread accept_Thread = new Accept_Thread(qq_Server_JFrame,qq_Server_JFrame.getServerSocket());
				this.accept_Thread = accept_Thread;// 保留accept_Thread线程的引用,停止服务时打断该线程
				accept_Thread.start();
				isAccepted = true;
			//非首次开启服务
			}else{
				try {
					ServerSocket serverSocket = new ServerSocket(qq_Server_JFrame.getServerSocket().getLocalPort());
					qq_Server_JFrame.setServerSocket(serverSocket);
					Accept_Thread accept_Thread = new Accept_Thread(qq_Server_JFrame,qq_Server_JFrame.getServerSocket());
					this.accept_Thread = accept_Thread;// 保留accept_Thread线程的引用,停止服务时打断该线程
					accept_Thread.start();
				} catch (IOException e1) {
					// 当停服后再开服时,如不能正常开启服务,给出提示
					
					// 设按钮
					serverManager_JPanel.getButton_Start().setEnabled(true);
					serverManager_JPanel.getButton_End().setEnabled(false);
					serverManager_JPanel.getButton_Offline().setEnabled(false);
					// 改图片
					serverManager_JPanel.getLabel_Image().setIcon(new ImageIcon("./Image/serverstop.gif"));
					serverManager_JPanel.getTextArea_NoticeSend().setEditable(false);
					serverManager_JPanel.getButton_NoticeSend().setEnabled(false);
					// 弹提示
					JOptionPane.showMessageDialog(qq_Server_JFrame, "开启服务失败!如此情况持续出现,建议您稍后再试或重启程序更换端口号!");
					//e1.printStackTrace();
				}
			}
			

			
			// 停止服务
		} else if (serverManager_JPanel.getButton_End() == e.getSource()) {
			int result = JOptionPane.showConfirmDialog(qq_Server_JFrame,"确定要停止服务吗?", "确认停止服务", JOptionPane.YES_NO_OPTION);
			if (result != 0) {
				return;
			}
			// 改图片
			serverManager_JPanel.getLabel_Image().setIcon(new ImageIcon("./Image/serverstop.gif"));
			serverManager_JPanel.getTextArea_NoticeSend().setEditable(false);
			serverManager_JPanel.getButton_NoticeSend().setEnabled(false);
			// 写服务器停服日志
			String filePath = "log/" + MyDate.dateFormat(MyDate.FORMAT_YMD) + ".log" ;
			String time = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
			String log = time + " " + "停止服务成功!" + "\n";
			LogsReaderWriter.writeIntoFile(log, filePath, true);
			serverManager_JPanel.getTextArea_CommunicationInfo().append(log);
			// 修改用户状态
			UserDaoImpl.getInstance().upOrDown("离线");

			// 刷新用户列表
			serverManager_JPanel.updateQQUsersInfo_JTable();
			qq_Server_JFrame.getUsersManager().updateQQUsersInfo_JTable();
			// 设按钮
			serverManager_JPanel.getButton_Start().setEnabled(true);
			serverManager_JPanel.getButton_End().setEnabled(false);
			serverManager_JPanel.getButton_Offline().setEnabled(false);
			// 通知用户下线,发stopServer包
			QQPackage qqPackageStop = new QQPackage();
			qqPackageStop.setPackType(PackType.stopServer);
			qqPackageStop.setData("服务器关闭,您已被迫下线!");
			Collection<Server_Read_Thread> serverReadThreads = qq_Server_JFrame.getServerread_Thread_Map().values();
			for (Server_Read_Thread serverReadThread : serverReadThreads) {
				try {
					ObjectOutputStream objectOutputStream = serverReadThread.getObjectOutputStream();
					objectOutputStream.writeObject(qqPackageStop);
					objectOutputStream.flush();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
			//写用户下线日志(在以下<<//关闭Server_Read_Thread及对应的流  >>时会产生异常,而异常处理中已有对应的日志写入  )
/*			Set<String> IDs = qq_Server_JFrame.getServerread_Thread_Map().keySet();
			for (String id : IDs) {
				String ymdhms = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
				String name = UserDaoImpl.getInstance().selectList(id).getSname();
				String log2 = ymdhms + " " + name + "(" + id + ")" + "下线了!" + "\n" ;
				serverManager_JPanel.getTextArea_CommunicationInfo().append(log2);
				LogsReaderWriter.writeIntoFile(log2, filePath, true);
			}*/
			// 停止Accept_Thread线程,关闭serversocket
			accept_Thread.interrupt();
			try {
				qq_Server_JFrame.getServerSocket().close();
			} catch (IOException e2) {
				//e2.printStackTrace();
			}
			// 关闭Server_Read_Thread及对应的流
			for (Server_Read_Thread serverReadThread : serverReadThreads) {

				serverReadThread.interrupt();

				try {
					serverReadThread.getObjectOutputStream().close();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
				try {
					serverReadThread.getObjectInputStream().close();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
				try {
					serverReadThread.getInputStream().close();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
				try {
					serverReadThread.getOutputStream().close();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
			// 清空集合
			qq_Server_JFrame.getServerread_Thread_Map().clear();

		// 强制用户下线
		} else if (serverManager_JPanel.getButton_Offline() == e.getSource()) {
			// 根据点击判断用户id
			int row = serverManager_JPanel.getQqUsersInfo_JTable().getSelectedRow();
			String id = serverManager_JPanel.getQqUsersInfo_JTable().getValueAt(row, 0).toString();
			// 封装强制下线包
			QQPackage qqPackageOffline = new QQPackage();
			qqPackageOffline.setPackType(PackType.enforceDown);
			qqPackageOffline.setData("您已被强制下线,请与服务器管理员联系!");
			ObjectOutputStream objectOutputStream = qq_Server_JFrame
					.getServerread_Thread_Map().get(id).getObjectOutputStream();

			try {
				objectOutputStream.writeObject(qqPackageOffline);
				objectOutputStream.flush();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			// 关闭被强制下线的用户的对应线程
			qq_Server_JFrame.getServerread_Thread_Map().get(id).interrupt();
			// 关闭被强制下线的用户的流
			try {
				qq_Server_JFrame.getServerread_Thread_Map().get(id)
						.getObjectInputStream().close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			try {
				qq_Server_JFrame.getServerread_Thread_Map().get(id)
						.getObjectOutputStream().close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			try {
				qq_Server_JFrame.getServerread_Thread_Map().get(id)
						.getInputStream().close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			try {
				qq_Server_JFrame.getServerread_Thread_Map().get(id)
						.getOutputStream().close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			// 在集合中清掉被强制下线的用户的线程
			qq_Server_JFrame.getServerread_Thread_Map().remove(id);
/*			// 通知其他在线用户，该用户已下线
			String name1 = UserDaoImpl.getInstance().selectList(id).getSname();
			String all1 = name1 + "(" + id + ")" + "下线了!" + "\n";
			QQPackage qqPackageNotify = new QQPackage();
			qqPackageNotify.setPackType(PackType.publicChat);
			qqPackageNotify.setData("【系统消息】 " + all1);
			Collection<Server_Read_Thread> c2 = qq_Server_JFrame.getServerread_Thread_Map().values();//发包
			for (Server_Read_Thread serverReadThread : c2) {
				ObjectOutputStream objectOutputStream2 =serverReadThread.getObjectOutputStream();
				try {
					objectOutputStream2.writeObject(qqPackageNotify);
					objectOutputStream2.flush();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
			// 写日志
			String ymd = MyDate.dateFormat(MyDate.FORMAT_YMD);
			String ymdhms = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
			String name = UserDaoImpl.getInstance().selectList(id).getSname();
			String all = name + "(" + id + ")";
			String log = ymdhms + " " + all + "下线了!" + "\n";
			String filePath = "log/" + ymd + ".log" ;
			LogsReaderWriter.writeIntoFile(log, filePath, true);
			serverManager_JPanel.getTextArea_CommunicationInfo().append(log);*/
			// 更新用户在线状态
			UserDaoImpl.getInstance().upOrDown(id, "离线");
			// 更新两表
			serverManager_JPanel.updateQQUsersInfo_JTable();
			qq_Server_JFrame.getUsersManager().updateQQUsersInfo_JTable();
			// 分发最新的在线用户列表DefaultListModel给每个在线的用户
			Set<String> onlineUsers = qq_Server_JFrame.getServerread_Thread_Map().keySet();
			DefaultListModel defaultListModel = new DefaultListModel();
			defaultListModel.addElement("所有人");
			Iterator<String> i = onlineUsers.iterator();
			while (i.hasNext()) {
				String IDTemp = i.next();
				String nameTemp = UserDaoImpl.getInstance().selectList(IDTemp).getSname();
				String all = nameTemp + "(" + IDTemp + ")";
				defaultListModel.addElement(all);
			}
			
			Collection<Server_Read_Thread> readThreads = qq_Server_JFrame.getServerread_Thread_Map().values();
			QQPackage qqPackageOnlineUser = new QQPackage();
			qqPackageOnlineUser.setPackType(PackType.onlineuser);
			qqPackageOnlineUser.setData(defaultListModel);

			for (Server_Read_Thread readThread : readThreads) {
				try {
					readThread.getObjectOutputStream().writeObject(qqPackageOnlineUser);
					readThread.getObjectOutputStream().flush();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
		// 发送公告
		} else if (serverManager_JPanel.getButton_NoticeSend() == e.getSource()) {
			String message = serverManager_JPanel.getTextArea_NoticeSend()
					.getText();
			if(message.length() > 200){
				JOptionPane.showMessageDialog(qq_Server_JFrame, "公告信息内容必须在200(含)个字内!");
				return;
			}
			String time = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
			String all = time + "\n" + message;
			// 写入本地文件,用户上线时默认将发送文件中的公告内容
			LogsReaderWriter.writeIntoFile(all, "./notice/notice.txt", false);
			// 封装公告包
			QQPackage qqPackageNotice = new QQPackage();
			qqPackageNotice.setPackType(PackType.post);
			qqPackageNotice.setData(all);
			Collection<Server_Read_Thread> serverReadThreads = qq_Server_JFrame
					.getServerread_Thread_Map().values();
			for (Server_Read_Thread serverReadThread : serverReadThreads) {
				ObjectOutputStream objectOutputStream = serverReadThread
						.getObjectOutputStream();
				try {
					objectOutputStream.writeObject(qqPackageNotice);
					objectOutputStream.flush();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
			serverManager_JPanel.getTextArea_NoticeSend().setText("");
			JOptionPane.showMessageDialog(qq_Server_JFrame, "公告发送成功!");
		}
	}

	/**
	 * 窗口事件监听器中的windowClosing()将调用此方法
	 */
	private void close() {
		int result = JOptionPane.showConfirmDialog(qq_Server_JFrame,"确定要关闭程序吗?如果服务已开启,所有在线用户将被强制下线!", "确认关闭程序",
				JOptionPane.YES_NO_OPTION);
		if (result != 0) {
			return;
		}

		// 写停服日志
		String filePath = "log/" + MyDate.dateFormat(MyDate.FORMAT_YMD) + ".log";
		String time = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
		String log = time + " " + "停止服务成功!" + "\n";
		LogsReaderWriter.writeIntoFile(log, filePath, true);
		serverManager_JPanel.getTextArea_CommunicationInfo().append(log);
		// 写用户下线日志
		Set<String> IDs = qq_Server_JFrame.getServerread_Thread_Map().keySet();
		for (String id : IDs) {
			String name = UserDaoImpl.getInstance().selectList(id).getSname();
			String log2 = name + "(" + id + ")" + "下线了!" + "\n";
			serverManager_JPanel.getTextArea_CommunicationInfo().append(log2);
			LogsReaderWriter.writeIntoFile(log2, filePath, true);
		}
		// 修改用户状态
		UserDaoImpl.getInstance().upOrDown("离线");
		// 通知用户下线,发stopServer包
		QQPackage qqPackageStop = new QQPackage();
		qqPackageStop.setPackType(PackType.stopServer);
		qqPackageStop.setData("服务器关闭,您已被迫下线!");
		Collection<Server_Read_Thread> serverReadThreads = qq_Server_JFrame
				.getServerread_Thread_Map().values();
		for (Server_Read_Thread serverReadThread : serverReadThreads) {
			try {
				ObjectOutputStream objectOutputStream = serverReadThread
						.getObjectOutputStream();
				objectOutputStream.writeObject(qqPackageStop);
				objectOutputStream.flush();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}

		System.exit(0);
	}

}

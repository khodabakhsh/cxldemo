package qq_server_thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;

import mytools.LogsReaderWriter;
import mytools.MyDate;

import pub.PackType;
import pub.QQPackage;
import dao.bean.User;
import dao.impl.UserDaoImpl;

import qq_server_jframe.QQ_Server_JFrame;

/**
 * 这是一个服务端监听客户连接的线程
 * @author Devon
 *
 */
public class Accept_Thread extends Thread {
	private ServerSocket serverSocket = null;

	private QQ_Server_JFrame qq_Server_JFrame = null;
	
	/**
	 * 创建一个服务端监听客户连接的线程
	 * @param qq_Server_JFrame	服务端主面板
	 * @param serverSocket	
	 */
	public Accept_Thread(QQ_Server_JFrame qq_Server_JFrame,
			ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.qq_Server_JFrame = qq_Server_JFrame;
	}

	/**
	 * 重写父类中的方法
	 */
	public void run() {
		Socket socket = null;
		while (!Thread.interrupted()) {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			ObjectInputStream objectInputStream = null;
			ObjectOutputStream objectOutputStream = null;

			try {
				socket = serverSocket.accept();
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				objectInputStream = new ObjectInputStream(inputStream);
				objectOutputStream = new ObjectOutputStream(outputStream);
				QQPackage qqPackageCheck = (QQPackage) objectInputStream
						.readObject();
				// 收到登录请求包
				if (qqPackageCheck.getPackType() == PackType.loginApply) {
					Vector<String> IDAndPsw = (Vector<String>) qqPackageCheck.getData();
					String ID = IDAndPsw.get(0);
					String psw = IDAndPsw.get(1);
					User user = UserDaoImpl.getInstance().selectList(ID);
					QQPackage qqPackageReturn = new QQPackage();

					// 用户名不存在时
					if (user == null) {
						qqPackageReturn.setPackType(PackType.loginFail);
						qqPackageReturn.setData("用户名不存在！");
						objectOutputStream.writeObject(qqPackageReturn);
						objectOutputStream.flush();
						// 用户名存在时
					} else {
						// 用户已在线
						if (user.getNisonlin().equals("在线")) {
							qqPackageReturn.setPackType(PackType.loginFail);
							qqPackageReturn.setData("用户已登录！");
							objectOutputStream.writeObject(qqPackageReturn);
							objectOutputStream.flush();
							// 用户不在线
						} else {
							// 密码错误
							if (!user.getSpassword().equals(psw)) {
								qqPackageReturn.setPackType(PackType.loginFail);
								qqPackageReturn.setData("密码不正确！");
								objectOutputStream.writeObject(qqPackageReturn);
								objectOutputStream.flush();
								// 密码正确
							} else {
								// new读取线程
								Server_Read_Thread server_Read_Thread = new Server_Read_Thread
								(qq_Server_JFrame, socket, objectOutputStream, objectInputStream, inputStream, outputStream , ID);
								
								qq_Server_JFrame.getServerread_Thread_Map().put(ID,server_Read_Thread);// 将ID与read_Thread的键值对加入集合

								UserDaoImpl.getInstance().upOrDown(ID, "在线");// 修改用户在线状态
								qq_Server_JFrame.getServerManager().updateQQUsersInfo_JTable();// 刷新两表
								qq_Server_JFrame.getUsersManager().updateQQUsersInfo_JTable();// 刷新两表
								String ymd = MyDate.dateFormat(MyDate.FORMAT_YMD);
								String ymdhms = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
								String log = ymdhms + " " + user.getSname() + "(" + ID + ")上线了!" + "\n";
								qq_Server_JFrame.getServerManager().getTextArea_CommunicationInfo().append(log);
								LogsReaderWriter.writeIntoFile(log, "log/" + ymd + ".log", true);
								// 封装登录者的id,name,在线用户列表DefaultListModel,公告
								qqPackageReturn.setPackType(PackType.loginSuccess);
								String name = user.getSname();
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
								LogsReaderWriter.createNewFile("notice/notice.txt");
								String notice = LogsReaderWriter.readFromFile("notice/notice.txt",null);
								if(notice == null || "".equals(notice)){//文件不存在或文件内容为空要将null转换掉
									notice = "暂时没有公告";
								}
								Vector data = new Vector();
								data.add(ID);
								data.add(name);
								data.add(defaultListModel);
								data.add(notice);
								qqPackageReturn.setData(data);
								objectOutputStream.writeObject(qqPackageReturn);
								objectOutputStream.flush();
								// 分发最新的在线用户列表DefaultListModel给每个在线的用户
								Collection<Server_Read_Thread> readThreads = qq_Server_JFrame.getServerread_Thread_Map().values();
		
								QQPackage qqPackageOnlineUser = new QQPackage();
								qqPackageOnlineUser.setPackType(PackType.onlineuser);
								qqPackageOnlineUser.setData(defaultListModel);

								for (Server_Read_Thread readThread : readThreads) {
									readThread.getObjectOutputStream().writeObject(qqPackageOnlineUser);
									readThread.getObjectOutputStream().flush();
								}
								//启动线程
								server_Read_Thread.start();

							}
						}
					}
				}
			} catch (IOException e) {
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
			}
		}
	}
}

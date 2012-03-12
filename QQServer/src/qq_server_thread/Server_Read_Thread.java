package qq_server_thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;

import dao.bean.User;
import dao.impl.UserDaoImpl;

import mytools.LogsReaderWriter;
import mytools.MyDate;

import pub.PackType;
import pub.QQPackage;

import qq_server_jframe.QQ_Server_JFrame;

/**
 * 这是一个读取线程类，服务端对应每个客户端都有一个这样的读取线程
 * @author Devon
 *
 */
public class Server_Read_Thread extends Thread {
	private Socket socket = null;
	private ObjectOutputStream objectOutputStream = null;
	private ObjectInputStream objectInputStream = null;
	private QQ_Server_JFrame qq_Server_JFrame = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private String ID;

	/**
	 * 创建一个读取线程
	 * @param qq_Server_JFrame
	 * @param socket
	 * @param objectOutputStream	对客户端的对象输出流
	 * @param objectInputStream		对客户端的对象输入流
	 * @param inputStream
	 * @param outputStream
	 * @param ID	每个线程都属于对应的每个用户
	 */
	public Server_Read_Thread(QQ_Server_JFrame qq_Server_JFrame, Socket socket,
			ObjectOutputStream objectOutputStream,
			ObjectInputStream objectInputStream, InputStream inputStream, OutputStream outputStream, String ID) {
		this.socket = socket;

		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
		this.outputStream = outputStream;
		this.inputStream = inputStream;
		this.ID = ID;
		
		this.qq_Server_JFrame = qq_Server_JFrame;
	}
	
	/**
	 * 重写父类中的方法
	 */
	public void run() {

		while (!Thread.interrupted()) {
			try {
				QQPackage qqPackageReceive = (QQPackage) objectInputStream.readObject();
				PackType packType = qqPackageReceive.getPackType();
				// 收到公聊包时
				if (packType == PackType.publicChat) {
					Collection<Server_Read_Thread> serverReadThreads = qq_Server_JFrame.getServerread_Thread_Map().values();
					String from = qqPackageReceive.getFrom();
					String message = qqPackageReceive.getData().toString();
					String time = MyDate.dateFormat(MyDate.FORMAT_HMS);
					String send = from + " " + time + " 对所有人说:" + "\n" + "    " + message + "\n";
					System.out.println(send);
					// 封装包
					QQPackage qqPackageSend = new QQPackage();
					qqPackageSend.setPackType(PackType.publicChat);
					qqPackageSend.setData(send);
					for (Server_Read_Thread serverReadThread : serverReadThreads) {
						ObjectOutputStream objectOutputStream = serverReadThread.getObjectOutputStream();
						if(!objectOutputStream.equals(this.objectOutputStream)){
							objectOutputStream.writeObject(qqPackageSend);
							objectOutputStream.flush();
						}

					}

					
				// 收到私聊包时
				} else if (packType == PackType.privateChat) {
					//拆包
					String from = qqPackageReceive.getFrom();
					String to = qqPackageReceive.getTo();
					String message = qqPackageReceive.getData().toString();
					String time = MyDate.dateFormat(MyDate.FORMAT_HMS);
					//封装包
					QQPackage qqPackagePrivate = new QQPackage();
					String send = from + " " + time + " 对你说:" + "\n" + "    " + message + "\n";
					System.out.println(send);
					qqPackagePrivate.setPackType(PackType.publicChat);//对于接收者来说公聊与私聊的操作是一样的....
					qqPackagePrivate.setData(send);
					ObjectOutputStream objectOutputStream = qq_Server_JFrame.getServerread_Thread_Map().get(to).getObjectOutputStream();
					objectOutputStream.writeObject(qqPackagePrivate);
					objectOutputStream.flush();
				// 收到用户下线包时
				} else if (packType == PackType.userOffline) {
					String ID = qqPackageReceive.getFrom();
					String IDName = qqPackageReceive.getData().toString();
					
					// 更新用户文件并刷新两表
					UserDaoImpl.getInstance().upOrDown(ID, "离线");
					qq_Server_JFrame.getServerManager().updateQQUsersInfo_JTable();
					qq_Server_JFrame.getUsersManager().updateQQUsersInfo_JTable();
					// 下线日志
					String ymdhms = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
					String ymd =  MyDate.dateFormat(MyDate.FORMAT_YMD);
					String log = ymdhms + " " + IDName + "下线了!" + "\n";
					qq_Server_JFrame.getServerManager().getTextArea_CommunicationInfo().append(log);
					LogsReaderWriter.writeIntoFile(log, "log/" + ymd+ ".log", true);
					// 关闭下线用的线程及对应的流
					this.interrupt();
					try {
						this.objectOutputStream.close() ;
					} catch (IOException e1) {
						//e1.printStackTrace();
					}
					try {
						this.objectInputStream.close();
					} catch (IOException e2) {
						//e2.printStackTrace();
					}
					try {
						this.outputStream.close() ;
					} catch (IOException e1) {
						//e1.printStackTrace();
					}
					try {
						this.inputStream.close();
					} catch (IOException e1) {
						//e1.printStackTrace();
					}
					// 删除对应的用户线程对象
					qq_Server_JFrame.getServerread_Thread_Map().remove(ID);
					// 通知其他在线用户，该用户已下线
					String name1 = UserDaoImpl.getInstance().selectList(ID).getSname();
					String all1 = name1 + "(" + ID + ")" + "下线了!" + "\n";
					QQPackage qqPackageNotify = new QQPackage();
					qqPackageNotify.setPackType(PackType.publicChat);
					qqPackageNotify.setData("【系统消息】 " + all1);
					Collection<Server_Read_Thread> c2 = qq_Server_JFrame.getServerread_Thread_Map().values();//发包
					for (Server_Read_Thread serverReadThread : c2) {
						ObjectOutputStream objectOutputStream =serverReadThread.getObjectOutputStream();
						objectOutputStream.writeObject(qqPackageNotify);
						objectOutputStream.flush();
					}
					// 分发最新的在线用户列表
					Set<String> IDs = qq_Server_JFrame.getServerread_Thread_Map().keySet();
					DefaultListModel defaultListModel = new DefaultListModel();
					defaultListModel.addElement("所有人");
					for (String s : IDs) {
						String name = UserDaoImpl.getInstance().selectList(s).getSname();
						String all = name + "(" + s + ")" ;
						defaultListModel.addElement(all);
					}
					QQPackage qqPackageOnlineUsers = new QQPackage();
					qqPackageOnlineUsers.setPackType(PackType.onlineuser);
					qqPackageOnlineUsers.setData(defaultListModel);
					
					Collection<Server_Read_Thread> c = qq_Server_JFrame.getServerread_Thread_Map().values();//发包
					for (Server_Read_Thread serverReadThread : c) {
						ObjectOutputStream objectOutputStream =serverReadThread.getObjectOutputStream();
						objectOutputStream.writeObject(qqPackageOnlineUsers);
						objectOutputStream.flush();
					}
					
				//收到修改密码包时
				}else if(packType == PackType.resetPassword){
					String ID = qqPackageReceive.getFrom();
					Vector<String> psw = (Vector<String>)qqPackageReceive.getData();
					String oldPsw = psw.get(0);
					String newPsw = psw.get(1);
					User user = UserDaoImpl.getInstance().selectList(ID);
					//封装包
					QQPackage qqPackageReturn = new QQPackage();
					qqPackageReturn.setPackType(PackType.resetPassword);
					//旧密码相符
					if(user.getSpassword().equals(oldPsw)){
						 boolean isSuccess = UserDaoImpl.getInstance().resetPWD(ID, newPsw);
						 //修改成功
						 if(isSuccess == true){
							 qqPackageReturn.setData("密码修改成功!请牢记新密码!");
						//修改失败
						 }else {
							 qqPackageReturn.setData("服务器错误,密码修改失败!请稍后再试!");
						 }
					//旧密码不相符
					}else {
						qqPackageReturn.setData("原密码输入不正确,密码修改失败!");
					}
					ObjectOutputStream objectOutputStream = qq_Server_JFrame.getServerread_Thread_Map().get(ID).getObjectOutputStream();
					objectOutputStream.writeObject(qqPackageReturn);
					objectOutputStream.flush();
				}

			} catch (IOException e) {
				//对方断电等意外情况
				this.interrupt();
				try {
					this.objectOutputStream.close() ;
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
				try {
					this.objectInputStream.close();
				} catch (IOException e2) {
					//e2.printStackTrace();
				}
				try {
					this.outputStream.close() ;
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
				try {
					this.inputStream.close();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
				qq_Server_JFrame.getServerread_Thread_Map().remove(ID);	
				// 通知其他在线用户，该用户已下线
				String name1 = UserDaoImpl.getInstance().selectList(ID).getSname();
				String all1 = name1 + "(" + ID + ")" + "下线了!" + "\n";
				QQPackage qqPackageNotify = new QQPackage();
				qqPackageNotify.setPackType(PackType.publicChat);
				qqPackageNotify.setData("【系统消息】 " + all1);
				Collection<Server_Read_Thread> c2 = qq_Server_JFrame.getServerread_Thread_Map().values();//发包
				for (Server_Read_Thread serverReadThread : c2) {
					ObjectOutputStream objectOutputStream =serverReadThread.getObjectOutputStream();
					try {
						objectOutputStream.writeObject(qqPackageNotify);
						objectOutputStream.flush();
					} catch (IOException e1) {
						//e1.printStackTrace();
					}
				}
				// 更改离线状态并刷新两表
				UserDaoImpl.getInstance().upOrDown(ID, "离线");
				qq_Server_JFrame.getServerManager().updateQQUsersInfo_JTable();
				qq_Server_JFrame.getUsersManager().updateQQUsersInfo_JTable();
				// 下线日志
				String ymdhms = MyDate.dateFormat(MyDate.FORMAT_YMDHMS);
				String ymd =  MyDate.dateFormat(MyDate.FORMAT_YMD);
				String name = UserDaoImpl.getInstance().selectList(ID).getSname();
				String IDName = name + "(" + ID + ")" ;
				String log = ymdhms + " " + IDName + "下线了!" + "\n";
				qq_Server_JFrame.getServerManager().getTextArea_CommunicationInfo().append(log);
				LogsReaderWriter.writeIntoFile(log, "log/" + ymd+ ".log", true);
				
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
			}

		}

	}

	/**
	 * 获得对象输出流
	 * @return	对象输出流
	 */
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	/**
	 * 获得对象输入流
	 * @return	对象输入流
	 */
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	/**
	 * 获得输入流
	 * @return	输入流
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 获得输出流
	 * @return	输出流
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}
}

package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JOptionPane;

import pub.PackType;
import pub.QQPackage;

import qq_client_jframe.QQ_Chat_JFrame;
import qq_client_jframe.QQ_Login_JFrame;
/**
 * 这是一个按钮事件的监听器类，它实现了java.awt.event.ActionListener接口
 * @author Devon
 *
 */
public class QQ_Login_Button_Listener implements ActionListener {
	private QQ_Login_JFrame qq_Login_JFrame;
	
	private Socket socket = null;
	
	/**
	 * 用于统计失败的次数
	 */
	private int wrongCount = 0;

	/**
	 * 构造一个按钮事件的监听器类
	 * @param qq_Login_JFrame	使监听器可以对qq_Chat_JFrame中的组件进行操作
	 */
	public QQ_Login_Button_Listener(QQ_Login_JFrame qq_Login_JFrame) {
		this.qq_Login_JFrame = qq_Login_JFrame;
	}

	/**
	 * 定义了各个按钮按下时做出的响应
	 */
	public void actionPerformed(ActionEvent e) {
		//设置按钮
		if (qq_Login_JFrame.getButton_NetOption() == e.getSource()) {
			if (qq_Login_JFrame.getPanel_NetOption().isVisible() == true) {
				qq_Login_JFrame.getPanel_NetOption().setVisible(false);
				// qq_Login.setSize(new Dimension(340,300));
				qq_Login_JFrame.pack();
			} else {
				qq_Login_JFrame.getPanel_NetOption().setVisible(true);
				// qq_Login.setSize(new Dimension(340,300));
				qq_Login_JFrame.pack();
			}
		//登录按钮
		} else if (qq_Login_JFrame.getButton_Login() == e.getSource()) {
			//以下排除非法IP
			Object objectIP = qq_Login_JFrame.getComboBox_IP().getSelectedItem();
			if(objectIP == null){
				qq_Login_JFrame.getLabel_NetOption().setText("IP不合法，请重新输入！");
				return;
			}
			String IP = objectIP.toString();
			IP = IP.replaceAll(" ", "");
			if (!IP.matches("^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$")) {
				qq_Login_JFrame.getLabel_NetOption().setText("IP不合法，请重新输入！");
				return;
			}
			//以下排除非法端口
			String port = qq_Login_JFrame.getTextField_Port().getText();
			port = port.replaceAll(" ", "");
			if(port == null){
				qq_Login_JFrame.getLabel_NetOption().setText("请输入端口号");
				return;
			}
			if(!port.matches("\\d{4,5}")){
				qq_Login_JFrame.getLabel_NetOption().setText("端口号应为1024~65535之间的整数");
				return;
			}
			int p = Integer.parseInt(port);
			if(p<1024 && p>65535){
				qq_Login_JFrame.getLabel_NetOption().setText("端口号应为1024~65535之间的整数");
				return;
			}
			//以下排除非法QQ号码
			Object objectID = qq_Login_JFrame.getComboBox_ID().getSelectedItem();
			if(objectID == null){
				JOptionPane.showMessageDialog(qq_Login_JFrame, "请输入QQ号码!");
				return;
			}
			String ID = objectID.toString();
			ID = ID.replaceAll(" ", "");
			//以下排除非法密码
			char[] pswArr = qq_Login_JFrame.getPasswordField_Password().getPassword();
			String psw = String.valueOf(pswArr);
			if(psw == null){
				JOptionPane.showMessageDialog(qq_Login_JFrame, "请输入密码!");
				return;
			}
			//4项都合法后
			try {
				socket = new Socket(IP, p);
			} catch (UnknownHostException e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(qq_Login_JFrame, "找不到服务器!请检查网络或联系系统管理员!");
				return;
			} catch (IOException e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(qq_Login_JFrame, "服务器连接失败!请检查网络或联系系统管理员!");
				return;
			}
			
			try {
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				//封装登录请求包
				Vector<String> IDAndPsw = new Vector<String>();
				IDAndPsw.add(ID);
				IDAndPsw.add(psw);
				QQPackage qqPackageCheck = new QQPackage();
				qqPackageCheck.setPackType(PackType.loginApply);
				qqPackageCheck.setData(IDAndPsw);
				objectOutputStream.writeObject(qqPackageCheck);
				objectOutputStream.flush();

				//收取服务器返的信息
				QQPackage qqPackageReturn = null;
				qqPackageReturn = (QQPackage)objectInputStream.readObject();
				PackType packType = qqPackageReturn.getPackType();
				String returnInfo = qqPackageReturn.getData().toString();
				//计数,3次错误后退出程序
				if(++wrongCount == 3){
					qq_Login_JFrame.dispose();
					System.exit(0);
				}
				//登录失败
				if(packType == PackType.loginFail){
					JOptionPane.showMessageDialog(qq_Login_JFrame, returnInfo + "累计三次登录失败后，程序将退出，您还可以操作：" + (3-wrongCount) + "次");
				//登录成功
				}else if(packType == PackType.loginSuccess){
					// 把当次输入的号码增加到文件，实现记录输入号码的功能
					qq_Login_JFrame.autoAppendID();
					qq_Login_JFrame.dispose();
					new QQ_Chat_JFrame(socket, qqPackageReturn,objectOutputStream,objectInputStream);
					
				}
			} catch (IOException e1) {
				//e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				//e1.printStackTrace();
			}
		//退出按钮
		} else if (qq_Login_JFrame.getButton_Exit() == e.getSource()) {
			System.exit(0);
		}
	}
}

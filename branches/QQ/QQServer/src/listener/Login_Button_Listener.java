package listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import qq_server_jframe.Login_JFrame;
import qq_server_jframe.QQ_Server_JFrame;

/**
 * 这是一个服务端登录窗口的按钮监听器，它实现了java.awt.event.ActionListener接口
 * @author Devon
 * 
 */
public class Login_Button_Listener implements ActionListener{
	private Login_JFrame login_JFrame;
	
	public Login_Button_Listener(Login_JFrame login_JFrame) {
		this.login_JFrame = login_JFrame;
	}

	/**
	 * 定义了一些按钮按下时做出的响应
	 */
	public void actionPerformed(ActionEvent e) {
		ServerSocket serverSocket = null;
		
		Properties properties = new Properties();
		String temp = login_JFrame.getTextField_Port().getText();
		if(temp == null){
			login_JFrame.getLabel_Message().setText("请输入端口号!");
			return;
		}
		String port = temp.replaceAll(" ", "");
		login_JFrame.getTextField_Port().setText(port);
		
		if(serverSocket == null){
			int p = 0;
			if(port.matches("[0-9]{1,5}")){
				p = Integer.parseInt(port);
				if(p>1023 && p<65536){
					try {
						serverSocket = new ServerSocket(p);
						properties.setProperty("port", port);
						if(e.getSource() != login_JFrame.getButton_Test()){
							login_JFrame.saveConfig(properties);
						}
						login_JFrame.getLabel_Message().setText("该端口可以使用!");
					} catch (IOException e1) {
						if(e.getSource() == login_JFrame.getButton_Test()){
							login_JFrame.getLabel_Message().setText("端口指定失败,请重新更换端口号后再试!");
						}else if(e.getSource() == login_JFrame.getButton_Save()){
							login_JFrame.getLabel_Message().setText("保存指定端口失败,请重新更换端口号后再试!");
						}
					}

				}else{
					login_JFrame.getLabel_Message().setText("端口号应为1024~65535之间的整数!");
					return;
				}
			}else{
				login_JFrame.getLabel_Message().setText("端口号应为1024~65535之间的整数!");
				return;
			}
		}
		//测试按钮
		if(login_JFrame.getButton_Test() == e.getSource()){
			try {
				serverSocket.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			
		}else{
			//保存配置
			if(login_JFrame.getButton_Save() == e.getSource()){
				try {
					serverSocket.close();
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
				login_JFrame.getLabel_Message().setText("保存配置成功!");

			//进入服务器
			}else if(login_JFrame.getButton_Enter() == e.getSource()){
				//把serversocket传给服务器主面板
				login_JFrame.dispose();
				new QQ_Server_JFrame(serverSocket);
			}
		}
	}
}

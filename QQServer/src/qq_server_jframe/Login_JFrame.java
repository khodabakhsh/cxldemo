package qq_server_jframe;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listener.Login_Button_Listener;
import mytools.LogsReaderWriter;

/**
 * 这是一个服务端登录窗体，它继承自javax.swing.JFrame
 * @author Devon
 *
 */
public class Login_JFrame extends JFrame {
	private JLabel label_Message = new JLabel(" ");
	private JTextField textField_File = new JTextField(15);
	private JTextField textField_Port = new JTextField(15);
	private JButton button_Test = new JButton("测试端口");
	private JButton button_Save = new JButton("保存配置");
	private JButton button_Enter = new JButton("进入服务器");
	private File file = new File("config/config.ini");
	
	/**
	 * 创建一个服务端登录窗体，它是可见的
	 */
	public Login_JFrame() {
		super("服务器启动配置");
		
		launchFrame();
		makeAll();
	}

	/**
	 * 生成一个包括服务端登录窗体中所有需要的控件的JPanel
	 */
	private void makeAll(){
		
		JPanel panel_Message = new JPanel(); 
		label_Message.setForeground(Color.red);
		panel_Message.add(label_Message);
		
		JPanel panel_File = new JPanel(); 
		JLabel label_File = new JLabel("文     件");
		label_File.setPreferredSize(new Dimension(50,23));
		textField_File.setEditable(false);
		panel_File.add(label_File);
		panel_File.add(textField_File);
		
		JPanel panel_Port = new JPanel();
		JLabel label_Port = new JLabel("端口号");
		label_Port.setPreferredSize(new Dimension(50,23));
		panel_Port.add(label_Port);
		panel_Port.add(textField_Port);
		
		Login_Button_Listener buttonListener = new Login_Button_Listener(this);
		button_Test.addActionListener(buttonListener);
		//button_Save.setEnabled(false);
		button_Save.addActionListener(buttonListener);
		//button_Enter.setEnabled(false);
		button_Enter.addActionListener(buttonListener);
		
		JPanel panel_Button = new JPanel();
		panel_Button.add(button_Test);
		panel_Button.add(button_Save);
		panel_Button.add(button_Enter);
		
		Box box = Box.createVerticalBox();
		box.add(Box.createRigidArea(new Dimension(1,5)));
		box.add(panel_Message);
		box.add(Box.createRigidArea(new Dimension(1,5)));
		box.add(panel_File);
		box.add(Box.createRigidArea(new Dimension(1,5)));
		box.add(panel_Port);
		box.add(Box.createRigidArea(new Dimension(1,5)));
		box.add(panel_Button);
		
		this.add(box);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * 如果配置文件不存在将创建并初始化文件地址
	 */
	private void launchFrame(){
		//保证相应的文件已生成
		LogsReaderWriter.createNewFile(file.getPath());
		//获得绝对路径显示在文本框里
		String absolutePath = file.getAbsolutePath();
		textField_File.setText(absolutePath);
		
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			properties.load(fileInputStream);
			String port = properties.getProperty("port");
			textField_Port.setText(port);
		} catch (FileNotFoundException e) {
			//textField_Port.setText("");
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}finally{
			try {
				fileInputStream.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	/**
	 * 保存端口到配置文件中
	 * @param properties
	 */
	public void saveConfig (Properties properties) {
		//保证相应的文件已生成
		LogsReaderWriter.createNewFile(file.getPath());
		
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file,false);
			properties.store(fileOutputStream, "comments");
		} catch (FileNotFoundException e1) {
			//e1.printStackTrace();
		} catch (IOException e1) {
			//e1.printStackTrace();
		}finally{
			try {
				fileOutputStream.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}
	}

	/**
	 * 获得端口输入框
	 * @return	端口输入框
	 */
	public JTextField getTextField_Port() {
		return textField_Port;
	}

	/**
	 * 获得测试按钮
	 * @return	测试按钮
	 */
	public JButton getButton_Test() {
		return button_Test;
	}

	/**
	 * 获得保存按钮
	 * @return	保存按钮
	 */
	public JButton getButton_Save() {
		return button_Save;
	}

	/**
	 * 获得进入服务器按钮
	 * @return	进入服务器按钮
	 */
	public JButton getButton_Enter() {
		return button_Enter;
	}

	/**
	 * 获得消息标签
	 * @return	消息标签
	 */
	public JLabel getLabel_Message() {
		return label_Message;
	}

}

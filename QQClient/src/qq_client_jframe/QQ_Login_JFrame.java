package qq_client_jframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import listener.QQ_Login_Button_Listener;
import mytools.LogsReaderWriter;

/**
 * 这是一个QQ登录窗体类，它继承自javax.swing.JFrame
 * @author Devon
 *
 */
public class QQ_Login_JFrame extends JFrame{
	private JComboBox comboBox_ID = new JComboBox();
	private JPasswordField passwordField_Password = new JPasswordField("123456");
	private JPanel panel_NetOption = null;
	private JLabel label_NetOption = new JLabel();
	private JButton button_NetOption = new JButton("设 置");
	private JButton button_Login = new JButton("登 录");
	private JButton button_Exit = new JButton("退 出");
	private File file = new File("id/id.txt");
	private JComboBox comboBox_IP = new JComboBox(new String[]{"127.0.0.1"});
	private JTextField textField_Port = new JTextField("6000");
	
	/**
	 * 构造一个登录窗体
	 */
	public QQ_Login_JFrame() {
		super("QQ用户登录");	
		
		//初始化QQ号码框,
		launchComboBox_ID();
		
		this.panel_NetOption = this.makeNetOption();
		this.panel_NetOption.setVisible(false);
		this.makeAll();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * 生成一个显示在登录窗体上的有图片的JLable
	 * @return		显示在登录窗体上的有图片的JLable
	 */
	private JLabel makeLoginImage(){
		Icon i = new ImageIcon("image/QQ_Login.jpg");
		JLabel l = new JLabel(i);
		return l;
	}
	
	/**
	 * 生成一个登录区域的JPanel
	 * @return		一个登录区域的JPanel
	 */
	private JPanel makeLoginArea(){
		JPanel p = new JPanel();
		BoxLayout bl = new BoxLayout(p,BoxLayout.PAGE_AXIS);
		p.setLayout(bl);
		//p.setBackground(Color.getHSBColor(54, 0.2f, 0.2f));//颜色
		Border border1 = BorderFactory.createEmptyBorder(5,5,5,5);
		Border border2 = BorderFactory.createLineBorder(Color.blue);
		Border border3 = BorderFactory.createTitledBorder(border2, "QQ登录");
		Border bordercom = BorderFactory.createCompoundBorder(border1, border3);
		p.setBorder(bordercom);
		
//		p.setBorder(border1);
		//p.setBorder(border2);
//		p.setBorder(border3);
		
		JPanel panel_ID = new JPanel();
		JLabel label_ID = new JLabel("QQ号码:");
		//l1.setPreferredSize(new Dimension(50,23));
		panel_ID.add(label_ID);
		
		comboBox_ID.setPreferredSize(new Dimension(175,23));
		comboBox_ID.setEditable(true);
		panel_ID.add(comboBox_ID);
		
		JPanel panel_Password = new JPanel();
		JLabel label_Password = new JLabel("QQ密码:");
		//l2.setPreferredSize(new Dimension(50,23));
		panel_Password.add(label_Password);
		
		passwordField_Password.setPreferredSize(new Dimension(175,23));
		passwordField_Password.setEchoChar('●');
		panel_Password.add(passwordField_Password);
		
		p.add(Box.createRigidArea(new Dimension(1,20)));
		p.add(panel_ID);
		p.add(Box.createRigidArea(new Dimension(1,10)));
		p.add(panel_Password);
		p.add(Box.createRigidArea(new Dimension(1,30)));
		
		return p;
	}
	
	/**
	 * 生成一个按钮区域的JPanel
	 * @return		一个按钮区域的JPanel
	 */
	private JPanel makeButton(){
		JPanel p = new JPanel();
		
		QQ_Login_Button_Listener listener = new QQ_Login_Button_Listener(this);
		button_NetOption.setPreferredSize(new Dimension(70,23));
		button_NetOption.addActionListener(listener);
		button_Login.setPreferredSize(new Dimension(70,23));
		button_Login.addActionListener(listener);
		button_Exit.setPreferredSize(new Dimension(70,23));
		button_Exit.addActionListener(listener);
		p.add(this.button_NetOption);
		p.add(Box.createRigidArea(new Dimension(20,1)));
		p.add(button_Login);
		p.add(Box.createRigidArea(new Dimension(20,1)));
		p.add(this.button_Exit);
		
		return p;
	}

	/**
	 * 生成登录界面JPanel
	 * @return		登录界面JPanel
	 */
	private JPanel makeMain(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(this.makeLoginImage(),BorderLayout.NORTH);
		panel.add(this.makeLoginArea(),BorderLayout.CENTER);
		panel.add(this.makeButton(),BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * 生成网络设置JPanel
	 * @return		网络设置JPanel
	 */
	private JPanel makeNetOption(){
		JPanel panel = new JPanel(new GridLayout(3,1));
		Border border1 = BorderFactory.createEmptyBorder(5,5,5,5);
		Border border2 = BorderFactory.createLineBorder(Color.BLUE);
		Border border3 = BorderFactory.createTitledBorder(border2,"网络设置");
		Border bordercom = BorderFactory.createCompoundBorder(border1, border3);
		panel.setBorder(bordercom);
		
		JPanel panel0 = new JPanel();
		label_NetOption.setForeground(Color.red);
		panel0.add(label_NetOption);
		
		JPanel panel1 = new JPanel();
		JLabel label1 = new JLabel("服务器地址:");
		label1.setPreferredSize(new Dimension(68,23));
		
		comboBox_IP.setEditable(true);
		comboBox_IP.setPreferredSize(new Dimension(175,23));
		panel1.add(label1);
		panel1.add(comboBox_IP);
		
		JPanel panel2 = new JPanel();
		JLabel label2 = new JLabel("端    口    号:");
		label2.setPreferredSize(new Dimension(68,23));
		
		textField_Port.setPreferredSize(new Dimension(175,23));
		panel2.add(label2);
		panel2.add(textField_Port);
		
		panel.add(panel0);
		panel.add(panel1);
		panel.add(panel2);
		
		return panel;
		
	}
	
	/**
	 * 生成集成了登录界面与网络设置界面的总窗体
	 */
	private void makeAll(){
		this.add(this.makeMain(),BorderLayout.CENTER);
		this.add(this.panel_NetOption,BorderLayout.SOUTH);
	}
	
	/**
	 * 登录成功时，使用此方法可将本次登录号码如不存在则存入ID文件末尾，否则将取代原有位置上的号码（即被移动到末尾）
	 */
	public void autoAppendID(){	
		LogsReaderWriter.createNewFile(file.getPath());
		
		String id = comboBox_ID.getSelectedItem().toString();
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		StringBuffer stringBuffer = new StringBuffer();
		try {
			String row = "";
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			while((row = bufferedReader.readLine()) != null){
				//id重复时不加
				if(row.equals(id)){
					continue;
				}
				stringBuffer.append(row + "\n");
			}
			//本次登录使用的id加到末尾
			stringBuffer.append(id + "\n");
			
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (FileNotFoundException e1) {
			//e1.printStackTrace();
		} catch (IOException e1) {
			//e1.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			try {
				bufferedWriter.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 从文件读取数据初始化ID输入框
	 */
	private void launchComboBox_ID(){
		LogsReaderWriter.createNewFile(file.getPath());
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			Vector<String> id = new Vector<String>();
			Vector<String> idReverse = new Vector<String>();
			String row = null;
			while((row = bufferedReader.readLine()) != null){
				id.add(row);
			}
			ListIterator<String> li = id.listIterator(id.size());
			while(li.hasPrevious()){
				idReverse.add(li.previous());
			}
			comboBox_ID.setModel(new DefaultComboBoxModel(idReverse));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}finally{
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获得设置按钮
	 * @return		设置按钮
	 */
	public JButton getButton_NetOption() {
		return button_NetOption;
	}

	/**
	 * 获得退出按钮
	 * @return		退出按钮
	 */
	public JButton getButton_Exit() {
		return button_Exit;
	}

	/**
	 * 获得网络设置面板
	 * @return		网络设置面板
	 */
	public JPanel getPanel_NetOption() {
		return panel_NetOption;
	}

	/**
	 * 获得登录按钮
	 * @return		登录按钮
	 */
	public JButton getButton_Login() {
		return button_Login;
	}
	
	/**
	 * 获得ID输入框
	 * @return		ID输入框
	 */
	public JComboBox getComboBox_ID() {
		return comboBox_ID;
	}

	/**
	 * 获得密码输入框
	 * @return		密码输入框
	 */
	public JPasswordField getPasswordField_Password() {
		return passwordField_Password;
	}

	/**
	 * 获得IP输入框
	 * @return		IP输入框
	 */
	public JComboBox getComboBox_IP() {
		return comboBox_IP;
	}

	/**
	 * 获得端口输入框
	 * @return		端口输入框
	 */
	public JTextField getTextField_Port() {
		return textField_Port;
	}

	/**
	 * 获得网络设置面板上的提示标签
	 * @return		网络设置面板上的提示标签
	 */
	public JLabel getLabel_NetOption() {
		return label_NetOption;
	}
}


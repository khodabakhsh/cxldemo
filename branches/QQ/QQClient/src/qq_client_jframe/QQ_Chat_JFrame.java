package qq_client_jframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import ext.JTextAreaExt;

import pub.QQPackage;
import qq_client_jdialog.ResetPassword_JDialog;
import qq_client_thread.Client_Read_Thread;


import listener.QQ_Chat_Button_Listener;
import listener.QQ_Chat_Key_Listener;
import listener.QQ_Chat_List_Listener;

/**
 * 这是一个QQ聊天窗体类，它继承自javax.swing.JFrame
 * @author Devon
 *
 */
public class QQ_Chat_JFrame extends JFrame{
	private JButton button_ChatLogs = new JButton("聊天记录↓");
	private JButton button_Send = new JButton("发送");
	private JButton button_Close = new JButton("关闭");
	private Socket socket = null;
	private JLabel lable_To = new JLabel();
	private JButton button_ResPsw = new JButton("修改密码");
	private JPanel panel_ChatLogs = null;
	private JTextAreaExt textArea_Dsp = new JTextAreaExt(15, 60);
	private JTextArea textArea_Input = new JTextArea(5, 60);
	private JList list_OnlineUsers = new JList();
	private JTextArea textArea_Notice = new JTextArea(25,25);
	private JTextAreaExt textArea_ChatLogs = new JTextAreaExt();
	private Client_Read_Thread readThread_Client = null;
	private ResetPassword_JDialog resetPassword_JDialog = null;
	private String ID = null;
	private String name_ID = null;
	private ObjectOutputStream objectOutputStream = null;
	private ObjectInputStream objectInputStream = null;
	
	public QQ_Chat_JFrame(Socket socket, QQPackage qqPackageReturn,ObjectOutputStream objectOutputStream ,
	ObjectInputStream objectInputStream ) {
		super("QQ聊天 - made by Devon | QQ:93785732");
		this.socket = socket;
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
		//拆包
		Vector data = (Vector)qqPackageReturn.getData();
		ID = data.get(0).toString();
		String name = data.get(1).toString();
		DefaultListModel defaultListModel = (DefaultListModel)data.get(2);
		String notice = data.get(3).toString();
		name_ID = name + "(" + ID + ")";
		setTitle(name_ID + " - Made By Devon | QQ:93785732");
		list_OnlineUsers.setModel(defaultListModel);
		textArea_Notice.setText(notice);
		
		makeAll();
		setSize(new Dimension(549,480));
		setPreferredSize(new Dimension(549,480));
		panel_ChatLogs.setVisible(false);
		//this.pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		readThread_Client = new Client_Read_Thread(this,socket);
		readThread_Client.start();
	}

	/**
	 * 生成一个聊天为主功能的JPanel
	 * @return		一个聊天为主内容的JPanel
	 */
	private JPanel makeChatArea(){
		QQ_Chat_Button_Listener listener = new QQ_Chat_Button_Listener(this);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel panel1 = new JPanel();
		BoxLayout boxLayout1 = new BoxLayout(panel1,BoxLayout.LINE_AXIS);
		panel1.setLayout(boxLayout1);
		
		button_ResPsw.setPreferredSize(new Dimension(95,23));
		button_ResPsw.addActionListener(listener);
		panel1.add(lable_To);
		panel1.add(Box.createHorizontalGlue());
		panel1.add(button_ResPsw);
	
		JScrollPane scrollPane_Dsp = new JScrollPane(textArea_Dsp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea_Dsp.setLineWrap(true);
		textArea_Dsp.setEditable(false);
		
		textArea_Input.setLineWrap(true);
		textArea_Input.addKeyListener(new QQ_Chat_Key_Listener(this));
		JScrollPane scrollPane_Input = new JScrollPane(textArea_Input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,scrollPane_Dsp,scrollPane_Input);
		splitPane.setResizeWeight(1);
		
		JPanel panel2 = new JPanel();
		BoxLayout boxLayout2 = new BoxLayout(panel2,BoxLayout.LINE_AXIS);
		panel2.setLayout(boxLayout2);
		
		JLabel label_SendKey = new JLabel("Ctrl+Enter发送消息  ");
		label_SendKey.setForeground(Color.GRAY);
		button_ChatLogs.setPreferredSize(new Dimension(99,23));
		button_ChatLogs.addActionListener(listener);
		button_Send.setPreferredSize(new Dimension(60,23));
		button_Send.addActionListener(listener);
		button_Close.setPreferredSize(new Dimension(60,23));
		button_Close.addActionListener(listener);
		panel2.add(button_ChatLogs);
		panel2.add(Box.createHorizontalGlue());
		panel2.add(label_SendKey);
		panel2.add(button_Send);
		panel2.add(Box.createRigidArea(new Dimension(5,1)));
		panel2.add(button_Close);
				
		panel.add(panel1,BorderLayout.NORTH);
		panel.add(splitPane,BorderLayout.CENTER);
		panel.add(panel2,BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * 生成一个具有公告信息和在线用户列表的JSplitPane
	 * @return		具有公告信息和在线用户列表的JSplitPane
	 */
	private JSplitPane makeImfomation(){
		QQ_Chat_List_Listener listener = new QQ_Chat_List_Listener(this);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JScrollPane scrollPane_Notice = new JScrollPane(textArea_Notice,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		textArea_Notice.setCaretPosition(0);
		textArea_Notice.setEditable(false);
		textArea_Notice.setLineWrap(true);
		scrollPane_Notice.setPreferredSize(new Dimension(150,150));
		tabbedPane.addTab("公告信息",null,scrollPane_Notice,"公告信息");
		
		
		JPanel panel_OnlineUsers = new JPanel(new BorderLayout());
		JLabel lable_OnlineUsers = new JLabel("在线用户");
		list_OnlineUsers.addListSelectionListener(listener);
		JScrollPane scrollPane_OnlineUsers = new JScrollPane(list_OnlineUsers,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_OnlineUsers.getVerticalScrollBar().setValue(0); 
		
		panel_OnlineUsers.add(lable_OnlineUsers,BorderLayout.NORTH);
		panel_OnlineUsers.add(scrollPane_OnlineUsers,BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,tabbedPane,panel_OnlineUsers);
		
		return splitPane;
		
	}
	
	/**
	 * 生成一个聊天总面板，即makeChatArea()与makeImfomation()的集成面板
	 * @return		makeChatArea()与makeImfomation()的集成面板
	 */
	private JSplitPane makeMain(){
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,makeChatArea(),makeImfomation());
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(1);
		
		return splitPane;
	}
	
	/**
	 * 生成一个聊天记录面板，它默认是不可见的
	 * @return		一个聊天记录面板
	 */
	private JPanel makeChatLogs(){
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel panel1 = new JPanel();
		BoxLayout boxLayout1 = new BoxLayout(panel1, BoxLayout.LINE_AXIS);
		panel1.setLayout(boxLayout1);
		JLabel lable0 = new JLabel("聊天记录");

//		JComboBox comboBox1_1 = new JComboBox(new String[]{"2009","2010"});
//		comboBox1_1.setPreferredSize(new Dimension(40,23));
//		JLabel lable1_1 = new JLabel("年");
//		JComboBox comboBox1_2 = new JComboBox(new String[]{"1","2"});
//		comboBox1_2.setPreferredSize(new Dimension(40,23));
//		JLabel lable1_2 = new JLabel("月");
//		JComboBox comboBox1_3 = new JComboBox(new String[]{"29","30"});
//		comboBox1_3.setPreferredSize(new Dimension(40,23));
//		JLabel lable1_3 = new JLabel("日");
//		JButton button1 = new JButton("查询");
//		button1.setPreferredSize(new Dimension(60,23));
		panel1.add(lable0);
		panel1.add(Box.createHorizontalGlue());
//		panel1.add(comboBox1_1);
//		panel1.add(lable1_1);
//		panel1.add(comboBox1_2);
//		panel1.add(lable1_2);
//		panel1.add(comboBox1_3);
//		panel1.add(lable1_3);
//		panel1.add(button1);
		
		//textArea_ChatLogs.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea_ChatLogs,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea_ChatLogs.setLineWrap(true);
		textArea_ChatLogs.setEditable(false);
		
		panel.add(panel1,BorderLayout.NORTH);
		panel.add(scrollPane,BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * 生成一个集成了聊天面板与聊天记录面板的总面板，聊天记录面板默认为不可见
	 */
	private void makeAll(){
		JPanel panel = new JPanel(new BorderLayout());
		
		panel_ChatLogs = makeChatLogs();
		panel_ChatLogs.setPreferredSize(new Dimension(549,130));
		panel.add(makeMain(),BorderLayout.CENTER);
		panel.add(panel_ChatLogs,BorderLayout.SOUTH);
		
		add(panel);
		
	}
	
	/**
	 * 获得聊天记录按钮
	 * @return		聊天记录按钮
	 */
	public JButton getButton_ChatLogs() {
		return button_ChatLogs;
	}

	/**
	 * 获得socket
	 * @return	socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * 获得显示接收者的标签
	 * @return		显示接收者的标签
	 */
	public JLabel getLable_To() {
		return lable_To;
	}

	/**
	 * 获得聊天记录面板
	 * @return		聊天记录面板
	 */
	public JPanel getPanel_ChatLogs() {
		return panel_ChatLogs;
	}

	/**
	 * 获得修改密码按钮
	 * @return		修改密码按钮
	 */
	public JButton getButton_ResPsw() {
		return button_ResPsw;
	}

	/**
	 * 获得显示当前聊天内容的文本域
	 * @return		显示当前聊天内容的文本域
	 */
	public JTextArea getTextArea_Dsp() {
		return textArea_Dsp;
	}

	/**
	 * 获得输入消息的文本域
	 * @return		输入消息的文本域
	 */
	public JTextArea getTextArea_Input() {
		return textArea_Input;
	}

	/**
	 * 获得发送按钮
	 * @return	发送按钮
	 */
	public JButton getButton_Send() {
		return button_Send;
	}

	/**
	 * 获得关闭按钮
	 * @return		关闭按钮
	 */
	public JButton getButton_Close() {
		return button_Close;
	}

	/**
	 * 获得在线用户列表JList
	 * @return		在线用户列表JList
	 */
	public JList getList_OnlineUsers() {
		return list_OnlineUsers;
	}

	/**
	 * 获得公告信息的文本域
	 * @return		公告信息的文本域
	 */
	public JTextArea getTextArea_Notice() {
		return textArea_Notice;
	}

	/**
	 * 获得使用该聊天窗体的用户ID
	 * @return		使用该聊天窗体的用户ID
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * 获得该窗体用户对服务器的对象输出流
	 * @return		该窗体用户对服务器的对象输出流
	 */
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	/**
	 * 获得该窗体用户对服务器的对象输入流
	 * @return		该窗体用户对服务器的对象输入流
	 */
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	/**
	 * 设置socket
	 * @param socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * 获得修改密码对话框
	 * @return		修改密码对话框
	 */
	public ResetPassword_JDialog getResetPassword_JDialog() {
		return resetPassword_JDialog;
	}

	/**
	 * 获得聊天记录的文本域
	 * @return		聊天记录的文本域
	 */
	public JTextArea getTextArea_ChatLogs() {
		return textArea_ChatLogs;
	}

	/**
	 * 设置修改密码对话框
	 * @param resetPassword_JDialog
	 */
	public void setResetPassword_JDialog(
			ResetPassword_JDialog resetPassword_JDialog) {
		this.resetPassword_JDialog = resetPassword_JDialog;
	}

	public String getName_ID() {
		return name_ID;
	}

	public void setName_ID(String nameID) {
		name_ID = nameID;
	}
}


package qq_server_jdialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import listener.UserInfo_Button_Listener;

import qq_server_jframe.QQ_Server_JFrame;
import qq_server_jframe.UsersManager_JPanel;

import dao.bean.User;
import dao.impl.UserDaoImpl;

/**
 * 这是一个添加用户和修改用户对话框，它继承自javax.swing.JDialog
 * @author Devon
 *
 */
public class UserInfo_JDialog extends JDialog {
	private JLabel label_Message = new JLabel();
	private JTextField textField_ID = new JTextField(15);
	private JPasswordField passwordField_Password = new JPasswordField(15);
	private JPasswordField passwordField_PasswordAgn = new JPasswordField(15);
	private JTextField textField_Name = new JTextField(15);
	private JRadioButton radioButton_Male = new JRadioButton("男        ", true);
	private JRadioButton radioButton_Female = new JRadioButton("女");
	private JTextField textField_Age = new JTextField(15);
	private JTextField textField_Address = new JTextField(15);
	private JComboBox comboBox_IsOnline = new JComboBox();
	private JTextField textField_RegTime = new JTextField(15);
	private JButton button_Save = new JButton("保存");
	private JButton button_Cancel = new JButton("取消");
	private QQ_Server_JFrame qq_Server_JFrame;
	private UsersManager_JPanel usersManager_JPanel;
	
	/**
	 * 添加用户
	 */
	public final static int USER_INFO_ADD = 1;
	
	/**
	 * 修改用户
	 */
	public final static int USER_INFO_MOD = 2;

	/**
	 * 创建一个用户信息的对话框
	 * @param usersManager_JPanel		用户管理面板
	 * @param qq_Server_JFrame		服务端窗体
	 * @param kind		选择创建的对话框的种类，有添加用户和修改用户两种。
	 */
	public UserInfo_JDialog(UsersManager_JPanel usersManager_JPanel,
			QQ_Server_JFrame qq_Server_JFrame, int kind) {
		super(qq_Server_JFrame);
		//ADD-----------------------------------------------------------------
		if(kind == 1){
			this.qq_Server_JFrame = qq_Server_JFrame;
			this.setTitle("添加用户");
			this.add(this.makeMainPane());

			// 初始化编号
			String id_Next = UserDaoImpl.getInstance().getNextSid();
			this.textField_ID.setText(id_Next);
			this.textField_ID.setEditable(false);
			// 初始化密码
			this.passwordField_Password.setText("123456");
			// this.passwordField_Password.setEditable(false);
			this.passwordField_PasswordAgn.setText("123456");
			// this.passwordField_PasswordAgn.setEditable(false);
			// 初始化注册时间
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			this.textField_RegTime.setText(sdf.format(date));
			this.textField_RegTime.setEditable(false);

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.pack();
			this.setResizable(false);
			this.setLocationRelativeTo(qq_Server_JFrame);
			this.setModal(true);
			this.setVisible(true);
		//MOD-------------------------------------------------------------------
		}else if(kind == 2){
			this.qq_Server_JFrame = qq_Server_JFrame;
			this.setTitle("修改资料");
			this.add(this.makeMainPane());

			// 获得编号
			int row = this.qq_Server_JFrame.getUsersManager().getQQUsersInfo_JTable().getSelectedRow();
			String sid = this.qq_Server_JFrame.getUsersManager().getQQUsersInfo_JTable().getValueAt(row, 0).toString();
			this.textField_ID.setText(sid);
			this.textField_ID.setEditable(false);
			// 获得密码
			User user = UserDaoImpl.getInstance().selectList(sid);
			this.passwordField_Password.setText(user.getSpassword());
			this.passwordField_Password.setEditable(false);
			this.passwordField_PasswordAgn.setText(user.getSpassword());
			this.passwordField_PasswordAgn.setEditable(false);
			// 获得姓名
			this.textField_Name.setText(user.getSname());
			// 获得性别
			String sex = user.getSsex();
			if(sex.equals("男")){
				this.radioButton_Male.setSelected(true);
			}else{
				this.radioButton_Female.setSelected(true);
			}
			//获得年龄
			this.textField_Age.setText(user.getNage());
			//获得地址
			this.textField_Address.setText(user.getSaddress());
			//是否在线
			this.comboBox_IsOnline.setSelectedItem("离线");
			// 初始化注册时间
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			this.textField_RegTime.setText(sdf.format(date));
			this.textField_RegTime.setEditable(false);

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.pack();
			this.setResizable(false);
			this.setLocationRelativeTo(qq_Server_JFrame);
			this.setModal(true);
			this.setVisible(true);
		}
		
	}

	/**
	 * 生成一个存放对话框内容的JPanel
	 * @return		存放对话框内容的JPanel
	 */
	private JPanel makeMainPane() {
		JPanel panel_Message = new JPanel();
		label_Message.setForeground(Color.red);
		panel_Message.add(label_Message);

		JPanel panel_ID = new JPanel();
		panel_ID.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_ID = new JLabel("编            号:");
		label_ID.setPreferredSize(new Dimension(80, 15));
		panel_ID.add(label_ID);
		panel_ID.add(this.textField_ID);

		JPanel panel_Password = new JPanel();
		panel_Password.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_Password = new JLabel("密            码:");
		label_Password.setPreferredSize(new Dimension(80, 15));
		this.passwordField_Password.setEchoChar('●');
		panel_Password.add(label_Password);
		panel_Password.add(this.passwordField_Password);

		JPanel panel_PasswordAgn = new JPanel();
		panel_PasswordAgn.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_PasswordAgn = new JLabel("确 认 密 码:");
		label_PasswordAgn.setPreferredSize(new Dimension(80, 15));
		this.passwordField_PasswordAgn.setEchoChar('●');
		panel_PasswordAgn.add(label_PasswordAgn);
		panel_PasswordAgn.add(this.passwordField_PasswordAgn);

		JPanel panel_Name = new JPanel();
		panel_Name.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_Name = new JLabel("姓            名:");
		label_Name.setPreferredSize(new Dimension(80, 15));
		panel_Name.add(label_Name);
		panel_Name.add(this.textField_Name);

		JPanel panel_Sex = new JPanel();
		panel_Sex.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_Sex = new JLabel("性            别:");
		label_Sex.setPreferredSize(new Dimension(80, 15));

		ButtonGroup bg5 = new ButtonGroup();
		bg5.add(radioButton_Male);
		bg5.add(radioButton_Female);
		panel_Sex.add(label_Sex);
		panel_Sex.add(radioButton_Male);
		panel_Sex.add(radioButton_Female);

		JPanel panel_Age = new JPanel();
		panel_Age.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_Age = new JLabel("年            龄:");
		label_Age.setPreferredSize(new Dimension(80, 15));

		panel_Age.add(label_Age);
		panel_Age.add(textField_Age);

		JPanel panel_Address = new JPanel();
		panel_Address.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_Address = new JLabel("住            址:");
		label_Address.setPreferredSize(new Dimension(80, 15));
		panel_Address.add(label_Address);
		panel_Address.add(textField_Address);

		JPanel panel_IsOnline = new JPanel();
		panel_IsOnline.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_IsOnline = new JLabel("是 否 在 线:");
		label_IsOnline.setPreferredSize(new Dimension(80, 15));
		comboBox_IsOnline.setPreferredSize(new Dimension(60, 22));
		comboBox_IsOnline.addItem("在线");
		comboBox_IsOnline.addItem("离线");
		comboBox_IsOnline.setSelectedItem("离线");
		comboBox_IsOnline.setEnabled(false);
		panel_IsOnline.add(label_IsOnline);
		panel_IsOnline.add(comboBox_IsOnline);

		JPanel panel_RegTime = new JPanel();
		panel_RegTime.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label_RegTime = new JLabel("注 册 时 间:");
		label_RegTime.setPreferredSize(new Dimension(80, 15));

		panel_RegTime.add(label_RegTime);
		panel_RegTime.add(textField_RegTime);

		JPanel panel_SaveCancel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		UserInfo_Button_Listener button_Listener = new UserInfo_Button_Listener(
				this, qq_Server_JFrame);
		button_Save.addActionListener(button_Listener);
		button_Cancel.addActionListener(button_Listener);
		panel_SaveCancel.add(button_Save);
		panel_SaveCancel.add(button_Cancel);

		JPanel p = new JPanel(new GridLayout(11, 1, 0, 0));
		Border border = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		p.setBorder(border);
		p.add(panel_Message);
		p.add(panel_ID);
		p.add(panel_Password);
		p.add(panel_PasswordAgn);
		p.add(panel_Name);
		p.add(panel_Sex);
		p.add(panel_Age);
		p.add(panel_Address);
		p.add(panel_IsOnline);
		p.add(panel_RegTime);
		p.add(panel_SaveCancel);

		return p;
	}

	/**
	 * 获得消息标签
	 * @return	消息标签
	 */
	public JLabel getLabel_Message() {
		return label_Message;
	}

	/**
	 * 获得ID输入框
	 * @return	ID输入框
	 */
	public JTextField getTextField_ID() {
		return textField_ID;
	}

	/**
	 * 获得密码输入框
	 * @return	密码输入框
	 */
	public JPasswordField getPasswordField_Password() {
		return passwordField_Password;
	}

	/**
	 * 获得确认密码输入框
	 * @return	确认密码输入框
	 */
	public JPasswordField getPasswordField_PasswordAgn() {
		return passwordField_PasswordAgn;
	}

	/**
	 * 获得姓名输入框
	 * @return	姓名输入框
	 */
	public JTextField getTextField_Name() {
		return textField_Name;
	}

	/**
	 * 获得单选框“男”
	 * @return	单选框“男”
	 */
	public JRadioButton getRadioButton_Male() {
		return radioButton_Male;
	}

	/**
	 * 获得单选框“妇”
	 * @return	单选框“妇”
	 */
	public JRadioButton getRadioButton_Female() {
		return radioButton_Female;
	}

	/**
	 * 获得年龄输入框
	 * @return	年龄输入框
	 */
	public JTextField getTextField_Age() {
		return textField_Age;
	}

	/**
	 * 获得地址输入框
	 * @return	地址输入框
	 */
	public JTextField getTextField_Address() {
		return textField_Address;
	}

	/**
	 * 获得是否在线下拉框
	 * @return	是否在线下拉框
	 */
	public JComboBox getComboBox_IsOnline() {
		return comboBox_IsOnline;
	}

	/**
	 * 获得注册时间输入框
	 * @return	注册时间输入框
	 */
	public JTextField getTextField_RegTime() {
		return textField_RegTime;
	}

	/**
	 * 获得保存按钮
	 * @return	保存按钮
	 */
	public JButton getButton_Save() {
		return button_Save;
	}

	/**
	 * 获得消取按钮
	 * @return	消取按钮
	 */
	public JButton getButton_Cancel() {
		return button_Cancel;
	}

	/**
	 * 获得服务器主窗体
	 * @return	服务器主窗体
	 */
	public QQ_Server_JFrame getQQ_Server_JFrame() {
		return qq_Server_JFrame;
	}

}


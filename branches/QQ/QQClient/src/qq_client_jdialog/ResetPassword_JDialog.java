package qq_client_jdialog;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import qq_client_jframe.QQ_Chat_JFrame;

import listener.ResetPassword_Button_Listener;

/**
 * 这是一个修改密码的对话框，它继承自javax.swing.JDialog
 * @author Devon
 *
 */
public class ResetPassword_JDialog extends JDialog {
	private JLabel label_Message = new JLabel(" ");
	private JPasswordField passwordField_Old = new JPasswordField();
	private JPasswordField passwordField_New = new JPasswordField();
	private JPasswordField passwordField_NewAgn = new JPasswordField();
	private JButton button_Confirm = new JButton("确定");
	private JButton button_Cancle = new JButton("取消");
	
	private QQ_Chat_JFrame qq_Chat_JFrame = null;
	
	/**
	 * 构造一个具有指定拥有者的修改密码对话框
	 * @param qq_Chat_JFrame	拥有者
	 */
	public ResetPassword_JDialog(QQ_Chat_JFrame qq_Chat_JFrame) {
		super(qq_Chat_JFrame,true);
		this.setTitle("修改密码");
		this.qq_Chat_JFrame = qq_Chat_JFrame;
		this.qq_Chat_JFrame.setResetPassword_JDialog(this);//让聊天窗户拥有此修改密码对话框的引用,这样可以在读取线程中进行关闭操作
		this.makeAll();
		
	}
	
	/**
	 * 产生一个可见的修改密码对话框
	 */
	private void makeAll() {

		JPanel panel_Message = new JPanel();
		label_Message.setForeground(Color.red);
		panel_Message.add(label_Message);

		JPanel panel_Old = new JPanel();
		JLabel label_Old = new JLabel("原      密      码");
		label_Old.setPreferredSize(new Dimension(80, 23));
		passwordField_Old.setPreferredSize(new Dimension(175,23));
		passwordField_Old.setEchoChar('●');
		panel_Old.add(label_Old);
		panel_Old.add(passwordField_Old);

		JPanel panel_New = new JPanel();
		JLabel label_New = new JLabel("新      密      码");
		label_New.setPreferredSize(new Dimension(80, 23));
		passwordField_New.setPreferredSize(new Dimension(175,23));
		passwordField_New.setEchoChar('●');
		panel_New.add(label_New);
		panel_New.add(passwordField_New);

		JPanel panel_NewAgn = new JPanel();
		JLabel label_NewAgn = new JLabel("确 认 新 密 码");
		label_NewAgn.setPreferredSize(new Dimension(80, 23));
		passwordField_NewAgn.setPreferredSize(new Dimension(175,23));
		passwordField_NewAgn.setEchoChar('●');
		panel_NewAgn.add(label_NewAgn);
		panel_NewAgn.add(passwordField_NewAgn);

		ResetPassword_Button_Listener buttonListener = new ResetPassword_Button_Listener(this,qq_Chat_JFrame);
		button_Confirm.addActionListener(buttonListener);
		button_Cancle.addActionListener(buttonListener);

		JPanel panel_Button = new JPanel();
		panel_Button.add(button_Confirm);
		panel_Button.add(button_Cancle);
		
		
		Box box = Box.createVerticalBox();
		box.add(Box.createRigidArea(new Dimension(1, 1)));
		box.add(panel_Message);
		box.add(Box.createRigidArea(new Dimension(1, 1)));
		box.add(panel_Old);
		box.add(Box.createRigidArea(new Dimension(1, 1)));
		box.add(panel_New);
		box.add(Box.createRigidArea(new Dimension(1, 1)));
		box.add(panel_NewAgn);
		box.add(Box.createRigidArea(new Dimension(1, 1)));
		box.add(panel_Button);

		this.add(box);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(qq_Chat_JFrame);
		this.setSize(new Dimension(315,225));
		this.setVisible(true);
	}

	/**
	 * 获得消息标签
	 * @return		消息标签
	 */
	public JLabel getLabel_Message() {
		return label_Message;
	}

	/**
	 * 获得确认按钮
	 * @return		确认按钮
	 */
	public JButton getButton_Confirm() {
		return button_Confirm;
	}

	/**
	 * 获得取消按钮
	 * @return		取消按钮
	 */
	public JButton getButton_Cancle() {
		return button_Cancle;
	}

	/**
	 * 获得旧密码输入框
	 * @return		旧密码输入框
	 */
	public JPasswordField getPasswordField_Old() {
		return passwordField_Old;
	}

	/**
	 * 获得新码输入框
	 * @return		新密码输入框
	 */
	public JPasswordField getPasswordField_New() {
		return passwordField_New;
	}

	/**
	 * 获得确认新密码输入框
	 * @return		确认新密码输入框
	 */
	public JPasswordField getPasswordField_NewAgn() {
		return passwordField_NewAgn;
	}
}

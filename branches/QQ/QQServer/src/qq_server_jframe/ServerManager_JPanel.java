package qq_server_jframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.impl.UserDaoImpl;
import ext.JTextAreaExt;

import listener.ServerManager_Button_Listener;
import listener.ServerManager_Table_Listener;

/**
 * 这是一个服务管理的面板，它继承自javax.swing.JPanel
 * @author Devon
 *
 */
public class ServerManager_JPanel extends JPanel {
	private JPanel panel_OnlineUsersList = new JPanel();
	private JTable qqUsersInfo_JTable = new JTable(){
		@Override
		public boolean isCellEditable(int row, int column) {
			// 列不可以编辑
			return false;
		}
		//渲染器,文字居中
		public TableCellRenderer getCellRenderer(int row, int column)
		{
			TableCellRenderer renderer = super.getCellRenderer(row, column);
			if (renderer instanceof JLabel)
			{
				((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
			}
			return renderer;
		}
	};
	private JButton button_Start = new JButton("启动通讯服务");
	private JButton button_End = new JButton("停止通讯服务");
	private JButton button_Offline = new JButton("强制用户下线");
	private JLabel label_Image = new JLabel();
	private JTextAreaExt textArea_CommunicationInfo = new JTextAreaExt(6, 80);
	private JTextArea textArea_NoticeSend = new JTextArea(4, 80);
	private JButton button_NoticeSend = new JButton("发送");
	private QQ_Server_JFrame qq_Server_JFrame = null;
	//定义一个监听器的全局,用于两个子panel,使之共用一个监听对象
	private ServerManager_Button_Listener button_Listener = null;
	//public JScrollPane scrollPane = null;

	private JLabel label_UsersTotal = new JLabel();
	private int total = 0;

	/**
	 * 创建一个服务管理面板
	 */
	public ServerManager_JPanel(QQ_Server_JFrame qq_Server_JFrame){
		super(new BorderLayout());
		
		this.qq_Server_JFrame = qq_Server_JFrame;
		this.button_Listener = new ServerManager_Button_Listener(this, qq_Server_JFrame);
		this.makeAll();
	}

	/**
	 * 生成一个显示在线用户列表的JPanel
	 * @return	显示在线用户列表的JPanel
	 */
	private JPanel makeOnlineUsersList() {
		ServerManager_Table_Listener  listener = new ServerManager_Table_Listener(this,qq_Server_JFrame);
		
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line,"在线用户列表");
		
		panel_OnlineUsersList.setBorder(border_LineTitle);
		panel_OnlineUsersList.setPreferredSize(new Dimension(1, 260));
		panel_OnlineUsersList.setLayout(new BorderLayout());
		qqUsersInfo_JTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置单选
		qqUsersInfo_JTable.getTableHeader().setReorderingAllowed(false); //设置不可移动列
		qqUsersInfo_JTable.getSelectionModel().addListSelectionListener(listener);
		
		panel_OnlineUsersList.add(new JScrollPane(qqUsersInfo_JTable), BorderLayout.CENTER);
		UserDaoImpl.getInstance().upOrDown("离线");//将所有用户置为离线状态
		updateQQUsersInfo_JTable();//程序启动时初始化在线用户列表
		
		return panel_OnlineUsersList;
	}

	/**
	 * 生成一个显示通讯信息提示的JPanel
	 * @return	通讯信息提示的JPanel
	 */
	private JPanel makeCommunicationInfo() {
		//Servermanager_TextArea_Listener listener = new Servermanager_TextArea_Listener(this);
		
		JPanel panel = new JPanel(new BorderLayout());
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line, "通讯信息提示");

		panel.setBorder(border_LineTitle);
		textArea_CommunicationInfo.setEditable(false);
		textArea_CommunicationInfo.setLineWrap(true);
		//textArea_CommunicationInfo.addCaretListener(listener);//添加插入文体事件监听
		JScrollPane scrollPane = new JScrollPane(textArea_CommunicationInfo,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel.add(scrollPane, BorderLayout.CENTER);
		// panel.setPreferredSize(new Dimension(200,50));//用于 以子组件的首选大小为基础调整
		// JSplitPane 的布局。

		return panel;
	}

	/**
	 * 生成一个公告发送的Box
	 * @return	一个公告发送的Box
	 */
	private Box makeNoticeSend() {
		Box box = Box.createVerticalBox();
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line,	"公告发送");
		box.setBorder(border_LineTitle);

		textArea_NoticeSend.setEditable(false);
		textArea_NoticeSend.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea_NoticeSend,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		
		button_NoticeSend.addActionListener(button_Listener);
		button_NoticeSend.setEnabled(false);
		//button_NoticeSend.setAlignmentX(Component.RIGHT_ALIGNMENT);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(button_NoticeSend);
		box.add(scrollPane);
		box.add(panel);
		// box.setPreferredSize(new Dimension(200,50));//用于 以子组件的首选大小为基础调整
		// JSplitPane 的布局。

		return box;
	}

	/**
	 * 生成一个服务管理的Box
	 * @return	服务管理的Box
	 */
	private Box makeServerManage() {
		Box box = Box.createHorizontalBox();
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line,"服务器管理");
		box.setBorder(border_LineTitle);

		Box box_Right = Box.createVerticalBox();
		
		ImageIcon icon = new ImageIcon("./Image/serverstop.gif");
		this.label_Image.setIcon(icon);
		
		
		button_Start.addActionListener(button_Listener);
		button_End.addActionListener(button_Listener);
		button_End.setEnabled(false);
		button_Offline.addActionListener(button_Listener);
		button_Offline.setEnabled(false);
		label_UsersTotal.setForeground(Color.blue);
		
		box_Right.add(button_Start);
		box_Right.add(Box.createRigidArea(new Dimension(1, 6)));
		box_Right.add(button_End);
		box_Right.add(Box.createRigidArea(new Dimension(1, 50)));
		box_Right.add(button_Offline);
		box_Right.add(Box.createVerticalGlue());
		box_Right.add(label_UsersTotal);
		
		box.add(this.label_Image);
		box.add(Box.createHorizontalGlue());
		box.add(Box.createRigidArea(new Dimension(40, 1)));
		box.add(box_Right);

		return box;
	}

	/**
	 * 集成了必需的面板
	 */
	private void makeAll() {
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.makeCommunicationInfo(), this.makeNoticeSend());
		// splitPane1.resetToPreferredSizes();//以子组件的首选大小为基础调整 JSplitPane 的布局。
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitPane1, this.makeServerManage());
		splitPane2.setResizeWeight(1);
		JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.makeOnlineUsersList(), splitPane2);
		splitPane3.setResizeWeight(1);
		this.add(splitPane3,BorderLayout.CENTER);
	}
	
	/**
	 * 从文件中获取最新的用户信息加载到table中,用户上下线后执行此方法
	 */
	public void updateQQUsersInfo_JTable(){
		Vector<Vector<String>> data = UserDaoImpl.getInstance().selectList("", "", UserDaoImpl.ONLY_UP);
		updateQQUsersInfo_JTable(data);
		
		
	}
	
	/**
	 * 显示查询结果，参数为查询后返回的结果
	 * @param data	用户数据
	 */
	public void updateQQUsersInfo_JTable(Vector<Vector<String>> data){
		Vector<String> columnName = new Vector<String>();//表头
		columnName.add("编号(QQ)");	
		columnName.add("姓名");
		columnName.add("性别");
		columnName.add("年龄");
		columnName.add("是否在线");
		
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setDataVector(data, columnName);
		qqUsersInfo_JTable.setModel(dtm);
		qqUsersInfo_JTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//显示当前在线人数
		total = data.size();
		label_UsersTotal.setText("当前在线用户人数：" + total + "    ");
	}

	/**
	 * 获得开始服务按钮
	 * @return	开始服务按钮
	 */
	public JButton getButton_Start() {
		return button_Start;
	}

	/**
	 * 获得停止服务按钮
	 * @return	停止服务按钮
	 */
	public JButton getButton_End() {
		return button_End;
	}

	/**
	 * 获得强制下线用户按钮
	 * @return	强制下线用户按钮
	 */
	public JButton getButton_Offline() {
		return button_Offline;
	}

	/**
	 * 获得显示图片的JLabel
	 * @return	显示图片的JLabel
	 */
	public JLabel getLabel_Image() {
		return label_Image;
	}

	/**
	 * 获得消息发送框
	 * @return	消息发送框
	 */
	public JTextArea getTextArea_NoticeSend() {
		return textArea_NoticeSend;
	}

	/**
	 * 获得消息发送按钮
	 * @return	消息发送按钮
	 */
	public JButton getButton_NoticeSend() {
		return button_NoticeSend;
	}

	/**
	 * 获得在线用户列表的JPanel
	 * @return	在线用户列表的JPanel
	 */
	public JPanel getPanel_OnlineUsersList() {
		return panel_OnlineUsersList;
	}

	/**
	 * 获得在线用户列表的JTable
	 * @return	在线用户列表的JTable
	 */
	public JTable getQqUsersInfo_JTable() {
		return qqUsersInfo_JTable;
	}

	/**
	 * 获得通讯信息提示框
	 * @return	通讯信息提示框
	 */
	public JTextArea getTextArea_CommunicationInfo() {
		return textArea_CommunicationInfo;
	}

	/**
	 * 获得服务端主窗体
	 * @return	服务端主窗体
	 */
	public QQ_Server_JFrame getQq_Server_JFrame() {
		return qq_Server_JFrame;
	}

	/**
	 * 获得在线用户列表
	 * @return	在线用户列表
	 */
	public JTable getQQUsersInfo_JTable() {
		return qqUsersInfo_JTable;
	}
}

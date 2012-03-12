package qq_server_jframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import listener.UsersManager_Button_Listener;
import listener.UsersManager_Table_Listener;

import dao.impl.UserDaoImpl;

/**
 * 这是一个用户管理面板，它继承自javax.swing.JPanel
 * @author Devon
 *
 */
public class UsersManager_JPanel extends JPanel{
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
	private JTextField textField_ID = new JTextField();
	private JTextField textField_Name = new JTextField();
	private JButton button_Query = new JButton("查询");
	private JLabel label_Total = new JLabel();
	private JButton button_Add = new JButton("添加用户");
	private JButton button_Del = new JButton("删除用户");
	private JButton button_Mod = new JButton("修改资料");
	private JButton button_Sel = new JButton("重置所选密码");
	private JButton button_All = new JButton("重置所有密码");
	//private JButton button_Ref = new JButton("刷新用户列表");

	private QQ_Server_JFrame qq_Server_JFrame = null;

	/**
	 * 创建一个用户管理面板
	 * @param qq_Server_JFrame	服务端主窗体
	 */
	public UsersManager_JPanel(QQ_Server_JFrame qq_Server_JFrame) {
		super(new BorderLayout());
		this.qq_Server_JFrame = qq_Server_JFrame;
		this.makeAll();
	}

	/**
	 * 生成一个用户管理面板
	 */
	public void makeAll(){
		JPanel panel = new JPanel(new BorderLayout());
		
		UsersManager_Button_Listener listener = new UsersManager_Button_Listener(this,qq_Server_JFrame);
		
		JPanel panel_North = new JPanel();
		JLabel lable_Account = new JLabel("编号QQ:");
		textField_ID.setPreferredSize(new Dimension(140,23));
		JLabel lable_UserName = new JLabel("用户名:");
		textField_Name.setPreferredSize(new Dimension(140,23));
		//JLabel lable_State = new JLabel("在线状态:");
		//Vector<String> vector_State = new Vector<String>();
		//vector_State.add("全部");
		//vector_State.add("在线");
		//vector_State.add("离线");
		//JComboBox comboBox_State = new JComboBox(vector_State);
		button_Query.addActionListener(listener);
		panel_North.add(lable_Account);
		panel_North.add(textField_ID);
		panel_North.add(lable_UserName);
		panel_North.add(textField_Name);
		//panel_North.add(lable_State);
		//panel_North.add(comboBox_State);
		panel_North.add(button_Query);
		panel_North.add(label_Total);
		
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line, "用户信息列表");
		
		JPanel panel_UsersInfoList = new JPanel(new BorderLayout());
		panel_UsersInfoList.setBorder(border_LineTitle);
		panel_UsersInfoList.setPreferredSize(new Dimension(744,480));
		panel_UsersInfoList.setLayout(new BorderLayout());
		panel_UsersInfoList.add(new JScrollPane(qqUsersInfo_JTable),BorderLayout.CENTER);
		qqUsersInfo_JTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置单选
		qqUsersInfo_JTable.getTableHeader().setReorderingAllowed(false); //设置不可移动列

		
		JPanel panel_South = new JPanel();
		button_Add.addActionListener(listener);
		button_Del.setEnabled(false);
		button_Del.addActionListener(listener);
		button_Mod.setEnabled(false);
		button_Mod.addActionListener(listener);
		button_Sel.setEnabled(false);
		button_Sel.addActionListener(listener);
		button_All.addActionListener(listener);
		//button_Ref.addActionListener(listener);
		panel_South.add(button_Add);
		panel_South.add(button_Del);
		panel_South.add(button_Mod);
		panel_South.add(button_Sel);
		panel_South.add(button_All);
		//panel_South.add(button_Ref);
		UsersManager_Table_Listener table_Listener = new UsersManager_Table_Listener(this,qq_Server_JFrame);
		qqUsersInfo_JTable.getSelectionModel().addListSelectionListener(table_Listener);//给table添加选择模型的监听
		

		updateQQUsersInfo_JTable();//程序启动时加载所有用户信息到table
		
		panel.add(panel_North,BorderLayout.NORTH);
		panel.add(panel_UsersInfoList,BorderLayout.CENTER);
		panel.add(panel_South,BorderLayout.SOUTH);
		
		add( panel);
	}
	
	/**
	 * 从文件中获取最新的用户信息加载到table中,每次对表的增删改查操作之后执行此方法
	 */
	public void updateQQUsersInfo_JTable(){
		Vector<Vector<String>> data = UserDaoImpl.getInstance().selectList("", "", UserDaoImpl.UP_AND_DOWN);
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
		columnName.add("住址");
		columnName.add("是否在线");
		columnName.add("注册时间");
		
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setDataVector(data, columnName);
		qqUsersInfo_JTable.setModel(dtm);
		qqUsersInfo_JTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		//更新模型后重置列宽
		qqUsersInfo_JTable.getColumnModel().getColumn(0).setPreferredWidth(28);
		qqUsersInfo_JTable.getColumnModel().getColumn(1).setPreferredWidth(45);
		qqUsersInfo_JTable.getColumnModel().getColumn(2).setPreferredWidth(2);
		qqUsersInfo_JTable.getColumnModel().getColumn(3).setPreferredWidth(3);
		qqUsersInfo_JTable.getColumnModel().getColumn(4).setPreferredWidth(230);
		qqUsersInfo_JTable.getColumnModel().getColumn(5).setPreferredWidth(2);
		qqUsersInfo_JTable.getColumnModel().getColumn(6).setPreferredWidth(90);
		//更新总用户数
		int total = data.size();
		label_Total.setForeground(Color.blue);
		label_Total.setText("    记录条数: " + total);
	}
	
	/**
	 * 获得用户列表的JTable
	 * @return	用户列表的JTable
	 */
	public JTable getQQUsersInfo_JTable() {
		return qqUsersInfo_JTable;
	}

	/**
	 * 获得添加用户按钮
	 * @return	添加用户按钮
	 */
	public JButton getButton_Add() {
		return button_Add;
	}

	/**
	 * 获得删除用户按钮
	 * @return	删除户用按钮
	 */
	public JButton getButton_Del() {
		return button_Del;
	}

	/**
	 * 获得修改用户按钮
	 * @return	修改用户按钮
	 */
	public JButton getButton_Mod() {
		return button_Mod;
	}

	/**
	 * 获得重置所选用户按钮
	 * @return	重置所选用户按钮
	 */
	public JButton getButton_Sel() {
		return button_Sel;
	}

	/**
	 * 获得重置所有用户按钮
	 * @return	重置所有用户按钮
	 */
	public JButton getButton_All() {
		return button_All;
	}

//	public JButton getButton_Ref() {
//		return button_Ref;
//	}

	/**
	 * 获得查询按钮
	 * @return	查询按钮
	 */
	public JButton getButton_Query() {
		return button_Query;
	}

	/**
	 * 获得输入ID框
	 * @return	输入ID框
	 */
	public JTextField getTextField_ID() {
		return textField_ID;
	}

	/**
	 * 获得输入姓名框
	 * @return	输入姓名框
	 */
	public JTextField getTextField_Name() {
		return textField_Name;
	}

	/**
	 * 获得服务端主面板
	 * @return	服务端主面板
	 */
	public QQ_Server_JFrame getQQ_Server_JFrame() {
		return qq_Server_JFrame;
	}
}

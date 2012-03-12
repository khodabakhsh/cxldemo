package qq_server_jframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import listener.LogsManager_Button_Listener;
import listener.LogsManager_ComboBox_Listener;
import mytools.MyDate;

/**
 * 这是一个日志管理面板，它继承自javax.swing.JPanel
 * @author Devon
 *
 */
public class LogsManager_JPanel extends JPanel{
	private JComboBox comboBox_Year = new JComboBox(MyDate.year());
	private JComboBox comboBox_Month = new JComboBox(MyDate.month());
	private JComboBox comboBox_Day = null;
	private JButton button_Query = new JButton("查询");
	private JTextArea textArea = new JTextArea(25,32);
	private JTextField textField_KeyWords = new JTextField(10);
	private QQ_Server_JFrame qq_Server_JFrame;
	

	/**
	 * 创建一个日志管理面板，它默认显示当前系统日期
	 * @param qq_Server_JFrame	服务端总窗体
	 */
	public LogsManager_JPanel(QQ_Server_JFrame qq_Server_JFrame) {
		this.qq_Server_JFrame = qq_Server_JFrame;
		//初始化日期选择中的天数
		Vector<Integer> date = MyDate.currentDate();
		int year = date.get(0);
		int month = date.get(1);
		int day = date.get(2);
		String year_str = String.valueOf(year);
		String str_Month = String.valueOf(month);
		String day_str = String.valueOf(day);
		//大月---31
		if(str_Month.matches("1|3|5|7|8|(10)|(12)")){
			comboBox_Day = new JComboBox(MyDate.dateOf31());
		//小月---30
		}else if(str_Month.matches("4|6|9|11")){
			comboBox_Day = new JComboBox(MyDate.dateOf30());
		//判断是否闰年
		}else if(str_Month.equals("2")){
			if(MyDate.isLeapYear(year)){
				comboBox_Day = new JComboBox(MyDate.dateOf29());
			}else{
				comboBox_Day = new JComboBox(MyDate.dateOf28());
			}
		}
		
		this.setLayout(new BorderLayout());
		this.makeAll();
	}

	/**
	 * 生成一个日期选择面板
	 * @return	日期选择面板
	 */
	public JPanel makeDateSelect() {
		JPanel panel = new JPanel();
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line, "日志日期选择");
		panel.setBorder(border_LineTitle);
		//初始化日期选择,直接选择当天的日期
		Vector<Integer> currentDate = MyDate.currentDate();
		comboBox_Year.setSelectedItem(currentDate.get(0));
		comboBox_Month.setSelectedItem(currentDate.get(1));
		comboBox_Day.setSelectedItem(currentDate.get(2));
		
		LogsManager_ComboBox_Listener combox_Listener = new LogsManager_ComboBox_Listener(this);
		LogsManager_Button_Listener button_Listener = new LogsManager_Button_Listener(this, qq_Server_JFrame);
		JLabel label_Year = new JLabel("年");	
		JLabel label_Month = new JLabel("月");
		JLabel label_Date = new JLabel("日");
		JLabel label_KeyWords = new JLabel("关键字:");
		comboBox_Year.addItemListener(combox_Listener);
		comboBox_Month.addItemListener(combox_Listener);
		comboBox_Day.addItemListener(combox_Listener);
		button_Query.addActionListener(button_Listener);
		
		panel.add(comboBox_Year);
		panel.add(label_Year);
		panel.add(comboBox_Month);
		panel.add(label_Month);
		panel.add(comboBox_Day);
		panel.add(label_Date);
		panel.add(Box.createRigidArea(new Dimension(8,1)));
		panel.add(label_KeyWords);
		panel.add(textField_KeyWords);
		panel.add(button_Query);
		
		return panel;
	}
	
	/**
	 * 生成一个显示历史记录的面板
	 * @return	显示历史记录的面板
	 */
	public JPanel makeHistoryLogs(){
		JPanel panel = new JPanel(new BorderLayout());
		
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line, "历史日志");
		panel.setBorder(border_LineTitle);
		
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane);
		
		return panel;
	}
	
/*	public JPanel makeCurrentLogs(){
		JPanel panel = new JPanel(new BorderLayout());
		
		Border border_Line = BorderFactory.createLineBorder(Color.blue);
		Border border_LineTitle = BorderFactory.createTitledBorder(border_Line, "当前日志");
		panel.setBorder(border_LineTitle);
		JTextArea textArea = new JTextArea(28,40);
		panel.add(textArea);
		
		return panel;
	}*/
	
	/**
	 * 集成日期选择面板与历史记录面板
	 */
	public void makeAll(){
		//JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,this.makeDateSelect(),this.makeHistoryLogs());
		//JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,this.makeCurrentLogs(),splitPane1);
		
		
		add(makeDateSelect(),BorderLayout.NORTH);
		add(makeHistoryLogs(),BorderLayout.CENTER);
	}
	

	/**
	 * 获得年的下拉框
	 * @return	年的下拉框
	 */
	public JComboBox getComboBox_Year() {
		return comboBox_Year;
	}

	/**
	 * 获得月的下拉框
	 * @return	月的下拉框
	 */
	public JComboBox getComboBox_Month() {
		return comboBox_Month;
	}

	/**
	 * 获得日的下拉框
	 * @return	日的下拉框
	 */
	public JComboBox getComboBox_Day() {
		return comboBox_Day;
	}

	/**
	 * 获得显示历史记录的文件域
	 * @return	显示历史记录的文件域
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * 获得关键字输入框
	 * @return	关键字输入框
	 */
	public JTextField getTextField_KeyWords() {
		return textField_KeyWords;
	}
}

package listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;

import qq_server_jframe.LogsManager_JPanel;

import mytools.MyDate;

/**
 * 这是一个JComboBox选择事件的监听器，它实现了java.awt.event.ItemListener接口
 * @author Devon
 *
 */
public class LogsManager_ComboBox_Listener implements ItemListener{
	private LogsManager_JPanel logsManager_JPanel;

	public LogsManager_ComboBox_Listener(LogsManager_JPanel logsManager_JPanel) {
		this.logsManager_JPanel = logsManager_JPanel;
	}

	/**
	 * 定义了一些JComboBox的选项被选择时所做出的响应
	 */
	public void itemStateChanged(ItemEvent e) {
		if(logsManager_JPanel.getComboBox_Month() == e.getSource()){
			Integer intg_Year = (Integer)(logsManager_JPanel.getComboBox_Year().getSelectedItem());
			String str_Month = logsManager_JPanel.getComboBox_Month().getSelectedItem().toString();
			
			//大月---31
			if(str_Month.matches("1|3|5|7|8|(10)|(12)")){
				logsManager_JPanel.getComboBox_Day().setModel(new DefaultComboBoxModel(MyDate.dateOf31()));
			//小月---30
			}else if(str_Month.matches("4|6|9|11")){
				logsManager_JPanel.getComboBox_Day().setModel(new DefaultComboBoxModel(MyDate.dateOf30()));
			//判断是否闰年
			}else if(str_Month.equals("2")){
				if(MyDate.isLeapYear(intg_Year)){
					logsManager_JPanel.getComboBox_Day().setModel(new DefaultComboBoxModel(MyDate.dateOf29()));
				}else{
					logsManager_JPanel.getComboBox_Day().setModel(new DefaultComboBoxModel(MyDate.dateOf28()));
				}
			}
			
		}else if(logsManager_JPanel.getComboBox_Year() == e.getSource()){//点击年的时候只需判断是否为2月，只有2月才需做出处理
			Integer intg_Year = (Integer)(logsManager_JPanel.getComboBox_Year().getSelectedItem());
			String str_Month = logsManager_JPanel.getComboBox_Month().getSelectedItem().toString();
			if(str_Month.equals("2")){
				if(MyDate.isLeapYear(intg_Year)){
					logsManager_JPanel.getComboBox_Day().setModel(new DefaultComboBoxModel(MyDate.dateOf29()));
				}else{
					logsManager_JPanel.getComboBox_Day().setModel(new DefaultComboBoxModel(MyDate.dateOf28()));
				}
			}
		}
	}
}

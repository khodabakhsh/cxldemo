package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mytools.LogsReaderWriter;

import qq_server_jframe.LogsManager_JPanel;
import qq_server_jframe.QQ_Server_JFrame;

/**
 * 这是一个日志面板的按钮监听器类，它实现了 java.awt.event.ActionListener接口
 * @author Devon
 *
 */
public class LogsManager_Button_Listener implements ActionListener {
	private LogsManager_JPanel logsManager_JPanel;
	private QQ_Server_JFrame qq_Server_JFrame;

	public LogsManager_Button_Listener(LogsManager_JPanel logsManager_JPanel, QQ_Server_JFrame qq_Server_JFrame) {
		this.logsManager_JPanel = logsManager_JPanel;
		this.qq_Server_JFrame = qq_Server_JFrame;
	}

	/**
	 * 写义了一些按钮按下时做出的响应
	 */
	public void actionPerformed(ActionEvent e) {
		String str_Year = this.logsManager_JPanel.getComboBox_Year()
				.getSelectedItem().toString();
		String str_Month = this.logsManager_JPanel.getComboBox_Month()
				.getSelectedItem().toString();
		String str_Day = this.logsManager_JPanel.getComboBox_Day()
				.getSelectedItem().toString();
		if (str_Month.length() == 1) {
			str_Month = 0 + str_Month;
		}
		if (str_Day.length() == 1) {
			str_Day = 0 + str_Day;
		}

		String filePath = "log/" + str_Year + str_Month + str_Day + ".log";
		String keyWords = logsManager_JPanel.getTextField_KeyWords().getText();
		String result = LogsReaderWriter.readFromFile(filePath, keyWords);
		//不存在该天日志
		if(result == null){
			logsManager_JPanel.getTextArea().setText("指定日期的日志文件不存在!");
		//存在该天日志但不存在包含该关键字的相关记录
		}else if("".equals(result)){
			logsManager_JPanel.getTextArea().setText("指定日期的日志文件中不存在包含该关键字的相关记录!");
		//存在该天日志(且存在包含该关键字的相关记录)
		}else{
			logsManager_JPanel.getTextArea().setText(result);
		}

	}

}

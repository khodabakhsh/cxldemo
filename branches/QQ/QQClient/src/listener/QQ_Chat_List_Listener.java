package listener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import qq_client_jframe.QQ_Chat_JFrame;

/**
 * 这是一个Jlist选中事件的监听器类，它实现了javax.swing.event.ListSelectionEvent接口
 * @author Devon
 *
 */
public class QQ_Chat_List_Listener implements ListSelectionListener{
	
	/**
	 * 一个qq_Chat_JFrame的实例
	 */
	private QQ_Chat_JFrame qq_Chat_JFrame = null;

	/**
	 * 构造一个Jlist选中事件的监听器
	 * @param qq_Chat_JFrame	使监听器可以对qq_Chat_JFrame中的组件进行操作
	 */
	public QQ_Chat_List_Listener(QQ_Chat_JFrame qq_Chat_JFrame) {
		this.qq_Chat_JFrame = qq_Chat_JFrame;
	}

	/**
	 * 定义了列表选中时做的响应
	 */
	public void valueChanged(ListSelectionEvent e) {
		String selectUser = (String)qq_Chat_JFrame.getList_OnlineUsers().getSelectedValue();
		if(selectUser == null){
			qq_Chat_JFrame.getLable_To().setText("");
		}else{
			qq_Chat_JFrame.getLable_To().setText("To:" + selectUser);
		}
	}

}

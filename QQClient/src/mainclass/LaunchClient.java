package mainclass;

import javax.swing.UIManager;

import qq_client_jframe.QQ_Login_JFrame;
/**
 * 客户端的启动器
 * @author Devon
 *
 */
public class LaunchClient {
	public static void main(String[] args) {
		try
		{
		UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		}
		catch (Exception ex)
		{
		//ex.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable()
		{
		public void run()
		{
			new QQ_Login_JFrame().setVisible(true);
		}
		});
		
	}
}

package mainclass;


import javax.swing.UIManager;

import qq_server_jframe.Login_JFrame;
/**
 * 服务端的启动器
 * @author Devon
 *
 */
public class LaunchServer {

	public static void main(String[] args) {
		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (ClassNotFoundException e) {
		 * 
		 * e.printStackTrace(); } catch (InstantiationException e) {
		 * 
		 * e.printStackTrace(); } catch (IllegalAccessException e) {
		 * 
		 * e.printStackTrace(); } catch (UnsupportedLookAndFeelException e) {
		 * 
		 * e.printStackTrace(); }
		 */
		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Login_JFrame().setVisible(true);
			}
		});

		;

	}
}

package ext;

import javax.swing.JTextArea;

/**
 * 继承自javax.swing.JTextArea，区别于直接父类，当字符串被追加到末尾时
 * 输入光标将自动被更新到末尾
 * @author Devon
 *
 */
public class JTextAreaExt extends JTextArea{
	
	/**
	 * 创建 一个新的JTextAreaExt对象
	 */
	public JTextAreaExt() {
		
	}
	
	/**
	 * 创建 一个新的JTextAreaExt对象，它具有指定的行数与列数
	 * @param row	指定的行数
	 * @param column	指定的列数
	 */
	public JTextAreaExt(int row, int column){
		super(row, column);
	}

	@Override
	/**
	 * 重写父类中的方法，当字符串被追加到末尾时 输入光标将自动被更新到末尾
	 * @param str	要追加的字符串
	 */
	public void append(String str) {
		super.append(str);
		setCaretPosition(getText().length());
	}
	
}

package org.asblog.frameworks.controller.commandtype 
{

	/**
	 * @author Halley
	 */
	final public class DesignCanvasCT
	{
		/**
		 * 添加一个元素到舞台
		 */
		public static const CMD_ADD_MEDIAOBJECT : String = "cmdAddMediaObject";
		/**
		 * 改变背景 
		 */
		public static const CMD_CHANGE_BACKGROUND : String = "cmdChangeBackGround";
		/**
		 * 矩阵值变化了
		 */
		public static const CMD_MATRIX_CHANGE : String = "cmdMatrixChange";
		/**
		 * 选择了桌面或者其他元素
		 */
		public static const CMD_SETSELECTION : String = "cmdSetSelection";
		/**
		 * 为某元件添加遮罩
		 */
		public static const CMD_ADD_MASK : String = "cmdAddMask";
		/**
		 * 画布大小改变
		 */
		public static const CMD_CHANGE_CANVASWH : String = "cmdChangCanvaswh";
	
	}
}

package org.asblog.frameworks.controller.commandtype
{
	final public class LayerManagerCT
	{
		/**
		 * 从舞台删除一个元素
		 */
		public static const CMD_REMOVE_MEDIAOBJECT : String = "cmdRemoveMediaObject";
		/**
		 * 从舞台删除All元素
		 */
		public static const CMD_REMOVEALL_MEDIAOBJECT : String = "cmdRemoveAllMediaObject";
		/**
		 * 把选中元素深度向上 
		 */		
		public static const CMD_SWITCH_UP : String = "cmdSwitchUp";
		/**
		 * 把选中元素深度向下
		 */		
		public static const CMD_SWITCH_DOWN : String = "cmdSwitchDown";
		
	}
}
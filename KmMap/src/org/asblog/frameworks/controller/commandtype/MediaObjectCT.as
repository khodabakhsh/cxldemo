package org.asblog.frameworks.controller.commandtype
{
	final public class MediaObjectCT
	{
		/**
		 * 是否开启色彩式样(亮度,对比度,饱和度,色相,等)
		 */
		public static const CMD_COLORSTYLE_ENABLE : String = "cmdColorStyleEnable";
		/**
		 * 亮度更改
		 */ 
		public static const CMD_COLORSTYLE_BRIGHT : String = "cmdColorStyleBright";
		/**
		 * 对比度更改
		 */ 
		public static const CMD_COLORSTYLE_SATURA : String = "cmdColorStyleSatura";
		/**
		 * 透明度变化 
		 */
		public static const CMD_ALPHA_CHANGE:String = "cmdAlphaChange";
		/**
		 * 混合模式改变了 
		 */
		public static const CMD_SET_BLEND:String = "cmdSetBlend";
		
	}
}
package org.asblog.contextmenu
{
	public final class ContextMenuLabels
	{
		public static const UP:String               = "向上一层";
		public static const DOWN:String             = "向下一层";
		
		public static const COPY:String	            = "复制";
		public static const PASTE:String            = "粘贴";
		public static const CUT:String              = "剪切";
		public static const DELETE:String           = "删除";
		
		public static const LOCK:String             = "锁定";
		public static const UNLOCK:String           = "解锁";
		
		public static const GROUP:String            = "组合";
		public static const UNGROUP:String          = "拆散";
		
		public static const UNDO:String             = "撤消";
		public static const REDO:String             = "重复";
		
		public static const CONTROL_MASK:String     = "操作遮罩";
		public static const CONTROL_IMAGE:String    = "操作原图";
		public static const CANCEL_MASK:String      = "取消遮罩";
		
		public static const BASIC_LABELS:Vector.<String> = Vector.<String>([COPY,PASTE,CUT,DELETE]);
		public static const LAYER_LABELS:Vector.<String> = Vector.<String>([UP,DOWN]);
		public static const LOCK_LABELS:Vector.<String>  = Vector.<String>([LOCK,UNLOCK]);
		public static const UNDO_LABELS:Vector.<String>  = Vector.<String>([UNDO,REDO]);
		public static const MASK_LABELS:Vector.<String>  = Vector.<String>([CONTROL_MASK,CONTROL_IMAGE,CANCEL_MASK]);
	}
}
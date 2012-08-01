/**
 * 右键菜单扩展类
 * @usage
 * 		var cm:ContextMenuPlus = new ContextMenuPlus();
 * 		target.contextMenu = cm.contextMenu;
 * @author	eidiot
 * @author	http://eidiot.net
 */
package org.asblog.contextmenu
{
	import flash.events.ContextMenuEvent;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuBuiltInItems;
	import flash.ui.ContextMenuItem;
	public class ContextMenuPlus
	{
		//##########################################################################
		//
		//	Constants
		//
		//##########################################################################
		public static const FORWARD_AND_BACK:String = "forwardAndBack";
		public static const LOOP:String = "loop";
		public static const PLAY:String = "play";
		public static const PRINT:String = "print";
		public static const QUALITY:String = "quality";
		public static const REWIND:String = "rewind";
		public static const SAVE:String = "save";
		public static const ZOOM:String = "zoom";
		//##########################################################################
		//
		//	Variables
		//
		//##########################################################################
		private var _contextMenu:ContextMenu;
		//##########################################################################
		//
		//	Constructor
		//
		//##########################################################################
		/**
		 * @param removeAll			是否禁用所有默认菜单
		 * @param listener			自定义菜单事件句柄
		 * @param ...customItems	自定义标签，可以是一个数组
		 */
		public function ContextMenuPlus(removeAll:Boolean = true, listener:Function = null, customItems:Vector.<String> = null)
		{
			_contextMenu = new ContextMenu();
			if(removeAll)
			{
				removeDefault();
			}
			if(customItems!=null && customItems.length > 0 && listener != null)
			{
				addCustomItems(listener, customItems);
			}
		}
		//##########################################################################
		//
		//	Property
		//
		//##########################################################################
		public function get contextMenu():ContextMenu
		{
			return _contextMenu;
		}
		public function get builtInItems():ContextMenuBuiltInItems
		{
			return _contextMenu.builtInItems;
		}
		//##########################################################################
		//
		//	Methods
		//
		//##########################################################################
		/**
		 * 禁用默认菜单
		 * @param ...leave	保留默认菜单(标签数组)
		 */
		public function removeDefault(...leave):void
		{
			_contextMenu.hideBuiltInItems();
			if(leave.length == 0)
			{
				return;
			}
			var defaultItems:ContextMenuBuiltInItems = _contextMenu.builtInItems;
			for each(var item:String in leave)
			{
				defaultItems[item] = true;
			}
		}
		/**
		 * 添加自定义菜单项
		 * @param caption			菜单项标题
		 * @param listener			事件句柄
		 * @param separatorBefore	是否在菜单上方添加分割线
		 * @param enabled			是否可用
		 * @param visible			是否可见
		 */
		public function addCustom(listener:Function, caption:String, separatorBefore:Boolean = false, enabled:Boolean = true, visible:Boolean = true):void
		{
			var item:ContextMenuItem = new ContextMenuItem(caption, separatorBefore, enabled, visible);
			_contextMenu.customItems.push(item);
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, listener);
		}
		/**
		 * 添加自定义菜单组，所有菜单项共用同一个事件句柄
		 * 通过 event.currentTarget.caption 区分不同的菜单项
		 * private function onSelect(event:ContextMenuEvent):void{
		 * 		var item:ContextMenuItem = event.currentTarget;
		 * 		switch(item.caption)...}
		 * @param listener			事件句柄
		 * @param separatorBefore	是否在菜单组上方添加分隔线
		 * @param customItems	菜单项标题，是一个Vector.<String>
		 */
		public function addGroup(listener:Function, separatorBefore:Boolean = false, customItems:Vector.<String> = null):void
		{
			addCustomItems(listener, customItems, separatorBefore);
		}
		//##########################################################################
		//
		//	Functions
		//
		//##########################################################################
		private function addCustomItems(listener:Function, customItems:Vector.<String>, separatorBefore:Boolean = false):void
		{
			for each(var caption:String in customItems)
			{
				addCustom(listener, caption, separatorBefore);
				if(separatorBefore)
				{
					separatorBefore = false;
				}
			}
		}
	}
}
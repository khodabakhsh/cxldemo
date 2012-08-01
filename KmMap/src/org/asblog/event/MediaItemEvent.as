package org.asblog.event
{
	import org.asblog.core.MediaLink;
	
	import flash.events.Event;

	/**
	 * 所有媒体元素的事件基类
	 * @author Administrator
	 * 
	 */
	public class MediaItemEvent extends Event
	{
		public static const CHANGE : String = "onItemChange";
		public static const BEFORE_SELECT : String = "beforeSelect";
		public static const SELECTED : String = "onSelected";		public static const BEFORE_ITEM_DOWN : String = "beforeItemDown";
		/**
		 * 有遮罩拖到舞台某元件上
		 */ 
		public static const MASKITEM_DROP : String = "maskItemDrop";
		
		/**
		 * 是否阻止被选择
		 */		
		public var preventSelection : Boolean;
		/**
		 * uid的
		 */		public var link : MediaLink;

		public function MediaItemEvent(type : String, link:MediaLink)
		{
			super( type, false, false );
			this.link = link;
		}
	}
}
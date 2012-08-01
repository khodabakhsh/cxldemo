package org.asblog.event 
{
	import org.asblog.core.MediaLink;
	
	import flash.events.Event;

	/**
	 * 媒体集合事件
	 * @author Administrator
	 * 
	 */
	public class MediaContainerEvent extends Event 
	{

		public static const ADD_CHILD : String = "onAddChild";
		public static const MULYIP_SELECTION : String = "mulyipSelection";
		public static const REMVOE_CHILD : String = "onRemoveChild";
		/**
		 * 当前被选中对象的link
		 */		
		public var link : MediaLink;

		public function MediaContainerEvent(type : String,link : MediaLink) 
		{
			super( type, false, false );
			this.link = link;
		}
	}
}
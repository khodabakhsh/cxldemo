package org.asblog.core
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	
	import mx.core.UIComponent;	

	/**
	 * 调整遮罩时触发
	 */			
	[Event(name="onMasking", type="org.asblog.event.MediaMaskEvent")]
	
	/**
	 * 所有遮罩的基类 
	 * @author Halley
	 * 
	 */		
	public class MediaMask extends UIComponent implements IRelatedObject
	{
		private var _masking:Boolean;
		private var _relatedObject:DisplayObject;
		/**
		 * 对被遮罩对象的引用
		 */		
		public var maskedObject:IMediaObject;
		public function MediaMask()
		{
			super();
		}
		public function get relatedObject():DisplayObject
		{
			return _relatedObject;
		}
		public function set relatedObject(v:DisplayObject):void
		{
			_relatedObject = v;
		}
		public function get masking():Boolean
		{
			return _masking;
		}
        public function set masking(v:Boolean):void
        {
        	_masking = v;
        }
	}
}
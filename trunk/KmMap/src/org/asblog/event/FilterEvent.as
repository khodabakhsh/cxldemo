package org.asblog.event
{
	import flash.events.Event;
	import flash.filters.BitmapFilter;

	public class FilterEvent extends Event
	{
		public static const CHANGE:String = "onFilterChange";
		public var filter : BitmapFilter;
		public function FilterEvent(type:String,filter:BitmapFilter, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			this.filter = filter;
		}
		
	}
}
package org.asblog.event 
{
	import flash.events.Event;

	/**
	 * @author Halley
	 */
	public class CommonEvent extends Event 
	{
		public var item : *;
		
		public function CommonEvent(type : String,item:*=null)
		{
			super( type, false, false );
			this.item = item;
		}
	}
}

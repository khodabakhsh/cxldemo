package events
{
	import flash.events.Event;
	
	public class DrawEvent extends Event
	{
		public static var ReDraw: String = "draw";
		
		public var pObj: Object;
		
		public function DrawEvent(type: String)
		{
			super(type);
		}

	}
}
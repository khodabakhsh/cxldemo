package events
{
	import flash.events.Event;
	
	public class BoxEvent extends Event
	{
		public static var MOUSE_DOWN: String = "box_mouse_down";
		public static var MOUSE_UP: String = "box_mouse_up";
		public static var MOUSE_MOVE: String = "box_mouse_move";
		
		public var pObj: Object;
		
		public function BoxEvent(type: String)
		{
			super(type);
		}

	}
}
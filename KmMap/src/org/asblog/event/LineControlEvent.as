package org.asblog.event
{
	import flash.events.Event;
	import flash.geom.Point;
	

	public class LineControlEvent extends Event
	{
		public static const Select : String = "Select";
		private var _point :Point ;

		public function LineControlEvent(type : String,point:Point)
		{
			super( type);
			this.point= point;
		}

		public function get point():Point
		{
			return _point;
		}

		public function set point(value:Point):void
		{
			_point = value;
		}

	}
}
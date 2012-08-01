package org.asblog.core
{
	public class ShapeLink extends MediaLink
	{
		
		public var width:Number = 80;
		public var height:Number = 80;
		public var shapType:String;
		public var color:uint
		public var bgColor : uint = 0xFFCC00;
		public var borderColor : uint = 0x666666;
		public var borderSize : uint = 0;
		public var cornerRadius : uint = 9;
		public var gutter : uint = 5;
		public function ShapeLink(shapType:String = null):void
		{
			this.shapType = shapType;
		}
		override public function clone() : * 
		{
			var link : ShapeLink = ShapeLink( super.clone( ) );
			link.width = width;
			link.height = height;
			link.shapType = shapType;
			link.color = color;
			link.bgColor = bgColor;
			link.borderColor = borderColor;
			link.borderSize = borderSize;
			link.cornerRadius = cornerRadius;
			link.gutter = gutter;
			return link;
		}
	}
}
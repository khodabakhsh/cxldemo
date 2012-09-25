package org.asblog.mediaItem 
{
	import flash.display.*;
	import flash.display.Sprite;
	import flash.geom.*;
	
	import flashx.textLayout.elements.BreakElement;
	
	import org.asblog.core.IRelatedObject;
	import org.asblog.core.MediaObject;
	import org.asblog.core.ShapeLink;
	
	import spark.primitives.Graphic;

	public class MediaShape extends MediaObject implements IRelatedObject 
	{
		public static const Circle : String = "circle";
		public static const Rect : String = "rect";
		public static const RoundRect : String = "roundRect";
		public static const DoubleArrow:String="doubleArrow";
		public static const Arrow:String="arrow";
		public static const Hexagon:String= "hexagon";
//		public static const Line:String= "line";
		
		private var shap : Sprite = new Sprite( );
		private var _shapeLink:ShapeLink;
		
		
		
		public function MediaShape(link:ShapeLink = null) 
		{
			super( );
			//name = "图形"
			if(!link)	_shapeLink = new ShapeLink();
			else		_shapeLink = link;
			
			mediaLink = _shapeLink;
			width = measuredWidth;
			height = measuredHeight;
			addChild( shap );
			relatedObject = shap;
		}
		public function get shapLink():ShapeLink
		{
			return _shapeLink;
		}
		public function set shapLink(v:ShapeLink):void
		{
			_shapeLink = v;
		}
		public function draw(shapType:String = null) : void 
		{
			shapLink = ShapeLink( mediaLink );
			if(shapType)	shapLink.shapType = shapType;
			switch(shapLink.shapType) 
			{
				case Circle:
					doDrawCircle(  );
					break;
				case RoundRect:
					doDrawRoundRect(  );
					break;
				case Rect:
					doDrawRect(  );
					break;
				case DoubleArrow:
					doDrawDoubleArrow();
					break;
				case Arrow:
					doDrawArrow();
					break;
				case Hexagon:
					doDrawHexagon();
					break;
//				case Line:
//				doDrawLine();
//				break;
			}
		}
		public function setGradient(graphics:Graphics):void{
		
			graphics.beginGradientFill(GradientType.LINEAR, [shapLink.bgColor,transformColor(shapLink.bgColor,1.6, 1.6,1.6)], [0.8,0.8], [0,255],null,flash.display.SpreadMethod.REFLECT);
		}
		public function doDrawCircle() : void 
		{
			var halfSize : uint = Math.round( shapLink.width / 2 );
			shap.graphics.clear( );
//			shap.graphics.beginFill( shapLink.bgColor ,shapLink.alpha);
			setGradient(shap.graphics);
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			shap.graphics.drawEllipse( 0,0, shapLink.width, shapLink.height );
			shap.graphics.endFill( );
		}

		public function doDrawRoundRect() : void 
		{
			shap.graphics.clear( );
//			shap.graphics.beginFill( shapLink.bgColor ,shapLink.alpha);
			setGradient(shap.graphics);
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			shap.graphics.drawRoundRect( 0, 0, shapLink.width, shapLink.height, shapLink.width/3, shapLink.height/3 );
			shap.graphics.endFill( );
		}

		public function doDrawRect() : void 
		{
			shap.graphics.clear( );
//			shap.graphics.beginFill( shapLink.bgColor ,shapLink.alpha);
			setGradient(shap.graphics);
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			shap.graphics.drawRect( 0, 0, shapLink.width, shapLink.height );
			shap.graphics.endFill( );
		}
		public function doDrawDoubleArrow() : void 
		{
			shap.graphics.clear( );
//			shap.graphics.beginFill( shapLink.bgColor ,shapLink.alpha);
			setGradient(shap.graphics);
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			shap.graphics.moveTo(0, shapLink.height / 2);
			shap.graphics.lineTo(shapLink.width / 3, 0);
			shap.graphics.lineTo(shapLink.width / 3, shapLink.height / 3);
			shap.graphics.lineTo(shapLink.width * 2 / 3, shapLink.height / 3);
			shap.graphics.lineTo(shapLink.width * 2 / 3, 0);
			shap.graphics.lineTo(shapLink.width, shapLink.height / 2);
			shap.graphics.lineTo(shapLink.width * 2 / 3, shapLink.height);
			shap.graphics.lineTo(shapLink.width * 2 / 3, shapLink.height * 2 / 3);
			shap.graphics.lineTo(shapLink.width / 3, shapLink.height * 2 / 3);
			shap.graphics.lineTo(shapLink.width / 3, shapLink.height);
			shap.graphics.lineTo(0, shapLink.height / 2);
			shap.graphics.endFill( );
		}
		public function doDrawArrow() : void 
		{
			shap.graphics.clear( );
//			shap.graphics.beginFill( shapLink.bgColor ,shapLink.alpha);
			setGradient(shap.graphics);
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			shap.graphics.moveTo(0, shapLink.height / 4);
			shap.graphics.lineTo(shapLink.width / 2, shapLink.height / 4);
			shap.graphics.lineTo(shapLink.width / 2, 0);
			shap.graphics.lineTo(shapLink.width, shapLink.height / 2);
			shap.graphics.lineTo(shapLink.width / 2, shapLink.height);
			shap.graphics.lineTo(shapLink.width / 2, shapLink.height * 3 / 4);
			shap.graphics.lineTo(0, shapLink.height * 3 / 4);
			shap.graphics.lineTo(0, shapLink.height / 4);
			shap.graphics.endFill();
		}
		public function doDrawHexagon() : void 
		{
			shap.graphics.clear( );
//			shap.graphics.beginFill( shapLink.bgColor ,shapLink.alpha);
			setGradient(shap.graphics);
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			var loc4:*=shapLink.height / 2 * Math.sqrt(3) / 3.5;
			shap.graphics.moveTo(0, shapLink.height / 2);
			shap.graphics.lineTo(loc4, 0);
			shap.graphics.lineTo(shapLink.width - loc4, 0);
			shap.graphics.lineTo(shapLink.width, shapLink.height / 2);
			shap.graphics.lineTo(shapLink.width - loc4, shapLink.height);
			shap.graphics.lineTo(loc4, shapLink.height);
			shap.graphics.lineTo(0, shapLink.height / 2);
			shap.graphics.endFill();
		}
//		public function doDrawLine() : void 
//		{
//			shap.graphics.clear( );
////			shap.graphics.beginFill( shapLink.bgColor ,shapLink.alpha);
//			shap.graphics.lineStyle( ShapeLink.LineBorderSize, shapLink.borderColor );
//			shap.graphics.moveTo(0, 0);
//			shap.graphics.lineTo(shapLink.width, shapLink.height );
//			shap.graphics.endFill();
//		}
		
		override public function clone() : * 
		{
			var s : MediaShape = MediaShape( super.clone( ) );
			s.draw( );
			return s;
		}
		public static function transformColor(arg1:uint, arg2:Number, arg3:Number, arg4:Number):uint
		{
			var loc1:*=0;
			var loc2:*=arg1 >> 16;
			var loc3:*=arg1 >> 8 & 255;
			var loc4:*=arg1 & 255;
			loc2 = formatColorChannel(loc2 * arg2);
			loc3 = formatColorChannel(loc3 * arg3);
			loc4 = formatColorChannel(loc4 * arg4);
			return loc1 = loc2 << 16 | loc3 << 8 | loc4;
		}
		
		
		internal static function formatColorChannel(arg1:uint):uint
		{
			if (arg1 > 255) 
			{
				arg1 = 255;
			}
			else if (arg1 < 0) 
			{
				arg1 = 0;
			}
			return arg1;
		}
	}
}
package org.asblog.mediaItem 
{
	import org.asblog.core.IRelatedObject;
	import org.asblog.core.MediaObject;
	import org.asblog.core.ShapeLink;
	
	import flash.display.Sprite;

	public class MediaShape extends MediaObject implements IRelatedObject 
	{
		public static const Circle : String = "circle";
		public static const Rect : String = "rect";
		public static const RoundRect : String = "roundRect";
		
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
		private function get shapLink():ShapeLink
		{
			return _shapeLink;
		}
		private function set shapLink(v:ShapeLink):void
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
			}
		}
		public function doDrawCircle() : void 
		{
			var halfSize : uint = Math.round( shapLink.width / 2 );
			shap.graphics.clear( );
			shap.graphics.beginFill( shapLink.bgColor );
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			shap.graphics.drawCircle( halfSize, halfSize, halfSize );
			shap.graphics.endFill( );
		}

		public function doDrawRoundRect() : void 
		{
			shap.graphics.clear( );
			shap.graphics.beginFill( shapLink.bgColor );
			shap.graphics.lineStyle( shapLink.borderSize, shapLink.borderColor );
			shap.graphics.drawRoundRect( 0, 0, shapLink.width, shapLink.height, shapLink.width, shapLink.height );
			shap.graphics.endFill( );
		}

		public function doDrawRect() : void 
		{
			shap.graphics.clear( );
			shap.graphics.beginFill( shapLink.bgColor );
			//shap.graphics.lineStyle(borderSize, borderColor);
			shap.graphics.drawRect( 0, 0, shapLink.width, shapLink.height );
			shap.graphics.endFill( );
		}

		override public function clone() : * 
		{
			var s : MediaShape = MediaShape( super.clone( ) );
			s.draw( );
			return s;
		}
	}
}
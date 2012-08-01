package org.asblog.graphics  {
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.IBitmapDrawable;
	import flash.geom.ColorTransform;
	import flash.geom.Matrix;
	import flash.geom.Rectangle;
	import flash.utils.ByteArray;	

	/**
	 * @author Xucan
	 */
	public  class ImageSnapshot 
	{
		public static const MAX_BITMAP_DIMENSION:int = 2880;
		public static var transparent:Boolean = true;

		public function ImageSnapshot(width:int = 0, height:int = 0,
                                  data:ByteArray = null,
                                  contentType:String = null)
		{
			super( );

			this.contentType = contentType;
			this.width = width;
			this.height = height;
			this.data = data;
		}

		private var _contentType:String;

		public function get contentType():String {
			return _contentType;
		}

		
		public function set contentType(value:String):void {
			_contentType = value;
		}

		
		private var _data:ByteArray;    

		
		public function get data():ByteArray {
			return _data;
		}

		public function set data(value:ByteArray):void {
			_data = value;
		}

		private var _height:int;

		
		/**
		 *  The image height in pixels.
		 */
		public function get height():int {
			return _height;
		}

		public function set height(value:int):void {
			_height = value;
		}

		private var _properties:Object = {};

		public function get properties():Object {
			return _properties;
		}

		/**
		 *  @private
		 */
		public function set properties(value:Object):void {
			_properties = value;
		}

		
		private var _width:int;

		
		/**
		 * The image width in pixels.
		 */
		public function get width():int {
			return _width;
		}

		/**
		 *  @private
		 */
		public function set width(value:int):void {
			_width = value;
		}

		public static function captureBitmapData(
                                source:IBitmapDrawable, matrix:Matrix = null,
                                colorTransform:ColorTransform = null,
                                blendMode:String = null,
                                clipRect:Rectangle = null,
                                smoothing:Boolean = false):BitmapData
		{
			var data:BitmapData;
			var width:int;
			var height:int;

			if (source != null)
			{
				if (source is DisplayObject)
				{
					width = DisplayObject( source ).width;
					height = DisplayObject( source ).height;
				}
                else if (source is BitmapData)
				{
					width = BitmapData( source ).width;
					height = BitmapData( source ).height;
				}
			}

			// We default to an identity matrix
			// which will match screen resolution
			if (!matrix)
                matrix = new Matrix( 1, 0, 0, 1 );

			var scaledWidth:Number = width * matrix.a;
			var scaledHeight:Number = height * matrix.d;
			var reductionScale:Number = 1;

			// Cap width to BitmapData max of 2880 pixels
			if (scaledWidth > MAX_BITMAP_DIMENSION)
			{
				reductionScale = scaledWidth / MAX_BITMAP_DIMENSION;
				scaledWidth = MAX_BITMAP_DIMENSION;
				scaledHeight = scaledHeight / reductionScale;
    
				matrix.a = scaledWidth / width;
				matrix.d = scaledHeight / height;
			}

			// Cap height to BitmapData max of 2880 pixels
			if (scaledHeight > MAX_BITMAP_DIMENSION)
			{
				reductionScale = scaledHeight / MAX_BITMAP_DIMENSION;
				scaledHeight = MAX_BITMAP_DIMENSION;
				scaledWidth = scaledWidth / reductionScale;
    
				matrix.a = scaledWidth / width;
				matrix.d = scaledHeight / height;
			}

			// the fill should be transparent: 0xARGB -> 0x00000000
			// only explicitly drawn pixels will show up
			data = new BitmapData( scaledWidth, scaledHeight, transparent, 0x00000000 );
			data.draw( source, matrix, colorTransform, blendMode, clipRect, smoothing );

			return data;
		}
	}
}

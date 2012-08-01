package org.asblog.mediaItem 
{
	import flash.system.LoaderContext;	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.events.Event;

	import mx.controls.Image;

	import org.asblog.core.IMediaObject;
	import org.asblog.core.IRelatedObject;
	import org.asblog.core.MediaObject;	

	/**
	 *
	 * @author Administrator
	 * 
	 */	
	public class MediaImage extends MediaObject implements IMediaObject,IRelatedObject
	{	
		private var _isSwf : Boolean;
		private var _image : Image;
		private var _smallSource : Object;
		private var _source : Object;

		
		private var _smallHeight : int = 80;
		private var _smallWidth : int = 80;
		private var _hasSmall : Boolean = false;

		//private var _isSmall : Boolean = false;

		public function MediaImage()
		{			
			super( );
			//name = "图片"
			_image = new Image( );
			relatedObject = _image;
			addChild( _image );
		}
		override public function get width( ) : Number
		{
			return _image.height;
		}
		
		override public function get height( ) : Number
		{
			return _image.height;
		}
		
		override public function set width(value : Number) : void
		{
			_image.width = value;
		}
		
		override public function set height(value : Number) : void
		{
			_image.height = value;
		}
		override public function get name() : String
		{
			return super.name;
		}

		override public function set name(v : String) : void
		{
			super.name = v;
		}

		/**
		 * 缩放图的高
		 * @return 
		 */		
		public function get smallHeight() : int
		{
			return _smallHeight;
		}

		public function set smallHeight(v : int) : void
		{
			_smallHeight = v;
		}

		/**
		 * 缩放图的宽
		 * @return 
		 */	
		public function get smallWidth() : int
		{
			return _smallWidth;
		}

		public function set smallWidth(v : int) : void
		{
			_smallWidth = v;
		}

		public function get hasSmall() : Boolean
		{
			return _hasSmall;
		}

		public function set hasSmall(v : Boolean) : void
		{
			_hasSmall = v;
		}

		public function get smallSource() : Object
		{
			return _smallSource;
		}

		public function set smallSource(v : Object) : void
		{
			if(v is String)	_isSwf = String( v ).lastIndexOf( ".swf" ) != -1 ? true : false;
			load( v );
		}

		public function get source() : Object
		{
			return _source;
		}

		public function set source(v : Object) : void
		{
			if(v is String)	_isSwf = String( v ).lastIndexOf( ".swf" ) != -1 ? true : false;
			_source = v;
			if(!hasSmall)	load( v );
		}

		public function load(v : Object) : void
		{
			_image.removeEventListener( Event.COMPLETE, onComplete );
			_image.loaderContext = new LoaderContext( false );
			_image.source = v;
			_image.addEventListener( Event.COMPLETE, onComplete );
		}

		public function changeToBig() : void
		{
			hasSmall = false;
			load( _source );
		}

		public function changeToSmall() : void
		{
			hasSmall = true;
			load( _smallSource );
		}

		/*
		private function maskFrame() : void
		{
		_image.graphics.clear( );
		_image.graphics.beginFill( 0xFFFFFF );
		//shap.graphics.lineStyle(borderSize, borderColor);
		_image.graphics.drawRect( -5, -5, _image.contentWidth + 10, _image.height + 10 );
		_image.graphics.endFill( );
		var myFilters : Array = [];
		var color : Number = 0x000000;
		var angle : Number = 45;
		var alpha : Number = 1;
		var blurX : Number = 4;
		var blurY : Number = 4;
		var distance : Number = 3;
		var strength : Number = 0.5;
		var inner : Boolean = false;
		var knockout : Boolean = false;
		var quality : Number = BitmapFilterQuality.LOW;
		myFilters.push( new DropShadowFilter( distance, angle, color, alpha, blurX, blurY, strength, quality, inner, knockout ) );
		this.filters = myFilters;
		}
		 */
		private function onComplete(eve : Event) : void
		{
			_image.width = _image.contentWidth;
			_image.height = _image.contentHeight;

			if(!_isSwf)
			{
				var myDataBitmap : BitmapData;
				myDataBitmap = new BitmapData( _image.width, _image.height, true, 0x00ffffff );				
				//使图片缩放平滑			
				myDataBitmap.draw( _image );
				var myBitmap : Bitmap = new Bitmap( myDataBitmap, "auto", true );
				_image.source = myBitmap;
				relatedObject = _image;
			}
			if(hasSmall)
			{
				_image.width = smallWidth;
				_image.height = smallHeight;
			}
			if(mediaLink.isBackground)
			{
				_image.maintainAspectRatio = false;
			}
			_image.removeEventListener( Event.COMPLETE, onComplete );
			dispatchEvent( eve.clone( ) );
		}
		override public function dispose() : void
		{
			super.dispose();
			_image.unloadAndStop();
		}
		override public function clone() : *
		{
			var cloneImage : MediaImage = MediaImage( super.clone( ) );
			cloneImage.source = _source;
			return cloneImage;
		}
	}
}
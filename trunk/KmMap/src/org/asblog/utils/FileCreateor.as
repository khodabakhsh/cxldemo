package org.asblog.utils 
{
	
	import flash.display.IBitmapDrawable;
	import flash.net.FileReference;
	import flash.utils.ByteArray;
	
	import org.asblog.graphics.ImageSnapshot;
	import org.asblog.graphics.JPEGEncoder;

	/**
	 * @author Halley
	 */
	public class FileCreateor 
	{
		private static var fileReference : FileReference = new FileReference( );
		private static const jpegEnc : JPEGEncoder = new JPEGEncoder( 95 );  

		public static function createImage(image : IBitmapDrawable) : void
		{
			fileReference.save( getImageBytes(image), "未命名.jpg" );
		}
		public static function getImageBytes(image:IBitmapDrawable):ByteArray
		{
			return jpegEnc.encode(ImageSnapshot.captureBitmapData( image ));
		}
	}
}

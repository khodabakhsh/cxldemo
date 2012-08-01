package org.asblog.mediaItem
{
	import org.asblog.core.IRelatedObject;
	import org.asblog.core.IMediaObject;
	import org.asblog.core.MediaObject;

	import mx.controls.Button;

	public class MediaButton extends MediaObject implements IMediaObject,IRelatedObject
	{

		public function MediaButton()
		{
			super( );
			
			var button : Button = new Button( );
			this.measuredWidth = button.width = 40;
			this.measuredHeight = button.height = 20;
			
			relatedObject = button;
			addChild( button );
		}
	}
}
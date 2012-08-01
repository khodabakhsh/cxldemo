package org.asblog.mediaItem
{
	import flash.events.MouseEvent;
	
	import flashx.textLayout.conversion.TextConverter;
	
	import org.asblog.core.MediaObject;
	
	import spark.components.RichText;
	import spark.components.SkinnableContainer;
	
	public class MediaText extends MediaObject
	{
		private var textField:RichText;
		public function MediaText()
		{
			super( );
			//name = "文字"
			var container:SkinnableContainer = new SkinnableContainer();
			textField = new RichText();
			//toolTip = "双击编辑文字"
			container.addElement( textField );
			relatedObject = textField;
			addChild( container );
		}

		public function get text():String
		{
			return textField.text;
		}

		public function set text(value:String):void
		{
			textField.textFlow = TextConverter.importToFlow(value, TextConverter.TEXT_FIELD_HTML_FORMAT);
		}
		
	}
}
package org.asblog.mediaItem
{
	import flash.events.MouseEvent;
	import flash.text.TextField;
	
	import flashx.textLayout.conversion.TextConverter;
	
	import org.asblog.core.DesignCanvas;
	import org.asblog.core.MediaObject;
	import org.asblog.core.TextLink;
	import org.asblog.utils.PopUpUtils;
	
	import spark.components.PopUpAnchor;
	import spark.components.RichText;
	import spark.components.SkinnableContainer;
	
	import ui.popUpWindow.PopUpRichTextWindow;
	
	
	
	public class MediaText extends MediaObject
	{
		public static var currentMediaText:MediaText;
		
		
		internal var _wordWrap:Boolean=false;
		
		internal var _isBold:Boolean=false;
		
		
		internal var _font:String="宋体";
		
		internal var _align:String="left";
		
		internal var _italic:Boolean=false;
		
		internal var _underline:Boolean=false;
		
		public var textLink:TextLink ;
		
//		private var textField:RichText;
		private var textField:TextField = new TextField();
		public function MediaText()
		{
			super( );
//			var container:SkinnableContainer = new SkinnableContainer();
//			textField = new RichText();
			textField.mouseEnabled = true;
			textField.selectable = true;
			textField.border = false;
			textField.wordWrap =true;
			textField.type = flash.text.TextFieldType.INPUT;
			textField.antiAliasType = flash.text.AntiAliasType.ADVANCED;
			textField.gridFitType = flash.text.GridFitType.PIXEL;
			textField.width = 100;
			textField.height=300;
			textField.autoSize = flash.text.TextFieldAutoSize.LEFT;
			textField.multiline = true;
			if(!DesignCanvas.viewOnly){
			toolTip = "双击编辑文字";
			}else{
				toolTip = "";
			}
			//			container.addElement( textField );
			relatedObject = textField;
			addChild( textField );
		}

		public function render():void{
			
			textField.setTextFormat( new flash.text.TextFormat(_font, textLink.fontSize, textLink.fontColor));
		}

		public function get text():String
		{
			return textField.text;
		}

		public function set text(value:String):void
		{
//			textField.textFlow = TextConverter.importToFlow(value, TextConverter.TEXT_FIELD_HTML_FORMAT);
			textField.text=value;
		}
		
	}
}
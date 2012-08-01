package org.asblog.frameworks.view
{
	import org.asblog.core.History;
	import org.asblog.core.IMediaObject;
	import org.asblog.frameworks.controller.commandtype.DesignCanvasCT;
	import org.asblog.frameworks.controller.commandtype.MediaObjectCT;
	
	import flash.events.Event;
	
	import mx.controls.ComboBox;
	import mx.controls.HSlider;
	
	import org.puremvc.as3.patterns.mediator.BaseMediator;
	
	import ui.window.properties.BaseProperties;
	//xwjstart
	public class BasePropertiesMediator extends BaseMediator
	{
		public static const NAME : String = "BasePropertiesMediator";
		public static var baseProperties : BaseProperties;
		private var colorStyle:ComboBox;
		private var hslider1:mx.controls.HSlider;
		private var hslider2:mx.controls.HSlider;
		private var hslider3:mx.controls.HSlider;
		private var hslider4:mx.controls.HSlider;
		private var hslider5:mx.controls.HSlider;
		private var hslider6:mx.controls.HSlider;
		private var hslider7:mx.controls.HSlider;
		
		public function BasePropertiesMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			baseProperties = BaseProperties(viewComponent);
			
			//			colorStyle = baseProperties.colorStyle;
			hslider1 = baseProperties.hslider1;
			hslider2 = baseProperties.hslider2;
			
			hslider1.addEventListener(Event.CHANGE,onHSlider1Change);
			hslider2.addEventListener(Event.CHANGE,onHSlider2Change);
			if(baseProperties.selectedItem!=null){
				var selectedItem:IMediaObject = baseProperties.selectedItem;
				hslider1.value = selectedItem.brightness;
				hslider2.value = selectedItem.saturation;
				selectedItem.cleanStyle();
			}
			//			colorStyle.addEventListener(Event.CHANGE,styleChange);
		}
		//xwjstart
		private function onHSlider1Change(event:Event):void
		{
			var selectedItem:IMediaObject = baseProperties.selectedItem;
			var value:int = event.currentTarget.value;			
			sendNotification( MediaObjectCT.CMD_COLORSTYLE_BRIGHT, new History( selectedItem.brightness, hslider1.value, selectedItem.mediaLink.uid) );
		}
		private function onHSlider2Change(event:Event):void
		{
			var selectedItem:IMediaObject = baseProperties.selectedItem;
			var value:int = event.currentTarget.value;			
			sendNotification( MediaObjectCT.CMD_COLORSTYLE_SATURA, new History( selectedItem.saturation, hslider2.value, selectedItem.mediaLink.uid) );
		}
		//xwjend
		
		//		private function onHSliderChange(event:Event):void
		//		{
		//			var selectedItem:IMediaObject = baseProperties.selectedItem;
		//			var value:int = event.currentTarget.value;
		//			switch (colorStyle.selectedItem.label)
		//			{
		//				case "亮度":
		//					sendNotification( MediaObjectCT.CMD_COLORSTYLE_BRIGHT, new History( selectedItem.brightness, hslider.value, selectedItem.mediaLink.uid) );
		//					break;
		//				case "饱和度":
		//					sendNotification( MediaObjectCT.CMD_COLORSTYLE_SATURA, new History( selectedItem.saturation, hslider.value, selectedItem.mediaLink.uid) );
		//					break;
		//				case "对比度":
		//					sendNotification( MediaObjectCT.CMD_COLORSTYLE_CONTRAST, new History( selectedItem.contrast, hslider.value, selectedItem.mediaLink.uid) );
		//					break;
		//			}
		//		}
		//		private function styleChange(event:Event):void
		//		{
		//			var selectedItem:IMediaObject = baseProperties.selectedItem;
		//			switch (colorStyle.selectedItem.label)
		//			{
		//				
		//				case "无":
		//					if(selectedItem.colorStyleEnable)
		//						sendNotification( MediaObjectCT.CMD_COLORSTYLE_ENABLE, new History( true, false, selectedItem.mediaLink.uid) );
		//					break;
		//				case "亮度":
		//					hslider.value = selectedItem.brightness;
		//					selectedItem.cleanStyle();
		//					break;
		//				case "饱和度":
		//					hslider.value = selectedItem.saturation;
		//					selectedItem.cleanStyle();
		//					break;
		//				case "对比度":
		//					hslider.value = selectedItem.contrast;
		//					selectedItem.cleanStyle();
		//					break;
		//			}
		//		}
	}
}
package org.asblog.frameworks.view 
{
	import org.asblog.core.DesignCanvas;
	import org.asblog.core.History;
	import org.asblog.core.IMediaObject;
	import org.asblog.frameworks.controller.commandtype.LayerManagerCT;
	import org.asblog.frameworks.controller.commandtype.MediaObjectCT;
	
	import flash.display.BlendMode;
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Button;
	import mx.controls.ComboBox;
	import mx.controls.List;
	import mx.controls.NumericStepper;
	import mx.events.CollectionEvent;
	import mx.events.CollectionEventKind;
	import mx.events.ListEvent;
	
	import org.puremvc.as3.patterns.mediator.BaseMediator;
	
	import ui.window.layer.LayersManager;

	/**
	 * @author Halley
	 */
	public class LayersManagerMdeiator extends BaseMediator 
	{
		public static const NAME : String = "LayersManagerMdeiator";
		public static var layersManager : LayersManager;
		private var itemList:ArrayCollection;
		private var layers:List;
		private var modelList:ComboBox;
		private var designCanvas:DesignCanvas;
		private var up_btn:Button;
		private var down_btn:Button;
		private var remove_btn:Button;
		private var removeAll_btn:Button;
		private var itemAlpha:NumericStepper;
		private var blendModeList:Array= [{name:"一般",data:BlendMode.NORMAL},
											{name:"图层",data:BlendMode.LAYER},
											{name:"变暗",data:BlendMode.DARKEN},
											{name:"增值",data:BlendMode.MULTIPLY},
											{name:"变亮",data:BlendMode.LIGHTEN},
											{name:"荧幕",data:BlendMode.SCREEN},
											{name:"叠加",data:BlendMode.OVERLAY},
											{name:"强光",data:BlendMode.HARDLIGHT},
											{name:"增加",data:BlendMode.ADD},
											{name:"减去",data:BlendMode.SUBTRACT},
											{name:"差异",data:BlendMode.DIFFERENCE},
											{name:"反转",data:BlendMode.INVERT},
											{name:"Alpha",data:BlendMode.ALPHA},
											{name:"擦除",data:BlendMode.ERASE}];
		public function LayersManagerMdeiator( viewComponent : Object = null)
		{
			super( NAME, viewComponent );
			layersManager = LayersManager( viewComponent );
			up_btn = layersManager.up_btn;
			down_btn = layersManager.down_btn;
			remove_btn = layersManager.remove_btn;
			removeAll_btn = layersManager.removeAll_btn;
			itemAlpha = layersManager.itemAlpha;
			itemList = layersManager.itemList;
			layers = layersManager.layers;
			modelList = layersManager.modelList;
			designCanvas = DesignCanvasMediator.designCanvas;

			up_btn.addEventListener( MouseEvent.CLICK, switchItemDepthUp );
			down_btn.addEventListener( MouseEvent.CLICK, switchItemDepthDown );
			remove_btn.addEventListener( MouseEvent.CLICK, onRemoveSelectedItem );
			removeAll_btn.addEventListener( MouseEvent.CLICK, onRemoveAllItem );
			itemAlpha.addEventListener( Event.CHANGE, onAlphaChange );
			layers.addEventListener( ListEvent.CHANGE, onListChange );
			itemList.addEventListener(CollectionEvent.COLLECTION_CHANGE,onCOllectionChange);
			modelList.dataProvider = blendModeList;
			modelList.addEventListener(ListEvent.CHANGE,onModeChange);
		}
		private function onAlphaChange(event:Event):void
		{
			var item:IMediaObject = IMediaObject(layers.selectedItem);
			sendNotification( MediaObjectCT.CMD_ALPHA_CHANGE, new History(item.alpha,itemAlpha.value/100,item.uid,function(value:Number):void
			{
				itemAlpha.value = value*100;
			}));
		}
		private function onModeChange(event:ListEvent):void
		{
			var item:IMediaObject = IMediaObject(layers.selectedItem);
			sendNotification( MediaObjectCT.CMD_SET_BLEND, new History(item.blendMode,modelList.selectedItem.data,item.uid,function(value:String):void
			{
				modelList.selectedIndex = getblendModelIndex(value);
			}) );
		}
		private function onCOllectionChange(event:CollectionEvent):void
		{
			if(event.kind==CollectionEventKind.ADD)
			{
				layers.selectedIndex = 0;
			}
		}
		//得元素的混合模式在混合模式列表里的索引
		private function getblendModelIndex(blendMode:String):int
		{
			var le:int = blendModeList.length
			for(var i:int = 0;i<le;i++)
			{
				if(blendModeList[i].data==blendMode)
				{
					return i;
				}
			}
			return 0
		}
		private function swapItemsAt(fromIndex:int, toIndex:int, collection:ArrayCollection):void
		{
			var fromItem:Object = collection.getItemAt(fromIndex);
			var toItem:Object = collection.getItemAt(toIndex);
			
			collection.setItemAt(fromItem, toIndex)
			collection.setItemAt(toItem, fromIndex);
			collection.refresh();
		}
		public function switchItemDepth(s:String,item:IMediaObject):void
		{
			var itemIndex:int = itemList.getItemIndex( item );
			if(s=="up")
			{
				var childInUp:IMediaObject = IMediaObject( itemList.getItemAt( itemIndex-1 ) );
				swapItemsAt( itemIndex, itemIndex-1, itemList );
				designCanvas.canvasContent.swapChildren( DisplayObject( childInUp ), DisplayObject( item ) );
			}
			else
			{
				var childInDown:IMediaObject = IMediaObject( itemList.getItemAt( itemIndex+1 ) );
				swapItemsAt( itemIndex,itemIndex+1,itemList );
				designCanvas.canvasContent.swapChildren( DisplayObject( childInDown ),DisplayObject( item ) );
			}
			layers.selectedItem = item;
			setBtnEnable(item);
		}
		public function setBtnEnable(selectedItem:IMediaObject):void
		{
			var index:int = itemList.getItemIndex( selectedItem );
			//trace("setBtnEnable index:"+index);
			if(itemList.length==1)
			{
				up_btn.enabled = false;
				down_btn.enabled = false;
			}
			else if(index==0)
			{
				up_btn.enabled = false;
				down_btn.enabled = true;
			}
			else if(index==itemList.length-1)
			{
				up_btn.enabled = true;
				down_btn.enabled = false;
			}
			else
			{
				up_btn.enabled = true;
				down_btn.enabled = true;
			}
			//layers.selectedIndex = designCanvas.selectedIndex;
			modelList.selectedIndex = getblendModelIndex( selectedItem.blendMode );
		}
		private function onListChange(event:ListEvent):void
		{
			designCanvas.setSelection( layers.selectedItem, false );
		}
		private function onRemoveSelectedItem(event : MouseEvent) : void
		{
			sendNotification( LayerManagerCT.CMD_REMOVE_MEDIAOBJECT, layers.selectedItem.mediaLink );
		}
		private function onRemoveAllItem(event : MouseEvent) : void
		{
			sendNotification( LayerManagerCT.CMD_REMOVEALL_MEDIAOBJECT );
		}
		private function switchItemDepthUp(event:MouseEvent):void
		{
			sendNotification( LayerManagerCT.CMD_SWITCH_UP, layers.selectedItem.mediaLink );
		}
		private function switchItemDepthDown(event:MouseEvent):void
		{
			sendNotification( LayerManagerCT.CMD_SWITCH_DOWN, layers.selectedItem.mediaLink );
		}
	}
}

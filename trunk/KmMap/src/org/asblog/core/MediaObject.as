package org.asblog.core
{
	import cn.riahome.color.DisplayObjectAdjustColor;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.external.ExternalInterface;
	import flash.filters.BitmapFilter;
	import flash.filters.ColorMatrixFilter;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;
	
	import flexlib.controls.sliderClasses.ExtendedSlider;
	
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.core.mx_internal;
	import mx.events.DragEvent;
	import mx.events.FlexEvent;
	import mx.managers.CursorManager;
	import mx.managers.DragManager;
	import mx.utils.UIDUtil;
	import mx.utils.URLUtil;
	
	import org.asblog.event.MediaItemEvent;
	import org.asblog.event.MediaMaskEvent;
	import org.asblog.frameworks.view.DesignCanvasMediator;
	import org.asblog.graphics.ColorStyleEnum;
	import org.asblog.mediaItem.MediaImage;
	import org.asblog.mediaItem.MediaShapMask;
	import org.asblog.mediaItem.MediaShape;
	import org.asblog.mediaItem.MediaText;
	import org.asblog.mxml.IMXML;
	import org.asblog.transform.TransformToolEvent;
	import org.asblog.utils.ArrayUtil;
	import org.asblog.utils.ColorMatrix;
	import org.asblog.utils.PopUpUtils;
	
	import ui.Snapshot;
	import ui.popUpWindow.PopUpRichTextWindow;


	use namespace mx_internal;

	/**
	 * 被选中之前触发
	 */
	[Event(name="beforSelect", type="org.asblog.event.MediaItemEvent")]

	/**
	 * 被选后时触发
	 */
	[Event(name="onSelected", type="org.asblog.event.MediaItemEvent")]

	/**
	 * 被遮罩后触发
	 */
	[Event(name="onMasked", type="org.asblog.event.MediaMaskEvent")]

	/**
	 *
	 * @author xucan
	 * 抽象媒体对象，继承FLEX的UIComponent，并实现IMXML接口。
	 * 只要能拖到面板上操作的对象都可以基于它的。
	 *
	 */
	[Bindable]

	public class MediaObject extends UIComponent implements IMXML, IMediaObject
	{
		private var _islock:Boolean=false;
		private var _maskobject:MediaMask;
		private var _selectenabled:Boolean=true;
		private var _selected:Boolean=false;
		private var _brightness:int;
		private var _saturation:int;
		private var _colorStyleEnable:Boolean;
		private var _isComplete:Boolean;
		private var _mediaLink:MediaLink;

		private var _colorStyle:String;
		/**
		 * 真实的对象
		 */
		protected var _relatedObject:DisplayObject;
		/**
		 * 对自身类型的引用
		 */
		protected var mediaObjectClass:Class=getDefinitionByName(getQualifiedClassName(this)) as Class;

		//关联
		protected var _rk:String="";
		protected var _rm:String="";
		protected var _rq:String="";
		protected var _obj_id:String="";


		public function MediaObject()
		{
			super();
			measuredHeight=80;
			measuredWidth=80;
			this.addEventListener(DragEvent.DRAG_ENTER, onDragEnter);
			//this.addEventListener( DragEvent.DRAG_DROP, onDragDrop );
			this.addEventListener(Event.REMOVED, onRemove);
			doubleClickEnabled=true;
			mouseChildren=false;
			this.addEventListener(MouseEvent.DOUBLE_CLICK, onMouseDoubleClick);
			this.addEventListener(FlexEvent.CREATION_COMPLETE,onComplete);
		}
		public function onComplete(e:FlexEvent):void
		{
			var viewOnly:Boolean=DesignCanvas.viewOnly;
			if (this is MediaShape ||((this is MediaImage) && (!(this.mediaLink.isBackground))))
			{
				if (viewOnly)
				{
					this.addEventListener(MouseEvent.MOUSE_OVER, onMouseOver);
					this.addEventListener(MouseEvent.MOUSE_OUT, onMouseOut);
					toolTip="双击查看关联";
				}
				else
				{
					toolTip="双击编辑关联";
				}
			}
		}

		public function onMouseOver(e:MouseEvent):void
		{
			if (e.target is MediaText)
			{
				return;
			}
			useHandCursor=true;
			buttonMode=true;
		}

		public function onMouseOut(e:MouseEvent):void
		{
			if (e.target is MediaText)
			{
				return;
			}
			useHandCursor=false;
			buttonMode=false;
		}

		public function get obj_id():String
		{
			return _obj_id;
		}

		public function set obj_id(value:String):void
		{
			_obj_id=value;
		}

		public function get rq():String
		{
			return _rq;
		}

		public function set rq(value:String):void
		{
			_rq=value;
		}

		public function get rm():String
		{
			return _rm;
		}

		public function set rm(value:String):void
		{
			_rm=value;
		}

		public function get rk():String
		{
			return _rk;
		}

		public function set rk(value:String):void
		{
			_rk=value;
		}

		public function onMouseDoubleClick(e:MouseEvent):void
		{
			var viewOnly:Boolean=DesignCanvas.viewOnly;
			if (e.target is MediaShape || ((e.target is MediaImage) && (!(MediaImage(e.target).mediaLink.isBackground))))
			{
				if (viewOnly)
				{
//					Alert.show("this.obj_id, rk, rm, rq = "+ this.obj_id+"  ,  "+ rk +"  , "+ rm+"  , "+rq);
					ExternalInterface.call("openViewMapNode", this.obj_id, rk, rm, rq);
				}
				else
				{
					ExternalInterface.call("openSetMapNode", this.obj_id, rk, rm, rq);
				}
			}

			if (!viewOnly && (e.target is MediaText))
			{
				//先取消文本选择
				var toolEvent:TransformToolEvent=new TransformToolEvent(TransformToolEvent.SETSELECTION);
				toolEvent.selectedUid=null;
				toolEvent.oldSelectedUid=DesignCanvasMediator.designCanvas.selectedItem != null ? DesignCanvasMediator.designCanvas.selectedItem.uid : null;
				DesignCanvasMediator.designCanvas.dispatchEvent(toolEvent);

				MediaText.currentMediaText=MediaText(e.target);
				PopUpUtils.addPop(PopUpRichTextWindow);
			}
		}

		public function get colorStyle():String
		{
			return _colorStyle;
		}

		public function set colorStyle(value:String):void
		{
			_colorStyle=value;
		}

		public function get mediaLink():MediaLink
		{
			return _mediaLink;
		}

		public function set mediaLink(value:MediaLink):void
		{
			_mediaLink=value;
		}

		public function get contentObject():DisplayObject
		{
			return relatedObject;
		}

		public function parseMXML(mxml:MXML):void
		{
		}

		public function createMXML(container:IMediaObjectContainer=null):MXML
		{
			return null;
		}

		public function clone():*
		{
			var s:IMediaObject=new mediaObjectClass();
			//s.depthAtParent = this.depthAtParent;
			s.blendMode=this.blendMode;
			s.isLock=this.isLock;
			s.maskObject=this.maskObject;
			s.mediaLink=mediaLink;
			return s;
		}

		override public function get width():Number
		{
			return $width;
		}

		override public function get height():Number
		{
			return $height;
		}

		override public function set width(value:Number):void
		{
			super.width=value;
		}

		override public function set height(value:Number):void
		{
			super.height=value;
		}

		public function get relatedObject():DisplayObject
		{
			return _relatedObject;
		}

		public function set relatedObject(v:DisplayObject):void
		{
			_relatedObject=v;
		}

		public function get selectEnabled():Boolean
		{
			return _selectenabled;
		}

		public function set selectEnabled(v:Boolean):void
		{
			_selectenabled=v;
		}

		public function get selected():Boolean
		{
			return _selected;
		}

		public function set selected(v:Boolean):void
		{
			_selected=v;
		}

		public function get isLock():Boolean
		{
			return _islock;
		}

		public function set isLock(v:Boolean):void
		{
			_islock=v;
		}

		public function get colorStyleEnable():Boolean
		{
			return _colorStyleEnable;
		}

		public function set colorStyleEnable(v:Boolean):void
		{
			if (v)
			{
				var oldFilters:Array=filters;
				for each (var eachFilter:BitmapFilter in oldFilters)
				{
					if (eachFilter is ColorMatrixFilter)
					{
						ArrayUtil.removeFrom(oldFilters, eachFilter);
					}
				}
			}
			else
			{
				_brightness=0;
				_saturation=0;
				colorStyle=null;
			}
			filters=oldFilters;
			_colorStyleEnable=v;
		}

		[Bindable("proChange")]
		public function get brightness():int
		{
			return _brightness;
		}

		private function setColorMatrix(matrix:Array):void
		{
			var oldFilters:Array=filters;
			var hasColorMatrixFilter:Boolean;
			for each (var eachFilter:BitmapFilter in oldFilters)
			{
				if (eachFilter is ColorMatrixFilter)
				{
					hasColorMatrixFilter=true;
					ColorMatrixFilter(eachFilter).matrix=matrix;
				}
			}
			if (!hasColorMatrixFilter)
			{
				var colorMatrixFilter:ColorMatrixFilter=new ColorMatrixFilter();
				colorMatrixFilter.matrix=matrix;
				oldFilters.push(colorMatrixFilter);
			}
			_colorStyleEnable=true;
			filters=oldFilters;
		}

		public function set brightness(v:int):void
		{
			_brightness=v;
			//			setColorMatrix( DisplayObjectAdjustColor.getBrightnessMatrix( v ) );
			var cm:ColorMatrix=new ColorMatrix();
			setColorMatrix(cm.adjustColor(this._brightness, this._saturation));
			if (_brightness != 0)
				_colorStyle=ColorStyleEnum.BRIGHTNESS;
			else
				_colorStyle=null;
			dispatchEvent(new Event("proChange"));
		}

		[Bindable("proChange")]
		public function get saturation():int
		{
			return _saturation;
		}

		public function set saturation(v:int):void
		{
			_saturation=v;
			//			setColorMatrix( DisplayObjectAdjustColor.getSaturationMatrix( v ) );
			var cm:ColorMatrix=new ColorMatrix();
			setColorMatrix(cm.adjustColor(this._brightness, this._saturation));
			if (_saturation != 0)
				_colorStyle=ColorStyleEnum.SATURATION;
			else
				_colorStyle=null;
			dispatchEvent(new Event("proChange"));

		}

		private function setBrightness(v:int):void
		{
			setColorMatrix(DisplayObjectAdjustColor.getBrightnessMatrix(v));
		}

		private function setSaturation(v:int):void
		{
			setColorMatrix(DisplayObjectAdjustColor.getSaturationMatrix(v));
		}

		public function get maskObject():MediaMask
		{
			return _maskobject;
		}

		public function set maskObject(v:MediaMask):void
		{
			_maskobject=v;
		}

		public function removeMask():void
		{
			relatedObject.mask=null;
			removeChild(maskObject);
			maskObject=null;
		}

		private function onDragEnter(event:DragEvent):void
		{
			if (event.dragInitiator is Snapshot)
			{
				if (Snapshot(event.dragInitiator).mediaLink.isMask && (maskObject == null) && (!mediaLink.isBackground))
				{
					Snapshot(event.dragInitiator).mediaLink.maskedItemUid=uid;
					DragManager.acceptDragDrop(this);
					DragManager.showFeedback(DragManager.LINK);
				}
			}
		}

		private function onDragDrop(event:DragEvent):void
		{
			var snapshot:Snapshot=Snapshot(event.dragInitiator);
			var link:MediaLink=snapshot.mediaLink;
			if (link.isMask)
			{
				var maskClass:Class=link.classRef
				var maskObj:MediaMask=new maskClass();
				if (maskObj is MediaShapMask)
				{
					MediaShapMask(maskObj).draw(ShapeLink(link).shapType);
				}
				IRelatedObject(maskObj.relatedObject).relatedObject.width=relatedObject.width;
				IRelatedObject(maskObj.relatedObject).relatedObject.height=relatedObject.height;
				maskObj.x=0;
				maskObj.y=0;
				//UIComponent(item).startDrag(true)
				this.addChild(maskObj);
				//maskObj.mouseChildren = false;
				maskObj.mouseEnabled=false;
				relatedObject.mask=DisplayObject(maskObj);
				this.maskObject=maskObj;
				//maskObj.masking = true
				maskObj.maskedObject=this;
			}
			else
			{
				parent.dispatchEvent(event.clone());
			}
		}

		public function cleanStyle():void
		{
			_brightness=0;
			_saturation=0;
			_colorStyle=null;
		}

		public function dispose():void
		{
			if (maskObject)
			{
				removeChild(maskObject);
			}
			parent.removeChild(this);
		}

		protected function onRemove(eve:Event):void
		{
			this.removeEventListener(Event.REMOVED, onRemove);
		}

		public function get isComplete():Boolean
		{
			return _isComplete;
		}

		public function set isComplete(isComplete:Boolean):void
		{
			_isComplete=isComplete;
		}
	}
}

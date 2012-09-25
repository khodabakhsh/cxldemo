package org.asblog.mediaItem
{
	import flash.display.GradientType;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.controls.Alert;
	import mx.core.UIComponent;
	
	import org.asblog.core.DesignCanvas;
	import org.asblog.event.LineControlEvent;
	import org.asblog.transform.TransformToolEvent;
	
	import ui.material.GraphicsList;

	public class MediaLine extends UIComponent
	{
		//pControlPoints 控制点
		public var pControlPoints:Array=new Array();
		public var GraphicsListwidth:Number=80;
		public var GraphicsListheight:Number=80;

		public static const LINE_COLOR:int=0x288adb;
		private var _selected:Boolean;

		private var _fromX:int;
		private var _fromY:int;
		private var _toX:int;
		private var _toY:int;

		private var _designCanvas:DesignCanvas;

//		private var shap : Sprite = new Sprite( );

		public function MediaLine(mouseX:int=0, mouseY:int=0)
		{
			this.fromX=mouseX;
			this.fromY=mouseY;
			this.x=fromX;
			this.y=fromY;
			this.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);

//			addChild(shap);
		}

		public function get toY():int
		{
			return _toY;
		}

		public function set toY(value:int):void
		{
			_toY=value;
		}

		public function get toX():int
		{
			return _toX;
		}

		public function set toX(value:int):void
		{
			_toX=value;
		}

		public function get fromY():int
		{
			return _fromY;
		}

		public function set fromY(value:int):void
		{
			_fromY=value;
		}

		public function get fromX():int
		{
			return _fromX;
		}

		public function set fromX(value:int):void
		{
			_fromX=value;
		}

		public function get designCanvas():DesignCanvas
		{
			return _designCanvas;
		}

		public function set designCanvas(value:DesignCanvas):void
		{
			_designCanvas=value;
		}

		private function onMouseDown(e:MouseEvent):void
		{
			if (this.designCanvas != null)
			{
//			this.designCanvas.lineControl.startControl.x=this.fromX-13;
//			this.designCanvas.lineControl.startControl.y=this.fromY-13;
				
				GraphicsList.isMediaLineSelecting = false;
				var slopy:Number;
				var cosy:Number;
				var siny:Number;
				var Par:Number=6;
				slopy=Math.atan2((toY - fromY), (toX - fromX));
				cosy=Math.cos(slopy);
				siny=Math.sin(slopy);
				this.designCanvas.lineControl.startControl.x=fromX - Par * cosy;
				this.designCanvas.lineControl.startControl.y=fromY - Par * siny;


				this.designCanvas.lineControl.endControl.x=this.toX + Par * cosy;
				this.designCanvas.lineControl.endControl.y=this.toY + Par * siny;

				this.designCanvas.lineControl.currentControlMediaLine=this;
				//lineControl置顶
				this.designCanvas.canvasContent.setChildIndex( this.designCanvas.lineControl, this.designCanvas.canvasContent.numChildren - 1 );

				var toolEvent:TransformToolEvent=new TransformToolEvent(TransformToolEvent.SETSELECTION);
				toolEvent.selectedUid=null;
				toolEvent.oldSelectedUid=_designCanvas.selectedItem != null ? _designCanvas.selectedItem.uid : null;
				this.designCanvas.dispatchEvent(toolEvent);
			}
		}


		public function draw4GraphicsList(borderColor:uint=0x0CBEEB):void
		{
			this.toolTip  = "点击选择之后，可在画布中画线";
			this.graphics.clear(); 
			this.graphics.lineStyle(12, borderColor);
			this.graphics.moveTo(0, 0);
			this.graphics.lineTo(GraphicsListwidth, GraphicsListheight);

		}

		//just for drawing
		internal var slopy:Number;
		internal var cosy:Number;
		internal var siny:Number;
		internal var Par:Number=6;

		public function draw():void
		{
			this.graphics.clear();

			this.graphics.lineStyle(3, LINE_COLOR);
//			var fp:Point = globalToLocal(new Point(0, 0));//转换坐标
//			var tp:Point = globalToLocal(new Point(toX, toY));//转换坐标
			this.graphics.moveTo(fromX, fromY);
			this.graphics.lineTo(toX, toY);
			//箭头
			this.graphics.beginFill(LINE_COLOR, 1);

			slopy=Math.atan2((fromY - toY), (fromX - toX));
			cosy=Math.cos(slopy);
			siny=Math.sin(slopy);
			this.graphics.moveTo(toX, toY);
			this.graphics.lineTo(toX + int(Par * cosy - (Par / 2.0 * siny)), toY + int(Par * siny + (Par / 2.0 * cosy)));
			this.graphics.lineTo(toX + int(Par * cosy + Par / 2.0 * siny), toY - int(Par / 2.0 * cosy - Par * siny));
			this.graphics.lineTo(toX, toY);
			this.graphics.endFill();
		}

		public function dispose():void
		{
			parent.removeChild(this);
		}
	}
}

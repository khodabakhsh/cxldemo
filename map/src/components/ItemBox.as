package components
{
	import controls.CrossCanvas;
	
	import events.BoxEvent;
	import events.DrawEvent;
	
	import flash.events.MouseEvent;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	
	import mx.core.UIComponent;
	
	
	
	[Event(name=BoxEvent.Line_MOUSE_DOWN, type="events.LineEvent")]
	[Event(name=BoxEvent.Line_MOUSE_UP, type="events.LineEvent")]
	[Event(name=BoxEvent.Line_MOVE, type="events.LineEvent")]
	
	public class ItemBox extends UIComponent
	{
		
		public static var ItemRectangle: String = "ItemRectangle";
		public static var ItemCircle: String = "ItemCircle";
		public static var ItemActor: String = "ItemActor";
		public static var ItemTriangle: String = "ItemTriangle";
		public static var ItemArrowLine: String = "ItemArrowLine";
		public static var ItemTransporter: String = "ItemTransporter";
		public static var ItemBranch: String = "ItemBranch";
		
		public var label:String="text";
		
		private var _startX: int; //左上角X坐标
		private var _startY: int;//左上角Y坐标
		private var _endX: int; //右下角X坐标
		private var _endY: int; //右下角Y坐标
		
		private var _thickness: Number; //线条粗细
		private var _lineColor: uint;//线条颜色
		private var _fillColor: uint;//
		
		private var text: TextField = new TextField();
		
		//pControlPoints 控制点
		public var pControlPoints:Array=new Array();
		
		//pArrayControlBox 连线吸引点
		public var pAttractPoints:Array=new Array();
		
		public function get thickness(): Number{
			return _thickness;
		}
		
		public function set thickness(value: Number): void{
			this._thickness = value;
		}
		
		public function get lineColor(): uint{
			return _lineColor;
		}
		
		public function set lineColor(value: uint): void{
			this._lineColor = value;
		}
		
		public function get fillColor(): uint{
			return _fillColor;
		}
		
		public function set fillColor(value: uint): void{
			this._fillColor = value;
		}
		
		public function get startX(): int{
			return _startX;
		}
		
		public function set startX(value: int): void{
			this._startX = value;
		}
		
		public function get startY(): int{
			return _startY;
		}
		
		public function set startY(value: int): void{
			this._startY = value;
		}
		
		public function get endX(): int{
			return _endX;
		}
		
		public function set endX(value: int): void{
			this._endX = value;
		}
		
		public function get endY(): int{
			return _endY;
		}
		
		public function set endY(value: int): void{
			this._endY = value;
		}
		
		//根据名称获取控制点
		public function getControlBox(strName:String):ItemControl
		{
			for (var j:int=0;j<this.pControlPoints.length;j++){
				var pControl:ItemControl=this.pControlPoints[j];
				if (pControl.name==strName){
					return pControl;
				}
			}
			return null;
		}
		
		//hide ControlBox
		public function hideControlBox():void
		{
			for (var j:int=0;j<pControlPoints.length;j++){
				var pItem:ItemControl=pControlPoints[j];
				pItem.visible=false;
			}
		}

		public function ItemBox(startX: int = 0,
			startY: int = 0,
			endX: int = 0,
			endY: int = 0,
			lineColor: uint = 0x000000,
			fillColor: uint = 0xFFFFFF,
			thickness: Number = 1)
		{
			super();
			this._endX = endX;
			this._endY = endY;
			this._startX = startX;
			this._startY = startY;
			
			this._lineColor = lineColor;
			this._fillColor = fillColor;
			
			this._thickness = thickness;
			
			text.autoSize = TextFieldAutoSize.CENTER;
			
			text.x = startX+(endX-startX)/2;
			text.y = startY+(endY-startY)/2+ 30;
			
			this.addChild(text);
			
			this.events();
			
			
		}
		
		
		
		//画控制方块
		public function drawControlBox(flow:CrossCanvas):Array
		{
			if (pControlPoints.length==0){
				var pICO1:ItemControl=flow.addNewICO(null,"1",startX,startY);
				var pICO2:ItemControl=flow.addNewICO(null,"2",endX,endY);
				
				pControlPoints.push(pICO1);
				pControlPoints.push(pICO2);
				
				pICO1.Parent=this;
				pICO2.Parent=this;
			}
			
			for (var i:int=0;i<pControlPoints.length;i++){
				pControlPoints[i].visible=true;
			}
				
			return pControlPoints;
		}
		
		//从控制盒中读取参数.
		public function readFromControlBox():void
		{
			for(var i:int=0;i<pControlPoints.length;i++){
				var pControl:ItemControl=pControlPoints[i];
				switch(pControl.name){
					case "1":
						this.startX=pControl.x+pControl.width/2-this.x;
						this.startY=pControl.y+pControl.height/2-this.y;
						break;
					case "2":
						this.endX=pControl.x+pControl.width/2-this.x;
						this.endY=pControl.y+pControl.height/2-this.y;
						break;
				}
			}
			
			var e: DrawEvent= new DrawEvent(DrawEvent.ReDraw);
			e.pObj = this;
			this.dispatchEvent(e);
		}
		
		public function resetControlBox():void
		{
			for(var i:int=0;i<pControlPoints.length;i++){
				var pBox:ItemControl=pControlPoints[i];
				switch(pBox.name){
					case "1":
						pBox.x=this.startX-pBox.width/2+this.x;
						pBox.y=this.startY-pBox.height/2+this.y;
						break;
					case "2":
						pBox.x=this.endX-pBox.width/2+this.x;
						pBox.y=this.endY-pBox.height/2+this.y;
						break;
				}
			}
		}
		
		public function draw_text(dbHeight:Number,dbAdd:Number):void{
			text.htmlText = "<b>" + this.label + "</b>";
			
			text.x = startX+(endX-startX-text.width)/2;
			text.y = startY+(endY-startY)*dbHeight+dbAdd;
			
		}
		
	     //事件绑定
		private function events(): void{
			this.addEventListener(MouseEvent.MOUSE_DOWN, onBoxMouseDown);
			this.addEventListener(MouseEvent.MOUSE_UP, onBoxMouseUp);
			this.addEventListener(MouseEvent.MOUSE_MOVE, onBoxMouseMove);
		}
		
		//触发鼠标按下事件
		private function onBoxMouseDown(event: MouseEvent): void{
			var e: BoxEvent= new BoxEvent(BoxEvent.MOUSE_DOWN);
			e.pObj = this;
			this.dispatchEvent(e);
			this.hideControlBox();
			this.startDrag();
		}
		
		//触发鼠标弹起事件
		private function onBoxMouseUp(event: MouseEvent): void{
			var e: BoxEvent= new BoxEvent(BoxEvent.MOUSE_UP);
			e.pObj = this;
			this.dispatchEvent(e);
			
			redraw();
			
			this.stopDrag();
			this.resetControlBox();
		}
		
		public function redraw(){
			var e2: DrawEvent= new DrawEvent(DrawEvent.ReDraw);
			e2.pObj = this;
			this.dispatchEvent(e2);
		}
		//触发鼠标移动事件
		private function onBoxMouseMove(event: MouseEvent): void{
			var e: BoxEvent= new BoxEvent(BoxEvent.MOUSE_MOVE);
			e.pObj = this;
			this.dispatchEvent(e);
		}
	}
}


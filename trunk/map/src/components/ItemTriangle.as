package components
{
	import events.DrawEvent;
	
	import flash.geom.Point;
	
	[Event(name=BoxEvent.MOUSE_DOWN, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_UP, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_MOVE, type="events.BoxEvent")]
	
	public class ItemTriangle extends ItemBox
	{
		private var _thickness: Number; //线条粗细
		
		private var _fromIcon: String; //从..开始 名称
		private var _toIcon: String; //到..结束 名称
		
		private var _lineColor: uint;//线条颜色
		


		public function ItemTriangle(startX: int = 0,
			startY: int = 0,
			endX: int = 0,
			endY: int = 0,
			lineColor: uint = 0x000000,
			fillColor: uint = 0xFFFF00,
			thickness: Number = 1)
		{
			super(startX,startY,endX,endY,lineColor,fillColor,thickness);
			
			////////////////////////////////////////
			this.pAttractPoints.push(new Point(0.5,0));
			
			this.pAttractPoints.push(new Point(0,1));
			this.pAttractPoints.push(new Point(0.5,1));
			this.pAttractPoints.push(new Point(1,1));
			
			////////////////////////////////////////
			this.pointArray.push(new Point(0.5,0));
			this.pointArray.push(new Point(0,1));
			this.pointArray.push(new Point(1,1));
			
			this.draw();
			
			this.addEventListener(DrawEvent.ReDraw, onPaint);
		}
		
		//触发Draw
		private function onPaint(event:DrawEvent): void{
			draw();
			super.draw_text(1,5);
		}
		
		private var pointArray:Array=new Array();
		
		private function CalX(dbX:Number):Number{
			return startX+(endX-startX)*dbX;
		}
		
		private function CalY(dbY:Number):Number{
			return startY+(endY-startY)*dbY;
		}
		
		private function drawPolygon(pointArray:Array):void
       	{
       		this.graphics.moveTo(
	       			CalX(pointArray[0].x),
	       			CalY(pointArray[0].y));
           	for(var i:Number=1;i<pointArray.length;i++){
              	this.graphics.lineTo(
	              	CalX(pointArray[i].x),
	              	CalY(pointArray[i].y));
           	}
			this.graphics.moveTo(
					CalX(pointArray[0].x),
					CalY(pointArray[0].y));
       	}


		//画三角形
		public function draw(): void{
			this.graphics.clear();//清除前面画的东东
			
			//画线
			this.graphics.lineStyle(thickness, lineColor);
			
			this.graphics.beginFill(this.fillColor,0.8);

 			this.drawPolygon(this.pointArray);
			this.graphics.endFill();
		}
		
	}
}


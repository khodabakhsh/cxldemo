package components
{
	import events.DrawEvent;
	
	import flash.geom.Point;
	
	[Event(name=BoxEvent.MOUSE_DOWN, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_UP, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_MOVE, type="events.BoxEvent")]
	
	//小车
	public class ItemTransporter extends ItemBox
	{
		private var _thickness: Number; //线条粗细
		
		private var _lineColor: uint;//线条颜色
		

		public function ItemTransporter(startX: int = 0,
			startY: int = 0,
			endX: int = 0,
			endY: int = 0,
			lineColor: uint = 0x000000,
			fillColor: uint = 0xFFFF00,
			thickness: Number = 2)
		{
			super(startX,startY,endX,endY,lineColor,fillColor,thickness);
			
			this.pAttractPoints.push(new Point(0,0.4));
			this.pAttractPoints.push(new Point(1,0.4));
			
			this.pAttractPoints.push(new Point(0,1));
			this.pAttractPoints.push(new Point(1,1));
			
			this.draw();
			
			this.addEventListener(DrawEvent.ReDraw, onPaint);
		}
		
		//触发Draw
		private function onPaint(event:DrawEvent): void{
			draw();
			super.draw_text(1.0,2);
		}
		
		
		//画
		public function draw(): void{
			this.graphics.clear();//清除前面画的东东
			
			//画线
			this.graphics.lineStyle(2, lineColor);
			
			var W:Number=endX-startX;
			var H:Number=endY-startY;
			var x:Number=startX;
			var y:Number=startY+H*0.5;
			
			this.graphics.beginFill(this.fillColor,0.8);
			this.graphics.drawEllipse(startX,startY+H*0.6,W*0.4,H*0.4);
			this.graphics.drawEllipse(startX+W*0.6,startY+H*0.6,W*0.4,H*0.4);
			this.graphics.endFill();
			
			this.graphics.moveTo(startX,startY+H*0.4);
			this.graphics.lineTo(startX,startY+H*0.6);
			this.graphics.lineTo(endX,startY+H*0.6);
			this.graphics.lineTo(endX,startY+H*0.4);
			
		}
		
	}
}


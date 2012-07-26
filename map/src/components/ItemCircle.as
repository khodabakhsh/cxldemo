package components
{
	import events.DrawEvent;
	import flash.geom.Point;
	
	[Event(name=BoxEvent.MOUSE_DOWN, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_UP, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_MOVE, type="events.BoxEvent")]
	
	public class ItemCircle extends ItemBox
	{
		private var _thickness: Number; //线条粗细
		
		private var _lineColor: uint;//线条颜色
		

		public function ItemCircle(startX: int = 0,
			startY: int = 0,
			endX: int = 0,
			endY: int = 0,
			lineColor: uint = 0x000000,
			fillColor: uint = 0xFFFF00,
			thickness: Number = 1)
		{
			super(startX,startY,endX,endY,lineColor,fillColor,thickness);
			
			
			this.pAttractPoints.push(new Point(0.5,0));
			this.pAttractPoints.push(new Point(0,0.5));
			this.pAttractPoints.push(new Point(1,0.5));
			this.pAttractPoints.push(new Point(0.5,1));
			
			this.draw();
			
			this.addEventListener(DrawEvent.ReDraw, onPaint);
		}
		
		//触发Draw
		private function onPaint(event:DrawEvent): void{
			draw();
			super.draw_text(0.5,0);
		}
		
		
		//画
		public function draw(): void{
			this.graphics.clear();//清除前面画的东东
			
			//画线
			this.graphics.lineStyle(thickness, lineColor);
			
			this.graphics.beginFill(this.fillColor,0.8);
			this.graphics.drawEllipse(startX,startY,endX-startX,endY-startY);
			this.graphics.endFill();
		}
		
	}
}


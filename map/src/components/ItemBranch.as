package components
{
	import events.DrawEvent;
	import flash.geom.Point;
	
	[Event(name=BoxEvent.MOUSE_DOWN, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_UP, type="events.BoxEvent")]
	[Event(name=BoxEvent.MOUSE_MOVE, type="events.BoxEvent")]
	
	public class ItemBranch extends ItemBox
	{
		private var _thickness: Number; //线条粗细
		
		private var _lineColor: uint;//线条颜色
		

		public function ItemBranch(startX: int = 0,
			startY: int = 0,
			endX: int = 0,
			endY: int = 0,
			lineColor: uint = 0x000000,
			fillColor: uint = 0xFFFF00,
			thickness: Number = 2)
		{
			super(startX,startY,endX,endY,lineColor,fillColor,thickness);
			
			this.label="分支";
			
			//this.pAttractPoints.push(new Point(0.5,0));
			this.pAttractPoints.push(new Point(0,0.5));
			this.pAttractPoints.push(new Point(1,0.5));
			this.pAttractPoints.push(new Point(0.5,1));
			
			this.draw();
			
			this.addEventListener(DrawEvent.ReDraw, onPaint);
		}
		
		//触发Draw
		private function onPaint(event:DrawEvent): void{
			draw();
			super.draw_text(0.5,-20);
		}
		
		
		//画
		public function draw(): void{
			this.graphics.clear();//清除前面画的东东
			
			//画线
			this.graphics.lineStyle(thickness, lineColor);
			
			//画线
			this.graphics.lineStyle(thickness, lineColor);
			this.graphics.moveTo(startX+10, startY+(endY-startY)/2);
			this.graphics.lineTo(endX-10, startY+(endY-startY)/2);
			
			this.graphics.moveTo(startX+(endX-startX)/2, startY+(endY-startY)/2);
			this.graphics.lineTo(startX+(endX-startX)/2, endY-10);


			this.graphics.drawEllipse(startX,startY+(endY-startY)/2-5,10,10);
			
			this.graphics.drawEllipse(endX-10, startY+(endY-startY)/2-5,10,10);
			
			this.graphics.beginFill(this.fillColor,0.8);
			this.graphics.drawEllipse(startX+(endX-startX)/2-5,endY-10,10,10);
			this.graphics.endFill();
			
		}
		
	}
}


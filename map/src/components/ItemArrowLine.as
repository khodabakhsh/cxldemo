package components
{
	import events.DrawEvent;
	[Event(name=LineEvent.Line_MOUSE_DOWN, type="events.LineEvent")]
	[Event(name=LineEvent.Line_MOUSE_UP, type="events.LineEvent")]
	[Event(name=LineEvent.Line_MOVE, type="events.LineEvent")]
	
	public class ItemArrowLine extends ItemBox
	{
		
		public function ItemArrowLine(
			startX: int = 0,
			startY: int = 0,
			endX: int = 0,
			endY: int = 0,
			lineColor: uint = 0x000000,
			thickness: Number = 2)
		{
			super(startX,startY,endX,endY,lineColor,lineColor,thickness);
			this.label="";
			this.draw();
			
			this.addEventListener(DrawEvent.ReDraw, onPaint);
		}
		
		//触发Draw
		private function onPaint(event:DrawEvent): void{
			draw();
			super.draw_text(0.5,-20);
		}
		
		//画直线
		public function draw(): void{
			this.graphics.clear();//清除前面画的东东
			
			//画线
			this.graphics.lineStyle(thickness, lineColor);
			this.graphics.moveTo(startX, startY);
			this.graphics.lineTo(endX, endY);
			
			//在线条尾部画三角
			this.drawArrowHead(startX,startY,endX,endY);
		}
		
		// 画箭头  
		private function drawArrowHead(
			startX:Number,startY:Number,
			endX:Number,endY:Number):void{

             var arrowLength : Number = 10;  
             var arrowAngle : Number = Math.PI / 6;  
             var lineAngle : Number;  
             if(endX - startX != 0)                            
                     lineAngle = Math.atan((endY - startY) / (endX - startX));  
             else{  
                     if(endY - startY < 0)  
                             lineAngle = Math.PI / 2;  
                     else  
                             lineAngle = 3 * Math.PI / 2;  
             }                                 
             if(endY - startY >= 0 && endX - startX <= 0){  
                     lineAngle = lineAngle + Math.PI;  
             }else if(endY - startY <= 0 && endX - startX <= 0){  
                     lineAngle = lineAngle + Math.PI;  
             }  
             //定义三角形  
             var angleC : Number = arrowAngle;  
             var rimA : Number = arrowLength;  
             var rimB : Number = Math.pow(Math.pow(endY - startY,2) + Math.pow(endX - startX,2),1/2);  
             var rimC : Number = Math.pow(Math.pow(rimA,2) + Math.pow(rimB,2) - 2 * rimA * rimB * Math.cos(angleC),1/2);  
             var angleA : Number = Math.acos((rimB - rimA * Math.cos(angleC)) / rimC);  
               
             var leftArrowAngle : Number = lineAngle + angleA;  
             var rightArrowAngle : Number = lineAngle - angleA;                        
             var leftArrowX : Number = startX + rimC * Math.cos(leftArrowAngle);  
             var leftArrowY : Number = startY + rimC * Math.sin(leftArrowAngle);                       
             var rightArrowX : Number = startX + rimC * Math.cos(rightArrowAngle);  
             var rightArrowY : Number = startY + rimC * Math.sin(rightArrowAngle);  
               
               
             with(graphics){  
                 moveTo(endX, endY);  
                 lineTo(leftArrowX, leftArrowY);  
                 moveTo(endX, endY);  
                 lineTo(rightArrowX, rightArrowY);  
             } 
	     }
	}
}


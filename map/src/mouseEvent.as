// ActionScript file
import Data.C_K_Str;

import components.ItemArrowLine;
import components.ItemBox;
import components.ItemControl;

import events.BoxEvent;

public var pSelectedItem:ItemBox=null; //选中的物件

//鼠标点击流程图画布时,画出一个图标,如果工具栏没有选中任何工具，则清除选择
internal function onDesignCanvasMouseDown(event:MouseEvent):void
{
	//如果用户点击的是画布,而不是按钮冒泡过来的事件
	if (event.target is CrossCanvas)
	{

		this.hideControlBox();
		pSelectedItem=null;
		if (icons.selectedItem != null)
		{
			//定位图标
			var p:Point=new Point();
			p=flow.globalToLocal(new Point(mouseX, mouseY)); //转换坐标
			//var pObj:ItemBox=null

			switch (icons.selectedIndex)
			{
				case 0:
					this.clearBorders();
					//如果不是在画布上新增元素，则是点击了空白的区域，应去除属性信息的显示
					properties.removeAll();
					break;
				case 1:
					pSelectedItem=addNewItem("Name" + Utils.randomString(10), ItemBox.ItemArrowLine, p.x, p.y, p.x + 100, p.y + 100);
					break;
				case 2:
					pSelectedItem=addNewItem("Name" + Utils.randomString(10), ItemBox.ItemRectangle, p.x, p.y, p.x + 100, p.y + 100);
					break;
				case 3:
					pSelectedItem=addNewItem("Name" + Utils.randomString(10), ItemBox.ItemCircle, p.x, p.y, p.x + 100, p.y + 100);
					break;
				case 4:
					pSelectedItem=addNewItem("Name" + Utils.randomString(10), ItemBox.ItemBranch, p.x, p.y, p.x + 100, p.y + 100);
					break;
				case 5:
					pSelectedItem=addNewItem("Name" + Utils.randomString(10), ItemBox.ItemTriangle, p.x, p.y, p.x + 100, p.y + 100);
					break;
				case 6:
					pSelectedItem=addNewItem("Name" + Utils.randomString(10), ItemBox.ItemActor, p.x, p.y, p.x + 100, p.y + 100);
					break;
				case 7:
					pSelectedItem=addNewItem("Name" + Utils.randomString(10), ItemBox.ItemTransporter, p.x, p.y, p.x + 100, p.y + 100);
					break;
				default:
					var pObj2:FlowIcon=addNewICO(icons.selectedIndex, "Name" + Utils.randomString(10), p.x, p.y);
			}
			icons.selectedIndex=0; //重新选择第一个,不然老是添加了.
			if (pSelectedItem != null)
			{
				this.setProperties(pSelectedItem);
			}
		}
	}
}

//删除ControlBox
private function hideControlBox():void
{
	var i:int=0;
	var pBox:ItemBox;

	var p:TreapEnumerator=pTreapItem.Elements(true);
	while (p.HasMoreElements())
	{
		pBox=ItemBox(p.NextElement());
		pBox.hideControlBox();
	}

}

//初始化ControlBox
public function initControlBox(pBox:ItemBox):void
{
	var pArray:Array=pBox.drawControlBox(flow);

	for (var i:int=0; i < pArray.length; i++)
	{
		pArray[i].addEventListener(IconEvent.ICON_MOUSE_DOWN, onIconMouseDown);
		pArray[i].addEventListener(IconEvent.ICON_MOUSE_UP, onIconMouseUp);
		pArray[i].addEventListener(IconEvent.ICON_MOVE, onIconMove);
	}
}

//当单击流程图画布上的图标时,呈选中状态,并出现发光效果.
internal function onIconMouseDown(event:IconEvent):void
{
	var img:ItemControl=ItemControl(event.icon);

	icons.selectedIndex=0;

	//添加选中效果(发光)
	glow1.target=img; //将效果指定到图标上(编程时不是为图标设置效果)
	glow1.end();
	glow1.play();

	this.clearBorders(); //删除所有图标的边框

	//判断是否已经加了蓝色边框,如果加了就不需要再加了,只发光就行.
	//img.numChildren == 2的意思是:FlowIcon上已经有了图标和文字,没有边框
	if (img.numChildren == 2)
	{
		this.drawBorder(img);
	}


	iconPressable=true;
	//实施拖动
	img.startDrag();
	//	bDrag=true;

	this.bDirty=true;
	this.showDirty();
}

/**
 * 拖动图标时重画与该图标相关的线条
 */
internal function onIconMove(event:IconEvent):void
{
	if (event.target is ItemControl)
	{
		var pControl:ItemControl=ItemControl(event.icon);
		if (event.icon.x <= 0)
			event.icon.x=0;
		if (event.icon.y <= 0)
			event.icon.y=0;
		var i:int=0;
		var pItemBox:ItemBox=pControl.Parent;

		if (pItemBox == null)
		{
			//this.setProperties(FlowIcon(event.target));
		}
		else
		{ //如果是ControlBox
			pItemBox.readFromControlBox();
			var k:int=0;
			var bAttract:Boolean=false;

			if (pItemBox is ItemArrowLine)
			{
				//如果是直线，判断是否连到吸引点
				var pBoxTmp:ItemBox=null;
				var x:Number, y:Number;

				var p:TreapEnumerator=pTreapItem.Elements(true);
				while (p.HasMoreElements())
				{
					pBoxTmp=ItemBox(p.NextElement());
					for (var j:int=0; j < pBoxTmp.pAttractPoints.length; j++)
					{
						x=pBoxTmp.x + pBoxTmp.startX + (pBoxTmp.endX - pBoxTmp.startX) * pBoxTmp.pAttractPoints[j].x;
						y=pBoxTmp.y + pBoxTmp.startY + (pBoxTmp.endY - pBoxTmp.startY) * pBoxTmp.pAttractPoints[j].y;
						if (Math.abs(pControl.x + 10 - x) < 10 && Math.abs(pControl.y + 10 - y) < 10)
						{
							bAttract=true;
							if (pControl.pAttractObj == pBoxTmp && pControl.pAttractIndex == j)
							{

							}
							else
							{
								pControl.pAttractObj=pBoxTmp;
								pControl.pAttractIndex=j;
								pControl.x=x - 10;
								pControl.y=y - 10;
								//pControl.Parent.readFromControlBox();

								pTreapAttract.insert(new C_K_Str(pControl.Parent.name + "/" + pControl.name), pControl);

								pControl.Parent.readFromControlBox();
								pControl.stopDrag();
								break;
							}
						}
						if (bAttract)
						{
							break;
						}
					}
				}
				if (bAttract == false)
				{
					pTreapAttract.remove(new C_K_Str(pControl.Parent.name + "/" + pControl.name));
					pControl.pAttractObj=null;
				}
			}
			RefreshAttractPoint(pItemBox);
		}
	}
}

internal function onIconMouseUp(event:IconEvent):void
{
	var img:ItemControl=ItemControl(event.icon);
	glow2.target=img;
	glow2.end();
	glow2.play();

	iconPressable=false;

	//停止拖动
	img.stopDrag();
}


internal function on_Box_MouseDown(event:BoxEvent):void
{
	this.hideControlBox();

	var pBox:ItemBox=ItemBox(event.pObj);
	pSelectedItem=pBox; //代表选择这个Item
	icons.selectedIndex=0;

	//添加选中效果(发光)
	glow1.target=pBox; //将效果指定到图标上(编程时不是为图标设置效果)
	glow1.end();
	glow1.play();

	this.clearBorders(); //删除所有图标的边框

	iconPressable=true;
	//实施拖动

	//设置属性对话框
	this.setProperties(pBox);

}


internal function on_Box_MouseMove(event:BoxEvent):void
{
	var pBox:ItemBox=ItemBox(event.pObj);
	if (pSelectedItem == pBox)
	{
		RefreshAttractPoint(pBox);
		this.bDirty=true;
		this.showDirty();
	}
}

internal function on_Box_MouseUp(event:BoxEvent):void
{
	var pBox:ItemBox=ItemBox(event.pObj);
	glow2.target=pBox;
	glow2.end();
	glow2.play();

	iconPressable=false;

	//停止拖动
	//pBox.stopDrag();
	pBox.drawControlBox(flow);
	//////////////////////zzzzzzzzzz

	RefreshAttractPoint(pBox);
}

//刷新相关联的吸引点对象
public function RefreshAttractPoint(pBox:ItemBox):void
{
	var x:Number, y:Number;

	var pControl:ItemControl=null;
	var p:TreapEnumerator=pTreapAttract.Elements(true);
	while (p.HasMoreElements())
	{
		pControl=ItemControl(p.NextElement());
		if (pControl.pAttractObj == pBox)
		{
			x=pBox.x + pBox.startX + (pBox.endX - pBox.startX) * pBox.pAttractPoints[pControl.pAttractIndex].x;
			y=pBox.y + pBox.startY + (pBox.endY - pBox.startY) * pBox.pAttractPoints[pControl.pAttractIndex].y;
			pControl.x=x - 10;
			pControl.y=y - 10;
			//LBInfo.text+=x+","+y;

			pControl.Parent.readFromControlBox();
		}
	}

	//	var pControl:ItemControl=null;
	//	for (var i:int=0;i<AttractArray.length;i++){
	//		pControl=AttractArray[i];
	//		//LBInfo.text=""+AttractArray.length;
	//		
	//	}
}

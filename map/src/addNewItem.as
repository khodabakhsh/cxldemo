// ActionScript file
import Data.C_K_Str;

import components.*;


import events.*;

public function addNewICO(
		index:int,strName:String,
		x:Number,y:Number):ItemControl{
	var icon:Object=icons.dataProvider[index].icon;// selectedItem.icon
	var newImage: ItemControl = new ItemControl(icon,index);
	newImage.label = icons.dataProvider[index].label;
	
	newImage.name = strName;
	//newImage.contextMenu = cm;
	newImage.x = x;
	newImage.y = y;
	
	newImage.addEventListener(IconEvent.ICON_MOUSE_DOWN, onIconMouseDown);
	newImage.addEventListener(IconEvent.ICON_MOUSE_UP, onIconMouseUp);
	newImage.addEventListener(IconEvent.ICON_MOVE, onIconMove);
	
	//添加到图标集合中去
	pTreapControl.insert(new C_K_Str(newImage.name),newImage);
	
	//iconArray.push(newImage);
	flow.addChild(newImage);
	return newImage;
}

public function addNewItem(
		strName:String,
		strType:String,
		x0:Number,y0:Number,
		x1:Number,y1:Number):ItemBox{

	var pBox:ItemBox=null;
	
	switch(strType){
		case ItemBox.ItemActor:
			pBox= new ItemActor(x0,y0,x1,y1,0x000000);
			break;
		case ItemBox.ItemCircle:
			pBox= new ItemCircle(x0,y0,x1,y1,0x000000);
			break;
		case ItemBox.ItemRectangle:
			pBox= new ItemRectangle(x0,y0,x1,y1,0x000000);
			break;
		case ItemBox.ItemArrowLine:
			pBox= new ItemArrowLine(x0,y0,x1,y1,0x000000);
			break;
		case ItemBox.ItemTriangle:
			pBox= new ItemTriangle(x0,y0,x1,y1,0x000000);
			break;
		case ItemBox.ItemTransporter:
			pBox= new ItemTransporter(x0,y0,x1,y1,0x000000);
			break;
		case ItemBox.ItemBranch:
			pBox= new ItemBranch(x0,y0,x1,y1,0x000000);
			break;
	}
	
	//从..开始,到..结束
	pBox.name = strName;
	pBox.contextMenu = cm;
	pBox.addEventListener(BoxEvent.MOUSE_DOWN, on_Box_MouseDown);
	pBox.addEventListener(BoxEvent.MOUSE_UP, on_Box_MouseUp);
	pBox.addEventListener(BoxEvent.MOUSE_MOVE, on_Box_MouseMove);
	
	pTreapItem.insert(new C_K_Str(pBox.name),pBox);
	
	flow.addChild(pBox);
	pBox.drawControlBox(flow);
	pBox.redraw();
	initControlBox(pBox);
	return pBox;
}


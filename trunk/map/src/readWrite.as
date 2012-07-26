// ActionScript file
import Data.C_K_Str;
import Data.Treap; 

import components.ItemActor;
import components.ItemArrowLine;
import components.ItemBox;
import components.ItemCircle;
import components.ItemControl;
import components.ItemRectangle;
import components.ItemTriangle;

private var pURL_ReadNode:URLLoader;
private var pURL_SaveNode:URLLoader = new URLLoader();
private var myRequest:URLRequest;

public var bDirty:Boolean=false;//是否修改

//显示是否修改
private function showDirty():void
{
//	if (bDirty){
//		this.LBModify.text="***";
//	}else{
//		this.LBModify.text=" ";
//	}
}
//readXML==>OBJ
public function readXML(strXML:String):void
{
	pTreapControl=new Treap();
	pTreapItem=new Treap();
	pTreapAttract=new Treap();
	
	flow.removeAllChildren();
	
	//var strXML:String
	strXML="<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n" + 
			strXML;//xmlHTML.text;
	var pxml:XML=new XML(strXML);
	var i:int=0;
	
	var pRoot:XMLList=null;
	
	pRoot=pxml.child("rect");
	for (i=0;i<pRoot.length();i++){
		parseItem(pRoot[i],ItemBox.ItemRectangle);
	}
	
	pRoot=pxml.child("circle");
	for (i=0;i<pRoot.length();i++){
		parseItem(pRoot[i],ItemBox.ItemCircle);
	}
	
	pRoot=pxml.child("triangle");
	for (i=0;i<pRoot.length();i++){
		parseItem(pRoot[i],ItemBox.ItemTriangle);
	}
	
	pRoot=pxml.child("line");
	for (i=0;i<pRoot.length();i++){
		parseItem(pRoot[i],ItemBox.ItemArrowLine);
	}
	
	pRoot=pxml.child("actor");
	for (i=0;i<pRoot.length();i++){
		parseItem(pRoot[i],ItemBox.ItemActor);
	}
	
	pRoot=pxml.child("transporter");
	for (i=0;i<pRoot.length();i++){
		parseItem(pRoot[i],ItemBox.ItemTransporter);
	}
	
	var strName:String="";
	var strLabel:String="";
	var strLabel2:String="";
	var pControl:ItemControl;
	
	pRoot=pxml.child("attract");
	var pNode:XML=null;
	
	for (i=0;i<pRoot.length();i++){
		pNode=pRoot[i];
		strName=pNode.attribute("name");
		strLabel=pNode.attribute("item");
		
		var pItem:ItemBox=this.getItemFromName(strLabel);
		if (pItem!=null){
			pControl=pItem.getControlBox(strName);
			if (pControl!=null){
				strLabel2=pNode.attribute("attractObj");
				if (strLabel!=strLabel2){
					pControl.pAttractObj=this.getItemFromName(strLabel2);
					pControl.pAttractIndex=parseInt(pNode.attribute("attractIndex"));
					pTreapAttract.insert(new C_K_Str(pControl.Parent.name+"/"+pControl.name),pControl);
					//AttractArray.push(pControl);
				}
			}
		}
		
	}
	
	
	this.hideControlBox();
	
	this.bDirty=false;
	this.showDirty();
}

private function parseItem(pNode:XML,strType:String):void{
	var strName:String=pNode.attribute("name");
	var strLabel:String=pNode.attribute("label");
	
	var x0:Number=parseFloat(pNode.attribute("startX"));
	var y0:Number=parseFloat(pNode.attribute("startY"));
	var x1:Number=parseFloat(pNode.attribute("endX"));
	var y1:Number=parseFloat(pNode.attribute("endY"));
	var strColor:String=String("000000" + pNode.attribute("fillColor")).substr(-6);
	
	var FillColor:uint=uint("0x"+strColor);
	
	var pLine:ItemBox=this.addNewItem(strName,strType,x0,y0,x1,y1);
	
	pLine.label=strLabel;
	pLine.fillColor=FillColor;
	pLine.redraw();
}



private function xmlContent():String{
	var x: String = "<workflow>\r\n";
	var node: String = "";
	var pBox:ItemBox=null;
	var strLabel:String="";
	var strFillColor:String="";
	var strTag:String="";
	
	var p:TreapEnumerator=pTreapItem.Elements(true);
	while(p.HasMoreElements()){
		pBox=ItemBox(p.NextElement());
		strLabel=pBox.label;
		strLabel=strLabel.replace("\"","");
		strFillColor=pBox.fillColor.toString(16);
		node="";
		strTag="";
		if (pBox is ItemArrowLine){
			strTag="line";
		}
		
		if (pBox is ItemRectangle){
			strTag="rect";
		}
		
		if (pBox is ItemCircle){
			strTag="circle";
		}
		
		if (pBox is ItemTriangle){
			strTag="triangle";
		}
		
		if (pBox is ItemActor){
			strTag="actor";
		}
		
		if (pBox is ItemTransporter){
			strTag="transporter";
		}
		
		if (strTag!=""){
			node = "    <" +strTag + 
					" name=\"" +  pBox.name +
					"\" label=\"" + strLabel +
					"\" fillColor=\"" + strFillColor +
					"\" startX=\"" + (pBox.startX+pBox.x) + 
					"\" startY=\"" +  (pBox.startY+pBox.y) +
					"\" endX=\"" + (pBox.endX+pBox.x) +
					"\" endY=\"" + (pBox.endY+pBox.y) +
					"\" />";
			x += node + "\r\n";
		}
	}
	
	var pControl:ItemControl=null;
	var p:TreapEnumerator=pTreapAttract.Elements(true);
	while(p.HasMoreElements()){
		pControl=ItemControl(p.NextElement());
		node = "    <attract" + 
				" name=\"" +  pControl.name +
				"\" item=\"" + pControl.Parent.name +
				"\" attractObj=\"" + pControl.pAttractObj.name +
				"\" attractIndex=\"" + pControl.pAttractIndex +
				"\" />";
		x += node + "\r\n";
	}
	
	x += "</workflow>";
	return x;
}

//新建文件
private function newFile():void{
//	TxID.text="";
//	
//	pTreapControl=new Treap();
//	pTreapItem=new Treap();
//	pTreapAttract=new Treap();
//	
//	flow.removeAllChildren();
}

private function saveNode_Click():void
{
//	if (bDirty){
//		saveNode(ID,TxFileName.text,xmlContent(),TxPassword.text);
//	}
}

private function saveasNode_Click():void
{
//	saveNode(0,TxFileName.text,xmlContent(),TxPassword.text);
}

internal function saveNode(
	ID:int,
	strName:String,
	strContent:String,
	strPassword:String):void
{
//	var url:String = "http://point.mathfan.com/Node/SaveNode.aspx";
//
//    myRequest = new URLRequest(url);
//    myRequest.method = URLRequestMethod.POST;
//    var data:URLVariables = new URLVariables();
//    //data.name = this.strUserName;
//    //data.md5 = this.strMD5;
//    //data.ToUser = getEmail(StringManipulator.trimJID(this._tojid));
//    data.ID=ID;
//    data.Name=strName;
//    data.Content= strContent;
//    data.Password=strPassword;
//    
//    myRequest.data = data;
//    
//    pURL_SaveNode.load(myRequest);
//    pURL_SaveNode.addEventListener(Event.COMPLETE,onLoadComplete_saveNode);
//    pURL_SaveNode.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
}

private function ioErrorHandler(event:IOErrorEvent):void {
    trace("IO error occurred");
    var strMsg:String="IOErrorEvent 错误"+event.text;
    Alert.show(strMsg,"系统提示");
}

internal function onLoadComplete_saveNode(event:Event):void
{ 
    var loader:URLLoader = URLLoader(event.target);
    var strHTML:String=loader.data;
    
    if (strHTML.indexOf("更新成功")>-1 || 
    	strHTML.indexOf("插入成功")>-1){
    	bDirty=false;
    	this.showDirty();
    }
    
    Alert.show(strHTML,"系统提示");
}

private function onLoadComplete_readNode(event:Event):void
{ 
    var loader:URLLoader = URLLoader(event.target);
    var strHTML:String=loader.data;
    this.readXML(strHTML);
}

private function onLoadComplete_readTitle(event:Event):void
{ 
//    var loader:URLLoader = URLLoader(event.target);
//    var strHTML:String=loader.data;
//    TxFileName.text=strHTML;
}

private function readNode(strID:String,strField:String,pCallBack:Function):void
{
//	var ID:int=parseInt(strID);
//	var url:String = "http://point.mathfan.com/Node/ReadNode.aspx";
//
//    myRequest = new URLRequest(url);
//    myRequest.method = URLRequestMethod.POST;
//    var data:URLVariables = new URLVariables();
//    //data.name = this.strUserName;
//    //data.md5 = this.strMD5;
//    //data.ToUser = getEmail(StringManipulator.trimJID(this._tojid));
//    data.Field=strField;
//    data.ID=ID;
//    
//    myRequest.data = data;
//    
//    pURL_ReadNode= new URLLoader();
//    pURL_ReadNode.load(myRequest);
//    pURL_ReadNode.addEventListener(Event.COMPLETE,pCallBack);
//    pURL_ReadNode.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
}




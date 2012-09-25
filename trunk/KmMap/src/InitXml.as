package
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.geom.Rectangle;
	import flash.net.URLLoader;
	import flash.net.URLLoaderDataFormat;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.managers.CursorManager;
	import mx.managers.PopUpManager;
	
	import org.asblog.core.DesignCanvas;
	import org.asblog.core.History;
	import org.asblog.core.MediaLink;
	import org.asblog.core.ShapeLink;
	import org.asblog.core.TextLink;
	import org.asblog.frameworks.ApplicationFacade;
	import org.asblog.frameworks.controller.commandtype.DesignCanvasCT;
	import org.asblog.frameworks.view.DesignCanvasMediator;
	import org.asblog.mediaItem.MediaImage;
	import org.asblog.mediaItem.MediaLine;
	import org.asblog.mediaItem.MediaShape;
	import org.asblog.mediaItem.MediaText;
	import org.asblog.utils.PopUpUtils;
	
	import ui.popUpWindow.ProgressBarWindow;

	public class InitXml
	{
		private var contextPath:String;
		private var mapId:String;
		private var designCanvas:DesignCanvas;

		private var xmlData:XML=null;//xml字符串
		private var objId_Collection_for_Img:ArrayCollection= new ArrayCollection();//记录obj_id顺序，img按该顺序生成图片
		private var progressBarWindow :ProgressBarWindow ;

		public function InitXml(contextPath:String, mapId:String, designCanvas:DesignCanvas)
		{
			this.contextPath=contextPath;
			this.mapId=mapId;
			this.designCanvas=designCanvas;
		}

		public function startInitXml():void
		{
			progressBarWindow = PopUpUtils.addPop(ProgressBarWindow);
			if (mapId != null && mapId != "")
			{
				//xml
				var request:URLRequest=new URLRequest(contextPath + "/map/getDesignerXml");
				request.method=URLRequestMethod.POST;
				request.data=new URLVariables("mapId=" + mapId);
				var loader:URLLoader=new URLLoader();
				CursorManager.setBusyCursor();
				loader.load(request);
				loader.addEventListener(Event.COMPLETE, xmlCompleteHandler);
				loader.addEventListener(IOErrorEvent.IO_ERROR, errorHandle);
			}else{
				PopUpManager.removePopUp(progressBarWindow);
			}
		}

		private function imgCompleteHandler(evt:Event):void
		{
			if (evt.type == Event.COMPLETE)
			{
				var data:ByteArray=URLLoader(evt.target).data as ByteArray;
				if (data.length != 0)
				{
					data.position=0;
					data.uncompress();
					try
					{
						var dict:Dictionary=designCanvas.imageID_position_dictionary;
						for (var i:int=0;i<objId_Collection_for_Img.length;i++){
							var obj:Object=dict[objId_Collection_for_Img.getItemAt(i)];
							data.position=obj.startPosition;
							
							var width:int=data.readUnsignedInt();
							var height:int=data.readUnsignedInt();
							//								Alert.show("width :   "+ width + "  ,  height  :"+ height +"  ,  startPosition :"+obj.startPosition+"  ,  endPosition :"+obj.endPosition);
							var itemByteArray:ByteArray=new flash.utils.ByteArray();
							data.position=0;
							itemByteArray.writeBytes(data, obj.startPosition + 8, obj.endPosition - obj.startPosition - 8);
							itemByteArray.position=0;
							
							var bmd:BitmapData=new BitmapData(width, height);
							bmd.setPixels(new Rectangle(0, 0, width, height), itemByteArray);
							var bm:Bitmap=new Bitmap(bmd);
							var link:MediaLink=new MediaLink();
							link.classRef=MediaImage;
							link.source=bm;
							link.x=obj.x;
							link.y=obj.y;
							link.rm=obj.rm;
							link.rq=obj.rq;
							link.rk=obj.rk;
							link.MediaImageWidth=obj.width;
							link.MediaImageHeight=obj.height;
							if (obj.isBackground == "1")
							{
								link.isBackground=true;
								link.isAdjuestImage=false;
								ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_CHANGE_BACKGROUND, new History(DesignCanvasMediator.designCanvas.background.mediaLink, link));
							}
							else
							{
								ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_ADD_MEDIAOBJECT, link);
							}
						}
//						for (var key:String in dict)
//						{
//							trace(key + "  :  " + dict[key]);
//							var obj:Object=dict[key];
//							data.position=obj.startPosition;
//
//							var width:int=data.readUnsignedInt();
//							var height:int=data.readUnsignedInt();
//							//								Alert.show("width :   "+ width + "  ,  height  :"+ height +"  ,  startPosition :"+obj.startPosition+"  ,  endPosition :"+obj.endPosition);
//							var itemByteArray:ByteArray=new flash.utils.ByteArray();
//							data.position=0;
//							itemByteArray.writeBytes(data, obj.startPosition + 8, obj.endPosition - obj.startPosition - 8);
//							itemByteArray.position=0;
//
//							var bmd:BitmapData=new BitmapData(width, height);
//							bmd.setPixels(new Rectangle(0, 0, width, height), itemByteArray);
//							var bm:Bitmap=new Bitmap(bmd);
//							var link:MediaLink=new MediaLink();
//							link.classRef=MediaImage;
//							link.source=bm;
//							link.x=obj.x;
//							link.y=obj.y;
//							link.rm=obj.rm;
//							link.rq=obj.rq;
//							link.rk=obj.rk;
//							link.MediaImageWidth=obj.width;
//							link.MediaImageHeight=obj.height;
//							if (obj.isBackground == "1")
//							{
//								link.isBackground=true;
//								link.isAdjuestImage=false;
//								ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_CHANGE_BACKGROUND, new History(DesignCanvasMediator.designCanvas.background.mediaLink, link));
//							}
//							else
//							{
//								ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_ADD_MEDIAOBJECT, link);
//							}
//						}


					}
					catch (e:Error)
					{
						Alert.show(e.toString());
						trace(e.toString());
					}
				}
				generate_MediaShape_MediaText_MediaLine();
			}
		}

		private function xmlCompleteHandler(e:Event):void
		{
			CursorManager.removeBusyCursor();
			var l:URLLoader=e.target as URLLoader;
			xmlData=new XML(l.data);
			var MediaShapeList:XMLList=xmlData.MediaShape;
			var hasImg:Boolean=false;
			for each (var elementXml4:XML in xmlData.MediaImage)
			{
				var obj:Object=new Object();
				obj.startPosition=uint(elementXml4.@startPosition);
				obj.endPosition=uint(elementXml4.@endPosition);
				obj.x=Number(elementXml4.@x);
				obj.y=Number(elementXml4.@y);
				obj.width=Number(elementXml4.@width);
				obj.height=Number(elementXml4.@height);
				obj.rk=elementXml4.@rk;
				obj.rm=elementXml4.@rm;
				obj.rq=elementXml4.@rq;
				obj.isBackground=elementXml4.@isBackground;
				designCanvas.imageID_position_dictionary[String(elementXml4.@obj_id)]=obj;
				objId_Collection_for_Img.addItem(String(elementXml4.@obj_id));
				hasImg=true;
			}
			//dont have img
			if (!hasImg)
			{
				generate_MediaShape_MediaText_MediaLine();
			}
			else
			{//have img
				var request1:URLRequest=new URLRequest(contextPath + "/map/getDesignerImg");
				request1.method=URLRequestMethod.POST;
				request1.data=new URLVariables("mapId=" + mapId);
				var ldr:URLLoader=new URLLoader();
				ldr.dataFormat=URLLoaderDataFormat.BINARY;
				ldr.addEventListener(Event.COMPLETE, imgCompleteHandler);
				ldr.addEventListener(IOErrorEvent.IO_ERROR, errorHandle);
				ldr.load(request1);
			}
		}

		private function generate_MediaShape_MediaText_MediaLine():void
		{
			for each (var elementXml:XML in xmlData.MediaShape)
			{
				var shapeLink:ShapeLink=new ShapeLink();
				shapeLink.classRef=MediaShape;
				shapeLink.shapType=elementXml.@shapType;
				shapeLink.x=elementXml.@x;
				shapeLink.y=elementXml.@y;
				shapeLink.width=elementXml.@width;
				shapeLink.height=elementXml.@height;
				shapeLink.rotation=elementXml.@rotation;
				shapeLink.rotationX=elementXml.@rotationX;
				shapeLink.rotationY=elementXml.@rotationY;
				shapeLink.rotationZ=elementXml.@rotationZ;
				shapeLink.rk=elementXml.@rk;
				shapeLink.rm=elementXml.@rm;
				shapeLink.rq=elementXml.@rq;
//				shapeLink.matrixA = elementXml.@matrixA;
//				shapeLink.matrixD = elementXml.@matrixD;
				shapeLink.obj_id=elementXml.@obj_id;
				ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_ADD_MEDIAOBJECT, shapeLink);
			}
			for each (var elementXml2:XML in xmlData.MediaText)
			{
				var textLink:TextLink=new TextLink();
				textLink.classRef=MediaText;
				textLink.x=elementXml2.@x;
				textLink.y=elementXml2.@y;
				//					textLink.width=elementXml2.@width;
				//					textLink.height=elementXml2.@height;
				textLink.obj_id=elementXml2.@obj_id;
				textLink.text=elementXml2.@text;
				textLink.scaleX=elementXml2.@scaleX;
				textLink.scaleY=elementXml2.@scaleY;
				textLink.rotation=elementXml2.@rotation;
				textLink.rotationX=elementXml2.@rotationX;
				textLink.rotationY=elementXml2.@rotationY;
				textLink.rotationZ=elementXml2.@rotationZ;
				textLink.fontColor=elementXml2.@fontColor;
				textLink.fontSize=elementXml2.@fontSize;
				ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_ADD_MEDIAOBJECT, textLink);
			}
			for each (var elementXml3:XML in xmlData.MediaLine)
			{
				var mediaLine:MediaLine=new MediaLine();
				//					mediaLine.obj_id=elementXml3.@obj_id;
				mediaLine.fromX=elementXml3.@fromX;
				mediaLine.fromY=elementXml3.@fromY;
				mediaLine.toY=elementXml3.@toY;
				mediaLine.toX=elementXml3.@toX;


				this.designCanvas.canvasContent.addChild(mediaLine);
				this.designCanvas.lineList.addItem(mediaLine);
				mediaLine.designCanvas=this.designCanvas;
				mediaLine.draw();
			}
			PopUpManager.removePopUp(progressBarWindow);
		}


		private function errorHandle(event:IOErrorEvent):void
		{
			Alert.show("IO异常，操作失败！", "提示");
		}
	}
}

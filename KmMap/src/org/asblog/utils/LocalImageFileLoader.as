package org.asblog.utils
{
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.net.FileReferenceList;
	import flash.utils.ByteArray;
	
	import mx.controls.Alert;
	
	import org.asblog.core.History;
	import org.asblog.core.MediaLink;
	import org.asblog.frameworks.ApplicationFacade;
	import org.asblog.frameworks.controller.commandtype.DesignCanvasCT;
	import org.asblog.frameworks.view.DesignCanvasMediator;
	import org.asblog.frameworks.view.MediaCreatorMediator;
	import org.asblog.mediaItem.MediaImage;
	
	import ui.Snapshot;

	public class LocalImageFileLoader
	{
		private var type:String;
		internal var fileReference: FileReference = new flash.net.FileReference();
		private function initSingle():void
		{
//			var fileReference:FileReference = new FileReference();
			fileReference.browse( [new FileFilter("所有图片 (*.jpg, *.jpeg, *.gif, *.png)", "*.jpg;*.jpeg;*.gif;*.png")] );
			fileReference.addEventListener(Event.SELECT, onFileSelected);
		}
		internal function onFileSelected(event:Event):void
		{
//			var fileReference:FileReference = FileReference(event.target);
			fileReference.addEventListener(Event.COMPLETE, completeLoadFile);
			fileReference.load();
		}
		private function init():void
		{
			var fileReferenceList:FileReferenceList = new FileReferenceList();
			fileReferenceList.addEventListener(Event.SELECT,function(event:Event):void
			{
				var fileList:FileReferenceList = FileReferenceList(event.target)
				for (var i:int = 0; i < fileList.fileList.length; i++)
				{
					fileList.fileList[i].addEventListener(Event.COMPLETE, completeLoadFile);
					fileList.fileList[i].load();
				}
			});
			fileReferenceList.addEventListener(Event.COMPLETE,completeLoadFile);
			fileReferenceList.browse( [new FileFilter("所有图片 (*.jpg, *.jpeg, *.gif, *.png)", "*.jpg;*.jpeg;*.gif;*.png")] );
		}
		public function addImageToCanvas():void
		{
			initSingle();
			type = "c"
		}
		
		public function addImageToLibrary():void
		{
			init();
			type = "l"
		}
		public function addImageToBg():void
		{
			initSingle();
			type = "b"
		}
		public function addImageToBgLibrary():void
		{
			MediaCreatorMediator.mediaCreator.accNav.selectedIndex = 4
			init();
			type = "bl"
		}
		
		private function completeLoadFile(event:Event):void
		{	
			var file:FileReference = FileReference(event.target); 
			if(file.size>512000){
				Alert.show("请上传小于500KB的图片","提示：图片太大"); 
				return;
			}
			var source:ByteArray = ByteArray(event.target.data);
//			var snapshot:Snapshot = new Snapshot();
//			snapshot.imageUrl = source;		
			
			var link:MediaLink = new MediaLink();
			link.classRef = MediaImage;
			link.source = source;
			link.x = 10;
			link.y = 10;
			link.isBackground=false;
//			snapshot.mediaLink = link;
			if(type=="c")
				ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_ADD_MEDIAOBJECT,link);
			else if(type=="l")
			{
//				MediaCreatorMediator.mediaCreator.picList.imgList.addChildAt(snapshot,0);
			}
			else if(type=="bl")
			{
//				link.isBackground = true;
//				MediaCreatorMediator.mediaCreator.bgList.imgList.addChildAt(snapshot,0);
			}
			else
			{
				link.isBackground = true;
				link.isAdjuestImage = false;
				ApplicationFacade.getInstance().sendNotification(DesignCanvasCT.CMD_CHANGE_BACKGROUND,new History(DesignCanvasMediator.designCanvas.background.mediaLink,link));
			}
		}
	}
}
package org.asblog.core
{
	import flash.display.InteractiveObject;
	import flash.display.Shape;
	import flash.geom.Matrix;
	import flash.utils.Dictionary;
	
	import mx.controls.Alert;
	
	import org.asblog.frameworks.view.DesignCanvasMediator;
	import org.asblog.mediaItem.MediaImage;
	import org.asblog.mediaItem.MediaShapMask;
	import org.asblog.mediaItem.MediaShape;
	import org.asblog.mediaItem.MediaText;
	import org.asblog.utils.CacheUtil;

	public final class ItemFactory
	{
		public static var ObjectId_MediaObject_Dic:Dictionary = new Dictionary();
		
		private static function get designCanvas():DesignCanvas
		{
			return DesignCanvasMediator.designCanvas;
		}
		/**
		 * 根据缩略图提供的了型与参数，实例化对象
		 * @param 资源链接
		 * @return 缩略图关联数据的实例化对象
		 */
		public static function creatMediaObject(link : MediaLink) : *
		{
			if(!link)	return null;
			var classRef : Class = link.classRef;
			var obj : * = new classRef( );
			if(obj is IMediaObject)	obj.mediaLink = link;
			//trace("before uid:"+link.uid);
			if(link.uid)	obj.uid = link.uid;   //如果有值说明说明是在执行UNDO
			else			link.uid = obj.uid;	  //没有值说明是新的命令
			obj.name = link.uid;
			
			if(obj is MediaShapMask)
			{
				MediaShapMask( obj ).draw( ShapeLink(link).shapType );
				InteractiveObject(obj).mouseEnabled = false;
			}			 					 		
			if(!link.isBackground)
			{
				if(!link.x)		link.x = designCanvas.mouseX;
				if(!link.y)		link.y = designCanvas.mouseY;
				trace("ItemFactory.creatMediaObject   obj.x:"+obj.x);
				trace("ItemFactory.creatMediaObject   obj.y:"+obj.y);
				obj.x = link.x;
				obj.y = link.y;
			}
			
			if (obj is MediaImage)
			{
				MediaImage( obj ).source = link.source;
//				MediaImage( obj ).imageWidth = link.MediaImageWidth;
//				MediaImage( obj ).imageHeight = link.MediaImageHeight;
				if(link.MediaImageWidth && link.MediaImageHeight){
					MediaImage( obj ).width = MediaImage( obj ).sprite.width = link.MediaImageWidth;
					MediaImage( obj ).height = MediaImage( obj ).sprite.height = link.MediaImageHeight;
//				Alert.show("MediaImage( obj ).image.width : "+ MediaImage( obj ).sprite.width + "  ,  MediaImage( obj ).image.height: "+MediaImage( obj ).sprite.height );
				}
				
			}
			else if (obj is MediaShape)
			{
				MediaShape( obj ).draw( );
				MediaShape( obj ).rotation=link.rotation;
				MediaShape( obj ).rotationX=link.rotationX;
				MediaShape( obj ).rotationY=link.rotationY;
				MediaShape( obj ).rotationZ=link.rotationZ;
			}
			else if( obj is MediaText)
			{
				MediaText(obj).textLink= TextLink(link);
				MediaText(obj).text = TextLink(link).text;
				MediaText(obj).render();
				trace("MediaText(obj).textLink.fontColor :  "+MediaText(obj).textLink.fontColor);
				trace("MediaText(obj).textLink.fontSize :  "+MediaText(obj).textLink.fontSize);
				MediaText(obj).scaleX=TextLink(link).scaleX;
				MediaText(obj).scaleY=TextLink(link).scaleY;
				MediaText(obj).rotation=link.rotation;
				MediaText( obj ).rotationX=link.rotationX;
				MediaText( obj ).rotationY=link.rotationY;
				MediaText( obj ).rotationZ=link.rotationZ;
				
			}
			//关联
			obj.obj_id=link.obj_id;
			obj.rk=link.rk;
			obj.rm=link.rm;
			obj.rq=link.rq;
			ObjectId_MediaObject_Dic[obj.obj_id]=obj;
			
			CacheUtil.allCacheMediaLinks[obj.uid] = link;
			return obj;
		}
	}
}
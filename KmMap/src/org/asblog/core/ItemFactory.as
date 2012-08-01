package org.asblog.core
{
	import org.asblog.frameworks.view.DesignCanvasMediator;
	import org.asblog.mediaItem.MediaImage;
	import org.asblog.mediaItem.MediaShapMask;
	import org.asblog.mediaItem.MediaShape;
	import org.asblog.mediaItem.MediaText;
	import org.asblog.utils.CacheUtil;
	
	import flash.display.InteractiveObject;

	public final class ItemFactory
	{
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
			}
			else if (obj is MediaShape)
			{
				MediaShape( obj ).draw( );
			}
			else if( obj is MediaText)
			{
				MediaText(obj).text = TextLink(link).text;
			}
			CacheUtil.allCacheMediaLinks[obj.uid] = link;
			return obj;
		}
	}
}
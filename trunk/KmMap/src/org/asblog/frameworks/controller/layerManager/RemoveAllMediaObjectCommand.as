package org.asblog.frameworks.controller.layerManager
{
	import org.asblog.core.IMediaObject;
	import org.asblog.core.ItemFactory;
	import org.asblog.core.MediaLink;
	import org.asblog.frameworks.controller.MediaCommand;
	import org.asblog.utils.CacheUtil;
	
	import org.puremvc.as3.interfaces.INotification;
	
	/**
	 * @author Halley
	 */
	public class RemoveAllMediaObjectCommand extends MediaCommand 
	{
		override public function execute( note : INotification) : void 
		{
			CacheUtil.removedMediaObjectLinks = designCanvas.removeAllMediaItem();
		}
		
		override public function undo( note : INotification) : void 
		{
			var newItems:Vector.<IMediaObject> = new Vector.<IMediaObject>();
			for each(var link:MediaLink in CacheUtil.removedMediaObjectLinks)
			{
				newItems.push(ItemFactory.creatMediaObject(link));
			}
			designCanvas.addMediaItems( newItems );
		}
	}
}
package org.asblog.frameworks.controller.layerManager 
{
	import org.asblog.core.ItemFactory;
	import org.asblog.core.MediaLink;
	import org.asblog.frameworks.controller.MediaCommand;
	import org.asblog.utils.CacheUtil;
	
	import org.puremvc.as3.interfaces.INotification;

	/**
	 * @author Halley
	 */
	public class RemoveMediaObjectCommand extends MediaCommand 
	{
		override public function execute( note : INotification) : void 
		{
			var link:MediaLink = MediaLink( note.getBody( ) );
			designCanvas.removeMediaItemByUID( link.uid );
			delete CacheUtil.allCacheMediaLinks[ link.uid ];
		}

		override public function undo( note : INotification) : void 
		{
			designCanvas.addMediaItem(ItemFactory.creatMediaObject( MediaLink( note.getBody( ) ) ) );
		}
	}
}

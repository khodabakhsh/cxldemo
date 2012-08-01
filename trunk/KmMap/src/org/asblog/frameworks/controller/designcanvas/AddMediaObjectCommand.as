package org.asblog.frameworks.controller.designcanvas 
{
	import org.asblog.core.ItemFactory;
	import org.asblog.core.MediaLink;
	import org.asblog.frameworks.controller.MediaCommand;
	import org.asblog.utils.CacheUtil;
	
	import org.puremvc.as3.interfaces.INotification;
	
	/**
	 * @author Halley
	 */
	public class AddMediaObjectCommand extends MediaCommand
	{
		override public function execute( note : INotification) : void 
		{
			designCanvas.addMediaItem( ItemFactory.creatMediaObject( MediaLink( note.getBody( ) ) ) );
		}
		
		override public function undo( note : INotification) : void 
		{
			var link:MediaLink = MediaLink( note.getBody( ) );
			designCanvas.removeMediaItemByUID( link.uid );
			delete CacheUtil.allCacheMediaLinks[ link.uid ];
		}
		
	}
}
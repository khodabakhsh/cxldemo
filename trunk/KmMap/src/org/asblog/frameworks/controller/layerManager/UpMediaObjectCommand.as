package org.asblog.frameworks.controller.layerManager
{
	import org.asblog.core.MediaLink;
	import org.asblog.frameworks.controller.MediaCommand;
	import org.asblog.frameworks.view.LayersManagerMdeiator;
	
	import org.puremvc.as3.interfaces.INotification;
	
	public class UpMediaObjectCommand extends MediaCommand
	{
		override public function execute( note : INotification) : void 
		{
			var layersManagerMdeiator:LayersManagerMdeiator = LayersManagerMdeiator(facade.retrieveMediator(LayersManagerMdeiator.NAME));
			layersManagerMdeiator.switchItemDepth( "up", designCanvas.getItemByUid( MediaLink( note.getBody( ) ).uid ) );
		}
		
		override public function undo( note : INotification) : void 
		{
			var layersManagerMdeiator:LayersManagerMdeiator = LayersManagerMdeiator(facade.retrieveMediator(LayersManagerMdeiator.NAME));
			layersManagerMdeiator.switchItemDepth( "down", designCanvas.getItemByUid( MediaLink( note.getBody( ) ).uid ) );
		}
	}
}
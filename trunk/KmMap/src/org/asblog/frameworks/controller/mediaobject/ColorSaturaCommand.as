package org.asblog.frameworks.controller.mediaobject
{
	import org.asblog.core.History;
	import org.asblog.core.IMediaObject;
	import org.asblog.frameworks.controller.MediaCommand;
	
	import flash.events.Event;
	
	import org.puremvc.as3.interfaces.INotification;
	
	public class ColorSaturaCommand extends MediaCommand
	{
		override public function execute( note : INotification) : void 
		{
			var history:History = History(note.getBody());
			var item:IMediaObject = designCanvas.getItemByUid( history.uid );
			item.saturation = history.newItem;
		}
		
		override public function undo( note : INotification) : void
		{
			var history:History = History(note.getBody());
			var item:IMediaObject = designCanvas.getItemByUid( history.uid );
			item.saturation = history.oldItem;
		}
	}
}
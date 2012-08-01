package org.asblog.frameworks.controller.designcanvas
{
	import org.asblog.core.History;
	import org.asblog.core.ItemFactory;
	import org.asblog.core.MediaLink;
	import org.asblog.frameworks.controller.MediaCommand;
	
	import org.puremvc.as3.interfaces.INotification;
	
	public class ChangeBackgroundCommand extends MediaCommand
	{
		override public function execute( note : INotification) : void 
		{
			var history:History = History(note.getBody());
			var newLink:MediaLink = history.newItem;
			designCanvas.chageBackground(ItemFactory.creatMediaObject(newLink));
		}
		
		override public function undo( note : INotification) : void 
		{
			var history:History = History(note.getBody());
			var oldLink:MediaLink = history.oldItem;
			designCanvas.chageBackground(ItemFactory.creatMediaObject(oldLink));
		}
	}
}
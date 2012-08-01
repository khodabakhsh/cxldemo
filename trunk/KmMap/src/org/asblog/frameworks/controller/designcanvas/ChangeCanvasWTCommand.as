package org.asblog.frameworks.controller.designcanvas
{
	import org.asblog.core.History;
	import org.asblog.frameworks.controller.MediaCommand;
	import org.asblog.frameworks.view.MediaCreatorMediator;
	import org.puremvc.as3.interfaces.INotification;
	
	public class ChangeCanvasWTCommand extends MediaCommand
	{
		override public function execute( note : INotification) : void 
		{
			var history:History = History(note.getBody());
			var newWT:Object = history.newItem;
			designCanvas.width = newWT.w;
			designCanvas.height = newWT.h;
		}
		
		override public function undo( note : INotification) : void 
		{
			var history:History = History(note.getBody());
			var oldWT:Object = history.oldItem;
			designCanvas.width = oldWT.w;
			designCanvas.height = oldWT.h;
		}
	}
}
package org.asblog.frameworks.view 
{
	import org.asblog.frameworks.ApplicationFacade;
	
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.BaseMediator;		

	/**
	 * @author Halley
	 */
	public class MediaCreatorMediator extends BaseMediator 
	{
		public static const NAME : String = "MediaCreatorMediator";
		public static var mediaCreator : MapDesigner;

		public function MediaCreatorMediator(viewComponent : Object = null)
		{
			super( NAME, viewComponent );
			mediaCreator = MapDesigner( viewComponent );
			mediaCreator.addEventListener( Event.ADDED_TO_STAGE, onAddToStage );
			
//			mediaCreator.undo_btn.addEventListener( MouseEvent.CLICK, undoClick );//			mediaCreator.redo_btn.addEventListener( MouseEvent.CLICK, redoClick );
		}
		
		private function  onAddToStage(event : Event) : void
		{
			mediaCreator.stage.addEventListener( KeyboardEvent.KEY_DOWN, onKeyDown );
		}
		private function  redoClick(event : MouseEvent) : void
		{
			redo( );
			//DesignCanvasMediator.designCanvas.dispatchEvent( new MouseEvent( MouseEvent.MOUSE_UP ) );
		}

		private function undoClick(event : MouseEvent) : void
		{
			undo( );
			//DesignCanvasMediator.designCanvas.dispatchEvent( new MouseEvent( MouseEvent.MOUSE_UP ) );
		}

		private function onKeyDown(event : KeyboardEvent) : void
		{
			if (event.ctrlKey)
			{
				switch (event.keyCode)
				{
					case 90:
						undo( );
						break;
					case 89:
						redo( );
						break;
				}
			}
		}

		private function undo() : void
		{
			//trace("undo");
			ApplicationFacade( facade ).undoLastCommand( );
		}

		private function redo() : void
		{
			//trace("redo");
			ApplicationFacade( facade ).redoPreviousCommand( );
		}

		override public function listNotificationInterests() : Array 
		{
			return [ ApplicationFacade.CMD_CHANGE ];            
		}

		override public function handleNotification( note : INotification ) : void 
		{
			switch ( note.getName( ) ) 
			{
				case  ApplicationFacade.CMD_CHANGE :
					//trace( "ApplicationFacade.CMD_CHANGE" );
					mediaCreator.dispatchEvent( new Event( "commandChange" ) );
					break;
			}
		}
	}
}

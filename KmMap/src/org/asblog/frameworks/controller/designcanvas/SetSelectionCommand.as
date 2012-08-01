package org.asblog.frameworks.controller.designcanvas 
{
	import org.asblog.frameworks.controller.MediaCommand;
	import org.asblog.transform.TransformToolEvent;
	
	import org.puremvc.as3.interfaces.INotification;	

	/**
	 * @author Halley
	 */
	public class SetSelectionCommand extends MediaCommand 
	{
		override public function execute( note : INotification) : void 
		{
			var event : TransformToolEvent = TransformToolEvent( note.getBody( ) );
			//如果是属于重做就不能立即移动
			//trace( "isDone=" + isDone );
			designCanvas.setSelection( event.selectedUid, (!isDone), false );
			isDone = true;
			undoAble = Boolean( event.selectedUid );
		}

		override public function undo( note : INotification) : void 
		{
			var event : TransformToolEvent = TransformToolEvent( note.getBody( ) );
			designCanvas.setSelection( event.oldSelectedUid, false, false );
		}
	}
}

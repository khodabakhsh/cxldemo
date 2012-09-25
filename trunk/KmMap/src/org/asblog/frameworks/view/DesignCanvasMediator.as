package org.asblog.frameworks.view 
{
	import flash.events.MouseEvent;
	
	import mx.events.DragEvent;
	
	import org.asblog.core.DesignCanvas;
	import org.asblog.core.History;
	import org.asblog.core.MediaLink;
	import org.asblog.event.MediaContainerEvent;
	import org.asblog.frameworks.controller.commandtype.DesignCanvasCT;
	import org.asblog.frameworks.controller.commandtype.LayerManagerCT;
	import org.asblog.transform.TransformTool;
	import org.asblog.transform.TransformToolEvent;
	import org.puremvc.as3.patterns.mediator.BaseMediator;
	
	import ui.Snapshot;

	/**
	 * @author Halley
	 */
	public class DesignCanvasMediator extends BaseMediator 
	{
		public static const NAME : String = "DesignCanvasMediator";		public static var designCanvas : DesignCanvas;		public static var transformTool : TransformTool;

		public function DesignCanvasMediator( viewComponent : Object = null)
		{
			super( NAME, viewComponent );
			designCanvas = DesignCanvas( viewComponent );
			transformTool = designCanvas.transformTool;
			designCanvas.addEventListener( TransformToolEvent.TARGET_MATRIX_CHANGE, onMatrixChange );			designCanvas.addEventListener( TransformToolEvent.SETSELECTION, onSetSelection );
			designCanvas.addEventListener( DragEvent.DRAG_DROP, onDragDrop );
			designCanvas.addEventListener( DragEvent.DRAG_DROP, onDragDrop,true );			designCanvas.addEventListener( MediaContainerEvent.REMVOE_CHILD, onRemoveMediaObject );
		}
		private function onRemoveMediaObject(event : MediaContainerEvent) : void
		{
			sendNotification( LayerManagerCT.CMD_REMOVE_MEDIAOBJECT, event.link );
		}

		private function onSetSelection(event : TransformToolEvent) : void
		{
			sendNotification( DesignCanvasCT.CMD_SETSELECTION, event );
			//designCanvas.setSelection( event.uid, true, false );
		}

		/**
		 * 当有拖拽进入画布区域并放下时
		 * @param event
		 */
		private function onDragDrop(event : DragEvent) : void
		{
			var snapshot:Snapshot;
			if (event.dragInitiator is Snapshot)
			{
				snapshot = Snapshot( event.dragInitiator );
				var link:MediaLink = snapshot.mediaLink.clone();
//				if(link.isBackground)
//					facade.sendNotification( DesignCanvasCT.CMD_CHANGE_BACKGROUND, new History(designCanvas.background.mediaLink,link) );
//				else if(link.isMask)
//					facade.sendNotification( DesignCanvasCT.CMD_ADD_MASK, link );
//				else				
					facade.sendNotification( DesignCanvasCT.CMD_ADD_MEDIAOBJECT, link );
			}
		}
		
		private function onMatrixChange(event : TransformToolEvent) : void
		{
//			trace( "DesignCanvasMediator onMatrixChange" );
//			trace( "onMatrixChange event.target  ："+event.target );
			sendNotification( DesignCanvasCT.CMD_MATRIX_CHANGE, event );
		}
		/*
		override public function listNotificationInterests() : Array 
		{
			return [];            
		}

		override public function handleNotification( note : INotification ) : void 
		{
			switch ( note.getName( ) ) 
			{
			}
		}*/
	}
}

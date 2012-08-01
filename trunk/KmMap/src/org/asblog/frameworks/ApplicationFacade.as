package org.asblog.frameworks 
{
	import org.asblog.frameworks.controller.commandtype.DesignCanvasCT;
	import org.asblog.frameworks.controller.commandtype.LayerManagerCT;
	import org.asblog.frameworks.controller.commandtype.MediaObjectCT;
	import org.asblog.frameworks.controller.designcanvas.AddMaskCommand;
	import org.asblog.frameworks.controller.designcanvas.AddMediaObjectCommand;
	import org.asblog.frameworks.controller.designcanvas.ChangeBackgroundCommand;
	import org.asblog.frameworks.controller.designcanvas.ChangeCanvasWTCommand;
	import org.asblog.frameworks.controller.designcanvas.MatrixChangeCommand;
	import org.asblog.frameworks.controller.designcanvas.SetSelectionCommand;
	import org.asblog.frameworks.controller.layerManager.DownMediaObjectCommand;
	import org.asblog.frameworks.controller.layerManager.RemoveAllMediaObjectCommand;
	import org.asblog.frameworks.controller.layerManager.RemoveMediaObjectCommand;
	import org.asblog.frameworks.controller.layerManager.UpMediaObjectCommand;
	import org.asblog.frameworks.controller.mediaobject.AlphaChangeCommand;
	import org.asblog.frameworks.controller.mediaobject.BlendChangeCommand;
	import org.asblog.frameworks.controller.mediaobject.ColorBrightCommand;
	import org.asblog.frameworks.controller.mediaobject.ColorEnableCommand;
	import org.asblog.frameworks.controller.mediaobject.ColorSaturaCommand;
	import org.puremvc.as3.patterns.facade.Facade;

	/**
	 * @author Halley
	 */
	public class ApplicationFacade extends Facade 
	{
		/**
		 * 每当一个MediaCommand被创建就会触发
		 */
		public static const CMD_CHANGE : String = "cmdChange";

		public function ApplicationFacade()
		{
			super( );
		}

		public function undoLastCommand() : void
		{
			controller.undoLastCommand( );
			super.sendNotification( CMD_CHANGE );
		}

		public function redoPreviousCommand() : void
		{
			controller.redoPreviousCommand( );
			super.sendNotification( CMD_CHANGE );
		}
		
		public function get hasLastCommand() : Boolean
		{
			return controller.hasLastCommand;
		}

		public function get hasRedoCommand() : Boolean
		{
			return controller.hasRedoCommand;
		}

		override public function sendNotification(notificationName : String, body : Object = null, type : String = null) : void
		{
			super.sendNotification( notificationName, body, type );
			super.sendNotification( CMD_CHANGE );
		}

		override protected function initializeController( ) : void 
		{
			super.initializeController( ); 
			registerCommand( DesignCanvasCT.CMD_CHANGE_BACKGROUND, ChangeBackgroundCommand );
			registerCommand( DesignCanvasCT.CMD_ADD_MEDIAOBJECT, AddMediaObjectCommand );			registerCommand( DesignCanvasCT.CMD_MATRIX_CHANGE, MatrixChangeCommand );			registerCommand( DesignCanvasCT.CMD_SETSELECTION, SetSelectionCommand );
			registerCommand( DesignCanvasCT.CMD_ADD_MASK, AddMaskCommand );
			registerCommand( DesignCanvasCT.CMD_CHANGE_CANVASWH, ChangeCanvasWTCommand );
			
			registerCommand( MediaObjectCT.CMD_COLORSTYLE_ENABLE, ColorEnableCommand );
			registerCommand( MediaObjectCT.CMD_COLORSTYLE_BRIGHT, ColorBrightCommand );
			registerCommand( MediaObjectCT.CMD_COLORSTYLE_SATURA, ColorSaturaCommand );
			registerCommand( MediaObjectCT.CMD_ALPHA_CHANGE, AlphaChangeCommand );
			registerCommand( MediaObjectCT.CMD_SET_BLEND, BlendChangeCommand );
			
			registerCommand( LayerManagerCT.CMD_REMOVE_MEDIAOBJECT, RemoveMediaObjectCommand );
			registerCommand( LayerManagerCT.CMD_REMOVEALL_MEDIAOBJECT, RemoveAllMediaObjectCommand );
			
			registerCommand( LayerManagerCT.CMD_SWITCH_UP, UpMediaObjectCommand );
			registerCommand( LayerManagerCT.CMD_SWITCH_DOWN, DownMediaObjectCommand );
			
			
		}

		public static function getInstance() : ApplicationFacade 
		{
			if ( instance == null ) instance = new ApplicationFacade( );
			return instance as ApplicationFacade;
		}
	}
}

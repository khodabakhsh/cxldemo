package org.asblog.utils
{
	import mx.managers.PopUpManager;
	
	import org.asblog.frameworks.view.DesignCanvasMediator;

	public class PopUpUtils
	{
		public static function addPop(classRef:Class):*
		{
			return PopUpManager.createPopUp(DesignCanvasMediator.designCanvas, classRef, true);
		}
	}
}
package org.puremvc.as3.patterns.mediator 
{
	/**
	 * @author Xucan
	 */
	public class BaseMediator extends Mediator
	{
		public static const NAME:String = "BaseMediator";
		private var _name:String;
		
		public function BaseMediator(name:String, viewComponent:Object = null)
		{
			super( name,viewComponent );
			_name = mediatorName;
		}

		/**
		 * 引用_name, 我们不必每次都不得不复写一次getMediatorName.
		 */
		override public function getMediatorName():String 
		{    
			return _name;
		}
	}
}

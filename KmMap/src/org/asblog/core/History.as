package org.asblog.core
{
	public class History
	{
		public var uid:String;
		public var oldItem:*;
		public var newItem:*
		private var fun:Function;
		public function callBack(arg:*):void
		{
			if(fun!=null)	fun(arg);
		}
		public function History(oldItem:*,newItem:*,uid:String = null,fun:Function = null)
		{
			this.oldItem = oldItem;	
			this.newItem = newItem;	
			this.uid = uid;
			this.fun = fun;
		}
	}
}
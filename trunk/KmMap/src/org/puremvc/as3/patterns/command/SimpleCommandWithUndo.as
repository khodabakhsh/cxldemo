package org.puremvc.as3.patterns.command 
{
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.interfaces.IUndoCommand;	

	/**
	 * @author Halley
	 */
	public class SimpleCommandWithUndo extends SimpleCommand implements IUndoCommand 
	{
		private var _isDone : Boolean;
		//默认都可以重做，都有些不必要，比如连续发了2次选中舞台的命令
		private var _undoAble:Boolean = true;

		public function get undoAble():Boolean
		{
			return _undoAble;
		}

		public function set undoAble(value:Boolean):void
		{
			_undoAble = value;
		}

		public function undo(note : INotification) : void
		{
		}

		public function get isDone() : Boolean
		{
			return _isDone;
		}

		public function set isDone(v : Boolean) : void
		{
			_isDone = v;
		}
	}
}

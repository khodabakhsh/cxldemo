package org.puremvc.as3.interfaces 
{

	/**
	 * @author Halley
	 */
	public interface IUndoCommand extends ICommand
	{
		/**
		 * 是否重做了
		 */
		function get isDone() : Boolean
		function set isDone(v : Boolean) : void
		/**
		 * 是否可以重做 
		 */		
		function get undoAble() : Boolean
		
		function set undoAble(v : Boolean) : void
		
		function undo(note : INotification) : void
	}
}

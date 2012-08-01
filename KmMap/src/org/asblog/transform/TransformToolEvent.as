package org.asblog.transform 
{
	import flash.events.Event;
	import flash.geom.Matrix;		

	/**
	 * @author Halley
	 */
	public class TransformToolEvent extends Event 
	{
		/**
		 * 被选中的uid(如果没选就是空)
		 */
		public var selectedUid : String;
		/**
		 * 上次被选的uid(如果没选就是空)
		 */		public var oldSelectedUid : String;
		/**
		 * 相关目标的uid
		 */
		public var uid : String;
		/**
		 * 旧的矩阵值
		 */
		public var oldMatrix : Matrix;
		/**
		 * 新的(当前)矩阵值
		 */		public var newMatrix : Matrix;
		/**
		 * 目标矩阵变更
		 */
		public static const TARGET_MATRIX_CHANGE : String = "targetMatrixChange";
		/**
		 * 选择的目标改变时触发(鼠标点击了桌布或者选择，取消选择时)
		 */
		public static const SETSELECTION : String = "setselection";

		public function TransformToolEvent(type : String,uid : String = null,oldMatrix : Matrix = null,newMatrix : Matrix = null)
		{
			super( type, false, false );
			this.uid = uid;			this.oldMatrix = oldMatrix;			this.newMatrix = newMatrix;
		}
	}
}

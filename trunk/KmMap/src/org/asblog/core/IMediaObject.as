package org.asblog.core
{
	import mx.core.IChildList;
	import mx.core.IUIComponent;
	import mx.core.IUID;

	/**
	 * 所有在画布里的可视元素必须实现此接口 
	 * @author Administrator
	 */
	[Bindable]

	public interface IMediaObject extends IUIComponent,IRelatedObject,ICloneable,IUID,IChildList
	{
		/**
		 * 资源链接
		 */		
		function get mediaLink():MediaLink
		function set mediaLink(v:MediaLink):void
		/**
		 * 是否初始化完成（或加载完成）
		 */
		function get isComplete() : Boolean

		function set isComplete(v : Boolean) : void 

		/**
		 * 是否可被选择
		 * @return 
		 */		  
		function get selectEnabled() : Boolean

		function set selectEnabled(v : Boolean) : void

		/**
		 * 是否已被选择
		 * @return 
		 */		  
		function get selected() : Boolean

		function set selected(v : Boolean) : void

		/**
		 * 设置深度,此深度并非实际的深度
		 * @return 
		 */
		//function get depthAtParent() : uint

		//function set depthAtParent(v : uint) : void

		/**
		 * 是否被锁定 
		 */
		function get isLock() : Boolean

		function set isLock(v : Boolean) : void

		/**
		 * 对遮罩对象的引用,检测是否有遮照或已经被设置了遮罩
		 * @return 
		 */          
		function get maskObject() : MediaMask

		function set maskObject(v : MediaMask) : void
		/**
		 * 删除遮罩
		 */ 
		function removeMask():void

		/**
		 * 亮度
		 */
		function get brightness() : int

		function set brightness(v : int) : void

		/**
		 * 饱和度
		 */
		function get saturation() : int

		function set saturation(v : int) : void

		/**
		 * 是否开启色彩式样(亮度,对比度,饱和度,色相,等)
		 */
		function get colorStyleEnable() : Boolean

		function set colorStyleEnable(v : Boolean) : void
		/**
		 * 当前色彩式样
		 */
		function get colorStyle() : String
		
		function set colorStyle(v : String) : void
		/**
		 * 释放内存
		 */		
		function dispose():void
		/**
		 * 清空当前STYLE
		 */ 
		function cleanStyle():void
	}
}
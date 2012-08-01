package org.asblog.utils
{
	import org.asblog.core.MediaLink;
	
	import flash.utils.Dictionary;
	/**
	 * 全局缓存 
	 * @author Halley
	 */	
	public class CacheUtil
	{
		/**
		 * 所有当前元素的Link 
		 */		
		public static var allCacheMediaLinks:Dictionary = new Dictionary( true );
		/**
		 * 被删除的LINK集合
		 */		
		public static var removedMediaObjectLinks:Vector.<MediaLink> 
	}
}
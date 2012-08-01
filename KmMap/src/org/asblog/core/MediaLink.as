package org.asblog.core
{
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;

	public class MediaLink implements ICloneable
	{
		public var classRef:Class;
		public var uid:String;
		public var x:Number;
		public var y:Number;
		public var isBackground:Boolean;
		public var isAdjuestImage:Boolean;
		public var isMask:Boolean;
		public var maskedItemUid:String;
		public var source:*;
		public var depth:uint = uint.MAX_VALUE;
		/**
		 * 对自身类型的引用
		 */		
		protected var MediaLinkClass : Class = getDefinitionByName( getQualifiedClassName( this ) ) as Class;
		public function clone():*
		{
			var newLink:MediaLink = new MediaLinkClass();
			newLink.classRef = classRef;
			newLink.uid = uid;
			newLink.x = x;
			newLink.y = y;
			newLink.isBackground = isBackground;
			newLink.isAdjuestImage = isAdjuestImage;
			newLink.isMask = isMask;
			newLink.maskedItemUid = maskedItemUid;
			newLink.source = source;
			newLink.depth = depth;
			return newLink;
		}
	}
}
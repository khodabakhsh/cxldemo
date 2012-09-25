package org.asblog.core
{
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;
	
	import mx.utils.UIDUtil;

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
		
		public var rotation:Number=0;
		public var rotationX:Number=0;
		public var rotationY:Number=0;
		public var rotationZ:Number=0;
		
		//关联
		public var obj_id:String = UIDUtil.createUID().replace(/-/g,"");
		public var rk:String="";
		public var rm:String="";
		public var rq:String="";
		
		//MediaImage的width和height
		public var MediaImageWidth:Number;
		public var MediaImageHeight:Number;
		
		
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
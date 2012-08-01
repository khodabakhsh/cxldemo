package org.asblog.core
{
	public class TextLink extends MediaLink
	{
		public var text:String;
		public function TextLink()
		{
			super();
		}
		override public function clone() : * 
		{
			var link : TextLink = TextLink( super.clone( ) );
			link.text = text;
			return link;
		}
	}
}
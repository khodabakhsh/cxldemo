package org.asblog.core
{
	import org.asblog.mxml.IMXML;
	
	/**
	 * 所有媒体集合的基类
	 * @author Administrator
	 * 
	 */
	public class MediaObjectContainer extends MediaObject implements IMXML,IMediaObjectContainer
	{
		public function MediaObjectContainer()
		{
			super();
		}
	}
}
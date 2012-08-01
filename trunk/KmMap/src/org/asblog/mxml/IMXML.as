package org.asblog.mxml
{
	import org.asblog.core.IMediaObjectContainer;
	import org.asblog.core.MXML;

	/**
	 * 
	 * @author xucan
	 * 提供生成与解释MXML的功能
	 * 
	 */	
	public interface IMXML
	{
		function parseMXML(mxml:MXML):void
		function createMXML(container:IMediaObjectContainer = null):MXML
	}
}
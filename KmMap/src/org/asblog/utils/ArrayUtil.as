package org.asblog.utils 
{

	/**
	 * @author Xucan
	 */
	public class ArrayUtil 
	{
		/**
		 * 从数组里删除一个对象
		 */
		public static function removeFrom(source : Array,obj : Object) : void
		{
			try
			{
				source.splice( source.lastIndexOf( obj ), 1 );
			}
			catch(e : Error)
			{
				trace( e.getStackTrace( ) );
			}
		}

		/**
		 * 有一个值
		 */
		public static function hasOneValue(source : Array) : Boolean
		{
			if(source == null)	   return false;
			if(source.length == 1) return true;
			else                   return false;
		}

		/**
		 * 有值
		 */
		public static function hasValue(source : Array) : Boolean
		{
			if(source != null && source.length > 1)	return true;
			else                                    return false;
		}
	}
}

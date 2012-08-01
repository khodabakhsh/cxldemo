package org.asblog.utils 
{
	import flash.net.ObjectEncoding;
	import flash.net.registerClassAlias;
	import flash.utils.ByteArray;
	import flash.utils.describeType;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;

	public final class ObjectTranslator
	{
		public static function clone(source:Object):* 
		{
			//获取全名
			var typeName:String = getQualifiedClassName( source );
			//切出包名
			var packageName:String = typeName.split( "::" )[1];
			//获取Class
			var type:Class = Class( getDefinitionByName( typeName ) );

			if(packageName == null)packageName = typeName;
			//注册Class
			registerClassAlias( packageName, type );
			
			return cloneByteArray( source );
		}

		public static function cloneByteArray(source:Object):*
		{
			//复制对象
			var copier:ByteArray = new ByteArray( );
			copier.objectEncoding = ObjectEncoding.AMF3;
			copier.writeObject( source );
			copier.position = 0;
			return copier.readObject( );
		}

		public static function xml2obj(x:XML, o:Object):void
		{
			var i:int = 0;
			var nodes:XMLList = x.children( );
			var nodeName:String;
			
			for each(var node:XML in nodes)
			{ 
				nodeName = node.name( ).toString( );
				
				// Handle Object
				if (nodeName == "obj")
				{
					var objName:String = node.@o;
					var objType:String = node.@t;
					
					// Create nested array
					if (objType == "a")
						o[objName] = [];
					
					// Create nested object
					else if (objType == "o")
						o[objName] = {};
						
					xml2obj( node, o[objName] );
				}
				
				// Handle Array
				else if (nodeName == "var")
				{
					var varName:String = node.@n;
					var varType:String = node.@t;
					var varVal:String = node.toString( );
					
					// Cast variable to its original type
					if (varType == "b")
						o[varName] = (varVal == "0" ? false : true);
							
					else if (varType == "n")
						o[varName] = Number( varVal );
						
					else if (varType == "s")
						o[varName] = varVal; // No need of Entities.decodeEntities()
						
					else if (varType == "x")
						o[varName] = null;
				}
			}	
		}

		public static function objectToInstance( object:Object, clazz:Class ):*
		{
			if(object == null)	return null;
			var bytes:ByteArray = new ByteArray( );
			bytes.objectEncoding = ObjectEncoding.AMF0;

			var objBytes:ByteArray = new ByteArray( );
			objBytes.objectEncoding = ObjectEncoding.AMF0;
			objBytes.writeObject( object );
                
			var typeInfo:XML = describeType( clazz );
			var fullyQualifiedName:String = typeInfo.@name.toString( ).replace( /::/, "." );
			registerClassAlias( fullyQualifiedName, clazz );
		
			// 0x10 是AMF0类型信息
			bytes.writeByte( 0x10 ); 
			 
			
			bytes.writeUTF( fullyQualifiedName );
			

			bytes.writeBytes( objBytes, 1 );
        

			bytes.position = 0;

			var result:* = bytes.readObject( );
			return result;
		}
	} 
}
 


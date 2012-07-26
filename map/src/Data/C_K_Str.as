package Data
{
	public class C_K_Str implements IComparable{
	    private var strMyKey:String; 
	
	    public function C_K_Str(key:String) 
	    { 
	        strMyKey = key; 
	    } 
	    
	    public function getStr():String{
	    	return strMyKey;
	    }
	
	    public function compareTo(key:IComparable):int 
	    { 
	    	
	        var intReturn:int=0;
	        var pkey:*=key;
	        var mKey:String=pkey.strMyKey;//(C_K_Str)
	        if (strMyKey!=null && mKey!=null){
	           //intReturn = strMyKey.compareToIgnoreCase(mKey);
	           if (strMyKey>mKey){
	           		return 1;
	           }else if (strMyKey<mKey){
	           		return -1;
	           }else{
	           		return 0;
	           }
	        }else{
	        	if (strMyKey==null){
	        		return -1;
	        	}else{
	        		return 1;
	        	}
	        }
	        return intReturn;
	    } 
	}
}
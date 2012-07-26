package Data
{
	public class C_K_ID implements IComparable{
		private var dbKey:int; 
	
		public function C_K_ID(Key:int) {
			dbKey = Key; 
		}
		
		public function compareTo(key:IComparable):int 
		{ 
		    
		    if (dbKey > C_K_ID(key).dbKey){ 
		        return 1; 
		    }else if (dbKey < C_K_ID(key).dbKey){ 
		        return -1; 
		    } 
		    else { 
		        return 0; 
		    } 
		}
	}
}
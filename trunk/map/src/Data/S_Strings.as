package Data
{
	public class S_Strings
	{
		public function S_Strings()
		{
		}
		
		
		public static function Str_ReplacePredeal(strWord:String):String
		{
		    strWord = strWord.replace(/：/gi, ":");
		    strWord = strWord.replace(/）/gi, ":");
		    strWord = strWord.replace(/（/gi, ":");
		    strWord = strWord.replace(/？/gi, ":");
		    strWord = strWord.replace(/，/gi, ":");
		    strWord = strWord.replace(/！/gi, ":");
		    strWord = strWord.toLocaleLowerCase();
		    
		    return strWord;
		}

		public static function ReplaceFirst(strWord:String):String
		{
			strWord = strWord.replace(/？/gi, "?");
			strWord = strWord.replace(/?/gi, " ? ");
			strWord = strWord.replace("!", " ! ");
			strWord = strWord.replace(",", " , ");
			strWord = strWord.replace("~", " ~ ");
			strWord = strWord.replace("。", ".");
			strWord = strWord.replace("'s", " 's");
			strWord = strWord.replace("'re", " 're");
			strWord = strWord.replace("I'm", "I 'm");
            strWord = strWord.replace("can't", "can 't");
            strWord = strWord.replace("isn't", "is not");
            strWord = strWord.replace("I'd", "I 'd");
            strWord = strWord.replace("wouldn't", "would 't");
            strWord = strWord.replace("\r\n", " ");
            
            return strWord;
  		}
  		
  		public static function ReplaceNLP(strPath:String):String
  		{

            strPath = strPath.replace(".", "C.dian");

            strPath = strPath.replace("{", "C.zuodakuohu");
            strPath = strPath.replace("}", "C.youdakuohu");

            strPath = strPath.replace("?", "C.wen");
            strPath = strPath.replace("%", "C.baifen");
            strPath = strPath.replace(":", "C.mao");
            strPath = strPath.replace("@", "C.at");

            strPath = strPath.replace("\\", "C.na");
            strPath = strPath.replace("/", "C.pie");
            strPath = strPath.replace("*", "C.xing");
            strPath = strPath.replace("\"", "C.yin");

            strPath = strPath.replace("<", "C.xiaoyu");
            strPath = strPath.replace(">", "C.dayu");
            strPath = strPath.replace("|", "C.shu");
            strPath = strPath.replace(",", "C.dou");

            strPath = strPath.replace("-", "C.jian");
            strPath = strPath.replace("=", "C.deng");
            strPath = strPath.replace("&", "C.and");
            strPath = strPath.replace(";", "C.fenhao");
            strPath = strPath.replace("#", "C.sharp");

            return strPath;
        }
        
		/// <summary>
		/// 在汉字前面后面加空格,以及在数字符号前后加空格
		/// </summary>
		public static function Blank_Chinese(strContext:String):String
		{
		    var strReturn:String = "";
		    
		    var mStr:String = "";
		    
		    //去掉两个空格，单个空格不能去掉，因为有english
		    while (strContext.indexOf("\n")>-1 ||
		    		strContext.indexOf("\r")>-1 ||
		    		strContext.indexOf("\t")>-1 ) {
		        strContext = strContext.replace("[\\r|\\n\\t]{1,10}", " ");
		    }
		    
		    while (strContext.indexOf("  ") > -1){
		        strContext = strContext.replace("( ){2,10}", " ");
		    }
		    
		    strContext = S_Strings.Str_ReplacePredeal(strContext);
		    strContext = ReplaceFirst(strContext);
		    
		    var i:int=0;
		    
		    for (i = 0; i <strContext.length; i++){
		        mStr = strContext.substr(i, 1);
		        
				switch (mStr) {
		            case "+":
		            case "-":
		            case "*":
		            case "\\":
		            case "^":
		            case "(":
		            case ")":
		            case ",":
		            case "/":
		            case "=":
		            case ":":
		            case "&":
		            case "#":
		                strReturn+=" {" + ReplaceNLP(mStr) + "} ";
		                break;
	            case "\"":
	            case "'":
	            case "?":
	            case ".":
	            case "@":
	            case "%":
     	       	case "<":
     	       	case ">":
     	       	case "{":
     	       	case "}":
     	       	case ";":
     	           	strReturn+=" {" + ReplaceNLP(mStr) + "} ";
     	           	break;
     	       	case String.fromCharCode(12288):
     	           strReturn+=" ";
     	           break;
     	       	case "â":
     	       	case "Â":
     	       	case "$":
     	       	case "[":
     	       	case "]":
     	           strReturn+=" " + mStr + " ";
     	           break;
     	       	default:
     	           if (mStr.charCodeAt(0) >= 0 && mStr.charCodeAt(0) <= 255){
     	               strReturn+=mStr;
     	           }
     	           else {
     	               strReturn+=" " + mStr + " ";
     	           }
  	   	           break;
				}
		    }	
		    		
		    var CharSplit:Array =new Array();
		    CharSplit[0]="((\\d|\\.)+)([a-zA-z]+)===1,3";
		    CharSplit[1]="([a-zA-z]+)((\\d|\\.)+)===1,2";
		    CharSplit[2]="([a-zA-z]+)((\\d|\\.)+)([a-zA-z]+)===1,2,4";
		    CharSplit[3]="((\\d|\\.)+)([a-zA-z]+)((\\d|\\.)+)===1,3,4";
		    CharSplit[4]="((\\d|\\.)+)([a-zA-z]+)((\\d|\\.)+)([a-zA-z]+)===1,3,4,6";
		    CharSplit[5]="([a-zA-z]+)((\\d|\\.)+)([a-zA-z]+)((\\d|\\.)+)===1,2,4,5";
	
	
		    var strSplit:Array = strReturn.split(" ");
		    var strConvert:Array = null;
		    var index:int=0;
		    var iCount:int = 0;
	
			var pattern:RegExp;
			var strURL:String="";
		
			strReturn ="";
		    for (i = 0; i <strSplit.length; i++) {
		     	iCount = 0;
				for (var j:int = 0; j <= CharSplit.length - 1; j++) {
	     			strConvert = CharSplit[j].split("===");
	     	    	if (strConvert.length > 1) {
	     	    		pattern =new RegExp(strConvert[0],"gi");
						var pMatchs:Array=strSplit[i].match(pattern);
	     	        	//pMatch = Regex.Match(strSplit(i), strConvert(0));
	     	           
	     	           	if (pMatchs.length>0 && pMatchs[0] == strSplit[i]) {
	     	               iCount += 1;
	     	               strConvert = strConvert[1].Split(",");
	     	               for (var k:int = 0; k <strConvert.length; k++) {
	     	                   if (parseInt(strConvert[k])+""==strConvert[k]) {
	     	                       index = parseInt(strConvert[k]);
	     	                       if (index < pMatchs.length) {
	     	                           strReturn+=pMatchs[index] + " ";
	     	                       }
	     	                   }
	     	                   else {
	     	                       strReturn+=strConvert[k] + " ";
	     	                   }
	     	               }
	     	           }
	     	       }
	     	   }
	     	   
	     	   //只允许一次
	     	   if (iCount == 0) {
	     	       if (strSplit[i]!="") {
	     	           strReturn+=strSplit[i] + " ";
	     	       }
	     	   }
		    }	
		    
		    return strReturn;// .ToString.Trim;
		}

	}
}
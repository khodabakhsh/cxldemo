package Data
{
	public class C_Word_Convert
	{


        public var Word:String;//'单词
        public var OutPutWord:String;
        //分词后的单词，比如 Word=发展中国, OutPutWord=发展 中国
        //如果为空，则无分词
        
		public function C_Word_Convert(strLine:String)
		{
			var Pos:int=strLine.indexOf("=");
            
            if (Pos > 0){
                this.Word = strLine.substr(0,Pos);
                this.OutPutWord = strLine.substr(Pos+1);
            } else {
                this.Word = strLine
            }
		}
	}
}
package tool.log.log4jMail;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
/**
 * 自定义layout,可以支持中文
 *
 */
public class MailEvaluator extends Layout{
	
	StringBuffer sbuf;
	@Override  
    public String getContentType() 
	{   
        return "text/html;charset=UTF-8";
	}
	public MailEvaluator() {
		sbuf = new StringBuffer(128);
	}
	@Override
	public String format(LoggingEvent event) 
	{
		sbuf.setLength(0);
        sbuf.append("错误等级:����ȼ���"+event.getLevel().toString()+"<br/>");
        sbuf.append("错误原因:"+event.getMessage().toString()+"<br/>");
        sbuf.append("����������错误所在类:"+event.getLocationInformation().getClassName()+"<br/>");
        sbuf.append("错误所在方法："+event.getLocationInformation().getMethodName()+"<br/>");
        sbuf.append("错误行������:"+event.getLocationInformation().getLineNumber());
        return sbuf.toString();
	}
	@Override
	public boolean ignoresThrowable() {
		// TODO Auto-generated method stub
		return false;
	}
	public void activateOptions() {
		// TODO Auto-generated method stub
		
	}
}

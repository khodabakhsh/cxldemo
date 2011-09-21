package cn.jcenterhome.web.servlet;
import javax.servlet.http.HttpServletRequest;
import cn.jcenterhome.util.Common;
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {
	public HttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	@Override
	public String getParameter(String name) {
		String value=super.getParameter(name);
		if(value!=null){
			value=Common.addSlashes(value.trim());
			if("get".equalsIgnoreCase(super.getMethod())){
				value=Common.htmlSpecialChars(value);
			}
		}
		return value;
	}
	@Override
	public String[] getParameterValues(String name) {
		String[] values=super.getParameterValues(name);
		if(values!=null){
			for (int i = 0; i < values.length; i++) {
				String value=values[i];
				if(value!=null){
					value=Common.addSlashes(value);
					if("get".equalsIgnoreCase(super.getMethod())){
						value=Common.htmlSpecialChars(value);
					}
					values[i]=value;
				}
			}
		}
		return values;
	}
}

package simpleThreadLocal;

import java.util.HashMap;

public class RequestContext {

	private static ThreadLocal requestContext = new RequestContextThreadLocal();

	private static class RequestContextThreadLocal extends ThreadLocal {
                  //设定此线程局部变量的当前线程的初始值
		protected Object initialValue() {
			return new RequestContext(new HashMap());
		}
	}
	
	public RequestContext(HashMap hashMap) {
		// TODO Auto-generated constructor stub
	}


	public static RequestContext getContext() {
		//返回此线程局部变量的当前线程副本中的值
		RequestContext context = (RequestContext) requestContext.get();
		
		if (context == null) {
			context = new RequestContext(new HashMap());
			setContext(context);
		}
		
		return context;
	}
	

	public static void setContext(RequestContext context) {
	      //将此线程局部变量的当前线程副本中的值设置为指定值
               requestContext.set(context);
	}
}
package com.seek.server.http;

import java.lang.reflect.Method;

public class RequestTransform {

	private final String ACTION_SIGN = "action";
	private final String HANDLER_SIGN = "handler";
	
	public void transfer(SocketRequest request,SocketResponse response) {
		String action = request.getValue(ACTION_SIGN);
		String handlerName = request.getValue(HANDLER_SIGN);
		//根据Session的请求类型，让不同的类方法去处理
		try {
			Class<?> c= Class.forName("com.seek.server.handler." + handlerName);
			Class<?>[] arg=new Class[]{SocketRequest.class,SocketResponse.class};
			Method method=c.getMethod(action,arg);
			method.invoke(c.newInstance(), new Object[]{request,response});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

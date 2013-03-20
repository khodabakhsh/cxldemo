package com.seek.server.handler;

import com.seek.server.http.SocketRequest;
import com.seek.server.http.SocketResponse;

public class UserHandler {

	public void login(SocketRequest request,SocketResponse response) {
		//TODO: 处理用户登录
		response.write("你肯定收到消息了");
	}
	public void getUserList(SocketRequest request,SocketResponse response) {
		//TODO: 
	}
}

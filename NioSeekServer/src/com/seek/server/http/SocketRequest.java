package com.seek.server.http;

import com.seek.server.JsonParser;
import com.seek.server.session.Session;

public class SocketRequest {

	private Session mSession;
	private String  mReceive;
	
	public SocketRequest(Session session) {
		mSession = session;
		mReceive = session.getReceiveData();
		mSession.clear();
	}
	
	public String getValue(String key) {
		return JsonParser.get(mReceive, key);
	}
	
	public String getQueryString() {
		return mReceive;
	}
}

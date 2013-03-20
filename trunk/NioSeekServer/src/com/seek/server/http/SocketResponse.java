package com.seek.server.http;


import com.seek.server.session.Session;
import com.seek.server.session.SessionManager;

public class SocketResponse {

	private Session mSession;
	public SocketResponse(Session session) {
		mSession = session;
	}
	
	public void write(String msg) {
		try {
			mSession.setSendData(msg);
		} catch (Exception e) {
			SessionManager.remove(mSession.getKey());
		}
	}
}

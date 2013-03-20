package com.seek.server.handler;

import java.util.Iterator;
import java.util.Map.Entry;

import com.seek.server.Packet;
import com.seek.server.http.SocketRequest;
import com.seek.server.http.SocketResponse;
import com.seek.server.policy.ServerConfig;
import com.seek.server.session.Session;
import com.seek.server.session.SessionManager;

public class MessageHandler {
	
	private MessageService mService = new MessageService();
	
	public void send(SocketRequest request,SocketResponse response) {
		System.out.println(request.getQueryString());
		//ÏûÏ¢·¢ËÍ
		String to = request.getValue("to");
		String me = request.getValue(ServerConfig.SESSION_KEY);
		String msg = request.getValue("data");
		Packet packet = new Packet(Packet.STATE_SUCCESS);
		packet.addResult(Packet.KEY_RESPONSE_CONTENT, msg);
		if (to == null) {
			return;
		}
		if(to.equals("all")) {
			mService.sendMsgToAll(me, packet);
		} else {
			mService.sendMsg(to, packet);
		}
	}
	class MessageService {
		public void sendMsgToAll(String me,Packet packet) {
			String msg = packet.createResponseMsg();
			SocketResponse tmpReq = null;
			Iterator<Entry<String, Session>> iter = SessionManager.getSessionsEntry().iterator();
			Entry<String, Session> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				if(!tmpEntry.getKey().equals(me)) {
					tmpReq = new SocketResponse(tmpEntry.getValue());
					tmpReq.write(msg);
				}
			}
		}
		
		public void sendMsg(String to,Packet packet) {
			Session session = SessionManager.getSession(to);
			if(session != null) {
				new SocketResponse(session).write(packet.createResponseMsg());
			}
		}
	}
}

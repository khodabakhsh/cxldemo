package com.seek.server.check;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.seek.server.Log;
import com.seek.server.http.SocketResponse;
import com.seek.server.policy.ServerConfig;
import com.seek.server.session.Session;
import com.seek.server.session.SessionManager;
/**
 * 定时发送心跳包并清除过期会话
 * @author Lucifer
 *
 */
public class Task {
	public void checkState() {
		Set<String> keys = SessionManager.getSessionKeys();
		if(keys.size() == 0) {
			return;
		}
		List<String> removes = new ArrayList<String>();
		Iterator<String> iterator = keys.iterator();
		String key = null;
		while(iterator.hasNext()) {
			key = iterator.next();
			if(!SessionManager.getSession(key).isKeekAlive()) {
				removes.add(key);
			}
		}
		if(removes.size() > 0  ) {
			Log.i("sessions is time out,remove " + removes.size() + "session");
		}
		SessionManager.remove(removes.toArray(new String[removes.size()]));
	}
	
	public void sendAck() throws Exception {
		Iterator<Entry<String, Session>> iter = SessionManager.getSessionsEntry().iterator();
		Entry<String, Session> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			tmpEntry.getValue().setSendData(ServerConfig.getHeartBeat(tmpEntry.getKey()));
		}
	}
}

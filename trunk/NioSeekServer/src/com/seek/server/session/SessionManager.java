package com.seek.server.session;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.seek.server.Log;
public class SessionManager {

	private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();
	
	public static void addSession(String key,Session session) {
		sessions.put(key, session);
		Log.i(key + " is connected! Current Session count is " + getSessionCount());
	}
	
	public static Session getSession(String key) {
		return sessions.get(key);
	}
	
	public static Set<String> getSessionKeys() {
		return sessions.keySet();
	}
	
	public static Set<Entry<String, Session>> getSessionsEntry(){
		return sessions.entrySet();
	}
	
	
	public static int getSessionCount() {
		return sessions.size();
	}
	
	public static boolean hasSession(String key) {
		return sessions.containsKey(key);
	}
	
	public static List<String> getAllSessionKey() {
		List<String> list = new ArrayList<String>();
		list.addAll(sessions.keySet());
		return list;
	}
	
	public static void remove(String[] keys) {
		for(String key:keys) {
			if(sessions.containsKey(key)) {
				sessions.get(key).distroy();
				sessions.remove(key);
			}
		}
	}
	public static void remove(String key) {
		if(sessions.containsKey(key)) {
			sessions.get(key).distroy();
			sessions.remove(key);
			Log.i("Session:" + key + " is offline! Current Session count is " + getSessionCount());
		}
	}
}

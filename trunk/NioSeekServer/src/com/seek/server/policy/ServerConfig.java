package com.seek.server.policy;

public class ServerConfig {
	public static final String SESSION_KEY = "imei";
	
	public static String getHeartBeat(String key) {
		return "{\"" + SESSION_KEY + "\":\"" + key + "\",\"type\":\"heartbeat\"}";
	}
}

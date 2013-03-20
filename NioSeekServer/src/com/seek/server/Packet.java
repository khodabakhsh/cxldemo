package com.seek.server;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Packet {
	public static final int STATE_SUCCESS = 1, STATE_FAIL = 0;
	public static final String KEY_RESPONSE_CONTENT = "content";
	
	private Map<String, String> mResult = new HashMap<String, String>();
	
	public Packet(int state) {
		mResult.put("type", "response");
	}
	
	public void addResult(String key,Object value) {
		mResult.put(key, value.toString());
	}
	
	public String createResponseMsg() {
		if(!mResult.containsKey(KEY_RESPONSE_CONTENT)) {
			mResult.put(KEY_RESPONSE_CONTENT, "null");
		}
		return JSON.toJSONString(mResult);
	}
}

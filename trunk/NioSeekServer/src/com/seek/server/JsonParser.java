package com.seek.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonParser {
	
	private static JSONObject mJson;
	
	public synchronized static String get(String json,String key) {
		mJson = JSON.parseObject(json);
		return mJson.getString(key);
	}
}

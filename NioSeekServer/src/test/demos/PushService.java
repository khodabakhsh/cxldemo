package test.demos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.seek.server.Log;


public class PushService {
	private boolean openDaemonThread = false;
	private MessageArrivalCallBack mCallBack = null;
	private Reader mReader = null;
	private DaemonThread mDaemon = null;
 	
	public PushService(String hostname, int port, String key) {
		mReader = new Reader(hostname,port,key);
	}
	
	public PushService(String hostname, int port, String key, boolean openDaemonThread) {
		this(hostname, port,key);
		this.openDaemonThread = openDaemonThread;
	}
	
	public void setMessageArrivalCallBack(MessageArrivalCallBack callBack) {
		mCallBack = callBack;
	}
	
	public void start() {
		mReader.start();
		if(openDaemonThread) {
			mDaemon = new DaemonThread();
			mDaemon.start();
		}
	}
	
	class Reader extends Thread {
		private JSONObject mJson = null;
		private Socket mSocket = null;
		private BufferedReader mReader = null;
		private BufferedWriter mWriter = null;
		private String CONNECT_SIGN = "{\"imei\":\"%s\",\"type\":\"connect\"}";
		private String HEART_BEAT   = "{\"imei\":\"%s\",\"type\":\"heartbeat\"}";
		public Reader(String host, int port, String key) {
			CONNECT_SIGN = String.format(CONNECT_SIGN, key);
			HEART_BEAT = String.format(HEART_BEAT, key);
			try {
				mSocket = new Socket(InetAddress.getByName(host), port);
				mReader = new BufferedReader(new InputStreamReader(
						mSocket.getInputStream()));
				mWriter = new BufferedWriter(new OutputStreamWriter(
						mSocket.getOutputStream()));
				write(CONNECT_SIGN);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			String read = null;
			try {
				while ((read = mReader.readLine()) != null) {
					process(read);
				}
				mWriter.close();
				mReader.close();
				mSocket.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private void write(String msg) throws IOException {
			mWriter.write(msg);
			mWriter.flush();
		}
		
		private void process(String msg) throws JSONException, IOException {
			mJson =JSON.parseObject(msg);
			String type = mJson.getString("type");
			if(type.equalsIgnoreCase("heartbeat")) {
				Log.i("==============>>received heartbeat info");
				write(HEART_BEAT);
				Log.i("==============>>send heartbeat info:" + HEART_BEAT);
			}else{
				mCallBack.arrivaled(mJson.getString("content"));
			}
		}
	}
	
	class DaemonThread extends Thread {
		public void run() {
			
		}
	}
	
	interface MessageArrivalCallBack {
		void arrivaled (String msg) ;
	}
}

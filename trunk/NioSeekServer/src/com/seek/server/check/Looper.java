package com.seek.server.check;

import com.seek.server.Log;

public class Looper extends Thread{
	private static Looper looper = new Looper();
	private static boolean isStart = false;
	private final int INTERVAL = 1000 * 1 * 5;
	private Looper(){}
	public static Looper getInstance() {
		return looper;
	}
	
	public void loop() {
		if(!isStart) {
			isStart = true;
			this.start();
		}
	}
	
	public void run() {
		Task task = new Task();
		while(true) {
			//心跳包发送
			try {
				task.sendAck();
			} catch (Exception e1) {
				Log.e(e1);
			}
			//Session过期检测
			task.checkState();
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Log.e(e);
			}
		}
	}
}

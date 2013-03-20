package com.seek.server.session;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.seek.server.JsonParser;
import com.seek.server.Log;
import com.seek.server.http.RequestTransform;
import com.seek.server.http.SocketRequest;
import com.seek.server.http.SocketResponse;
/**
 * 会话请求分发处理
 * @author Lucifer
 *
 */
public class SessionProcessor implements Runnable{
	
	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 200, 500, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(10),new ThreadPoolExecutor.CallerRunsPolicy());
	public static void start() {
		new Thread(new SessionProcessor()).start();
	}
	
	@Override
	public void run() {
		while(true) {
			Session tmp = null;
			Iterator<Entry<String, Session>> iter = SessionManager.getSessionsEntry().iterator();
			Entry<String, Session> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				tmp = tmpEntry.getValue();
				//处理Session未处理的请求
				if(tmp.getReceiveData() != null) {
					pool.execute(new Process(tmp));
					if(tmp.isClose()) {
						SessionManager.remove(tmpEntry.getKey());
					}
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Log.e(e);
			}
		}
	}
	
	class Process implements Runnable {

		private SocketRequest request;
		private SocketResponse response;
		
		public Process(Session session) {
			//将Session封装成Request和Response
			request = new SocketRequest(session);
			response = new SocketResponse(session);
		}
		
		@Override
		public void run() {
			String type = request.getValue("type");
			if((type != null && type.equalsIgnoreCase("HEARTBEAT")) 
					||(type != null && type.equalsIgnoreCase("CONNECT"))) {
				return;
			}
			new RequestTransform().transfer(request, response);
		}
	}

}

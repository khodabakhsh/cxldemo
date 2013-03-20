package com.seek.server.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Calendar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seek.server.JsonParser;
import com.seek.server.Log;
import com.seek.server.policy.ServerConfig;

public class Session extends Acceptor{

	private SocketChannel mChannel;
	private SelectionKey  mKey;
    private ByteBuffer mRreceiveBuffer = ByteBuffer.allocate(10240);  
    private Charset charset = Charset.forName("UTF-8");
    private CharsetDecoder mDecoder = charset.newDecoder();
    private CharsetEncoder mEncoder = charset.newEncoder();
	private long lastPant;//最后活动时间
	private final int TIME_OUT = 1000 * 60 * 6; //心跳应答超时时间
	private String key;
	
	private String sendData = "";
	private String receiveData = null;
	private boolean isClose = false;
	
	public static final int READING = 0,SENDING = 1;
	int mState = READING;
	
	public Session(SocketChannel socket, Selector selector) throws IOException {
		this.mChannel = socket;
		mChannel = socket; 
		mChannel.configureBlocking(false);
		mKey = mChannel.register(selector, 0);
		mKey.attach(this);
		mKey.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
		lastPant = Calendar.getInstance().getTimeInMillis();
	}
	
	public synchronized String getReceiveData() {
		return receiveData;
	}
	
	public String getKey() {
		return key;
	}
	
	public void clear() {
		receiveData = null;
	}
	
	public boolean isClose() {
		return isClose;
	}

	public synchronized void setSendData(String sendData) throws Exception{
		mState = SENDING;
		mKey.interestOps(SelectionKey.OP_WRITE);
		this.sendData = sendData + "\n";
	}

	public boolean isKeekAlive() {
		return lastPant + TIME_OUT > Calendar.getInstance().getTimeInMillis();
	}
	
	public void setAlive() {
		lastPant = Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * 注销当前Session
	 */
	public void distroy() {
		try {
			mChannel.close();
			mKey.cancel();
		} catch (IOException e) {}
	}
	
	@Override
	public synchronized void exec() {
		try {
			if(mState == READING) {
				read();
			}else if(mState == SENDING) {
				write();
			}
		} catch (IOException e) {
			SessionManager.remove(key);
			try {
				mChannel.close();
			} catch (IOException e1) {
				Log.e(e1);
			}
			mKey.cancel();
		}
	}
	
	public synchronized void read() throws IOException{
		mRreceiveBuffer.clear();
		int sign = mChannel.read(mRreceiveBuffer);
		if(sign > 0) {
			mRreceiveBuffer.flip();
			receiveData = mDecoder.decode(mRreceiveBuffer).toString();
			setAlive();
			setSign();
			if(!SessionManager.hasSession(key)) {
				SessionManager.addSession(key, this);
			}
		}
		if(sign == -1) { //客户端连接关闭
			mChannel.close();
			mKey.cancel();
			isClose = true;
		}
	}
	
	private void setSign() {
		//设置当前Session的Key
		key = JsonParser.get(receiveData,ServerConfig.SESSION_KEY);
	}
	
	
	/**
	 * 写消息
	 */
	public void write() {
		try {
			mChannel.write(mEncoder.encode(CharBuffer.wrap(sendData)));
			sendData = null;
			mState = READING;
			mKey.interestOps(SelectionKey.OP_READ);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			try {
				mChannel.close();
			} catch (IOException e1) {
				Log.e(e1);
			}
		}
	}
}

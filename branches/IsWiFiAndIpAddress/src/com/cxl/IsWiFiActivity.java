package com.cxl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class IsWiFiActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView networkInfo = (TextView) findViewById(R.id.networkInfo);
		networkInfo.setText(getNetworkInfo());
		TextView IpAddress = (TextView) findViewById(R.id.IpAddress);
		IpAddress.setText("ip : " + getLocalIpAddress());

	}

	/**
	 * 获取联网状态
	 */
	private String getNetworkInfo() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cm.getActiveNetworkInfo();
		if (nInfo.isAvailable()) {//已经联网
			if (nInfo.getType() == ConnectivityManager.TYPE_WIFI) {//wifi上网
				return "通过wifi上网";
			}
			return "已经联网";
		}
		return "未连接网络";
	}

	/**
	 * 获取WIFI和G3卡的Ip地址
	 */
	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("IsWiFi", ex.toString());
		}
		return null;
	}
}
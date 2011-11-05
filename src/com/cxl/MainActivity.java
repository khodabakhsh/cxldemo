package com.cxl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cxl.psychology.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	Button btnXingge;
	Button btnLinglei;
	Button btnQinggan;
	Button btnChenggong;
	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 50;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分

	final Handler mHandler = new Handler();

	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				if (update_text) {
					pointsTextView.setText(displayText);
					update_text = false;
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		AppConnect.getInstance(this).getPoints(this);
		super.onResume();
	}

	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 * 
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {

		currentPointTotal = pointTotal;
		if (currentPointTotal >= requirePoint) {
			hasEnoughRequrePoint = true;
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
		mHandler.post(mUpdateResults);
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		currentPointTotal = 0;
		update_text = true;
		displayText = error;
		mHandler.post(mUpdateResults);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		btnXingge = (Button) findViewById(R.id.btnXingge);
		btnXingge.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("indexPage", "index_xingge.html");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		btnLinglei = (Button) findViewById(R.id.btnLinglei);
		btnLinglei.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("indexPage", "index_linglei.html");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		btnQinggan = (Button) findViewById(R.id.btnQinggan);
		btnQinggan.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("indexPage", "index_qinggan.html");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		btnChenggong = (Button) findViewById(R.id.btnChenggong);
		btnChenggong.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("indexPage", "index_chenggong.html");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

}
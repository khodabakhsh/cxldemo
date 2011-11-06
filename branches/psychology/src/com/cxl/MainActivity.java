package com.cxl;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	public static final String hasEnoughRequrePointPreferenceKey = "hasEnoughRequrePointPreferenceKey";
	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里

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
			if (!hasEnoughRequrePointPreferenceValue) {
				hasEnoughRequrePointPreferenceValue = true;
				SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
				SharedPreferences.Editor mEditor = mPerferences.edit();
				mEditor.putBoolean(hasEnoughRequrePointPreferenceKey, true);
				mEditor.commit();
			}
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
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
	}

	private void initRequrePointPreference() {
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		hasEnoughRequrePointPreferenceValue = mPerferences.getBoolean(hasEnoughRequrePointPreferenceKey, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initRequrePointPreference();
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
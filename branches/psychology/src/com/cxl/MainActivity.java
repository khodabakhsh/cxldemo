package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cxl.psychology.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	Button btnXingge;
	Button btnLinglei;
	Button btnQinggan;
	Button btnChenggong;

	public static boolean hasEnoughReadPointPreferenceValue = false;
	public static final int requireReadPoint = 80;
	public static int currentPointTotal = 0;

	private Handler handler = new Handler();

	protected void onResume() {
		if (!hasEnoughReadPointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}

	private void initRequrePointPreference() {
		hasEnoughReadPointPreferenceValue = PreferenceUtil.getHasEnoughReadPoint(MainActivity.this);
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
		if (pointTotal >= requireReadPoint) {
			hasEnoughReadPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughReadPoint(MainActivity.this, true);
		}
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		hasEnoughReadPointPreferenceValue = false;
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	private void showGetPointDialog(String type) {
		new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.happy2).setTitle("当前积分：" + currentPointTotal)
				.setMessage("只要积分满足" + requireReadPoint + "，就可以" + type)
				.setPositiveButton("免费获得积分", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
					}
				}).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		initRequrePointPreference();

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
				if (!hasEnoughReadPointPreferenceValue) {
					handler.post(new Runnable() {
						public void run() {
							showGetPointDialog("开启【另类测试】");
						}
					});
				} else {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, DetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("indexPage", "index_linglei.html");
					intent.putExtras(bundle);
					startActivity(intent);
				}

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
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout2);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

}
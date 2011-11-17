package com.cxl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cxl.psychology.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class MainActivity extends Activity {
	Button btnXingge;
	Button btnLinglei;
	Button btnQinggan;
	Button btnChenggong;

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
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
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout2);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

}
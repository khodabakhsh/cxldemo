package com.cxl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.cxl.lxpyl.R;
import com.waps.AppConnect;

public class CoverActivity extends Activity {
	ImageView imageView;

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// AppConnect.getInstance(this).getPoints(this);
		super.onResume();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cover);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CoverActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("startByMenu", false);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}
}
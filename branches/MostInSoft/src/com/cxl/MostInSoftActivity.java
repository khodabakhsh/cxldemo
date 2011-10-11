package com.cxl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxl.soft.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class MostInSoftActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		AppConnect.getInstance(this);
		// 显示推荐安装程序（Offer）.
		AppConnect.getInstance(MostInSoftActivity.this).showOffers(MostInSoftActivity.this);

		//互动广告
		LinearLayout container1 = (LinearLayout) findViewById(R.id.AdLinearLayout1);
		new AdView(this, container1).DisplayAd(20);//每20秒轮换一次广告；最少为20
		LinearLayout container2 = (LinearLayout) findViewById(R.id.AdLinearLayout2);
		new AdView(this, container2).DisplayAd(20);//每20秒轮换一次广告；最少为20
		//推荐列表
		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(MostInSoftActivity.this).showOffers(MostInSoftActivity.this);
			}
		});
		//自家应用
		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AppConnect.getInstance(MostInSoftActivity.this).showMore(MostInSoftActivity.this);
			}
		});

		//图标1
		ImageView happy1 = (ImageView) findViewById(R.id.happy1);
		happy1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(MostInSoftActivity.this).showOffers(MostInSoftActivity.this);
			}
		});
		//图标2
		ImageView happy2 = (ImageView) findViewById(R.id.happy2);
		happy2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(MostInSoftActivity.this).showOffers(MostInSoftActivity.this);
			}
		});
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}
}
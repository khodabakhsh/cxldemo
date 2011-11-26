package com.cxl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxl.xbt.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class DetailActivity extends Activity {

	private TextView content;
	private Button returnButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

		Bundle bundle = getIntent().getExtras();
		String name = bundle.getString("name");
		setTitle(name);
		content = (TextView) findViewById(R.id.content);
		content.setText(MainActivity.Title_Content_Map.get(name));

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});
//		Button owns = (Button) findViewById(R.id.OwnsButton);
//		owns.setText("更多免费应用...");
//		owns.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View arg0) {
//				// 显示自家应用列表.
//				AppConnect.getInstance(DetailActivity.this).showMore(
//						DetailActivity.this);
//			}
//		});
		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setText("其他推荐的免费应用...");
		offers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(
						DetailActivity.this);
			}
		});
	}
}

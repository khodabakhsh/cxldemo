package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.cxl.constellationpair.R;
import com.waps.AppConnect;

public class DetailActivity extends Activity {
	private void showDialog() {
		new AlertDialog.Builder(DetailActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + MainActivity.currentPointTotal)
				.setMessage(
						"只要积分满足" + MainActivity.requirePoint + "，就可以消除本提示信息！！ 您当前的积分不足" + MainActivity.requirePoint
								+ "，所以会有此 提示。\n【免费获得积分方法】：请点击【确认键】进入推荐下载列表 , 下载并安装软件获得相应积分，消除本提示！！")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish();
					}
				}).show();
	}

	private WebView mWebView;

	private Button returnButton;

	private ProgressDialog progressDialog = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		progressDialog = new ProgressDialog(DetailActivity.this);
		progressDialog.setTitle("请稍等...");
		progressDialog.setMessage("获取数据中...");
		progressDialog.setIndeterminate(true);
		progressDialog.setIndeterminateDrawable(this.getResources().getDrawable(R.drawable.wait));
		progressDialog.show();

		new Thread(new Runnable() {
			public void run() {
				Bundle bundle = getIntent().getExtras();
				String id = bundle.getString("id");
				mWebView = (WebView) findViewById(R.id.myWebView);
				mWebView.loadDataWithBaseURL(ConstellationPairUtil.getConstellationPairDetailUrl,
						new ConstellationPairUtil().getConstellationPairDetail(id), "text/html", ConstellationPairUtil.UTF8,
						"");
				progressDialog.dismiss();

			}
		}).start();

		if (!MainActivity.hasEnoughRequrePoint) {// 没达到积分
			showDialog();
		}

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});
		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多免费应用...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(DetailActivity.this).showMore(DetailActivity.this);
			}
		});
		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setText("其他推荐的免费应用...");
		offers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
			}
		});
	}
}

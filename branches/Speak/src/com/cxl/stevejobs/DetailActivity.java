package com.cxl.stevejobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxl.speak.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class DetailActivity extends Activity {

	private Button returnButton;
	private TextView textView;
	private String menu;
	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF8";

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		Bundle bundle = getIntent().getExtras();
		menu = bundle.getString("menu");

		setTitle(menu);

		AssetManager assets = getAssets();
		// 打开指定资源对应的输入流
		InputStream assetFile;
		try {
			assetFile = assets.open(MainActivity.MENU_FILE_MAP
					.get(menu));
			textView = (TextView) findViewById(R.id.TextView);
			textView.setText(genFileContent(assetFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});
		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
			}
		});

		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

	}

	public static String genFileContent(InputStream inputStream) {
		StringBuffer returnString = new StringBuffer("");
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, UTF8);
			bufferedReader = new BufferedReader(inputStreamReader);
			String contentString = "";
			while (null != (contentString = bufferedReader.readLine())) {
				returnString.append(contentString + "\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return returnString.toString();
	}

}

package com.cxl.stevejobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxl.brainfun.R;
import com.waps.AdView;

public class DetailActivity extends Activity {

	private Button returnButton;
	private TextView textView;
	private String menu;
	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF8";
	
	public static final String Current_Page_Key = "Current_Page_Key";
	public static int Current_Page_Value = 1;// 保存在配置里
	TextView page;
	Button btnPrevious;
	Button btnNext;
	public static final int Page_Sum = 72;// 保存在配置里
	

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		initCurrentPagePreference();
		setContentView(R.layout.detail);

		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				try {
					AssetManager assets = getAssets();
					InputStream assetFile;
					assetFile = assets.open((--Current_Page_Value) + ".txt");
					
					setButtonVisible();
					textView.setText(genFileContent(assetFile));
				} catch (IOException e) {
					e.printStackTrace();
				}
				setButtonVisible();
			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					AssetManager assets = getAssets();
					InputStream assetFile;
					assetFile = assets.open((++Current_Page_Value) + ".txt");
					setButtonVisible();
					textView.setText(genFileContent(assetFile));
				} catch (IOException e) {
					e.printStackTrace();
				}
				setButtonVisible();
			}
		});

		Bundle bundle = getIntent().getExtras();
		menu = bundle.getString("menu");

		setTitle(menu);

		AssetManager assets = getAssets();
		// 打开指定资源对应的输入流
		InputStream assetFile;
		try {
			Current_Page_Value = Integer.valueOf(menu.substring(1, menu.length()-1));
			assetFile = assets.open(Current_Page_Value + ".txt");
			textView = (TextView) findViewById(R.id.TextView);
			textView.setText(genFileContent(assetFile));
			setButtonVisible() ;
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

		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

	}



	@Override
	protected void onResume() {
		setButtonVisible();
		super.onResume();
	}
	private void initCurrentPagePreference() {
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(this);
		Current_Page_Value = mPerferences.getInt(Current_Page_Key, 1);
	}

	private void setButtonVisible() {
		setTitle("第"+Current_Page_Value+"章");
		// page.setText("第【" + Current_Page_Value + "】页");
		if (Current_Page_Value == 1) {
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Value == Page_Sum) {
			btnNext.setVisibility(View.INVISIBLE);
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
		SharedPreferences mPerferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor mEditor = mPerferences.edit();
		mEditor.putInt(Current_Page_Key, Current_Page_Value);
		mEditor.commit();
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

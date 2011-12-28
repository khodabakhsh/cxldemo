package com.cxl;

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
import android.widget.Toast;

import com.cxl.tangshi.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class DetailActivity extends Activity {

	private Button returnButton;
	private TextView textView;
	private String fileName;
	public static final String Favorite_Key = "Favorite_Key";
	private String subMenuName;
	public static final String Favorite_Item_Split = "@@##";
	public static final String Item_Key_Value_Split = "#===";
	public static final String UTF8 = "UTF8";

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		Bundle bundle = getIntent().getExtras();
		fileName = bundle.getString("fileName");

		subMenuName = bundle.getString("subMenuName");
		setTitle(subMenuName);
		AssetManager assets = getAssets();

		try {
			// 打开指定资源对应的输入流
			InputStream assetFile = assets.open(fileName);
			textView = (TextView) findViewById(R.id.TextView);
			textView.setText(genFileContent(assetFile));
		} catch (IOException e) {
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

		Button favoriteButton = (Button) findViewById(R.id.favoriteButton);
		favoriteButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String myFavorite = mPerferences.getString(Favorite_Key, "");
				String itemString = fileName + Item_Key_Value_Split + subMenuName;
				if (!exist(myFavorite, itemString)) {
					MainActivity.favoriteList.add(new ListManager.KeyValue(subMenuName, fileName));
				}

				SharedPreferences.Editor mEditor = mPerferences.edit();
				mEditor.putString(Favorite_Key, add(myFavorite, itemString));
				mEditor.commit();

				Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_LONG).show();

				mEditor.commit();
			}
		});

		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

	}

	private boolean exist(String orginString, String id) {
		boolean exist = false;
		String[] splitStrings = orginString.split(Favorite_Item_Split);
		for (String itemString : splitStrings) {
			if (itemString.equals(id)) {
				exist = true;
			}
		}
		return exist;
	}

	private String add(String orginString, String id) {
		if ("".equals(orginString)) {
			return id;
		}
		if (!exist(orginString, id)) {
			orginString += Favorite_Item_Split + id;
		}
		return orginString;

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
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return returnString.toString();
	}

}

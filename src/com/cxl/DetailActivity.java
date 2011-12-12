package com.cxl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.guobao.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class DetailActivity extends Activity {

	private Button returnButton;
	private ImageView imageView;
	private TextView textView;
	private Button btnPrevious;
	private Button btnNext;
	private static final String txtSuffix = ".txt";
	private static final String gb2312 = "gb2312";

	public static int Current_Page_Index = 1;
	public static final int Start_Page_Index = 1;//起始页索引
	public static final int Max_Page_Index = ListManager.AllList.size() + Start_Page_Index - 1;//最大页索引

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		btnPrevious = (Button) findViewById(R.id.previous);
		btnNext = (Button) findViewById(R.id.next);

		Bundle bundle = getIntent().getExtras();
		String car = bundle.getString("car");
		setButtonVisibleAndSaveState(car);

		imageView = (ImageView) findViewById(R.id.ImageView);
		AssetManager assets = getAssets();
		String carIndex = car.substring(0, car.lastIndexOf(MainActivity.Separator));
		Current_Page_Index = Integer.parseInt(carIndex);

		try {
			// 打开指定资源对应的输入流  
			InputStream assetFile = assets.open("image/" + carIndex + ".jpg");
			// 改变ImageView显示的图片  
			imageView.setImageBitmap(BitmapFactory.decodeStream(assetFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		textView = (TextView) findViewById(R.id.TextView);
		textView.setMovementMethod(ScrollingMovementMethod.getInstance());
		textView.setText(getFileContent(DetailActivity.this, carIndex));

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});

		btnPrevious.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AssetManager assets = getAssets();
				String carIndex = getModifiedPage(--Current_Page_Index);
				try {
					// 打开指定资源对应的输入流  
					InputStream assetFile = assets.open("image/" + carIndex + ".jpg");
					// 改变ImageView显示的图片  
					imageView.setImageBitmap(BitmapFactory.decodeStream(assetFile));
				} catch (IOException e) {
					e.printStackTrace();
				}
				textView.setText(getFileContent(DetailActivity.this, carIndex));
				setButtonVisibleAndSaveState(getTitle(carIndex));
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AssetManager assets = getAssets();
				String carIndex = getModifiedPage(++Current_Page_Index);
				try {
					// 打开指定资源对应的输入流  
					InputStream assetFile = assets.open("image/" + carIndex + ".jpg");
					// 改变ImageView显示的图片  
					imageView.setImageBitmap(BitmapFactory.decodeStream(assetFile));
				} catch (IOException e) {
					e.printStackTrace();
				}
				textView.setText(getFileContent(DetailActivity.this, carIndex));
				setButtonVisibleAndSaveState(getTitle(carIndex));
			}
		});

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多精品下载...");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
			}
		});
		LinearLayout container2 = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container2).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

	private String getTitle(String pageIndex) {
		for (String item : ListManager.AllList) {
			if (item.startsWith(pageIndex + MainActivity.Separator)) {
				return item;
			}
		}
		return "";
	}

	private void setButtonVisibleAndSaveState(String pageInfoString) {
		Toast.makeText(DetailActivity.this, "开始阅读：" + pageInfoString, Toast.LENGTH_SHORT).show();
		String tempString = getString(R.string.app_name) + " —— " + pageInfoString;
		setTitle(tempString);

		if (Current_Page_Index == Start_Page_Index) {
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Index == Max_Page_Index) {
			btnNext.setVisibility(View.INVISIBLE);
			Toast.makeText(DetailActivity.this, "已经是最后一页了哦", Toast.LENGTH_SHORT).show();
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
	}

	public String getModifiedPage(int pageIndex) {
		return (pageIndex < 10) ? "0" + pageIndex : String.valueOf(pageIndex);
	}

	public String getFileContent(Context context, String carIndex) {// 规划了file参数、ID参数，方便多文件写入。
		InputStream in = null;
		BufferedReader bufferedReader = null;
		StringBuilder sBuffer = new StringBuilder("");
		try {
			AssetManager assets = getAssets();
			in = assets.open("txt/" + carIndex + txtSuffix);

			bufferedReader = new BufferedReader(new InputStreamReader(in, gb2312));
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sBuffer.toString();
	}

}

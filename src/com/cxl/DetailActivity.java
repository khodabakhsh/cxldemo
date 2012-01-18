package com.cxl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.cxl.baijiaxing.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class DetailActivity extends Activity implements UpdatePointsNotifier {

	private Button returnButton;
	private WebView myWebView;
	private Button btnPrevious;
	private Button btnNext;
	private Button btnGetPoint;
	private ScrollView scrollView;
	
	private Button OwnsButton2; 
	private Button OwnsButton3; 

	Handler handler = new Handler();

	InputStream assetFile = null;
	Bitmap bitmap = null;
	private static final String txtSuffix = ".txt";
	private static final String gb2312 = "gb2312";
	private static final String utf8 = "utf8";
	private static final String txt_charset = utf8;
	private static boolean isNeedModifiedPage = false;

	public static final int Start_Page_Index = 1;// 起始页索引
	public static int Current_Page_Index = Start_Page_Index;
	public static final int Max_Page_Index = ListManager.AllList.size()
			+ Start_Page_Index - 1;// 最大页索引

	Handler msgHandler = new Handler();

	public static boolean hasEnoughRequreAdPointPreferenceValue = false;// 保存在配置里
	public static final int requireAdPoint = 70;// 要求积分
	public static int currentPointTotal = 0;// 当前积分


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		initRequrePointPreference();

		btnPrevious = (Button) findViewById(R.id.previous);
		btnNext = (Button) findViewById(R.id.next);
		myWebView = (WebView) findViewById(R.id.myWebView);
		returnButton = (Button) findViewById(R.id.returnButton);
		btnGetPoint = (Button) findViewById(R.id.OwnsButton);
		scrollView = (ScrollView) findViewById(R.id.scrollView);


		returnButton.setText("目录");
		btnGetPoint.setText("更多下载");

		Bundle bundle = getIntent().getExtras();
		String selectItem = bundle.getString("selectItem");
		String itemIndex = selectItem.substring(0,
				selectItem.lastIndexOf(MainActivity.Separator));
		boolean canRead = true;

		Current_Page_Index = Integer.parseInt(itemIndex);

		changePageContent(itemIndex);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});

		btnPrevious.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String itemIndex = getModifiedPage(--Current_Page_Index);
				changePageContent(itemIndex);
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					String itemIndex = getModifiedPage(++Current_Page_Index);
					changePageContent(itemIndex);
			}
		});

		btnGetPoint.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(DetailActivity.this).showOffers(
						DetailActivity.this);
			}
		});
		OwnsButton2 = (Button) findViewById(R.id.OwnsButton2);
		OwnsButton2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(DetailActivity.this).showOffers(
						DetailActivity.this);
			}
		});
		OwnsButton3 = (Button) findViewById(R.id.OwnsButton3);
		OwnsButton3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(DetailActivity.this).showOffers(
						DetailActivity.this);
			}
		});

	}

	private void changePageContent(String itemIndex) {
		
		myWebView.loadDataWithBaseURL(null,getFileContent(DetailActivity.this, itemIndex), "text/html", "UTF-8",
				null);
//		textView.setText(getFileContent(DetailActivity.this, itemIndex));
		scrollView.post(new Runnable() {
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_UP);
			}
		});

		setButtonVisibleAndSaveState(getTitle(itemIndex));
	}

	protected void onDestroy() {
		if (bitmap != null) {
			bitmap.recycle();
		}
		super.onDestroy();
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
		String tempString = getString(R.string.app_name) + " —— "
				+ pageInfoString;
		setTitle(tempString);

		if (Current_Page_Index == Start_Page_Index) {
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Index == Max_Page_Index) {
			btnNext.setVisibility(View.INVISIBLE);
			Toast.makeText(DetailActivity.this, "已经是最后一页了哦", Toast.LENGTH_SHORT)
					.show();
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
	}

	public String getModifiedPage(int pageIndex) {
		if (!isNeedModifiedPage) {
			return String.valueOf(pageIndex);
		}
		return (pageIndex < 10) ? "0" + pageIndex : String.valueOf(pageIndex);
	}


	private void initRequrePointPreference() {
		hasEnoughRequreAdPointPreferenceValue = PreferenceUtil.getHasEnoughAdPoint(DetailActivity.this);
	}
	protected void onResume() {
		if (!hasEnoughRequreAdPointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}


	public String getFileContent(Context context, String itemIndex) {// 规划了file参数、ID参数，方便多文件写入。
		InputStream in = null;
		BufferedReader bufferedReader = null;
		StringBuilder sBuffer = new StringBuilder("");
		try {
			AssetManager assets = getAssets();
			in = assets.open("txt/" + itemIndex + txtSuffix);

			bufferedReader = new BufferedReader(new InputStreamReader(in,
					txt_charset));
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

	public void getUpdatePoints(String currencyName, int pointTotal) {
		currentPointTotal = pointTotal;
		if (pointTotal >= requireAdPoint) {
			hasEnoughRequreAdPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughAdPoint(DetailActivity.this, true);
		}
		if(!hasEnoughRequreAdPointPreferenceValue){
			msgHandler.post(new Runnable() {
				public void run() {
					new AlertDialog.Builder(DetailActivity.this)
							.setIcon(R.drawable.happy2)
							.setTitle("感谢使用本程序")
							.setMessage(
									"说明：本程序的一切提示信息，在积分满足" + requireAdPoint
											+ "后，自动消除！\n\n可通过【免费赚积分】，获得积分。\n\n通过【更多应用】，可以下载各种好玩应用。\n\n当前积分："
											+ currentPointTotal)
							.setPositiveButton("更多应用", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {
									AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
								}
							}).setNeutralButton("免费赚积分", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {
									AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
								}
							}).setNegativeButton("继续", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {
								}
							}).show();
				}
			});
		}
	}
	public void getUpdatePointsFailed(String error) {
		hasEnoughRequreAdPointPreferenceValue = false;
	}
}

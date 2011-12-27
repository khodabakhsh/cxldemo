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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.car.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class DetailActivity extends Activity implements UpdatePointsNotifier {

	private Button returnButton;
	private ImageView imageView;
	private TextView textView;
	private Button btnPrevious;
	private Button btnNext;
	private Button btnGetPoint;
	private ScrollView scrollView;
	private LinearLayout adLinearLayout;

	Handler handler = new Handler();

	InputStream assetFile = null;
	Bitmap bitmap = null;
	private static final String txtSuffix = ".txt";
	private static final String gb2312 = "gb2312";
	private static final String utf8 = "utf8";
	private static final String txt_charset = gb2312;

	
	public static final int Start_Page_Index = 1;//起始页索引
	public static int Current_Page_Index = Start_Page_Index;
	public static final int Max_Page_Index = ListManager.AllList.size() + Start_Page_Index - 1;//最大页索引

	public static boolean hasEnoughAdPointPreferenceValue = false;
	public static final int requireAdPoint = 50;
	public static boolean hasEnoughReadPointPreferenceValue = false;
	public static final int requireReadPoint = 30;
	public static final int Read_Requre_Point_Page_Index = 30;

	public static int currentPointTotal = 0;

	private boolean canView(int pageIndex) {
		if ((pageIndex >= Read_Requre_Point_Page_Index) && !hasEnoughReadPointPreferenceValue) {
			return false;
		} else {
			return true;
		}
	}

	private void showGetPointDialog(String type) {
		new AlertDialog.Builder(DetailActivity.this).setIcon(R.drawable.happy2).setTitle("当前积分：" + currentPointTotal)
				.setMessage("只要积分满足" + requireReadPoint + "，就可以" + type)
				.setPositiveButton("免费获得积分", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
					}
				}).show();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		initRequrePointPreference();

		btnPrevious = (Button) findViewById(R.id.previous);
		btnNext = (Button) findViewById(R.id.next);
		imageView = (ImageView) findViewById(R.id.ImageView);
		textView = (TextView) findViewById(R.id.TextView);
		returnButton = (Button) findViewById(R.id.returnButton);
		btnGetPoint = (Button) findViewById(R.id.OwnsButton);
		scrollView = (ScrollView) findViewById(R.id.scrollView);

		adLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout);

		returnButton.setText("返回目录");
		btnGetPoint.setText("移除所有广告");

		Bundle bundle = getIntent().getExtras();
		String selectItem = bundle.getString("selectItem");
		String itemIndex = selectItem.substring(0, selectItem.lastIndexOf(MainActivity.Separator));
		boolean canRead = true;
		if (!canView(Integer.parseInt(itemIndex))) {
			canRead = false;
			showGetPointDialog("继续阅读 【" + ListManager.AllList.get(Read_Requre_Point_Page_Index - Start_Page_Index)
					+ "】 之后的内容哦!");
			itemIndex = "01";
		}

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
				if (canView(Current_Page_Index + 1)) {
					String itemIndex = getModifiedPage(++Current_Page_Index);
					changePageContent(itemIndex);
				} else {
					showGetPointDialog("继续阅读 【"
							+ ListManager.AllList.get(Read_Requre_Point_Page_Index - Start_Page_Index) + "】 之后的内容哦!");
				}

			}
		});

		btnGetPoint.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
			}
		});
		if (!hasEnoughAdPointPreferenceValue) {
			new AdView(this, adLinearLayout).DisplayAd(20);// 每20秒轮换一次广告；最少为20
		}
		if (canRead && !hasEnoughAdPointPreferenceValue) {

			new AlertDialog.Builder(DetailActivity.this).setIcon(R.drawable.happy2).setTitle("永久移除所有广告")
					.setMessage("当前积分：" + currentPointTotal + "。\n只要积分满足" + requireAdPoint + "，就可以永久移除所有广告！")
					.setPositiveButton("免费获得积分", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							// 显示推荐安装程序（Offer）.
							AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
		}
		if (hasEnoughAdPointPreferenceValue) {
			btnGetPoint.setText("更多精品下载...");
		}

	}

	private void changePageContent(String itemIndex) {
		AssetManager assets = getAssets();
		try {
			assetFile = assets.open("image/" + itemIndex + ".jpg");
			//			imageView.setImageBitmap(BitmapFactory.decodeStream(assetFile));
			bitmap = BitMapUtil.adujstSizeByMax(assetFile);
			imageView.setImageBitmap(bitmap);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (assetFile != null) {
				try {
					assetFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		textView.setText(getFileContent(DetailActivity.this, itemIndex));
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

	private void initRequrePointPreference() {
		hasEnoughAdPointPreferenceValue = PreferenceUtil.getHasEnoughAdPoint(DetailActivity.this);
		hasEnoughReadPointPreferenceValue = PreferenceUtil.getHasEnoughReadPoint(DetailActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughAdPointPreferenceValue || !hasEnoughReadPointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}

	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 * 
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {
		currentPointTotal = pointTotal;
		if (pointTotal >= requireAdPoint) {
			hasEnoughAdPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughAdPoint(DetailActivity.this, true);
			handler.post(new Runnable() {
				public void run() {
					adLinearLayout.setVisibility(View.GONE);
					btnGetPoint.setText("更多精品下载...");
				}
			});
		}
		if (pointTotal >= requireReadPoint) {
			hasEnoughReadPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughReadPoint(DetailActivity.this, true);
		}
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		hasEnoughAdPointPreferenceValue = false;
		hasEnoughReadPointPreferenceValue = false;
	}

	public String getFileContent(Context context, String itemIndex) {// 规划了file参数、ID参数，方便多文件写入。
		InputStream in = null;
		BufferedReader bufferedReader = null;
		StringBuilder sBuffer = new StringBuilder("");
		try {
			AssetManager assets = getAssets();
			in = assets.open("txt/" + itemIndex + txtSuffix);

			bufferedReader = new BufferedReader(new InputStreamReader(in, txt_charset));
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

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
import com.waps.UpdatePointsNotifier;

public class DetailActivity extends Activity implements UpdatePointsNotifier {

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

	public static boolean hasEnoughAdPointPreferenceValue = false;
	public static final int requireAdPoint = 400;
	public static boolean hasEnoughReadPointPreferenceValue = false;
	public static final int requireReadPoint = 400;
	public static final int Read_Requre_Point_Page_Index = 40;

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
				.setMessage("只要积分满足" + requireReadPoint + "，就可以" + type )
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

		Bundle bundle = getIntent().getExtras();
		String car = bundle.getString("car");
		String carIndex = car.substring(0, car.lastIndexOf(MainActivity.Separator));
		if (!canView(Integer.parseInt(carIndex))) {
			showGetPointDialog("浏览【" + ListManager.AllList.get(Read_Requre_Point_Page_Index - Start_Page_Index)
					+ "】之后的内容哦!");
			carIndex = "01";
		}

		Current_Page_Index = Integer.parseInt(carIndex);

		setButtonVisibleAndSaveState(car);

		AssetManager assets = getAssets();
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
				if (canView(Current_Page_Index + 1)) {
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
				else {
					showGetPointDialog("浏览【" + ListManager.AllList.get(Read_Requre_Point_Page_Index - Start_Page_Index)
							+ "】之后的内容哦!");
				}

			}
		});

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多精品下载...！");
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
			}
		});
		if (!hasEnoughAdPointPreferenceValue) {
			LinearLayout container2 = (LinearLayout) findViewById(R.id.AdLinearLayout);
			new AdView(this, container2).DisplayAd(20);// 每20秒轮换一次广告；最少为20

			new AlertDialog.Builder(DetailActivity.this).setTitle("永久移除所有广告")
					.setMessage("只要积分满足" + requireAdPoint + "，就可以永久移除所有广告！")
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
		if (!hasEnoughAdPointPreferenceValue||!hasEnoughReadPointPreferenceValue) {
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
			LinearLayout container2 = (LinearLayout) findViewById(R.id.AdLinearLayout);
			container2.setVisibility(View.INVISIBLE);
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

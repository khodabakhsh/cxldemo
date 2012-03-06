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
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.qiuAiMiJi.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class DetailActivity extends Activity implements UpdatePointsNotifier,
		View.OnClickListener {

	private TextView textView;
	public static final String txtChartset = "utf-8";

	Button btnPrevious;
	Button btnNext;
	Button menuButton;

	public static int Current_Page_Index = 1;
	public static final int Start_Page_Index = 1;// 起始页索引
	public static final int Max_Page_Index = MainActivity.MENU_List.size()
			+ Start_Page_Index - 1;// 最大页索引
	private int scrollY = 0;

	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里
	public static final int requirePoint = 100;// 要求积分
	public static int currentPointTotal = 0;// 当前积分

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		initRequrePointPreference();

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		btnPrevious = (Button) findViewById(R.id.previous);
		btnNext = (Button) findViewById(R.id.next);
		Bundle bundle = getIntent().getExtras();
		textView = (TextView) findViewById(R.id.textView);
		textView.setMovementMethod(ScrollingMovementMethod.getInstance());

		textView.setTextSize(PreferenceUtil.getFontSize(this));
		setMode(PreferenceUtil.getMode(this));

		if (bundle != null && bundle.getBoolean("startByMenu")) {
			int selectMenu = Integer.valueOf(bundle.getString("menu"));
			Current_Page_Index = selectMenu;
			String fileContent = getFileContent(DetailActivity.this,
					Current_Page_Index);
			textView.setText(fileContent);
			scrollY = 0;
			textView.scrollTo(0, 0);
		} else {
			Current_Page_Index = PreferenceUtil.getTxtIndex(this);
			String fileContent = getFileContent(DetailActivity.this,
					Current_Page_Index);
			textView.setText(fileContent);
			scrollY = PreferenceUtil.getScrollY(DetailActivity.this);
			textView.scrollTo(0, scrollY);
		}
		setTitleAndSaveState();

		// LinearLayout container = (LinearLayout)
		// findViewById(R.id.AdLinearLayout);
		// new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

		findViewById(R.id.previous).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);

		findViewById(R.id.OffersButton).setOnClickListener(this);
		findViewById(R.id.OffersButton2).setOnClickListener(this);
		findViewById(R.id.rl_set1tv).setOnClickListener(this);
		findViewById(R.id.rl_set2tv).setOnClickListener(this);
		findViewById(R.id.rl_set3tv).setOnClickListener(this);
		findViewById(R.id.rl_set4tv).setOnClickListener(this);
	}

	protected void onPause() {
		saveState();
		super.onPause();
	}

	// 保存当前页和滚动位置
	private void saveState() {
		PreferenceUtil.setScrollY(this, textView.getScrollY());
		PreferenceUtil.setTxtIndex(this, Current_Page_Index);
	}

	private void setTitleAndSaveState() {
		saveState();
		String currentTitle = MainActivity.MENU_List.get(
				Current_Page_Index - Start_Page_Index).getValue()+" / "+MainActivity.MENU_List.size();
		setTitle(currentTitle);
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		SubMenu menu = paramMenu.addSubMenu(0, 0, 0, "目录");
		menu.setIcon(R.drawable.menu);
		SubMenu menu2 = paramMenu.addSubMenu(0, 1, 0, "更多免费精品下载...");
		menu2.setIcon(R.drawable.more);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == 0) {
			Intent intent = new Intent();
			intent.setClass(DetailActivity.this, MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putBoolean("showMenu", true);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		} else if (paramMenuItem.getItemId() == 1) {
			// 显示推荐安装程序（Offer）.
			AppConnect.getInstance(DetailActivity.this).showOffers(
					DetailActivity.this);
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	private void initRequrePointPreference() {
		hasEnoughRequrePointPreferenceValue = PreferenceUtil
				.getHasEnoughRequrePoint(DetailActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughRequrePointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}

	Handler msgHandler = new Handler();

	public void getUpdatePoints(String currencyName, int pointTotal) {
		currentPointTotal = pointTotal;
		if (pointTotal >= requirePoint) {
			hasEnoughRequrePointPreferenceValue = true;
			PreferenceUtil.setHasEnoughRequrePoint(DetailActivity.this, true);
		}
		if (!hasEnoughRequrePointPreferenceValue) {

			msgHandler.post(new Runnable() {
				public void run() {
					new AlertDialog.Builder(DetailActivity.this)
							.setTitle("感谢使用本程序")
							.setMessage(
									"说明：\n 1.按menu键可以进入目录。\n\n 2.可通过【更多下载】，下载更多精彩内容。\n\n3.支持我们，通过【更多应用】，下载各种好玩应用")
							.setPositiveButton("更多下载",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialoginterface,
												int i) {
											AppConnect
													.getInstance(
															DetailActivity.this)
													.showOffers(
															DetailActivity.this);
										}
									})
							.setNeutralButton("更多应用",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialoginterface,
												int i) {
											AppConnect
													.getInstance(
															DetailActivity.this)
													.showOffers(
															DetailActivity.this);
										}
									})
							.setNegativeButton("继续",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialoginterface,
												int i) {
										}
									}).show();
				}
			});
		}
	}

	public void getUpdatePointsFailed(String error) {
		hasEnoughRequrePointPreferenceValue = false;
	}

	public String getFileContent(Context context, int fileName) {// 规划了file参数、ID参数，方便多文件写入。
		InputStream in = null;
		BufferedReader bufferedReader = null;
		StringBuilder sBuffer = new StringBuilder("");
		try {
			AssetManager assets = getAssets();
			in = assets.open(String.valueOf(fileName));

			bufferedReader = new BufferedReader(new InputStreamReader(in));
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

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.rl_set1tv: {
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
			localBuilder.setTitle(R.string.app_set_font_title);
			localBuilder.setSingleChoiceItems(R.array.dash_display_font, 0,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							int font = 12 + 4 * arg1;
							textView.setTextSize(12 + 4 * arg1);
							PreferenceUtil.setFontSize(DetailActivity.this,
									font);
							arg0.cancel();
						}
					});
			localBuilder.create().show();

		}
			break;
		case R.id.rl_set2tv: {
			AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
			localBuilder1.setTitle(R.string.app_set_mode_title);
			localBuilder1.setSingleChoiceItems(R.array.dash_display_mode, 0,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							setMode(arg1);
							PreferenceUtil.setMode(DetailActivity.this, arg1);
							arg0.cancel();
						}
					});
			localBuilder1.create().show();

		}
			break;
		case R.id.rl_set3tv: {

			Intent sendIntent = new Intent("android.intent.action.SEND");
			sendIntent.setType("image/*");
			sendIntent.putExtra("android.intent.extra.SUBJECT", "分享");
			sendIntent.putExtra("android.intent.extra.TEXT", textView.getText()
					.toString());
			Intent selectIntent = Intent.createChooser(sendIntent, "使用以下方式发送");
			startActivity(selectIntent);

		}
			break;
		case R.id.rl_set4tv: {
			Intent intent = new Intent();
			intent.setClass(DetailActivity.this, AboutActivity.class);
			startActivity(intent);

		}
			break;
		case R.id.OffersButton: {
			AppConnect.getInstance(DetailActivity.this).showOffers(
					DetailActivity.this);

		}
			break;
		case R.id.OffersButton2: {
			AppConnect.getInstance(DetailActivity.this).showOffers(
					DetailActivity.this);

		}
			break;
		case R.id.previous: {
			if (Current_Page_Index == Start_Page_Index) {
				Toast.makeText(DetailActivity.this, "已经是第一页",
						Toast.LENGTH_SHORT).show();
			} else {
				String fileContent = getFileContent(DetailActivity.this,
						--Current_Page_Index);
				textView.setText(fileContent);
				scrollY = 0;
				textView.scrollTo(0, scrollY);
				setTitleAndSaveState();
			}

		}
			break;
		case R.id.next: {
			if (Current_Page_Index == Max_Page_Index) {
				Toast.makeText(DetailActivity.this, "已经是最后一页",
						Toast.LENGTH_SHORT).show();
			} else {
				String fileContent = getFileContent(DetailActivity.this,
						++Current_Page_Index);
				textView.setText(fileContent);
				scrollY = 0;
				textView.scrollTo(0, scrollY);
				setTitleAndSaveState();
			}

		}
			break;

		}

	}

	private void setMode(int mode) {
		if (mode == 1) {
			findViewById(R.id.all).setBackgroundColor(-16777216);
			textView.setTextColor(-1);
		} else {
			findViewById(R.id.all).setBackgroundColor(-1);
			textView.setTextColor(-16777216);
		}
	}
}

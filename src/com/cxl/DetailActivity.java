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
import android.graphics.Picture;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cxl.gshgw.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class DetailActivity extends Activity implements UpdatePointsNotifier {

	private MyWebView webView;
	Button btnPrevious;
	Button btnNext;

	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF8";

	public static final int Start_Page_Index = 0;//起始页索引
	public static final String Page_Prefix = "";//内容页前缀
	public static final String Page_Suffix = ".txt";//内容页后缀
	public static final boolean Is_Load_Page_By_Url = false;//是否直接用url，否则读取文件内容	

	public static int Current_Page_Index = Start_Page_Index;
	public static final int Max_Page_Index = MainActivity.MENU_List.size() + Start_Page_Index - 1;//最大页索引
	private int scrollY = 0;
	private static boolean firstComeIn = true;

	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里
	public static final int requirePoint = 30;// 要求积分
	public static int currentPointTotal = 0;// 当前积分
	public static final int Requre_Point_Page_Index = 400;//需要积分才能查看的页面

	class MyPictureListener implements PictureListener {
		public void onNewPicture(WebView view, Picture arg1) {
			// put code here that needs to run when the page has finished
			// loading and
			// a new "picture" is on the webview.
			webView.scrollTo(0, scrollY);
		}
	}

	private boolean canView(int pageIndex) {
		if ((pageIndex >= Requre_Point_Page_Index) && !hasEnoughRequrePointPreferenceValue) {
			showGetPointDialog("浏览【" + MainActivity.MENU_List.get(Requre_Point_Page_Index-Start_Page_Index) + "】之后的内容哦");
			return false;
		} else {
			return true;
		}
	}

	private void setPageInfo(String loadUrl, int scrollY) {
		String currentTitle = MainActivity.MENU_List.get(Current_Page_Index - Start_Page_Index).getValue();
		setTitle(currentTitle);
		Toast.makeText(DetailActivity.this, "开始阅读："+currentTitle, Toast.LENGTH_SHORT).show();
		if (Is_Load_Page_By_Url) {
			webView.loadUrl(loadUrl);
		} else {
			webView.loadDataWithBaseURL(null,
					getFileContent(DetailActivity.this, loadUrl.substring("file:///android_asset/".length())),
					"text/html", UTF8, null);
		}
		this.scrollY = scrollY;
		setButtonVisibleAndSaveState();
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		initRequrePointPreference();

		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setPageInfo("file:///android_asset/" + Page_Prefix + (--Current_Page_Index) + Page_Suffix, 0);
			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (canView(Current_Page_Index + 1)) {
					setPageInfo("file:///android_asset/" + Page_Prefix + (++Current_Page_Index) + Page_Suffix, 0);
				}
			}
		});

		Bundle bundle = getIntent().getExtras();
		webView = (MyWebView) findViewById(R.id.webView);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setDefaultFixedFontSize(18);

		webView.setPictureListener(new MyPictureListener());

		boolean startByMenu = bundle.getBoolean("startByMenu");
		if (startByMenu) {
			int selectMenu = Integer.valueOf(bundle.getString("menu"));
			if (canView(selectMenu)) {
				Current_Page_Index = selectMenu;
				setPageInfo("file:///android_asset/" + Page_Prefix + Current_Page_Index + Page_Suffix, 0);
			} else {
				Current_Page_Index = PreferenceUtil.getTxtIndex(this);
				setPageInfo("file:///android_asset/" + Page_Prefix + Current_Page_Index + Page_Suffix,
						PreferenceUtil.getScrollY(DetailActivity.this));
			}
		} else {
			Current_Page_Index = PreferenceUtil.getTxtIndex(this);
			setPageInfo("file:///android_asset/" + Page_Prefix + Current_Page_Index + Page_Suffix,
					PreferenceUtil.getScrollY(DetailActivity.this));
		}

		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setText("更多免费精品下载...");
		offers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
			}
		});
		if (firstComeIn) {
			new AlertDialog.Builder(DetailActivity.this).setTitle("说明")
					.setMessage("1.按【手机菜单键(Menu)】可以选择目录。\n2.下载更多精品应用...")
					.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			firstComeIn = false;
		}

		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

	}

	protected void onDestroy() {
		webView.destroyDrawingCache();
		webView.destroy();
		super.onDestroy();
	}

	protected void onPause() {
		saveState();
		super.onPause();
	}

	// 保存当前页和滚动位置
	private void saveState() {
		PreferenceUtil.setScrollY(this, webView.getScrollY());
		PreferenceUtil.setTxtIndex(this, Current_Page_Index);
	}

	private void setButtonVisibleAndSaveState() {
		saveState();
		
		if (Current_Page_Index == Start_Page_Index) {
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Index == Max_Page_Index) {
			btnNext.setVisibility(View.INVISIBLE);
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
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
			startActivity(intent);
			finish();
		} else if (paramMenuItem.getItemId() == 1) {
			// 显示推荐安装程序（Offer）.
			AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	private void showGetPointDialog(String type) {
		new AlertDialog.Builder(DetailActivity.this).setIcon(R.drawable.happy2).setTitle("当前积分：" + currentPointTotal)
				.setMessage("只要积分满足" + requirePoint + "，就可以" + type + "！\n 您当前的积分不足" + requirePoint + "哦。")
				.setPositiveButton("免费获得积分", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
					}
				}).show();
	}

	private void initRequrePointPreference() {
		hasEnoughRequrePointPreferenceValue = PreferenceUtil.getHasEnoughRequrePoint(DetailActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughRequrePointPreferenceValue) {
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
		if (pointTotal >= requirePoint) {
			hasEnoughRequrePointPreferenceValue = true;
			PreferenceUtil.setHasEnoughRequrePoint(DetailActivity.this, true);
		}
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		hasEnoughRequrePointPreferenceValue = false;
	}
	public String getFileContent(Context context, String name ) {
		InputStream in = null;
		BufferedReader bufferedReader = null;
		StringBuilder sBuffer = new StringBuilder("");
		try {
			in = getAssets().open(name);

			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				sBuffer.append(strLine.replace("\\n", "<br/><br/>") + "\n<br/><br/>");
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

package com.cxl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.cxl.lxpwb.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class DetailActivity extends Activity {

	private WebView webView;
	private String menu;
	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF8";

	public static int Current_Page_Value = 0;
	Button btnPrevious;
	Button btnNext;
	public static final int Page_Sum = MainActivity.MENU_List.size() - 1;// 由0开始，减去1，
	private int scrollY = 0;

	class MyPictureListener implements PictureListener {
		public void onNewPicture(WebView view, Picture arg1) {
			// put code here that needs to run when the page has finished
			// loading and
			// a new "picture" is on the webview.
			webView.scrollTo(0, scrollY);
		}
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		btnPrevious = (Button) findViewById(R.id.previous);
		btnPrevious.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				webView.loadUrl("file:///android_asset/chapter"
						+ (--Current_Page_Value) + ".html");
				scrollY = 0;
				setButtonVisibleAndSaveState();
			}
		});
		btnNext = (Button) findViewById(R.id.next);
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				webView.loadUrl("file:///android_asset/chapter"
						+ (++Current_Page_Value) + ".html");
				scrollY = 0;
				setButtonVisibleAndSaveState();
			}
		});

		Bundle bundle = getIntent().getExtras();
		webView = (WebView) findViewById(R.id.webView);
		
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		webView.setPictureListener(new MyPictureListener());

		boolean startByMenu = bundle.getBoolean("startByMenu");
		if (startByMenu) {
			menu = bundle.getString("menu");
			Current_Page_Value = Integer.valueOf(menu);
			webView.loadUrl("file:///android_asset/chapter"
					+ Current_Page_Value + ".html");
		} else {
			Current_Page_Value = Util.getTxtIndex(this);
			if (Current_Page_Value > Page_Sum) {
				Current_Page_Value = Page_Sum;
			}
			webView.loadUrl("file:///android_asset/chapter"
					+ Current_Page_Value + ".html");

			scrollY = Util.getScrollY(DetailActivity.this);
		}
		setButtonVisibleAndSaveState();

		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setText("更多免费精品下载....");
		offers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(DetailActivity.this).showOffers(
						DetailActivity.this);
			}
		});

		// returnButton = (Button) findViewById(R.id.returnButton);
		// returnButton.setOnClickListener(new Button.OnClickListener() {
		// public void onClick(View arg0) {
		// setButtonVisibleAndSaveState();
		// // finish();
		// Intent intent = new Intent();
		// intent.setClass(DetailActivity.this, MainActivity.class);
		// startActivity(intent);
		// finish();
		// }
		// });

		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20

	}
	protected void onDestroy(){
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
		Util.setScrollY(this, webView.getScrollY());
		Util.setTxtIndex(this, Current_Page_Value);
	}

	private void setButtonVisibleAndSaveState() {
		saveState();
		String currentTitle = MainActivity.MENU_List.get(Current_Page_Value)
				.getValue();
		setTitle(currentTitle);
		if (Current_Page_Value == 0) {
			btnPrevious.setVisibility(View.INVISIBLE);
		} else {
			btnPrevious.setVisibility(View.VISIBLE);
		}
		if (Current_Page_Value == Page_Sum) {
			btnNext.setVisibility(View.INVISIBLE);
		} else {
			btnNext.setVisibility(View.VISIBLE);
		}
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		SubMenu menu = paramMenu.addSubMenu(0, 0, 0, "郎咸平简介");
		SubMenu menu2 = paramMenu.addSubMenu(0, 1, 0, "更多免费精品下载...");
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == 0) {
			new AlertDialog.Builder(DetailActivity.this)
					.setTitle("郎咸平简介")
					.setMessage(
							"郎咸平，经济学家。他用开阔的视角，独到的分析阐述各种经济问题，并且像曼昆一样，用听的懂的语言解释经济现象。1956年6月21日生于中国台湾桃园县，父亲是国民党空军少将，母亲是高中化学名师，其父母系东北大学同学。郎咸平目前在香港中文大学任教，其父母回上海居住。1974-1978年就读于台湾东海大学经济系，之后就读台湾大学经济学研究所。1980年获台大经济学硕士学位，硕士毕业后，服过两年义务兵役，当过记者。1983年（时年27岁）在母亲的信任和大力支持下赴美留学，就读宾夕法尼亚大学沃顿商学院金融系。1985年获金融学硕士学位，1986年获金融学博士学位（corporate finance，即偏向公司金融）。 ")
					.setPositiveButton("更多免费精品下载...",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									// 显示推荐安装程序（Offer）.
									AppConnect.getInstance(DetailActivity.this)
											.showOffers(DetailActivity.this);
								}
							}).show();
		}
		if (paramMenuItem.getItemId() == 1) {
			// 显示推荐安装程序（Offer）.
			AppConnect.getInstance(DetailActivity.this).showOffers(
					DetailActivity.this);
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

}

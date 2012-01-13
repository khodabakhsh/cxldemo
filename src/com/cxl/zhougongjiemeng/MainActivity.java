package com.cxl.zhougongjiemeng;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cxl.zhougongjiemeng.util.DataParser;
import com.cxl.zhougongjiemeng.util.IndexParser;
import com.cxl.zhougongjiemeng.util.PreferenceUtil;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	private static String js;
	private static String style;
	Handler dh;
	private LinearLayout l;

	private ArrayAdapter<String> s1_aspn;
	private ArrayAdapter<String> s2_aspn;
	private WebView web;

	private static String selectType;
	private static String selectDetail;

	DataParser dataParser = new DataParser(this);

	Handler msgHandler = new Handler();
	Handler titleHandler = new Handler();

	public static boolean hasEnoughRequreAdPointPreferenceValue = false;// 保存在配置里
	public static final int requireAdPoint = 60;// 要求积分
	public static int currentPointTotal = 0;// 当前积分

	public static boolean hasShowMyDialog = false;

	public void onCreate(Bundle paramBundle) {
		AppConnect.getInstance(this);
		super.onCreate(paramBundle);
		setContentView(R.layout.main);
		this.web = (WebView) findViewById(R.id.more_webview);
		this.web.getSettings().setJavaScriptEnabled(true);
		Hashtable localHashtable = IndexParser.getInstance(this).getResult();
		ArrayList<String> localArrayList = new ArrayList<String>();
		Enumeration localEnumeration = localHashtable.keys();

		Spinner localSpinner1 = (Spinner) findViewById(R.id.Spinner01);
		final Spinner localSpinner2 = (Spinner) findViewById(R.id.Spinner02);

		final IndexParser indexParser = IndexParser.getInstance(this);
		while (localEnumeration.hasMoreElements()) {
			String str3 = (String) localEnumeration.nextElement();
			localArrayList.add(str3);
			// style = getResources().getString(2131034120);
			// js = getResources().getString(2131034119);
		}
		this.s1_aspn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, localArrayList);
		s1_aspn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		localSpinner1.setAdapter(s1_aspn);

		localSpinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				selectType = arg0.getSelectedItem().toString();
				String typeDetail = (String) indexParser.getResult().get(selectType);

				s2_aspn = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, typeDetail
						.split("#"));
				s2_aspn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				localSpinner2.setAdapter(s2_aspn);
				selectDetail = (String) s2_aspn.getItem(0);
				web.loadDataWithBaseURL(null, dataParser.getResult(selectType, selectDetail), "text/html", "UTF-8",
						null);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		String typeDetail = (String) indexParser.getResult().get((String) s1_aspn.getItem(0));

		this.s2_aspn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeDetail.split("#"));
		s2_aspn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		localSpinner2.setAdapter(s2_aspn);
		localSpinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				selectDetail = arg0.getSelectedItem().toString();
				web.loadDataWithBaseURL(null, dataParser.getResult(selectType, selectDetail), "text/html", "UTF-8",
						null);
				titleHandler.post(new Runnable() {
					public void run() {
						setTitle("周公解梦【 "+selectType+" --> "+selectDetail+" 】");
					}
				});
				if (!hasEnoughRequreAdPointPreferenceValue) {
					showMyDialog();
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		selectType = (String) s1_aspn.getItem(0);
		selectDetail = (String) s2_aspn.getItem(0);
		web.loadDataWithBaseURL(null, dataParser.getResult(selectType, selectDetail), "text/html", "UTF-8", null);
		initRequrePointPreference();
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	private void initRequrePointPreference() {
		hasEnoughRequreAdPointPreferenceValue = PreferenceUtil.getHasEnoughRequreAdPoint(MainActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughRequreAdPointPreferenceValue) {
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
			hasEnoughRequreAdPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughRequreAdPoint(MainActivity.this, true);
		}
	}

	private void showMyDialog() {
		if (hasShowMyDialog)
		{
			return;
		}
		
		msgHandler.post(new Runnable() {
			public void run() {
				new AlertDialog.Builder(MainActivity.this)
						.setIcon(R.drawable.happy2)
						.setTitle("感谢使用本程序")
						.setMessage(
								"说明：本程序的一切提示信息，在积分满足" + requireAdPoint
										+ "后，自动消除！可通过【免费赚积分】，获得积分。\n\n通过【更多应用】，可以下载各种好玩应用。\n\n当前积分："
										+ currentPointTotal)
						.setPositiveButton("更多应用", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								hasShowMyDialog = false;
								AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
							}
						}).setNeutralButton("免费赚积分", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								hasShowMyDialog = false;
								AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
							}
						}).setNegativeButton("继续", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								hasShowMyDialog = false;
							}
						}).show();
			}
		});
		hasShowMyDialog = true;
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		hasEnoughRequreAdPointPreferenceValue = false;
	}
}
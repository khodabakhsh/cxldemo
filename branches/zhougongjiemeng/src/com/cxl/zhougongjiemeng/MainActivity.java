package com.cxl.zhougongjiemeng;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import android.app.Activity;
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

public class MainActivity extends Activity {
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

	public void onCreate(Bundle paramBundle) {
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
		this.s1_aspn = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, localArrayList);
		s1_aspn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		localSpinner1.setAdapter(s1_aspn);

		localSpinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selectType = arg0.getSelectedItem().toString();
				String typeDetail = (String) indexParser.getResult().get(
						selectType);

				s2_aspn = new ArrayAdapter<String>(MainActivity.this,
						android.R.layout.simple_spinner_item, typeDetail
								.split("#"));
				s2_aspn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				localSpinner2.setAdapter(s2_aspn);
				selectDetail = (String) s2_aspn.getItem(0);
				web.loadDataWithBaseURL(null,
						dataParser.getResult(selectType, selectDetail),
						"text/html", "UTF-8", null);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		String typeDetail = (String) indexParser.getResult().get(
				(String) s1_aspn.getItem(0));

		this.s2_aspn = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, typeDetail.split("#"));
		s2_aspn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		localSpinner2.setAdapter(s2_aspn);
		localSpinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selectDetail = arg0.getSelectedItem().toString();
				web.loadDataWithBaseURL(null,
						dataParser.getResult(selectType, selectDetail),
						"text/html", "UTF-8", null);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		selectType = (String) s1_aspn.getItem(0);
		selectDetail = (String) s2_aspn.getItem(0);
		web.loadDataWithBaseURL(null,
				dataParser.getResult(selectType, selectDetail), "text/html",
				"UTF-8", null);
	}
}
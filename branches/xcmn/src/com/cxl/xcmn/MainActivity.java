package com.cxl.xcmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements
		AdapterView.OnItemClickListener {

	private static String img = "img";
	private static String text = "text";
	static final int ICON_COUNT = 8;
	private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	private static boolean hasInited = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		String[] arrayOfString = new String[2];
		arrayOfString[0] = text;
		arrayOfString[1] = img;
		if (!hasInited) {
			getData();
			hasInited = true;
		}
		int[] arrayOfInt = new int[2];
		arrayOfInt[0] = R.id.gridtext;
		arrayOfInt[1] = R.id.gridimg;
		SimpleAdapter localSimpleAdapter = new SimpleAdapter(this, list,
				R.layout.gridview_child, arrayOfString, arrayOfInt);
		GridView localGridView = (GridView) findViewById(R.id.gridview);
		localGridView.setAdapter(localSimpleAdapter);
		localGridView.setOnItemClickListener(this);

	}

	private List<Map<String, Object>> getData() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < ICON_COUNT; i++) {
			map = new HashMap<String, Object>();
			map.put(img,
					getResources().getIdentifier(img + "_" + i, "drawable",
							getPackageName()));
			map.put(text, getString(getResources().getIdentifier("text"+(i + 1), "string", getPackageName())));
			list.add(map);
		}

		return list;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent localIntent = new Intent();
		Bundle localBundle = new Bundle();
		String str = String.valueOf(arg2);
		localBundle.putString("position", str);
		localIntent.setClass(this, ImageActivity.class);
		localIntent.putExtras(localBundle);
		startActivity(localIntent);
	}


}
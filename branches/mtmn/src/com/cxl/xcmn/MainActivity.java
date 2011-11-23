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
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.cxl.mtmn.R;
import com.waps.AdView;
import com.waps.AppConnect;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

	private static String img = "img";
	private static String text = "text";
	static final int TYPE_COUNT = 10;
	private static List<Map<String, Object>> Grid_Adapter_List = new ArrayList<Map<String, Object>>();

	private static boolean hasInited = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

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
		SimpleAdapter localSimpleAdapter = new SimpleAdapter(this, Grid_Adapter_List, R.layout.gridview_child,
				arrayOfString, arrayOfInt);
		GridView localGridView = (GridView) findViewById(R.id.gridview);
		localGridView.setAdapter(localSimpleAdapter);
		localGridView.setOnItemClickListener(this);

	}

	private List<Map<String, Object>> getData() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < TYPE_COUNT; i++) {
			map = new HashMap<String, Object>();
			map.put(img, getResources().getIdentifier(img + "_" + i, "drawable", getPackageName()));
			map.put(text, getString(getResources().getIdentifier("text" + (i + 1), "string", getPackageName())));
			Grid_Adapter_List.add(map);
		}

		return Grid_Adapter_List;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent imageIntent = new Intent();
		Bundle localBundle = new Bundle();
		localBundle.putInt("typeIndex", arg2);
		imageIntent.setClass(this, ImageActivity.class);
		imageIntent.putExtras(localBundle);
		startActivity(imageIntent);
	}

	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	protected void onResume() {
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout1);
		new AdView(this, container).DisplayAd(20);//每20秒轮换一次广告；最少为20 
		super.onResume();
	}

}
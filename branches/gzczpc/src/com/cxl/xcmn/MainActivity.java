package com.cxl.xcmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.cxl.gzczpc.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements
		AdapterView.OnItemClickListener{

	private static String img = "img";
	private static String text = "text";
	static int TYPE_COUNT = 10;
	private static List<Map<String, Object>> Grid_Adapter_List = new ArrayList<Map<String, Object>>();

	private static boolean hasInitedAdapterData = false;
	private static boolean hasInitedTypeCount = false;

	SimpleAdapter localSimpleAdapter;
	GridView localGridView;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		String[] arrayOfString = new String[2];
		arrayOfString[0] = text;
		arrayOfString[1] = img;
		if (!hasInitedTypeCount) {
			initTypeCount();
			hasInitedTypeCount = true;
		}
		if (!hasInitedAdapterData) {
			getData();
			hasInitedAdapterData = true;
		}
		int[] arrayOfInt = new int[2];
		arrayOfInt[0] = R.id.gridtext;
		arrayOfInt[1] = R.id.gridimg;
		localSimpleAdapter = new SimpleAdapter(this, Grid_Adapter_List,
				R.layout.gridview_child, arrayOfString, arrayOfInt);
		localGridView = (GridView) findViewById(R.id.gridview);
		localGridView.setAdapter(localSimpleAdapter);
		localGridView.setOnItemClickListener(this);

		// LinearLayout container = (LinearLayout)
		// findViewById(R.id.AdLinearLayout1);
		// new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

	private void initTypeCount() {
		for (int i = 1; i < 15; i++) {
			if (getResources().getIdentifier("text" + i, "string",
					getPackageName()) == 0) {
				break;
			}
			TYPE_COUNT = i;
		}
	}

	private List<Map<String, Object>> getData() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < TYPE_COUNT; i++) {
			map = new HashMap<String, Object>();
			map.put(img,
					getResources().getIdentifier(img + i + "_0_icon",
							"drawable", getPackageName()));
			map.put(text,
					getString(getResources().getIdentifier("text" + (i + 1),
							"string", getPackageName())));
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
}
package com.cxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cxl.birthsecret.R;
import com.waps.AppConnect;

public class MainActivity extends Activity {

	private ArrayAdapter<String> yueAdapter;
	private ArrayAdapter<String> riAdapter;
	Button queryButton;
	Spinner yue;
	Spinner ri;
	public static Map<String, String> ConstellationNameIdMap = new HashMap<String, String>();

	private static final String[] Yue_Array = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	private static final Map<String, List<String>> Yue_Ri_Map = initYueRi();

	private static Map<String, List<String>> initYueRi() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> dayListOf29 = new ArrayList<String>();
		for (int i = 1; i <= 29; i++) {
			dayListOf29.add(String.valueOf(i));
		}
		List<String> dayListOf30 = new ArrayList<String>();
		for (int i = 1; i <= 30; i++) {
			dayListOf30.add(String.valueOf(i));
		}
		List<String> dayListOf31 = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			dayListOf31.add(String.valueOf(i));
		}
		map.put("1", dayListOf31);
		map.put("2", dayListOf29);
		map.put("3", dayListOf31);
		map.put("4", dayListOf30);
		map.put("5", dayListOf31);
		map.put("6", dayListOf30);
		map.put("7", dayListOf31);
		map.put("8", dayListOf31);
		map.put("9", dayListOf30);
		map.put("10", dayListOf31);
		map.put("11", dayListOf30);
		map.put("12", dayListOf31);
		return map;
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		yue = (Spinner) findViewById(R.id.yue);

		yueAdapter = new ArrayAdapter<String>(this, R.layout.vlist, R.id.name, Yue_Array);
		yue.setAdapter(yueAdapter);
		yue.setSelection(0);
		yue.setPrompt("请选择月");
		yue.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Object selectedId = yue.getSelectedItem();
				riAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.vlist, R.id.name, Yue_Ri_Map
						.get(selectedId));
				ri.setAdapter(riAdapter);
				ri.setSelection(0);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		ri = (Spinner) findViewById(R.id.ri);
		ri.setPrompt("请选择日");
		riAdapter = new ArrayAdapter<String>(this, R.layout.vlist, R.id.name, Yue_Ri_Map.get(yue.getSelectedItem()));

		queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String selectedYue = (String) yue.getSelectedItem();
				String selectedRi = (String) ri.getSelectedItem();

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("yue", selectedYue);
				bundle.putString("ri", selectedRi);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

	}
}
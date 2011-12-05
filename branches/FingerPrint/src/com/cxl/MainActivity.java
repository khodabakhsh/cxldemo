package com.cxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.cxl.fingerprint.R;
import com.waps.AppConnect;

public class MainActivity extends Activity {

	private SimpleAdapter fingerPrintAdapter;
	Button queryButton;
	Spinner wo1;
	Spinner wo2;
	Spinner wo3;
	Spinner wo4;
	Spinner wo5;
	
	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		wo1 = (Spinner) findViewById(R.id.wo1);
		wo2 = (Spinner) findViewById(R.id.wo2);
		wo3 = (Spinner) findViewById(R.id.wo3);
		wo4 = (Spinner) findViewById(R.id.wo4);
		wo5 = (Spinner) findViewById(R.id.wo5);

		fingerPrintAdapter = new SimpleAdapter(this, getData(), R.layout.vlist, new String[] { "id", "name",
				 "img" }, new int[] { R.id.id, R.id.name,  R.id.img });

		wo1.setAdapter(fingerPrintAdapter);
		wo2.setAdapter(fingerPrintAdapter);
		wo3.setAdapter(fingerPrintAdapter);
		wo4.setAdapter(fingerPrintAdapter);
		wo5.setAdapter(fingerPrintAdapter);

		wo1.setPrompt("请选择指纹纹路");
		wo2.setPrompt("请选择指纹纹路");
		wo3.setPrompt("请选择指纹纹路");
		wo4.setPrompt("请选择指纹纹路");
		wo5.setPrompt("请选择指纹纹路");

		queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new Button.OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				String selectedWo1 = ((Map<String, String>) wo1.getSelectedItem()).get("id");
				String selectedWo2 = ((Map<String, String>) wo2.getSelectedItem()).get("id");
				String selectedWo3 = ((Map<String, String>) wo3.getSelectedItem()).get("id");
				String selectedWo4 = ((Map<String, String>) wo4.getSelectedItem()).get("id");
				String selectedWo5 = ((Map<String, String>) wo5.getSelectedItem()).get("id");
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("wo1", selectedWo1);
				bundle.putString("wo2", selectedWo2);
				bundle.putString("wo3", selectedWo3);
				bundle.putString("wo4", selectedWo4);
				bundle.putString("wo5", selectedWo5);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		map.put("id", "1");
		map.put("name", "涡纹");
		map.put("img", R.drawable.one);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "0");
		map.put("name", "流纹");
		map.put("img", R.drawable.zero);
		list.add(map);
		return list;
	}
}
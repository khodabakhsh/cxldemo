package com.cxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cxl.fingerprint.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {

	private SimpleAdapter fingerPrintAdapter;
	Button queryButton;
	Spinner wo1;
	Spinner wo2;
	Spinner wo3;
	Spinner wo4;
	Spinner wo5;
	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 60;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分

	final Handler mHandler = new Handler();

	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				if (update_text) {
					pointsTextView.setText(displayText);
					update_text = false;
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		AppConnect.getInstance(this).getPoints(this);
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
		if (currentPointTotal >= requirePoint) {
			hasEnoughRequrePoint = true;
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
		mHandler.post(mUpdateResults);
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		currentPointTotal = 0;
		update_text = true;
		displayText = error;
		mHandler.post(mUpdateResults);
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
				"dateRange", "img" }, new int[] { R.id.id, R.id.name, R.id.dateRange, R.id.img });

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
		map.put("dateRange", "「涡纹」即指纹整体纹路，呈现似旋涡状，纹路由内一圈一圈向外");
		map.put("img", R.drawable.one);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "0");
		map.put("name", "流纹");
		map.put("dateRange", "「流纹」意指其指纹之纹路是无法成为似旋涡状的纹路，其指纹纹路呈现似外流的现象");
		map.put("img", R.drawable.zero);
		list.add(map);
		return list;
	}
}
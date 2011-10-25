package com.cxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.style;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cxl.constellationpair.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {
	Button queryButton;
	Spinner firstConstellation;
	Spinner secondConstellation;
	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 80;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分
	public static Map<String, String> ConstellationNameIdMap = new ConstellationPairUtil()
			.getConstellationPair();

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
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.vlist,
				new String[] { "id", "name", "dateRange", "img" }, new int[] {
						R.id.id, R.id.name, R.id.dateRange, R.id.img });
		firstConstellation = (Spinner) findViewById(R.id.firstConstellation);
		secondConstellation = (Spinner) findViewById(R.id.secondConstellation);
		firstConstellation.setPrompt("请选择星座");
		secondConstellation.setPrompt("请选择星座");
		firstConstellation.setAdapter(adapter);
		secondConstellation.setAdapter(adapter);
		queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
					String firstName = ((Map<String, String>) firstConstellation
							.getSelectedItem()).get("name");
					String secondName = ((Map<String, String>) secondConstellation
							.getSelectedItem()).get("name");
					String id = ConstellationNameIdMap.get(firstName + "和"
							+ secondName);

					Intent intent = new Intent();
					intent.setClass(MainActivity.this, DetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("id", id);
					intent.putExtras(bundle);
					startActivity(intent);
			}
		});
		// queryButton.setBackgroundDrawable(d)

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

	}
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		map.put("id", "1");
		map.put("name", "白羊座");
		map.put("dateRange", "3.21-4.20");
		map.put("img", R.drawable.one);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "2");
		map.put("name", "金牛座");
		map.put("dateRange", "4.21-5.20");
		map.put("img", R.drawable.two);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "3");
		map.put("name", "双子座");
		map.put("dateRange", "5.21-6.21");
		map.put("img", R.drawable.three);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "4");
		map.put("name", "巨蟹座");
		map.put("dateRange", "6.22-7.22");
		map.put("img", R.drawable.four);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "5");
		map.put("name", "狮子座");
		map.put("dateRange", "7.23-8.22");
		map.put("img", R.drawable.five);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "6");
		map.put("name", "处女座");
		map.put("dateRange", "8.23-9.22");
		map.put("img", R.drawable.six);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "7");
		map.put("name", "天秤座");
		map.put("dateRange", "9.23-10.22");
		map.put("img", R.drawable.seven);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "8");
		map.put("name", "天蝎座");
		map.put("dateRange", "10.23-11.21");
		map.put("img", R.drawable.eight);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "9");
		map.put("name", "射手座");
		map.put("dateRange", "11.22-12.21");
		map.put("img", R.drawable.nine);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "10");
		map.put("name", "摩羯座");
		map.put("dateRange", "12.22-1.19");
		map.put("img", R.drawable.ten);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "11");
		map.put("name", "水瓶座");
		map.put("dateRange", "1.20-2.19");
		map.put("img", R.drawable.eleven);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "12");
		map.put("name", "双鱼座");
		map.put("dateRange", "2.20-3.20");
		map.put("img", R.drawable.twelve);
		list.add(map);

		return list;
	}
}
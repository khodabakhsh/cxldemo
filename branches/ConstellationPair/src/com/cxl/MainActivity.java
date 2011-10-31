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
	public static final int requirePoint = 60;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分
	public static Map<String, String> ConstellationNameIdMap  = null;
	static{
		ConstellationNameIdMap = new HashMap<String, String>();
		ConstellationNameIdMap.put("双子座和天秤座","258" );
		ConstellationNameIdMap.put("白羊座和狮子座","284" );
		ConstellationNameIdMap.put("双鱼座和白羊座","156" );
		ConstellationNameIdMap.put("天秤座和金牛座","215" );
		ConstellationNameIdMap.put("摩羯座和白羊座","180" );
		ConstellationNameIdMap.put("处女座和天秤座","222" );
		ConstellationNameIdMap.put("射手座和狮子座","188" );
		ConstellationNameIdMap.put("巨蟹座和狮子座","248" );
		ConstellationNameIdMap.put("双子座和摩羯座","255" );
		ConstellationNameIdMap.put("双子座和白羊座","264" );
		ConstellationNameIdMap.put("射手座和双子座","190" );
		ConstellationNameIdMap.put("天秤座和白羊座","216" );
		ConstellationNameIdMap.put("狮子座和天秤座","234" );
		ConstellationNameIdMap.put("天秤座和双子座","214" );
		ConstellationNameIdMap.put("水瓶座和处女座","163" );
		ConstellationNameIdMap.put("双子座和射手座","256" );
		ConstellationNameIdMap.put("天蝎座和处女座","199" );
		ConstellationNameIdMap.put("白羊座和双子座","286" );
		ConstellationNameIdMap.put("狮子座和天蝎座","233" );
		ConstellationNameIdMap.put("金牛座和白羊座","276" );
		ConstellationNameIdMap.put("狮子座和巨蟹座","237" );
		ConstellationNameIdMap.put("金牛座和天秤座","270" );
		ConstellationNameIdMap.put("摩羯座和巨蟹座","177" );
		ConstellationNameIdMap.put("白羊座和天蝎座","281" );
		ConstellationNameIdMap.put("巨蟹座和摩羯座","243" );
		ConstellationNameIdMap.put("处女座和天蝎座","221" );
		ConstellationNameIdMap.put("巨蟹座和水瓶座","242" );
		ConstellationNameIdMap.put("水瓶座和巨蟹座","165" );
		ConstellationNameIdMap.put("双鱼座和处女座","151" );
		ConstellationNameIdMap.put("白羊座和水瓶座","278" );
		ConstellationNameIdMap.put("金牛座和天蝎座","269" );
		ConstellationNameIdMap.put("处女座和射手座","220" );
		ConstellationNameIdMap.put("双子座和天蝎座","257" );
		ConstellationNameIdMap.put("天蝎座和双鱼座","193" );
		ConstellationNameIdMap.put("白羊座和双鱼座","277" );
		ConstellationNameIdMap.put("处女座和白羊座","228" );
		ConstellationNameIdMap.put("摩羯座和狮子座","176" );
		ConstellationNameIdMap.put("金牛座和双子座","274" );
		ConstellationNameIdMap.put("天蝎座和天秤座","198" );
		ConstellationNameIdMap.put("巨蟹座和天蝎座","245" );
		ConstellationNameIdMap.put("白羊座和巨蟹座","285" );
		ConstellationNameIdMap.put("双子座和水瓶座","254" );
		ConstellationNameIdMap.put("摩羯座和金牛座","179" );
		ConstellationNameIdMap.put("双鱼座和天秤座","150" );
		ConstellationNameIdMap.put("狮子座和水瓶座","230" );
		ConstellationNameIdMap.put("处女座和双鱼座","217" );
		ConstellationNameIdMap.put("天秤座和水瓶座","206" );
		ConstellationNameIdMap.put("双子座和双鱼座","253" );
		ConstellationNameIdMap.put("双鱼座和摩羯座","147" );
		ConstellationNameIdMap.put("巨蟹座和射手座","244" );
		ConstellationNameIdMap.put("双子座和金牛座","263" );
		ConstellationNameIdMap.put("天蝎座和白羊座","204" );
		ConstellationNameIdMap.put("水瓶座和狮子座","164" );
		ConstellationNameIdMap.put("射手座和处女座","187" );
		ConstellationNameIdMap.put("天蝎座和巨蟹座","201" );
		ConstellationNameIdMap.put("射手座和天蝎座","185" );
		ConstellationNameIdMap.put("摩羯座和双鱼座","169" );
		ConstellationNameIdMap.put("金牛座和处女座","271" );
		ConstellationNameIdMap.put("狮子座和金牛座","239" );
		ConstellationNameIdMap.put("巨蟹座和双子座","250" );
		ConstellationNameIdMap.put("处女座和处女座","223" );
		ConstellationNameIdMap.put("射手座和双鱼座","181" );
		ConstellationNameIdMap.put("天蝎座和水瓶座","194" );
		ConstellationNameIdMap.put("射手座和金牛座","191" );
		ConstellationNameIdMap.put("摩羯座和摩羯座","171" );
		ConstellationNameIdMap.put("天秤座和射手座","208" );
		ConstellationNameIdMap.put("狮子座和白羊座","240" );
		ConstellationNameIdMap.put("狮子座和射手座","232" );
		ConstellationNameIdMap.put("射手座和射手座","184" );
		ConstellationNameIdMap.put("双鱼座和金牛座","155" );
		ConstellationNameIdMap.put("水瓶座和双鱼座","157" );
		ConstellationNameIdMap.put("双子座和巨蟹座","261" );
		ConstellationNameIdMap.put("射手座和天秤座","186" );
		ConstellationNameIdMap.put("巨蟹座和处女座","247" );
		ConstellationNameIdMap.put("双子座和双子座","262" );
		ConstellationNameIdMap.put("金牛座和射手座","268" );
		ConstellationNameIdMap.put("狮子座和狮子座","236" );
		ConstellationNameIdMap.put("处女座和巨蟹座","225" );
		ConstellationNameIdMap.put("摩羯座和双子座","178" );
		ConstellationNameIdMap.put("射手座和白羊座","192" );
		ConstellationNameIdMap.put("双鱼座和天蝎座","149" );
		ConstellationNameIdMap.put("狮子座和双子座","238" );
		ConstellationNameIdMap.put("摩羯座和天蝎座","173" );
		ConstellationNameIdMap.put("白羊座和白羊座","288" );
		ConstellationNameIdMap.put("狮子座和双鱼座","229" );
		ConstellationNameIdMap.put("摩羯座和天秤座","174" );
		ConstellationNameIdMap.put("狮子座和摩羯座","231" );
		ConstellationNameIdMap.put("水瓶座和天秤座","162" );
		ConstellationNameIdMap.put("射手座和摩羯座","183" );
		ConstellationNameIdMap.put("白羊座和处女座","283" );
		ConstellationNameIdMap.put("处女座和狮子座","224" );
		ConstellationNameIdMap.put("天蝎座和狮子座","200" );
		ConstellationNameIdMap.put("双鱼座和巨蟹座","153" );
		ConstellationNameIdMap.put("天蝎座和金牛座","203" );
		ConstellationNameIdMap.put("处女座和摩羯座","219" );
		ConstellationNameIdMap.put("射手座和巨蟹座","189" );
		ConstellationNameIdMap.put("摩羯座和水瓶座","170" );
		ConstellationNameIdMap.put("水瓶座和水瓶座","158" );
		ConstellationNameIdMap.put("处女座和双子座","226" );
		ConstellationNameIdMap.put("摩羯座和处女座","175" );
		ConstellationNameIdMap.put("摩羯座和射手座","172" );
		ConstellationNameIdMap.put("金牛座和双鱼座","265" );
		ConstellationNameIdMap.put("水瓶座和天蝎座","161" );
		ConstellationNameIdMap.put("天秤座和狮子座","212" );
		ConstellationNameIdMap.put("金牛座和水瓶座","266" );
		ConstellationNameIdMap.put("双子座和处女座","259" );
		ConstellationNameIdMap.put("水瓶座和摩羯座","159" );
		ConstellationNameIdMap.put("天蝎座和双子座","202" );
		ConstellationNameIdMap.put("天蝎座和射手座","196" );
		ConstellationNameIdMap.put("水瓶座和双子座","166" );
		ConstellationNameIdMap.put("天秤座和天秤座","210" );
		ConstellationNameIdMap.put("天秤座和天蝎座","209" );
		ConstellationNameIdMap.put("天蝎座和摩羯座","195" );
		ConstellationNameIdMap.put("双鱼座和射手座","148" );
		ConstellationNameIdMap.put("双鱼座和狮子座","152" );
		ConstellationNameIdMap.put("双鱼座和双子座","154" );
		ConstellationNameIdMap.put("处女座和水瓶座","218" );
		ConstellationNameIdMap.put("双鱼座和双鱼座","145" );
		ConstellationNameIdMap.put("巨蟹座和巨蟹座","249" );
		ConstellationNameIdMap.put("双子座和狮子座","260" );
		ConstellationNameIdMap.put("处女座和金牛座","227" );
		ConstellationNameIdMap.put("天蝎座和天蝎座","197" );
		ConstellationNameIdMap.put("狮子座和处女座","235" );
		ConstellationNameIdMap.put("天秤座和处女座","211" );
		ConstellationNameIdMap.put("白羊座和金牛座","287" );
		ConstellationNameIdMap.put("水瓶座和射手座","160" );
		ConstellationNameIdMap.put("巨蟹座和白羊座","252" );
		ConstellationNameIdMap.put("金牛座和摩羯座","267" );
		ConstellationNameIdMap.put("白羊座和天秤座","282" );
		ConstellationNameIdMap.put("射手座和水瓶座","182" );
		ConstellationNameIdMap.put("白羊座和摩羯座","279" );
		ConstellationNameIdMap.put("天秤座和巨蟹座","213" );
		ConstellationNameIdMap.put("巨蟹座和天秤座","246" );
		ConstellationNameIdMap.put("金牛座和巨蟹座","273" );
		ConstellationNameIdMap.put("巨蟹座和金牛座","251" );
		ConstellationNameIdMap.put("白羊座和射手座","280" );
		ConstellationNameIdMap.put("天秤座和摩羯座","207" );
		ConstellationNameIdMap.put("水瓶座和白羊座","168" );
		ConstellationNameIdMap.put("金牛座和金牛座","275" );
		ConstellationNameIdMap.put("巨蟹座和双鱼座","241" );
		ConstellationNameIdMap.put("水瓶座和金牛座","167" );
		ConstellationNameIdMap.put("金牛座和狮子座","272" );
		ConstellationNameIdMap.put("天秤座和双鱼座","205" );
		ConstellationNameIdMap.put("双鱼座和水瓶座","146" );

	}

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
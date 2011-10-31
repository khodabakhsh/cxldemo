package com.cxl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cxl.birthsecret.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {

	private ArrayAdapter<String> yueAdapter;
	Button queryButton;
	Spinner yue;
	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 60;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分

	private static String[] Yue_Array = null;
	static {
		String nationString = "回族 羌族 景颇族 纳西族 赫哲族 藏族 怒族 门巴族 苗族 佤族 仫佬族 珞巴族 彝族 京族 布朗族 基诺族 壮族 汉族 撒拉族 维吾尔族 傣族 蒙古族 毛南族 哈萨克族 满族 朝鲜族 锡伯族 俄罗斯族 侗族 土家族 阿昌族 瑶族 哈尼族 普米族 塔吉克族 白族 布依族 仡佬族 塔塔尔族 黎族 傈僳族 德昂族 鄂温克族 畲族 高山族 保安族 鄂伦春族 水族 拉祜族 裕固族 乌孜别克族 土族 东乡族 独龙族 柯尔克孜族";
		Yue_Array = nationString.split(" ");
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

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		yue = (Spinner) findViewById(R.id.yue);

		yueAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Yue_Array);
		// 设置下拉列表的风格
		yueAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yue.setAdapter(yueAdapter);
		yue.setSelection(0);
		yue.setPrompt("请选择要查询的民族");

		queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", String.valueOf(yue.getSelectedItemPosition()));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
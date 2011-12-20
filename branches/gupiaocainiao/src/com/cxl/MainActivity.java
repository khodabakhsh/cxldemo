package com.cxl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cxl.gupiaocainiao.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1", "1、股票的定义"));
		MENU_List.add(new KeyValue("2", "2、涨跌停板制度"));
		MENU_List.add(new KeyValue("3", "3、交易市场"));
		MENU_List.add(new KeyValue("4", "4、交易时间　　"));
		MENU_List.add(new KeyValue("5", "5、交易费用"));
		MENU_List.add(new KeyValue("6", "6、费用介绍"));
		MENU_List.add(new KeyValue("7", "7、股票分类"));
		MENU_List.add(new KeyValue("8", "8、代码命名规则"));
		MENU_List.add(new KeyValue("9", "9、炒股软件"));
		MENU_List.add(new KeyValue("10", "10、股东权利"));
		MENU_List.add(new KeyValue("11", "11、开户流程"));
		MENU_List.add(new KeyValue("12", "12、认购新股"));
		MENU_List.add(new KeyValue("13", "13、转托管"));
		MENU_List.add(new KeyValue("14", "14、过户"));
		MENU_List.add(new KeyValue("15", "15、基本名词解释"));
		MENU_List.add(new KeyValue("16", "16、正确的投资理念（炒股的正确理念）"));
		MENU_List.add(new KeyValue("17", "17、股市格言"));
		MENU_List.add(new KeyValue("18", "18、短线中线长线"));
		MENU_List.add(new KeyValue("19", "19、股票短线操作十大秘密"));
		MENU_List.add(new KeyValue("20", "20、股票短线操作六大法则"));
		MENU_List.add(new KeyValue("21", "21、中长线投资规避三类股"));
		MENU_List.add(new KeyValue("22", "22、什么是K线图"));
		MENU_List.add(new KeyValue("23", "23、如何看k线图"));
		MENU_List.add(new KeyValue("24", "24、k线图指标"));
		MENU_List.add(new KeyValue("25", "25、K线图分析技巧"));
		MENU_List.add(new KeyValue("26", "26、什么是成交量"));
		MENU_List.add(new KeyValue("27", "27、成交量的运用"));
		MENU_List.add(new KeyValue("28", "28、市场成交量与价格的关系"));
		MENU_List.add(new KeyValue("29", "29、成交量的五种形态"));
		MENU_List.add(new KeyValue("30", "30、移动平均线"));
		MENU_List.add(new KeyValue("31", "31、技术指标"));
		MENU_List.add(new KeyValue("32", "32、各类技术指标介绍"));
		MENU_List.add(new KeyValue("33", "33、MACD指标"));
		MENU_List.add(new KeyValue("34", "34、KDJ随机指标"));
		MENU_List.add(new KeyValue("35", "35、布林线指标"));
		MENU_List.add(new KeyValue("36", "36、什么是基本面"));
		MENU_List.add(new KeyValue("37", "37、影响基本面分析的重要因素"));
		MENU_List.add(new KeyValue("38", "38、基本面分析与技术分析的对比"));
		MENU_List.add(new KeyValue("39", "39、价值投资"));
		MENU_List.add(new KeyValue("40", "40、基本面投资与传统价值投资"));
		MENU_List.add(new KeyValue("41", "41、相对强弱指标RSI"));
		MENU_List.add(new KeyValue("42", "42、市盈率"));
		MENU_List.add(new KeyValue("43", "43、市净率"));
		MENU_List.add(new KeyValue("44", "44、每股收益"));
		MENU_List.add(new KeyValue("45", "45、做操盘手需要哪些条件？"));
		MENU_List.add(new KeyValue("46", "46、投资如做人六度助修心"));
		MENU_List.add(new KeyValue("47", "47、十大民间股神论剑 中小投资者应学点什么"));
		MENU_List.add(new KeyValue("48", "48、抄底是门复杂的学问 "));
		MENU_List.add(new KeyValue("49", "49、如何把握股市中的量价关系"));
		MENU_List.add(new KeyValue("50", "50、你是股市的最后一个傻瓜吗"));
	}



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		menuList = (ListView) findViewById(R.id.menuList);
		menuAdapter = new ArrayAdapter<KeyValue>(this,
				R.layout.simple_list_layout, R.id.txtListItem, MENU_List);
		menuList.setAdapter(menuAdapter);
		menuList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				KeyValue menu = (KeyValue) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("menu", menu.getKey());
				bundle.putBoolean("startByMenu", true);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
	}
}
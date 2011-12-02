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

import com.cxl.youmo.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1","前言  教你幽默到心田"));
		MENU_List.add(new KeyValue("2","第一章  相爱不偷？ "));
		MENU_List.add(new KeyValue("3","第二章  起床干什么？"));
		MENU_List.add(new KeyValue("4","第三章  换个脑子吧！"));
		MENU_List.add(new KeyValue("5","第四章  不要忘记母老虎 "));
		MENU_List.add(new KeyValue("6","第五章  醉翁之意不在酒 "));
		MENU_List.add(new KeyValue("7","第六章  张冠可以李戴 "));
		MENU_List.add(new KeyValue("8","第七章  狗肉、猫肉、牛肉 "));
		MENU_List.add(new KeyValue("9","第八章 我跟那金发美女回家了 "));
		MENU_List.add(new KeyValue("10","第九章 她会闭上眼睛吗？ "));
		MENU_List.add(new KeyValue("11","第十章 你妈生了吗？"));
		MENU_List.add(new KeyValue("12","第十一章 牛头不对马嘴 "));
		MENU_List.add(new KeyValue("13","第十二章 狗屎的滋味  "));
		MENU_List.add(new KeyValue("14","第十三章 要不要送上床？  "));
		MENU_List.add(new KeyValue("15","第十四章 太太真伟大  "));
		MENU_List.add(new KeyValue("16","后 记  幽默就像太极拳 "));
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
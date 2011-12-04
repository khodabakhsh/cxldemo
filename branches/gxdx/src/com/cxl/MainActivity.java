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

import com.cxl.gxdx.R;

public class MainActivity extends Activity {
	ListView menuList;
	ArrayAdapter<KeyValue> menuAdapter;
	public static final ArrayList<KeyValue> MENU_List = new ArrayList<KeyValue>();
	static {
		MENU_List.add(new KeyValue("1","1  超级幽默搞笑短信2011"));
		MENU_List.add(new KeyValue("2","2  手机爆笑短信大全2011"));
		MENU_List.add(new KeyValue("3","3  超搞的逗老婆开心的短信"));
		MENU_List.add(new KeyValue("4","4  搞笑生日祝福短信"));
		MENU_List.add(new KeyValue("5","5  最新夫妻幽默搞笑短信"));
		MENU_List.add(new KeyValue("6","6  追女孩发的搞笑短信大全"));
		MENU_List.add(new KeyValue("7","7  新婚祝福搞笑短信大全"));
		MENU_List.add(new KeyValue("8","8  最新的超级搞笑的爱情短信"));
		MENU_List.add(new KeyValue("9","9  非主流爱情谜语短信"));
		MENU_List.add(new KeyValue("10","10  感动爱人的爱情短信 "));
		MENU_List.add(new KeyValue("11","11  情人短信祝福：思念篇"));
		MENU_List.add(new KeyValue("12","12  精选浪漫求婚爱情短信大全"));
		MENU_List.add(new KeyValue("13","13  悲伤的爱情短信"));
		MENU_List.add(new KeyValue("14","14  非主流浪漫爱情短信"));
		MENU_List.add(new KeyValue("15","15  流行经典浪漫爱情短信大全"));
		MENU_List.add(new KeyValue("16","16   哄女孩子开心的爱情短信大全"));
		MENU_List.add(new KeyValue("17","17  最新浪漫求婚爱情短信"));
		MENU_List.add(new KeyValue("18","18 超级感人爱情短信"));
		MENU_List.add(new KeyValue("19","19  经典最感人的爱情短信"));

		
		
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
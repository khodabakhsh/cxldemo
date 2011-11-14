package com.cxl;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.cxl.ListManager.KeyValue;
import com.cxl.carknowledge.R;
import com.waps.AppConnect;

public class SubMenuActivity extends Activity {

	private Button returnButton;
	private ListView subMenuListView;
	private ArrayAdapter<KeyValue> subMenuAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_menu_list);

		Bundle bundle = getIntent().getExtras();
		String menu = bundle.getString("menu");
		setTitle(menu);
		List<KeyValue> list = ListManager.Menu_File_Map.get(menu);

		subMenuListView = (ListView) findViewById(R.id.subMenuList);

		subMenuAdapter = new ArrayAdapter<KeyValue>(this, R.layout.simple_list_layout, R.id.txtListItem, list);
		subMenuListView.setAdapter(subMenuAdapter);
		subMenuListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				KeyValue selectItem = (KeyValue) arg0.getItemAtPosition(pos);
				Intent intent = new Intent();
				intent.setClass(SubMenuActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("fileName", selectItem.getKey());
				bundle.putString("subMenuName", selectItem.getValue());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		if (!MainActivity.hasEnoughRequrePoint) {// 没达到积分
													//			showDialog();
		}

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});
	}

}

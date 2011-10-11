package com.cxl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class YouDao extends Activity {
	//查询按钮申明
	private Button myButton01;
	//清空按钮申明
	private Button myButton02;
	//输入框申明
	private EditText mEditText1;
	//加载数据的WebView申明
	private WebView mWebView1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获得布局的几个控件
		myButton01 = (Button) findViewById(R.id.myButton01);
		myButton02 = (Button) findViewById(R.id.myButton02);
		mEditText1 = (EditText) findViewById(R.id.myEditText1);
		mWebView1 = (WebView) findViewById(R.id.myWebView1);

		//查询按钮添加事件
		myButton01.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				String strURI = (mEditText1.getText().toString());
				strURI = strURI.trim();
				//如果查询内容为空提示
				if (strURI.length() == 0) {
					Toast.makeText(YouDao.this, "查询内容不能为空!", Toast.LENGTH_LONG).show();
				}
				//否则则以参数的形式从http://dict.youdao.com/m取得数据，加载到WebView里.
				else {
					String strURL = "http://dict.youdao.com/m/search?keyfrom=dict.mindex&q=" + strURI;
					mWebView1.loadUrl(strURL);
				}
			}
		});

		//清空按钮添加事件，将EditText置空
		myButton02.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mEditText1.setText("");
			}
		});
	}
}
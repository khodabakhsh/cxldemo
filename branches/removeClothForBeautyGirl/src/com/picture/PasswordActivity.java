package com.picture;

import com.cxl.removeClothForBeautyGirl.R;
import com.waps.AppConnect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends Activity implements View.OnClickListener {
	private Button yesBtn, cancelBtn;
	private EditText password;
	
	private static boolean isNeedPassword = false;

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}
	protected void onCreate(Bundle savedInstanceState) {
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		
		super.onCreate(savedInstanceState);
		
		if(!isNeedPassword){
			Intent intent = new Intent();
    		intent.setClass(PasswordActivity.this, SettingsActivity.class);
    		startActivity(intent);
    		PasswordActivity.this.finish();
		}else {
			setContentView(R.layout.password);
			setUpView();
			setUpListener();
		}
	
	}

	private void setUpView() {
		yesBtn = (Button) findViewById(R.id.yesBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		password = (EditText) findViewById(R.id.password);
	}

	private void setUpListener() {
		yesBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.yesBtn: {
			SharedPreferences preferences = getSharedPreferences("password",Activity.MODE_PRIVATE);  
	        String pwd = preferences.getString("pwd", "");
	        if(pwd!=null&&!"".equals(pwd)){
	        	if(password.getText().toString()!=""&&password.getText()!=null&&password.getText().toString().equals(pwd)){
	        		//Toast.makeText(PasswordActivity.this, "密码一致，请重新输入密码", Toast.LENGTH_SHORT).show();
	        		Intent intent = new Intent();
	        		intent.setClass(PasswordActivity.this, SettingsActivity.class);
	        		startActivity(intent);
	        		PasswordActivity.this.finish();
	        	}else{
	        		Toast.makeText(PasswordActivity.this, "密码不一致，请重新输入密码", Toast.LENGTH_SHORT).show();
	        	}
	        }else{
	        	SharedPreferences ferences = getSharedPreferences("password",Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = ferences.edit();
				editor.putString("pwd", password.getText().toString());
				editor.commit();
				//Toast.makeText(PasswordActivity.this, "已经设置密码", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
        		intent.setClass(PasswordActivity.this, SettingsActivity.class);
        		startActivity(intent);
        		PasswordActivity.this.finish();
	        }
	        
	        
		}
			break;
		case R.id.cancelBtn: {
			PasswordActivity.this.finish();
		}
			break;
		}
	}
}

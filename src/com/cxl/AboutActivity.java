package com.cxl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cxl.qiuAiMiJi.R;
import com.waps.AppConnect;

public class AboutActivity extends Activity {
	Button offerButton3;
	Button offerButton4;
	TextView returnButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		offerButton3 = (Button) findViewById(R.id.OffersButton3);
		offerButton3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(AboutActivity.this).showOffers(
						AboutActivity.this);
			}
		});
		offerButton4 = (Button) findViewById(R.id.OffersButton4);
		offerButton4.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				AppConnect.getInstance(AboutActivity.this).showOffers(
						AboutActivity.this);
			}
		});
		
	}
}
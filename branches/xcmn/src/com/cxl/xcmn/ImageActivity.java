package com.cxl.xcmn;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageActivity extends Activity {

	TextView text_num;
	ImageView imgCenter;
	private final String img = "img";
	private final String drawable = "drawable";
	private int typeIndex = 0;
	private int currentPageIndex = 0;
	private int MaxCount = 20;
	private ImageButton btn_previous;
	private ImageButton btn_next;

	private static Map<Integer, Integer> ImageCount = new HashMap<Integer, Integer>();

	private static boolean hasInited = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		typeIndex = Integer.parseInt(getIntent().getExtras().getString(
				"position"));
		imgCenter = (ImageView) findViewById(R.id.imgCenter);
		imgCenter.setImageResource(getResourceId(
				getImgName(typeIndex, currentPageIndex), drawable));
		if (!hasInited) {
			getImageCount();
		}
		setTitle("香车美女（" + (typeIndex + 1) + "）");
		text_num = (TextView) findViewById(R.id.text_num);
		setTextNumber(currentPageIndex + 1, ImageCount.get(typeIndex));

		btn_previous = (ImageButton) findViewById(R.id.btn_previous);
		btn_previous.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentPageIndex == 0) {
					Toast.makeText(getApplicationContext(), "这里已经是第一页哦",
							Toast.LENGTH_LONG).show();
				} else {
					currentPageIndex--;
					imgCenter.setImageResource(getResourceId(
							getImgName(typeIndex, currentPageIndex), drawable));
					setTextNumber(currentPageIndex + 1,
							ImageCount.get(typeIndex));
				}

			}
		});
		btn_next = (ImageButton) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentPageIndex == ImageCount.get(typeIndex) - 1) {
					Toast.makeText(getApplicationContext(), "这里已经是最后一页哦",
							Toast.LENGTH_LONG).show();
				} else {
					currentPageIndex++;
					imgCenter.setImageResource(getResourceId(
							getImgName(typeIndex, currentPageIndex), drawable));
					setTextNumber(currentPageIndex + 1,
							ImageCount.get(typeIndex));
				}

			}
		});
	}

	private int getResourceId(String name, String type) {
		return getResources().getIdentifier(name, type, getPackageName());
	}

	private String getImgName(int typeIndex, int numberIndex) {
		return img + typeIndex + "_" + numberIndex;
	}

	private void setTextNumber(int currentPageNum, int allPageNum) {
		text_num.setText(currentPageNum + "/" + allPageNum);
	}

	private void getImageCount() {
		hasInited = true;
		for (int j = 0; j < MainActivity.ICON_COUNT; j++) {
			for (int i = 0; i < MaxCount; i++) {
				if (getResources().getIdentifier(img + j + "_" + i, drawable,
						getPackageName()) == 0) {
					ImageCount.put(j, i);
					break;
				}
			}
		}
	}
}

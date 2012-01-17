package com.cxl;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.manhua1.R;
import com.waps.AppConnect;

public class MainActivity extends Activity {

	public static int i = 0;
	private TextView app_store;
	private Button bt1;
	private Button bt2;
	public ImageView img;

	private ArrayList<Integer> imgList = new ArrayList<Integer>();
	private ScrollView scrollView;

	public void getImgList(String paramString) {
		Field[] arrayOfField = R.drawable.class.getDeclaredFields();
		Field field;
		for (int i = 0; i < arrayOfField.length; i++) {
			field = arrayOfField[i];
			if ((field.getName().startsWith(paramString))) {
				int m;
				try {
					m = field.getInt(R.drawable.class);
					imgList.add(m);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppConnect.getInstance(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		img = (ImageView) findViewById(R.id.pic);
		ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
		img.setScaleType(localScaleType);
		getImgList("mh");
		img.setImageResource(imgList.get(i));

		bt1 = (Button) findViewById(R.id.previous);
		bt1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (i > 0) {
					img.setImageResource(imgList.get(--i));
					setPageInfo(0);
				} else {
					Toast.makeText(MainActivity.this, "这里是第一页",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		bt2 = (Button) findViewById(R.id.next);
		bt2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (i < imgList.size() - 1) {
					img.setImageResource(imgList.get(++i));
					setPageInfo(0);
				} else {
					Toast.makeText(MainActivity.this, "这里是最后一页",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		scrollView = (ScrollView) findViewById(R.id.scroll);
		scrollView.post(new Runnable() {
			public void run() {
				scrollView.scrollTo(0,
						PreferenceUtil.getScrollY(MainActivity.this));
			}
		});
	}

	protected void onPause() {
		saveState();
		super.onPause();
	}

	// 保存当前页和滚动位置
	private void saveState() {
		PreferenceUtil.setScrollY(this, scrollView.getScrollY());
		// PreferenceUtil.setTxtIndex(this, i);
	}

	private void setPageInfo(final int scrollY) {
		setTitle("第" + i + "页");
		if (scrollY == 0) {
			scrollView.post(new Runnable() {
				public void run() {
					scrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});
		} else {
			scrollView.post(new Runnable() {
				public void run() {
					scrollView.scrollTo(0, scrollY);
				}
			});
		}
	}

}
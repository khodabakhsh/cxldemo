package com.cxl;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.cxl.manhua3.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends Activity implements UpdatePointsNotifier {

	public static int currentIndex = 0;
	private Button moreOffer1;
	private Button moreOffer2;
	private Button bt1;
	private Button bt2;
	public ImageView img;
	private static final String ImgPrefix = "mh";
	private static int showDialogPage = 20;

	private ArrayList<Integer> imgList = new ArrayList<Integer>();
	private ScrollView scrollView;

	Handler msgHandler = new Handler();

	public static boolean hasEnoughRequreAdPointPreferenceValue = false;
	public static final int requireAdPoint = 80;
	public static int currentPointTotal = 0;

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
		
//		BitmapFactory.Options opt = new BitmapFactory.Options();
//		opt.inTempStorage = new byte[1024*1024*4]; //4MB的临时存储空间
//		Bitmap bm = BitmapFactory.decodeFile(inputStream,opt); //这里opt为Options属性
		
		

		initRequrePointPreference();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		img = (ImageView) findViewById(R.id.pic);
		ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
		img.setScaleType(localScaleType);
		getImgList(ImgPrefix);
		currentIndex = PreferenceUtil.getCurrentPage(this);
		img.setImageResource(imgList.get(currentIndex));

		bt1 = (Button) findViewById(R.id.previous);
		bt1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (currentIndex > 0) {
					img.setImageResource(imgList.get(--currentIndex));
					setPageInfo(0);

				} else {
					Toast.makeText(MainActivity.this, "这里是第一页", Toast.LENGTH_SHORT).show();
				}

			}
		});
		bt2 = (Button) findViewById(R.id.next);
		bt2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				
				if (currentIndex < imgList.size() - 1) {
					img.setImageResource(imgList.get(++currentIndex));
					setPageInfo(0);
					if (currentIndex > showDialogPage) {
						showMyDialog();
					}
				} else {
					Toast.makeText(MainActivity.this, "这里是最后一页", Toast.LENGTH_SHORT).show();
				}

			}
		});
		scrollView = (ScrollView) findViewById(R.id.scroll);
		scrollView.post(new Runnable() {
			public void run() {
				scrollView.scrollTo(0, PreferenceUtil.getScrollY(MainActivity.this));
			}
		});
		moreOffer1 = (Button) findViewById(R.id.moreOffer1);
		moreOffer1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});
		moreOffer1.setText("更多下载");
		moreOffer2 = (Button) findViewById(R.id.moreOffer2);
		moreOffer2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
			}
		});
		moreOffer2.setText("更多游戏");
	}

	protected void onPause() {
		saveState();
		super.onPause();
	}

	// 保存当前页和滚动位置
	private void saveState() {
		PreferenceUtil.setScrollY(this, scrollView.getScrollY());
		PreferenceUtil.setCurrentPage(this, currentIndex);
	}

	private void setPageInfo(final int scrollY) {
		setTitle("第" + currentIndex + "页");
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

	private void initRequrePointPreference() {
		hasEnoughRequreAdPointPreferenceValue = PreferenceUtil.getHasEnoughRequreAdPoint(MainActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughRequreAdPointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}

	public void getUpdatePoints(String currencyName, int pointTotal) {
		currentPointTotal = pointTotal;
		if (pointTotal >= requireAdPoint) {
			hasEnoughRequreAdPointPreferenceValue = true;
			PreferenceUtil.setHasEnoughRequreAdPoint(MainActivity.this, true);
		}
		showMyDialog();

	}

	public void showMyDialog() {
		if (!hasEnoughRequreAdPointPreferenceValue) {
			msgHandler.post(new Runnable() {
				public void run() {
					new AlertDialog.Builder(MainActivity.this)
							.setTitle("感谢使用本程序")
							.setMessage(
									"说明：本程序的一切提示信息，在积分满足" + requireAdPoint
											+ "后，自动消除！\n\n可通过【免费赚积分】，获得积分。\n\n通过【更多应用】，可以下载更多好玩应用。\n\n当前积分："
											+ currentPointTotal)
							.setPositiveButton("更多应用", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {

									AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
								}
							}).setNeutralButton("免费赚积分", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {

									AppConnect.getInstance(MainActivity.this).showOffers(MainActivity.this);
								}
							}).setNegativeButton("继续", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {

								}
							}).show();
				}
			});
		}
	}

	public void getUpdatePointsFailed(String error) {
		hasEnoughRequreAdPointPreferenceValue = false;
	}

}
package com.cxl.xcmn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.gzczpc.R;
import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class ImageActivity extends Activity implements UpdatePointsNotifier {

	TextView text_num;
	ImageView imgCenter;
	private final String img = "img";
	private final String drawable = "drawable";

	private int typeIndex = 0;
	private int currentPageIndex = 0;

	private ImageButton btn_previous;
	private ImageButton btn_next;

	private static String saveBasePath;

	private int MaxCount = 40;// 单个类型最大图片数（用于统计图像数目）
	private static Map<Integer, Integer> ImageCount = new HashMap<Integer, Integer>();// 保存各个类型的最大图片数
	private static boolean hasInited = false;

	private static boolean firstComeIn = true;

	Bitmap bitmap = null;

	BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

	public Bitmap getBitmap(int id) {
		// bitmapOptions.inSampleSize = 2;//暂时先不调整
		return BitmapFactory.decodeResource(getResources(), id, bitmapOptions);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		saveBasePath = getString(R.string.app_name) + "/";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		initRequrePointPreference();
		typeIndex = getIntent().getExtras().getInt("typeIndex");
		imgCenter = (ImageView) findViewById(R.id.imgCenter);
		bitmap = getBitmap(getResourceId(
				getImgName(typeIndex, currentPageIndex), drawable));
		imgCenter.setImageBitmap(bitmap);
		if (!hasInited) {
			getImageCount();
			hasInited = true;
		}

		setTitle(getResources().getIdentifier("text" + (typeIndex + 1),
				"string", getPackageName()));
		text_num = (TextView) findViewById(R.id.text_num);
		setTextNumber(currentPageIndex + 1, ImageCount.get(typeIndex));

		btn_previous = (ImageButton) findViewById(R.id.btn_previous);
		btn_previous.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentPageIndex == 0) {
					Toast.makeText(getApplicationContext(), "这里已经是第一页哦",
							Toast.LENGTH_SHORT).show();
				} else {
					currentPageIndex--;
					bitmap = getBitmap(getResourceId(
							getImgName(typeIndex, currentPageIndex), drawable));
					imgCenter.setImageBitmap(bitmap);
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
							Toast.LENGTH_SHORT).show();
				} else {
					currentPageIndex++;
					bitmap = getBitmap(getResourceId(
							getImgName(typeIndex, currentPageIndex), drawable));
					imgCenter.setImageBitmap(bitmap);
					setTextNumber(currentPageIndex + 1,
							ImageCount.get(typeIndex));
				}

			}
		});
		if (firstComeIn) {
			new AlertDialog.Builder(ImageActivity.this)
					.setTitle("说明")
					.setMessage(
							"1.点击图片的【左下角、右下角】可翻页。\n2.按【手机菜单键(Menu)】可以保存图片、设置图片为壁纸。")
					.setPositiveButton("我知道了",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
								}
							}).show();
			firstComeIn = false;
		}
		LinearLayout container = (LinearLayout) findViewById(R.id.AdLinearLayout2);
		new AdView(this, container).DisplayAd(20);// 每20秒轮换一次广告；最少为20
	}

	protected void onDestroy() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
		super.onDestroy();
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
		for (int j = 0; j < MainActivity.TYPE_COUNT; j++) {
			for (int i = 0; i < MaxCount; i++) {
				if (getResources().getIdentifier(img + j + "_" + i, drawable,
						getPackageName()) == 0) {
					break;
				}
				ImageCount.put(j, i + 1);
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		SubMenu moreSubMenu = paramMenu.addSubMenu(0, 2, 0, "更多免费精品下载...");
		SubMenu saveSubMenu = paramMenu.addSubMenu(0, 0, 0, "保存");
		SubMenu pictureSubMenu = paramMenu.addSubMenu(0, 1, 0, "设为壁纸");

		saveSubMenu.setIcon(R.drawable.save);
		pictureSubMenu.setIcon(R.drawable.set);
		moreSubMenu.setIcon(R.drawable.exit);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == 0) {
			if (!hasEnoughRequrePointPreferenceValue) {
				showGetPointDialog("保存图片");
			} else {
				new AlertDialog.Builder(ImageActivity.this)
						.setTitle("保存图片")
						.setMessage("确定要保存图片？")
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialoginterface,
											int i) {
										new Thread(new Runnable() {
											public void run() {
												BitmapDrawaleTypeUtil
														.saveFile(
																getBitmap(),
																saveBasePath
																		+ getString(getResources()
																				.getIdentifier(
																						"text"
																								+ (typeIndex + 1),
																						"string",
																						getPackageName()))
																		+ "/",
																getImgName(
																		typeIndex,
																		currentPageIndex)
																		+ ".jpg");
											}
										}).start();
										Toast.makeText(ImageActivity.this,
												"已保存在SD卡：" + saveBasePath,
												Toast.LENGTH_SHORT).show();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}

		} else if (paramMenuItem.getItemId() == 1) {
			if (!hasEnoughRequrePointPreferenceValue) {
				showGetPointDialog("设置壁纸");
			} else {
				new AlertDialog.Builder(ImageActivity.this)
						.setTitle("设置图片")
						.setMessage("确定要设置图片？")
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialoginterface,
											int i) {
										new Thread(new Runnable() {
											public void run() {
												try {
													getApplicationContext()
															.setWallpaper(
																	getBitmap());
												} catch (IOException e) {
													e.printStackTrace();
												}
											}
										}).start();
										Toast.makeText(ImageActivity.this,
												"设置成功", Toast.LENGTH_SHORT)
												.show();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}

		} else if (paramMenuItem.getItemId() == 2) {
			// 显示推荐安装程序（Offer）.
			AppConnect.getInstance(ImageActivity.this).showOffers(
					ImageActivity.this);
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	private Bitmap getBitmap() {
		return BitmapDrawaleTypeUtil.drawableToBitmap(getResources()
				.getDrawable(
						getResourceId(getImgName(typeIndex, currentPageIndex),
								drawable)));
	}

	private void showGetPointDialog(String type) {
		new AlertDialog.Builder(ImageActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + currentPointTotal)
				.setMessage(
						"只要积分满足" + requirePoint + "，就可以" + type + "！！ 您当前的积分不足"
								+ requirePoint + "。")
				.setPositiveButton("免费获得积分",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								// 显示推荐安装程序（Offer）.
								AppConnect.getInstance(ImageActivity.this)
										.showOffers(ImageActivity.this);
							}
						}).show();
	}

	public static boolean hasEnoughRequrePointPreferenceValue = false;// 保存在配置里
	public static final int requirePoint = 30;// 要求积分
	public static int currentPointTotal = 0;// 当前积分

	private void initRequrePointPreference() {
		hasEnoughRequrePointPreferenceValue = PreferenceUtil
				.getHasEnoughRequrePoint(ImageActivity.this);
	}

	protected void onResume() {
		if (!hasEnoughRequrePointPreferenceValue) {
			AppConnect.getInstance(this).getPoints(this);
		}
		super.onResume();
	}

	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 * 
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {
		currentPointTotal = pointTotal;
		if (pointTotal >= requirePoint) {
			hasEnoughRequrePointPreferenceValue = true;
			PreferenceUtil.setHasEnoughRequrePoint(ImageActivity.this, true);
		}
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		hasEnoughRequrePointPreferenceValue = false;
	}

}

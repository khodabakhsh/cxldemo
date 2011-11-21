package com.cxl.xcmn;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
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

	private final String basePath = "车模美女/";

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
		
		setTitle(getResources().getIdentifier("text"+(typeIndex + 1), "string", getPackageName()));
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

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		SubMenu localSubMenu1 = paramMenu.addSubMenu(0, 0, 0, "保存");
		SubMenu localSubMenu2 = paramMenu.addSubMenu(0, 1, 0, "设为壁纸");
		localSubMenu1.setIcon(R.drawable.save);
		localSubMenu2.setIcon(R.drawable.set);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == 0) {
			new AlertDialog.Builder(ImageActivity.this)
					// .setIcon(R.drawable.happy2)
					.setTitle("保存图片")
					.setMessage("确定要保存图片？")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									BitmapDrawaleTypeUtil.saveFile(
											getBitmap(),
											basePath + getString(getResources().getIdentifier("text"+(typeIndex + 1), "string", getPackageName()))+"/",
											getImgName(typeIndex,
													currentPageIndex) + ".jpg");
									Toast.makeText(ImageActivity.this, "保存成功",
											Toast.LENGTH_LONG).show();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// finish();
								}
							}).show();

		} else if (paramMenuItem.getItemId() == 1) {
			new AlertDialog.Builder(ImageActivity.this)
					// .setIcon(R.drawable.happy2)
					.setTitle("设置图片")
					.setMessage("确定要设置图片？")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									try {
									Display display = getWindowManager().getDefaultDisplay();
									int width = display.getWidth();
									int height = display.getHeight();
									Toast.makeText(getApplicationContext(), "width: "+ width +",height:  "+height, Toast.LENGTH_LONG).show();
									
//										PaperManager
//												.setWallpaper(
//														getApplicationContext(),
//														getResourceId(
//																getImgName(
//																		typeIndex,
//																		currentPageIndex),
//																drawable), 384,
//																284, 220, 268);
										 getApplicationContext().setWallpaper(
										 getBitmap());
										Toast.makeText(ImageActivity.this,
												"设置成功", Toast.LENGTH_LONG)
												.show();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// finish();
								}
							}).show();

		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	private Bitmap getBitmap() {
		return BitmapDrawaleTypeUtil.drawableToBitmap(getResources()
				.getDrawable(
						getResourceId(getImgName(typeIndex, currentPageIndex),
								drawable)));
	}

}

package com.hitme;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxl.beatmouse.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class HitMe extends Activity implements UpdatePointsNotifier {
	/** Called when the activity is first created. */
	// 控件定义
	private TextView scoreView; // 分数栏
	private ImageView image1; // 9个窗口
	private ImageView image2;
	private ImageView image3;
	private ImageView image4;
	private ImageView image5;
	private ImageView image6;
	private ImageView image7;
	private ImageView image8;
	private ImageView image9;
	private MyHandler myHandler; // 子线程循环队列
	private Button button_start; // 开始按键
	// private Button button_end; // 结束按键
	private static int index = 100; // 判断窗口touch事件发生时，窗口是否处在打开状态
	private static int temp = 101; // 记录哪个窗口打开，以这个参数找到该窗口及时关闭
	private static int score = 0; // 分数变量，【已改为打中个数】
	private Handler changeMe; // 更改UI线程，通过message与子线程通信
	private Handler changeWindow; // 更改UI线程，通过message与子线程通信
	private static boolean close = false; // 结束线程的开关，true时线程和looper结束。
	private static boolean beginning = true; // 开始开关，值为true时开始键可用，修复可以多次点击开始键的BUG

	ArrayList<ImageView> imageList = new ArrayList<ImageView>(); // 按键集合，简化代码，代替N多的switch()语句

	private Button btn;
	private static String speed = "";
	private static int openWindowSpeed = 1500;// 最低从1200改为1500
	private static int closeWindowSpeed = 900;// 最低从700改为900
	private static final int LowOpenWindowSpeed = 1300;
	private static final int LowCloseWindowSpeed = 700;
	private static final int MediumOpenWindowSpeed = 1100;
	private static final int MediumCloseWindowSpeed = 700;
	private static final int HighOpenWindowSpeed = 900;
	private static final int HighCloseWindowSpeed = 500;
	private static final int SuperOpenWindowSpeed = 700;
	private static final int SuperCloseWindowSpeed = 500;
	private static int outMouse = 0;
	private static final int gameTime = 60000;

	private int currentPointTotal = 0;// 当前积分
	public static final int Setting_Require_Point = 180;
	private static boolean Has_Setting_Require_Point = false;// 是否达到积分

	TextView pointsTextView;

	String displayText;
	boolean update_text = false;

	final Handler mHandler = new Handler();

	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				if (update_text) {
					pointsTextView.setText(displayText);
					update_text = false;
				}
			}
		}
	};

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
		if (currentPointTotal >= Setting_Require_Point) {
			Has_Setting_Require_Point = true;
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
		mHandler.post(mUpdateResults);
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		currentPointTotal = 0;
		update_text = true;
		displayText = error;
		mHandler.post(mUpdateResults);
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		AppConnect.getInstance(this).getPoints(this);
		super.onResume();
	}

	private void showMyDialog(int requirePoint) {
		new AlertDialog.Builder(HitMe.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + currentPointTotal)
				.setMessage(
						"【温馨提示:】只要积分满足"
								+ requirePoint
								+ "，本功能就可以永久使用，您就可以自由设置游戏的速度哦！！ 您当前的积分不足"
								+ requirePoint
								+ "，无法使用本功能。\n【免费获得积分方法:】请点击确认键进入推荐下载列表 , 下载并安装软件获得相应积分。")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(HitMe.this).showOffers(
								HitMe.this);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish();
					}
				}).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		// Button offers = (Button) findViewById(R.id.OffersButton);
		// offers.setOnClickListener(new Button.OnClickListener() {
		// public void onClick(View arg0) {
		// // 显示推荐安装程序（Offer）.
		// AppConnect.getInstance(HitMe.this).showOffers(HitMe.this);
		// }
		// });

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(HitMe.this).showMore(HitMe.this);
			}
		});

		String[] array = getResources().getStringArray(R.array.dialog_items);
		speed = array[0];

		pointsTextView = (TextView) findViewById(R.id.PointsTextView);

		btn = (Button) findViewById(R.id.selectSpeed);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if (Has_Setting_Require_Point) {
					new AlertDialog.Builder(HitMe.this)
							.setTitle("请选择游戏速度")
							.setIcon(R.drawable.happy2)
							.setItems(
									R.array.dialog_items,
									new android.content.DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											String[] array = getResources()
													.getStringArray(
															R.array.dialog_items);
											switch (arg1) {
											case 1:
												openWindowSpeed = LowOpenWindowSpeed;
												closeWindowSpeed = LowCloseWindowSpeed;
												break;
											case 2:
												openWindowSpeed = MediumOpenWindowSpeed;
												closeWindowSpeed = MediumCloseWindowSpeed;
												break;
											case 3:
												openWindowSpeed = HighOpenWindowSpeed;
												closeWindowSpeed = HighCloseWindowSpeed;
												break;
											case 4:
												openWindowSpeed = SuperOpenWindowSpeed;
												closeWindowSpeed = SuperCloseWindowSpeed;
												break;
											default:
												break;
											}
											speed = array[arg1];
											scoreView.setText("当前速度：" + speed);
										}
									})
							.setNegativeButton(
									"退出",
									new android.content.DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
											arg0.dismiss();
										}
									}).show();
				} else {
					showMyDialog(Setting_Require_Point);
				}

			}
		});

		// 获取控件实例
		scoreView = (TextView) findViewById(R.id.score);
		scoreView.setText("当前速度：" + speed);
		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);
		image3 = (ImageView) findViewById(R.id.image3);
		image4 = (ImageView) findViewById(R.id.image4);
		image5 = (ImageView) findViewById(R.id.image5);
		image6 = (ImageView) findViewById(R.id.image6);
		image7 = (ImageView) findViewById(R.id.image7);
		image8 = (ImageView) findViewById(R.id.image8);
		image9 = (ImageView) findViewById(R.id.image9);
		button_start = (Button) findViewById(R.id.start);
		// button_end = (Button) findViewById(R.id.end);
		// 将按键加入集合
		imageList.add(image1);
		imageList.add(image2);
		imageList.add(image3);
		imageList.add(image4);
		imageList.add(image5);
		imageList.add(image6);
		imageList.add(image7);
		imageList.add(image8);
		imageList.add(image9);

		// 开启消息循环队列并与myHandler绑定
		HandlerThread handlerThread = new HandlerThread("thread");
		handlerThread.start(); // 这句很重要，一定要加
		myHandler = new MyHandler(handlerThread.getLooper());

		// 更换窗口图片队列，接受两种参数，arg1显示开窗图片，arg2显示被击中图片
		/*
		 * 这里有个想法，只用一个Handler处理4个事件
		 * 
		 * （1）将被打开的窗口图片改为me 传递arg1 = 1, arg2 = [0-9] （2）将被打开的窗口关上 传递arg1 = 2,
		 * arg2 = [0-9] （3）如果被打中显示hitme图片 传递arg1 = 3, arg2 = [0-9] （4）关闭所有窗口
		 * 传递arg1 = 4;
		 * 
		 * 这样可以省略一个Handler,因为只有一个消息队列，应该能消除某些BUG
		 * 用虚拟机测试时，我感觉是因为电脑线程分配问题，偶尔CPU忙时，造成两个handler执行顺序上的问题 因为在朋友真机上测试机没出现BUG
		 */
		changeMe = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				if (msg.arg1 != 102) {

					imageList.get(msg.arg1).setImageDrawable(
							getResources().getDrawable(R.drawable.me));

				} else if (msg.arg1 == 102) {

					imageList.get(msg.arg2).setImageDrawable(
							getResources().getDrawable(R.drawable.hitme));

					score += 1;
					scoreView.setText("打中：" + score + " , 当前速度：" + speed);

				}
			}

		};
		// 更换窗口图片队列
		changeWindow = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				if (msg.arg2 == 0) {

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					imageList.get(msg.arg1).setImageDrawable(
							getResources().getDrawable(R.drawable.window));

				} else if (msg.arg2 == 1) {

					for (int i = 0; i < 9; i++) {
						imageList.get(i).setImageDrawable(
								getResources().getDrawable(R.drawable.window));
					}

					scoreView.setText("Game over，总时间："+gameTime/1000+"秒，出现总数:" + outMouse + " ,最后"
							+ scoreView.getText());
					btn.setEnabled(true);
				}
			}

		};
		// 开始按键监听事件
		button_start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				/*
				 * 程序的执行语句顺序很重要 原来close = false; 放在myHandler.post(playThread);之后
				 * 会造成点击开始无效的bug,可能是因为先执行了线程， 而close还没设置，导致线程又结束
				 */

				if (beginning) {
					btn.setEnabled(false);

					Timer timer = new Timer();
					TimerTask myTask = new TimerTask() {

						@Override
						public void run() {

							close = true;

							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							index = 100;
							beginning = true;

							Message changeMsg = changeWindow.obtainMessage();
							changeMsg.arg2 = 1;
							changeWindow.sendMessage(changeMsg);

						}

					};

					beginning = false;
					scoreView.setText("打中：0" + ",当前速度：" + speed);
					score = 0;
					outMouse = 0;
					close = false;
					myHandler.post(playThread);

					timer.schedule(myTask, gameTime);
				}
			}

		});
		// 结束按键监听事件
		// button_end.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		//
		//
		// finish();
		//
		// }
		//
		// });
		// 窗口1监听事件
		image1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (index == 0) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 0;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 2 ,index == " + index);
				if (index == 1) {
					System.out.println("change hitme");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 1;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 3 ,index == " + index);
				if (index == 2) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 2;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image4.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 4 ,index == " + index);
				if (index == 3) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 3;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image5.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 5 ,index == " + index);
				if (index == 4) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 4;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image6.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 6 ,index == " + index);
				if (index == 5) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 5;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image7.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 7 ,index == " + index);
				if (index == 6) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 6;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image8.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 8 ,index == " + index);
				if (index == 7) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 7;
					changeMe.sendMessage(changeMsg);

				}
			}

		});

		image9.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				System.out.println("press 9 ,index == " + index);
				if (index == 8) {
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 8;
					changeMe.sendMessage(changeMsg);

				}
			}

		});
	}

	// 分线程
	Runnable playThread = new Runnable() {
		Random rand = new Random();

		public void run() {

			if (temp != 101) {

				Message changeMsg = changeWindow.obtainMessage();
				changeMsg.arg1 = temp;
				outMouse++;
				// scoreView.setText("打中：" +
				// score+" , 出现总数: "+outMouse+" , 当前速度："+speed);
				changeWindow.sendMessage(changeMsg);
				index = 100;

			}
			// 每1200ms打开一个窗口
			try {

				Thread.sleep(openWindowSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			temp = rand.nextInt(9);
			index = temp;
			Message msg = myHandler.obtainMessage();
			msg.arg1 = temp;
			myHandler.sendMessage(msg);
		}

	};

	// 与分线程传递Msg的Handler
	class MyHandler extends Handler {

		MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {

			// 在handlerMessage方法中结束线程和消息队列，最好不要在run方法中结束。
			if (close) {
				myHandler.removeCallbacks(playThread);
				return;
			}

			Message changeMsg = changeMe.obtainMessage();
			changeMsg.arg1 = msg.arg1;
			changeMe.sendMessage(changeMsg);
			// 窗口打开后700ms关闭
			try {
				Thread.sleep(closeWindowSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			myHandler.post(playThread);
		}

	}
}
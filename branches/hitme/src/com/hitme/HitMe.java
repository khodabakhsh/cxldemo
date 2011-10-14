package com.hitme;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.*;
import android.os.*;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.*;

public class HitMe extends Activity {
    /** Called when the activity is first created. */
    //控件定义 
	private TextView scoreView;		//分数栏
	private ImageView image1;		//9个窗口
	private ImageView image2;
	private ImageView image3;
	private ImageView image4;
	private ImageView image5;
	private ImageView image6;
	private ImageView image7;
	private ImageView image8;
	private ImageView image9;
	private MyHandler myHandler;	//子线程循环队列
	private Button button_start;	//开始按键
	private Button button_end;		//结束按键
	private static int index = 100; 	//判断窗口touch事件发生时，窗口是否处在打开状态
	private static int temp = 101;		//记录哪个窗口打开，以这个参数找到该窗口及时关闭
	private static int score = 0;	//分数变量
	private Handler changeMe;	//更改UI线程，通过message与子线程通信
	private Handler changeWindow;	//更改UI线程，通过message与子线程通信
	private static boolean close = false;	//结束线程的开关，true时线程和looper结束。
	private static boolean beginning = true;	//开始开关，值为true时开始键可用，修复可以多次点击开始键的BUG
	
	ArrayList<ImageView> imageList = new ArrayList<ImageView>();	//按键集合，简化代码，代替N多的switch()语句
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        //获取控件实例
        scoreView = (TextView)findViewById(R.id.score);
        image1 = (ImageView)findViewById(R.id.image1);
        image2 = (ImageView)findViewById(R.id.image2);
        image3 = (ImageView)findViewById(R.id.image3);
        image4 = (ImageView)findViewById(R.id.image4);
        image5 = (ImageView)findViewById(R.id.image5);
        image6 = (ImageView)findViewById(R.id.image6);
        image7 = (ImageView)findViewById(R.id.image7);
        image8 = (ImageView)findViewById(R.id.image8);
        image9 = (ImageView)findViewById(R.id.image9);
        button_start = (Button)findViewById(R.id.start);
        button_end = (Button)findViewById(R.id.end);
        //将按键加入集合
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        imageList.add(image4);
        imageList.add(image5);
        imageList.add(image6);
        imageList.add(image7);
        imageList.add(image8);
        imageList.add(image9);
        
        //开启消息循环队列并与myHandler绑定
        HandlerThread handlerThread = new HandlerThread("thread");
        handlerThread.start();	//这句很重要，一定要加
        myHandler = new MyHandler(handlerThread.getLooper());
        
        //更换窗口图片队列，接受两种参数，arg1显示开窗图片，arg2显示被击中图片
        /*
         * 这里有个想法，只用一个Handler处理4个事件
         * 
         * （1）将被打开的窗口图片改为me  传递arg1 = 1, arg2 = [0-9]
         * （2）将被打开的窗口关上	传递arg1 = 2, arg2 = [0-9]
         * （3）如果被打中显示hitme图片	传递arg1 = 3, arg2 = [0-9]
         * （4）关闭所有窗口			传递arg1 = 4;
         * 
         * 这样可以省略一个Handler,因为只有一个消息队列，应该能消除某些BUG
         * 用虚拟机测试时，我感觉是因为电脑线程分配问题，偶尔CPU忙时，造成两个handler执行顺序上的问题
         * 因为在朋友真机上测试机没出现BUG
         */
        changeMe = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				if(msg.arg1 != 102){
					
					imageList.get(msg.arg1).setImageDrawable(getResources().getDrawable(R.drawable.me));
					
				}else if(msg.arg1 == 102){
					
					imageList.get(msg.arg2).setImageDrawable(getResources().getDrawable(R.drawable.hitme));
					
					score +=10;
					scoreView.setText("得分："+score);
					
				}
			}
        	
        };
        //更换窗口图片队列
        changeWindow = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.arg2 == 0){
					
					try{
						Thread.sleep(100);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					
					imageList.get(msg.arg1).setImageDrawable(getResources().getDrawable(R.drawable.window));
				
					
				}else if(msg.arg2 == 1){
					
					for(int i=0; i<9; i++){
						imageList.get(i).setImageDrawable(getResources().getDrawable(R.drawable.window));
					}
					
					scoreView.setText("Game over，最后"+scoreView.getText());
				}
			}
        	
        };
        //开始按键监听事件
        button_start.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * 程序的执行语句顺序很重要  原来close = false;
				 * 放在myHandler.post(playThread);之后
				 * 会造成点击开始无效的bug,可能是因为先执行了线程，
				 * 而close还没设置，导致线程又结束
				 */
				
				if(beginning){
					
					Timer timer = new Timer();
					TimerTask myTask = new TimerTask(){
	
						@Override
						public void run() {
							// TODO Auto-generated method stub
		
							close = true;
							
							try{
								Thread.sleep(200);
							}catch(InterruptedException e){
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
					scoreView.setText("得分：0");
					score = 0;
					close = false;
					myHandler.post(playThread);
					
					timer.schedule(myTask, 30000);
				}	
			}
        	
        });
        //结束按键监听事件 
        button_end.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
        	
        });
        //窗口1监听事件
        image1.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				if(index == 0){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 0;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image2.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 2 ,index == "+index);
				if(index == 1){
					System.out.println("change hitme");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 1;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image3.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 3 ,index == "+index);
				if(index == 2){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 2;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image4.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 4 ,index == "+index);
				if(index == 3){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 3;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image5.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 5 ,index == "+index);
				if(index == 4){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 4;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image6.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 6 ,index == "+index);
				if(index == 5){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 5;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image7.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 7 ,index == "+index);
				if(index == 6){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 6;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image8.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 8 ,index == "+index);
				if(index == 7){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 7;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
        
        image9.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("press 9 ,index == "+index);
				if(index == 8){
					System.out.println("change coin");
					Message changeMsg = changeMe.obtainMessage();
					changeMsg.arg1 = 102;
					changeMsg.arg2 = 8;
					changeMe.sendMessage(changeMsg);
					
				}
			}
        	
        });
    }
	//分线程
	Runnable playThread = new Runnable(){
		Random rand = new Random();
		public void run() {
			// TODO Auto-generated method stub
			
			if(temp != 101){
				
				Message changeMsg = changeWindow.obtainMessage();
				changeMsg.arg1 = temp;
				changeWindow.sendMessage(changeMsg);
				index = 100;
			}
			//每1200ms打开一个窗口
			try{
				Thread.sleep(1200);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			temp = rand.nextInt(9);
			index = temp;
			Message msg = myHandler.obtainMessage();
			msg.arg1 = temp;
			myHandler.sendMessage(msg);
		}
		
	};
	//与分线程传递Msg的Handler
	class MyHandler extends Handler{
		
		MyHandler(Looper looper){
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//在handlerMessage方法中结束线程和消息队列，最好不要在run方法中结束。
			if(close){
				myHandler.removeCallbacks(playThread);
				return;
			}
			
			Message changeMsg = changeMe.obtainMessage();
			changeMsg.arg1 = msg.arg1;
			changeMe.sendMessage(changeMsg);
			//窗口打开后700ms关闭
			try{
				Thread.sleep(700);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			myHandler.post(playThread);
		}
		
	}
}
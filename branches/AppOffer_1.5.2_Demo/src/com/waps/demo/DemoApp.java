package com.waps.demo;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.waps.*;

public class DemoApp extends Activity implements View.OnClickListener,
        UpdatePointsNotifier {

    TextView pointsTextView;
    TextView SDKVersionView;
    
    String displayText;
    boolean update_text = false;

    final Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
        AppConnect.getInstance(this);

        // 设置状态栏中推送广告的图标（此设置为可选，未添加此句代码，则使用默认图标）
//        AppConnect.getInstance(this).setPushIcon(R.drawable.icon);
        // 设置状态栏中推送广告是否有提示音（此设置为可选，未添加此句代码，则默认有提示音）
//      AppConnect.getInstance(this).setPushAudio(false);

        Button offers = (Button) findViewById(R.id.OffersButton);
        Button owns = (Button) findViewById(R.id.OwnsButton);
        Button getPoints = (Button) findViewById(R.id.PointsButton);
        Button spendPoints = (Button) findViewById(R.id.SpendPointsButton);
        Button tabButton = (Button) findViewById(R.id.pushAdButton);
        Button feedbackBurron = (Button) findViewById(R.id.Feedback);
        Button awardBurron = (Button) findViewById(R.id.awardButton);

        offers.setOnClickListener(this);
        owns.setOnClickListener(this);
        getPoints.setOnClickListener(this);
        spendPoints.setOnClickListener(this);
        tabButton.setOnClickListener(this);
        feedbackBurron.setOnClickListener(this);
        awardBurron.setOnClickListener(this);
        
        pointsTextView = (TextView) findViewById(R.id.PointsTextView);
        SDKVersionView = (TextView) findViewById(R.id.SDKVersionView);
        SDKVersionView.setText("SDK版本: " + AppConnect.LIBRARY_VERSION_NUMBER);

        //互动广告调用方式
        LinearLayout container =(LinearLayout)findViewById(R.id.AdLinearLayout);
        new AdView(this,container).DisplayAd(30);// 30秒刷新一次

    }

    public void onClick(View v) {
        if (v instanceof Button) {
            int id = ((Button) v).getId();

            switch (id) {
            case R.id.OffersButton:
                // 显示推荐安装程序（Offer）.
                AppConnect.getInstance(this).showOffers(this);
                break;
            case R.id.pushAdButton:
            	// 调用推送广告(系统限制每小时只能获取一次)
            	AppConnect.getInstance(this).getPushAd();
            	break;

            case R.id.SpendPointsButton:
                // 消费虚拟货币.
                AppConnect.getInstance(this).spendPoints(10, this);
                break;
            case R.id.awardButton:
            	// 奖励虚拟货币
            	AppConnect.getInstance(this).awardPoints(10, this);
            	break;
            case R.id.PointsButton:
            	// 从服务器端获取当前用户的积分.
            	// 返回结果在回调函数getUpdatePoints(...)中处理
            	AppConnect.getInstance(this).getPoints(this);
            	break;
            case R.id.Feedback:
                // 反馈
            	AppConnect.getInstance(this).showFeedback();
                break;
            case R.id.OwnsButton:
            	// 显示自家应用列表.
            	AppConnect.getInstance(this).showMore(this);
            	break;
            }
        }
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
        update_text = true;
        displayText = error;
        mHandler.post(mUpdateResults);
    }

}
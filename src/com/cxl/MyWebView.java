package com.cxl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MyWebView extends WebView {
	Context context;
	GestureDetector gd;
	boolean isLongPress = false;//记录长按事件

	public MyWebView(Context context, AttributeSet attributes) {
		super(context, attributes);
		this.context = context;
		gd = new GestureDetector(context, sogl);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isLongPress && (event.getAction() == MotionEvent.ACTION_UP)) {
			isLongPress = false;
			return false;
		}
		super.onTouchEvent(event);
		gd.onTouchEvent(event);
		return true;
	}


	GestureDetector.SimpleOnGestureListener sogl = new GestureDetector.SimpleOnGestureListener() {
		public void onLongPress(MotionEvent event) {
			isLongPress = true;
		}

	};
}

package com.android.weatherforecast.views;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.android.weatherforecast.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The View capable of showing a WeatehrIcon + a Temperature-TextView.
 */
public class SingleWeatherInfoView extends LinearLayout {

	// ===========================================================
	// Fields
	// ===========================================================

	private ImageView myWeatherImageView = null;
	private TextView myTempTextView = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	public SingleWeatherInfoView(Context context) {
		super(context);
	}

	public SingleWeatherInfoView(Context context, AttributeSet attrs) {
		super(context,attrs);
		/* Setup the ImageView that will show weather-icon. */
		this.myWeatherImageView = new ImageView(context);
		this.myWeatherImageView.setImageDrawable(getResources().getDrawable(
				R.drawable.dunno));

		/* Setup the textView that will show the temperature. */
		this.myTempTextView = new TextView(context);
		this.myTempTextView.setText("?°C");
		this.myTempTextView.setTextSize(16);
		this.myTempTextView.setTypeface(Typeface
				.create("Tahoma", Typeface.BOLD));

		/* Add child views to this object. */
		this.addView(this.myWeatherImageView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		this.addView(this.myTempTextView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void reset() {
		this.myWeatherImageView.setImageDrawable(getResources().getDrawable(
				R.drawable.dunno));
		this.myTempTextView.setText("?°C");
	}

	/** Sets the Child-ImageView of this to the URL passed. */
	public void setRemoteImage(URL aURL) {
		try {
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
			this.myWeatherImageView.setImageBitmap(bm);
		} catch (IOException e) {
			/* Reset to 'Dunno' on any error. */
			this.myWeatherImageView.setImageDrawable(getResources()
					.getDrawable(R.drawable.dunno));
		}
	}

	public void setTempCelcius(int aTemp) {
		this.myTempTextView.setText("" + aTemp + " °C");
	}

	public void setTempFahrenheit(int aTemp) {
		this.myTempTextView.setText("" + aTemp + " °F");
	}

	public void setTempFahrenheitMinMax(int aMinTemp, int aMaxTemp) {
		this.myTempTextView.setText("" + aMinTemp + "/" + aMaxTemp + " °F");
	}

	public void setTempCelciusMinMax(int aMinTemp, int aMaxTemp) {
		this.myTempTextView.setText("" + aMinTemp + "/" + aMaxTemp + " °C");
	}

	public void setTempString(String aTempString) {
		this.myTempTextView.setText(aTempString);
	}
}

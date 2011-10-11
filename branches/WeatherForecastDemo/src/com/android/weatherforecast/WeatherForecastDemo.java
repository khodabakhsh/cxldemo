package com.android.weatherforecast;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.android.weatherforecast.views.SingleWeatherInfoView;
import com.android.weatherforecast.weather.GoogleWeatherHandler;
import com.android.weatherforecast.weather.WeatherCurrentCondition;
import com.android.weatherforecast.weather.WeatherForecastCondition;
import com.android.weatherforecast.weather.WeatherSet;
import com.android.weatherforecast.weather.WeatherUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class WeatherForecastDemo extends Activity {

	private final String DEBUG_TAG = "WeatherForcasterDemo";
	private CheckBox chk_usecelsius = null;
	private static String[] CITY_EN = {"hangzhou","shanghai","beijing"};
	private static String[] CITY_CN = {"杭州","上海","北京"};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		setTheme(android.R.style.Theme);

		this.chk_usecelsius = (CheckBox) findViewById(R.id.chk_usecelsius);

		Button cmd_submit = (Button) findViewById(R.id.cmd_submit);
		cmd_submit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(WeatherForecastDemo.this, "开始查询", Toast.LENGTH_LONG).show();
				URL url;
				try {
					/* 获得输入值. */
					String cityParamString = ((EditText) findViewById(R.id.edit_input))
							.getText().toString();
					/* 对输入是汉字的处理*/
					boolean isCharater = isCN(cityParamString);
					int position = 0;
					if(isCharater)
					{
						position = getCnPosition(CITY_CN,cityParamString);
						if(position != -1)
						{
							cityParamString =  CITY_EN[position];
						}
					}
									
					/* 通过参数形式获取天气情况*/
					String queryString = "http://www.google.com/ig/api?weather="
							+ cityParamString;
				
					url = new URL(queryString.replace(" ", "%20"));

				
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();

				
					XMLReader xr = sp.getXMLReader();

					/*
					 * Create a new ContentHandler and apply it to the
					 * XML-Reader
					 */
					GoogleWeatherHandler gwh = new GoogleWeatherHandler();
					xr.setContentHandler(gwh);

					/* Parse the xml-data our URL-call returned. */
					xr.parse(new InputSource(url.openStream()));

					/* Our Handler now provides the parsed weather-data to us. */
					WeatherSet ws = gwh.getWeatherSet();

					/* Update the SingleWeatherInfoView with the parsed data. */
					updateWeatherInfoView(R.id.weather_today, ws
							.getWeatherCurrentCondition());

					updateWeatherInfoView(R.id.weather_1, ws
							.getWeatherForecastConditions().get(0));
					updateWeatherInfoView(R.id.weather_2, ws
							.getWeatherForecastConditions().get(1));
					updateWeatherInfoView(R.id.weather_3, ws
							.getWeatherForecastConditions().get(2));
					updateWeatherInfoView(R.id.weather_4, ws
							.getWeatherForecastConditions().get(3));

				} catch (Exception e) {
					resetWeatherInfoViews();
					Log.e(DEBUG_TAG, "WeatherQueryError", e);
				}
			}
		});
	}

	private void updateWeatherInfoView(int aResourceID,
			WeatherForecastCondition aWFIS) throws MalformedURLException {
		
		/* Construct the Image-URL. */
		URL imgURL = new URL("http://www.google.com" + aWFIS.getIconURL());
		((SingleWeatherInfoView) findViewById(aResourceID)).setRemoteImage(imgURL);

		int tempMin = aWFIS.getTempMinCelsius();
		int tempMax = aWFIS.getTempMaxCelsius();

		/* Convert from Celsius to Fahrenheit if necessary. */
		if (this.chk_usecelsius.isChecked()) {
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempCelciusMinMax(tempMin, tempMax);
		} else {
			tempMin = WeatherUtils.celsiusToFahrenheit(tempMin);
			tempMax = WeatherUtils.celsiusToFahrenheit(tempMax);
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempFahrenheitMinMax(tempMin, tempMax);
		}
	}

	private void updateWeatherInfoView(int aResourceID,
			WeatherCurrentCondition aWCIS) throws MalformedURLException {
		
		/* Construct the Image-URL. */
		URL imgURL = new URL("http://www.google.com" + aWCIS.getIconURL());
		((SingleWeatherInfoView) findViewById(aResourceID)).setRemoteImage(imgURL);

		/* Convert from Celsius to Fahrenheit if necessary. */
		if (this.chk_usecelsius.isChecked()){
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempCelcius(aWCIS.getTempCelcius());
		}else{
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempFahrenheit(aWCIS.getTempFahrenheit());
		}
	}

	private void resetWeatherInfoViews() {
		((SingleWeatherInfoView)findViewById(R.id.weather_today)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_1)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_2)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_3)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_4)).reset();
	}
	//判断输入是否为汉字
	public static boolean isCN(String str)
	 {
	  char chr = str.charAt(0);
	  if((chr >='a' && chr <='z') ||(chr >='A' && chr <='Z') )
	  {
	   return false;
	  }else{
	   return true;
	  }
	 }

    //查询城市所在的数组位置	 
	 public static int getCnPosition(String[] str_arr, String str_)
	 {
	  int result = -1;
	  for(int i = 0; i < str_arr.length; i++)
	  {
	   if(str_arr[i].equals(str_))
	   {
	    result = i;
	   }
	  }
	  return result;
	 }
	
}
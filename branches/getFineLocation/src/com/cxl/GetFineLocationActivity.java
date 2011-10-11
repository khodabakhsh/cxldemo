package com.cxl;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class GetFineLocationActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		openGPSSettings();

	}

	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
			doWork();
			return;
		}

		Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		startActivityForResult(intent, 0); //此为设置完成后返回到获取界面  

	}

	private void doWork() {

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		// 获得最好的定位效果  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		// 使用省电模式  
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 获得当前的位置提供者  
		String provider = locationManager.getBestProvider(criteria, true);
		// 获得当前的位置  
		Location location = locationManager.getLastKnownLocation(provider);
		updateWithNewLocation(location);
		//监听位置变化，2秒一次，距离10米以上
		locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
	}

	//将位置信息显示在TextView中
	private void updateWithNewLocation(Location location) {
		String msg = "";
		TextView textView = (TextView) findViewById(R.id.fineLocation);

		if (location != null) {

			Geocoder gc = new Geocoder(this);
			List<Address> addresses = null;
			try {
				addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block  
				e.printStackTrace();
			}
			if (addresses.size() > 0) {
				msg += "AddressLine：" + addresses.get(0).getAddressLine(0) + "\n";
				msg += "CountryName：" + addresses.get(0).getCountryName() + "\n";
				msg += "Locality：" + addresses.get(0).getLocality() + "\n";
				msg += "FeatureName：" + addresses.get(0).getFeatureName();
			}
		} else {
			msg = "没有找到位置";
		}
		textView.setText(msg);
	}

	//位置监听器
	private final LocationListener locationListener = new LocationListener() {
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}

		//当位置变化时触发
		public void onLocationChanged(Location location) {
			//使用新的location更新TextView显示
			updateWithNewLocation(location);
		}
	};

}
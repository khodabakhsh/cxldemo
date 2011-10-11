package com.zhou.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class WhereAmIActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//声明LocationManager对象
		LocationManager loctionManager;
		String contextService = Context.LOCATION_SERVICE;
		//通过系统服务，取得LocationManager对象
		loctionManager = (LocationManager) getSystemService(contextService);

		//通过GPS位置提供器获得位置
		//        String provider=LocationManager.GPS_PROVIDER;
		//        Location location = loctionManager.getLastKnownLocation(provider);

		//使用标准集合，让系统自动选择可用的最佳位置提供器，提供位置
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
		criteria.setAltitudeRequired(false);//不要求海拔
		criteria.setBearingRequired(false);//不要求方位
		criteria.setCostAllowed(true);//允许有花费
		criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

		//从可用的位置提供器中，匹配以上标准的最佳提供器
		String provider = loctionManager.getBestProvider(criteria, true);

		//获得最后一次变化的位置
		Location location = loctionManager.getLastKnownLocation(provider);

		//显示在TextView中
		updateWithNewLocation(location);

		//监听位置变化，2秒一次，距离10米以上
		loctionManager.requestLocationUpdates(provider, 2000, 10, locationListener);
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

	//将位置信息显示在TextView中
	private void updateWithNewLocation(Location location) {
		String latLongString;
		TextView myLoctionText;
		myLoctionText = (TextView) findViewById(R.id.myLoctionText);
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat(纬度): " + lat + "\nLong(经度): " + lng;
		} else {
			latLongString = "没找到位置";
		}
		myLoctionText.setText("您当前的位置是:\n" + latLongString);
	}
}
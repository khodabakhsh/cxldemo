package com.cxl;

import java.util.List;
import java.util.Locale;
import com.google.android.maps.GeoPoint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class LocationDemo extends Activity {

	private TextView longitude;
	private TextView latitude;
	private TextView address;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		longitude = (TextView) findViewById(R.id.longitude);
		latitude = (TextView) findViewById(R.id.latitude);
		address = (TextView) findViewById(R.id.address);

		Location mLocation = getLocation(this);
		GeoPoint gp = getGeoByLocation(mLocation);
		Address mAddress = getAddressbyGeoPoint(this, gp);

		longitude.setText("Longitude: " + mLocation.getLongitude());
		latitude.setText("Latitude: " + mLocation.getLatitude());
		address.setText("Address: " + mAddress.getCountryName() + "," + mAddress.getLocality());
	}

	//Get the Location by GPS or WIFI  
	public Location getLocation(Context context) {
		LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		return location;
	}

	//通过Location获取GeoPoint  
	public GeoPoint getGeoByLocation(Location location) {
		GeoPoint gp = null;
		try {
			if (location != null) {
				double geoLatitude = location.getLatitude() * 1E6;
				double geoLongitude = location.getLongitude() * 1E6;
				gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gp;
	}

	//通过GeoPoint来获取Address  
	public Address getAddressbyGeoPoint(Context cntext, GeoPoint gp) {
		Address result = null;
		try {
			if (gp != null) {
				Geocoder gc = new Geocoder(cntext, Locale.CHINA);

				double geoLatitude = (int) gp.getLatitudeE6() / 1E6;
				double geoLongitude = (int) gp.getLongitudeE6() / 1E6;

				List<Address> lstAddress = gc.getFromLocation(geoLatitude, geoLongitude, 1);
				if (lstAddress.size() > 0) {
					result = lstAddress.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
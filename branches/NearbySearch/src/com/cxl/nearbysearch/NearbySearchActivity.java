package com.cxl.nearbysearch;

import android.R.integer;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiInfo;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.PoiOverlay;
import com.baidu.mapapi.c;

public class NearbySearchActivity extends MapActivity {
	BMapManager mBMapMan = null;
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	MapView mMapView = null; // 地图View
	Button mBtnSearch = null; // 搜索按钮
	private static final String[] m={"2000","1000","500","300","100"};  

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Spinner spinner = (Spinner) findViewById(R.id.bound);  
	        //将可选内容与ArrayAdapter连接起来  
		ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);  
	          
	        //设置下拉列表的风格  
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	          
	        //将adapter 添加到spinner中  
	        spinner.setAdapter(adapter);  
	          
	          
	        //设置默认值  
	        spinner.setVisibility(View.VISIBLE);  
	        
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("AF1A46F0DCA20F042FC869792EFD3623871DF746", null);
		super.initMapActivity(mBMapMan);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		// 设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setDrawOverlayWhenZooming(true);

		// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		mSearch.init(mBMapMan, new MKSearchListener() {

			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					
					Toast.makeText(NearbySearchActivity.this, "抱歉，未找到结果",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				// 将地图移动到第一个POI中心点
				if (res.getCurrentNumPois() > 0) {
					
					
					// 将poi结果显示到地图上
					PoiOverlay poiOverlay = new PoiOverlay(
							NearbySearchActivity.this, mMapView);
					poiOverlay.setData(res.getAllPoi());
					mMapView.getOverlays().clear();
					mMapView.getOverlays().add(poiOverlay);
					mMapView.invalidate();
					mMapView.getController().animateTo(res.getPoi(0).pt);
				} else if (res.getCityListNum() > 0) {
					String strInfo = "在";
					for (int i = 0; i < res.getCityListNum(); i++) {
						strInfo += res.getCityListInfo(i).city;
						strInfo += ",";
					}
					strInfo += "找到结果";
					Toast.makeText(NearbySearchActivity.this, strInfo,
							Toast.LENGTH_LONG).show();
				}
			}
			/**
			 * 根据经纬度搜索地址信息结果
			 * 
			 * @param result
			 *            搜索结果
			 * @param iError
			 *            错误号（0表示正确返回）
			 */
			public void onGetAddrResult(MKAddrInfo result, int iError) {
				if (result == null) {
					return;
				}
				StringBuffer sb = new StringBuffer();
				// 经纬度所对应的位置
				sb.append(result.strAddr).append("\n");

				// 判断该地址附近是否有POI（Point of Interest,即兴趣点）
				if (null != result.poiList) {
					// 遍历所有的兴趣点信息
					for (MKPoiInfo poiInfo : result.poiList) {
						sb.append("----------------------------------------")
								.append("\n");
						sb.append("名称：").append(poiInfo.name).append("\n");
						sb.append("地址：").append(poiInfo.address).append("\n");
						sb.append("经度：")
								.append(poiInfo.pt.getLongitudeE6() / 1000000.0f)
								.append("\n");
						sb.append("纬度：")
								.append(poiInfo.pt.getLatitudeE6() / 1000000.0f)
								.append("\n");
						sb.append("电话：").append(poiInfo.phoneNum).append("\n");
						sb.append("邮编：").append(poiInfo.postCode).append("\n");
						// poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路
						sb.append("类型：").append(poiInfo.ePoiType).append("\n");
					}
				}
				// 将地址信息、兴趣点信息显示在TextView上
				TextView currentLocation = (TextView) findViewById(R.id.currentLocation);
				currentLocation.setText(sb.toString());
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}

		});

		// 设定搜索按钮的响应
		mBtnSearch = (Button) findViewById(R.id.search);

		OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				SearchButtonProcess(v);
			}
		};

		mBtnSearch.setOnClickListener(clickListener);
	}

	void SearchButtonProcess(View v) {
		if (mBtnSearch.equals(v)) {
			Location location =  getLocation(NearbySearchActivity.this);
			Spinner bound = (Spinner) findViewById(R.id.bound);
			EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
			int latitude = (int) (location.getLatitude() * 1E6);
			int  longitude = (int) (location.getLongitude() * 1E6);
			// 查询该经纬度值所对应的地址位置信息
			mSearch.reverseGeocode(new GeoPoint(latitude, longitude));
//			mSearch.poiSearchInCity(editCity.getText().toString(),
//					editSearchKey.getText().toString());
			mSearch.poiSearchNearBy(editSearchKey.getText().toString(), new GeoPoint(latitude, longitude), Integer.valueOf(bound.getSelectedItem().toString()));
		}
	}

	// Get the Location by GPS or WIFI
	public Location getLocation(Context context) {
		LocationManager locMan = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = locMan
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locMan
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		return location;
	}

	// MapController mMapController = mMapView.getController(); //
	// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
	// GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
	// (int) (116.404 * 1E6)); // 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
	// mMapController.setCenter(point); // 设置地图中心点
	// mMapController.setZoom(12); // 设置地图zoom级别
	// }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onDestroy() {
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}
}
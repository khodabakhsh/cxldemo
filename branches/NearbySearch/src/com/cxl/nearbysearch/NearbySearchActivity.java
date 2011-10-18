package com.cxl.nearbysearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
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
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.PoiOverlay;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class NearbySearchActivity extends MapActivity implements UpdatePointsNotifier {
	BMapManager mBMapMan = null;
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	MapView mMapView = null; // 地图View
	Button mBtnSearch = null; // 搜索按钮
	private static final String[] m = { "4000", "2000", "1000", "800", "500", "300", "100" };

	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	int currentPointTotal = 0;//当前积分
	public static final int requirePoint = 200;//要求积分
	private static boolean hasEnoughRequrePoint = false;//是否达到积分

	private void showDialog() {
		new AlertDialog.Builder(NearbySearchActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("提示,当前的积分：" + currentPointTotal)
				.setMessage(
						"【温馨提示】:只要积分满足" + requirePoint + "，本程序就可以永久使用！！ 您当前的积分不足" + requirePoint
								+ "，所以有此提示。\n【免费获得积分方法】：请点击确认键进入推荐下载列表 , 下载并安装软件获得相应积分。")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(NearbySearchActivity.this).showOffers(NearbySearchActivity.this);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						//						finish();
					}
				}).show();
	}

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
		if (currentPointTotal >= requirePoint) {
			hasEnoughRequrePoint = true;
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

	/*
	 * 自定义标示
	 */
	public class MyOverlay extends Overlay {
		GeoPoint geoPoint;
		Paint paint;

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			Point point = mMapView.getProjection().toPixels(geoPoint, null);
			paint.setColor(Color.RED);
			paint.setTextSize(20);
			paint.setFakeBoldText(true);
			canvas.drawText("★您当前所在位置", point.x, point.y, paint);
		}

		public MyOverlay(Location location) {
			int latitude = (int) (location.getLatitude() * 1E6);
			int longitude = (int) (location.getLongitude() * 1E6);
			this.geoPoint = new GeoPoint(latitude, longitude);
			this.paint = new Paint();

		}
	}

	/**
	 * 显示当前位置
	 */
	private void showCurrentLocation() {
		Location location = getLocation(NearbySearchActivity.this);
		if (location == null) {
			Toast.makeText(NearbySearchActivity.this, "请确保您的手机已经开启了GPS功能", Toast.LENGTH_LONG).show();
			return;
		}
		mMapView.getOverlays().clear();
		//当前位置
		MyOverlay myOverlay = new MyOverlay(location);
		mMapView.getOverlays().add(myOverlay);
		mMapView.getController().setCenter(myOverlay.geoPoint); //设置地图中心点  
		mMapView.getController().setZoom(15); //设置地图zoom级别  
		mMapView.getController().animateTo(myOverlay.geoPoint);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(NearbySearchActivity.this).showMore(NearbySearchActivity.this);
			}
		});
		Button offers = (Button) findViewById(R.id.OffersButton);
		offers.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(NearbySearchActivity.this).showOffers(NearbySearchActivity.this);
			}
		});

		Spinner spinner = (Spinner) findViewById(R.id.bound);
		//将可选内容与ArrayAdapter连接起来  
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);

		//设置下拉列表的风格  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		//将adapter 添加到spinner中  
		spinner.setAdapter(adapter);
		//选择默认第四个
		spinner.setSelection(3);

		//设置默认值  
		spinner.setVisibility(View.VISIBLE);

		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("1A48205208F7914483A0E3D46A2FCF898CDC4396", null);
		super.initMapActivity(mBMapMan);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		// 设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setDrawOverlayWhenZooming(true);
		showCurrentLocation();

		// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		mSearch.init(mBMapMan, new MKSearchListener() {

			public void onGetPoiResult(MKPoiResult res, int type, int error) {

				showCurrentLocation();

				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {

					Toast.makeText(NearbySearchActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
				}

				// 将地图移动到第一个POI中心点
				if (res.getCurrentNumPois() > 0) {

					// 将poi结果显示到地图上
					PoiOverlay poiOverlay = new PoiOverlay(NearbySearchActivity.this, mMapView);
					poiOverlay.setData(res.getAllPoi());

					mMapView.getOverlays().add(poiOverlay);

					mMapView.invalidate();
					//					mMapView.getController().animateTo(res.getPoi(0).pt);

				}
			}

			public void onGetAddrResult(MKAddrInfo result, int iError) {
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
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

			Location location = getLocation(NearbySearchActivity.this);
			if (location == null) {
				Toast.makeText(NearbySearchActivity.this, "请确保您的手机已经开启了GPS功能", Toast.LENGTH_LONG).show();
				return;
			}

			if (!hasEnoughRequrePoint) {//没达到积分
					showDialog();
			}

			Spinner bound = (Spinner) findViewById(R.id.bound);
			EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
			int latitude = (int) (location.getLatitude() * 1E6);
			int longitude = (int) (location.getLongitude() * 1E6);
			//			mSearch.reverseGeocode(new GeoPoint(latitude, longitude));
			//			mSearch.poiSearchInCity(editCity.getText().toString(),
			//					editSearchKey.getText().toString());
			mSearch.poiSearchNearBy(editSearchKey.getText().toString(), new GeoPoint(latitude, longitude),
					Integer.valueOf(bound.getSelectedItem().toString()));
		}
	}

	// Get the Location by GPS or WIFI
	public Location getLocation(Context context) {
		LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		return location;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
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
		AppConnect.getInstance(this).getPoints(this);
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}
}
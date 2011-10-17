package com.cxl.lifesearch;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class LifeSearchActivity extends MapActivity  {
	// 定义地图引擎管理类
	private BMapManager mapManager;
	// 定义搜索服务类
	private MKSearch mMKSearch;

	private EditText longitudeEditText;
	private EditText latitudeEditText;
	private TextView addressTextView;
	private Button queryButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_address);

		// 初始化MapActivity
		mapManager = new BMapManager(getApplication());
		// init方法的第一个参数需填入申请的API Key
		mapManager.init("AF1A46F0DCA20F042FC869792EFD3623871DF746", null);
		super.initMapActivity(mapManager);

		// 初始化MKSearch
		mMKSearch = new MKSearch();
		mMKSearch.init(mapManager, new MySearchListener());

		// 通过id查询在布局文件中定义的控件
		longitudeEditText = (EditText) findViewById(R.id.longitude_input);
		latitudeEditText = (EditText) findViewById(R.id.latitude_input);
		addressTextView = (TextView) findViewById(R.id.address_text);
		queryButton = (Button) findViewById(R.id.query_button);

		// 给地址查询按钮设置单击事件监听器
		queryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 用户输入的经度值
				String longitudeStr = longitudeEditText.getText().toString();
				// 用户输入的纬度值
				String latitudeStr = latitudeEditText.getText().toString();

				try {
					// 将用户输入的经纬度值转换成int类型
					int longitude = (int) (1000000 * Double
							.parseDouble(longitudeStr));
					int latitude = (int) (1000000 * Double
							.parseDouble(latitudeStr));

					// 查询该经纬度值所对应的地址位置信息
					mMKSearch.reverseGeocode(new GeoPoint(latitude, longitude));
				} catch (Exception e) {
					addressTextView.setText("查询出错，请检查您输入的经纬度值！");
				}
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onDestroy() {
		if (mapManager != null) {
			// 程序退出前需调用此方法
			mapManager.destroy();
			mapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mapManager != null) {
			// 终止百度地图API
			mapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mapManager != null) {
			// 开启百度地图API
			mapManager.start();
		}
		super.onResume();
	}

	/**
	 * 内部类实现MKSearchListener接口,用于实现异步搜索服务
	 * 
	 * @author liufeng
	 */
	public class MySearchListener implements MKSearchListener {
		/**
		 * 根据经纬度搜索地址信息结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
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
			addressTextView.setText(sb.toString());
		}

		/**
		 * 驾车路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
		}

		/**
		 * POI搜索结果（范围检索、城市POI检索、周边检索）
		 * 
		 * @param result
		 *            搜索结果
		 * @param type
		 *            返回结果类型（11,12,21:poi列表 7:城市列表）
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
		}

		/**
		 * 公交换乘路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
		}

		/**
		 * 步行路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
		}
	}
}
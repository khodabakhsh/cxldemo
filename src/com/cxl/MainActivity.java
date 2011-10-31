package com.cxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cxl.shengxiao.R;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class MainActivity extends ListActivity implements UpdatePointsNotifier {

	TextView pointsTextView;
	String displayText;
	boolean update_text = false;
	public static int currentPointTotal = 0;// 当前积分
	public static final int requirePoint = 60;// 要求积分
	public static boolean hasEnoughRequrePoint = false;// 是否达到积分

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.vlist, new String[] { "id", "name",
				"dateRange", "img" }, new int[] { R.id.id, R.id.name, R.id.dateRange, R.id.img });
		setListAdapter(adapter);
		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);

	}

	protected void onListItemClick(ListView arg0, View arg1, int pos, long id) {
		//获得选中项的HashMap对象 
		HashMap<String, String> map = (HashMap<String, String>) arg0.getItemAtPosition(pos);
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, DetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", map.get("id"));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		map.put("id", "shu");
		map.put("name", "子鼠");
		map.put("dateRange", "1900 1912 1924 1936 1948 1960 1972 1984 1996 2008 2020 2032 2044 2056");
		map.put("img", R.drawable.shu);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "niu");
		map.put("name", "丑牛");
		map.put("dateRange", "1901 1913 1925 1937 1949 1961 1973 1985 1997 2009 2021 2033 2045 2057");
		map.put("img", R.drawable.niu);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "hu");
		map.put("name", "寅虎");
		map.put("dateRange", "1902 1914 1926 1938 1950 1962 1974 1986 1998 2010 2022 2034 2046 2058");
		map.put("img", R.drawable.hu);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "tu");
		map.put("name", "卯兔");
		map.put("dateRange", "1903 1915 1927 1939 1951 1963 1975 1987 1999 2011 2023 2035 2047 2059");
		map.put("img", R.drawable.tu);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "long");
		map.put("name", "辰龙");
		map.put("dateRange", "1904 1916 1928 1940 1952 1964 1976 1988 2000 2012 2024 2036 2048 2060");
		map.put("img", R.drawable.long1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "she");
		map.put("name", "巳蛇");
		map.put("dateRange", "1905 1917 1929 1941 1953 1965 1977 1989 2001 2013 2025 2037 2049 2061");
		map.put("img", R.drawable.she);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "ma");
		map.put("name", "午马");
		map.put("dateRange", "1906 1918 1930 1942 1954 1966 1978 1990 2002 2014 2026 2038 2050 2062");
		map.put("img", R.drawable.ma);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "yang");
		map.put("name", "未羊");
		map.put("dateRange", "1907 1919 1931 1943 1955 1967 1979 1991 2003 2015 2027 2039 2051 2063");
		map.put("img", R.drawable.yang);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "hou");
		map.put("name", "申猴");
		map.put("dateRange", "1908 1920 1932 1944 1956 1968 1980 1992 2004 2016 2028 2040 2052 2064");
		map.put("img", R.drawable.hou);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "ji");
		map.put("name", "酉鸡");
		map.put("dateRange", "1909 1921 1933 1945 1957 1969 1981 1993 2005 2017 2029 2041 2053 2065");
		map.put("img", R.drawable.ji);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "gou");
		map.put("name", "戌狗");
		map.put("dateRange", "1910 1922 1934 1946 1958 1970 1982 1994 2006 2018 2030 2042 2054 2066");
		map.put("img", R.drawable.gou);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("id", "zhu");
		map.put("name", "亥猪");
		map.put("dateRange", "1911 1923 1935 1947 1959 1971 1983 1995 2007 2019 2031 2043 2055 2067");
		map.put("img", R.drawable.zhu);
		list.add(map);

		return list;
	}
}
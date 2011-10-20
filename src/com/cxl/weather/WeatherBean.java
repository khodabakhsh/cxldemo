package com.cxl.weather;

import java.util.List;
import java.util.Map;

public class WeatherBean {

	private String cityName;

	private String cityDescription;
	private String liveWeather;

	private List<Map<String, Object>> list;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityDescription() {
		return cityDescription;
	}

	public void setCityDescription(String cityDescription) {
		this.cityDescription = cityDescription;
	}

	public String getLiveWeather() {
		return liveWeather;
	}

	public void setLiveWeather(String liveWeather) {
		this.liveWeather = liveWeather;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	
}

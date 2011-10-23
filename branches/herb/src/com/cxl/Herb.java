package com.cxl;

import java.util.HashMap;
import java.util.Map;

import android.R.string;
import com.cxl.herb.R;

public class Herb {
	private String id;
	private String name;
	private String dateRange;
	private String picture;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Herb(String id, String name, String dateRange,
			String picture) {
		super();
		this.id = id;
		this.name = name;
		this.dateRange = dateRange;
		this.picture = picture;
	}

	@Override
	public String toString() {
		String item = "map = new HashMap<String, Object>();	map.put(\"id\", \""+id+"\");		map.put(\"name\", \""+name+"\");		map.put(\"dateRange\", \""+dateRange+"\");		map.put(\"img\", R.drawable.icon);		list.add(map)";
		return item;
	}

}

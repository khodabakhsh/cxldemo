package com.bincode.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 列车数据函数 
 * @author bincode
 * @email	5235852@qq.com
 */
public class TrainDataUtils {
	
	public static final String saveLoginCodePath = "D://login-code.jpg";	
	public static final String saveSumbitCodePath = "D://submit-code.jpg";
	
	/**
	 * 加载站点信息
	 */
	public static Map<String,String> stationNames = new HashMap<String, String>();
	static{
		try {
			String station_name_string = FileUtils.readFileToString(new File(JavaUtils.classpath+"/station_name_java.js"), "UTF-8");
			for(String station_name : StringUtils.split(station_name_string, "@")){
				String[] station = StringUtils.split(station_name, "|");
				stationNames.put(station[1], station[2]);
			}
		} catch (IOException e) {
		}
	}
	
	/**
	 * 加载座位信息
	 */
	public static Map<String,String> seatNameValueMap = new LinkedHashMap<String,String>();//座位在官网提交数据对应的值
	public static Map<String,Integer> seatNameIndexMap = new LinkedHashMap<String,Integer>();//座位在官网搜索数据中所在的序号
	static{
		seatNameValueMap.put("商务座","9");
		seatNameIndexMap.put("商务座", 5);
		
		seatNameValueMap.put("特等座", "P");
		seatNameIndexMap.put("特等座", 6);
		
		seatNameValueMap.put("一等座", "M");
		seatNameIndexMap.put("一等座", 7);
		
		seatNameValueMap.put("二等座","O");
		seatNameIndexMap.put("二等座", 8);
		
		seatNameValueMap.put("高级软卧", "6");
		seatNameIndexMap.put("高级软卧", 9);
		
		seatNameValueMap.put("软卧", "4");
		seatNameIndexMap.put("软卧", 10);
		
		seatNameValueMap.put("硬卧", "3");
		seatNameIndexMap.put("硬卧", 11);
		
		seatNameValueMap.put("软座", "2");
		seatNameIndexMap.put("软座", 12);
		
		seatNameValueMap.put("硬座", "1");
		seatNameIndexMap.put("硬座", 13);
	}
	
	public static String today;
	static{
		today = DateUtils.getDateTime("yyyy-MM-dd", new Date());
	}
	
	/**
	 * 订票帐号信息
	 */
	public static String query_train_date = "2012-01-13";//订单时间
	public static String username = "****";//登录帐号
	public static String password = "****";//登录密码
	public static String name = "****";//姓名
	public static String cardid = "****";//身份证
	public static String mobile = "****";//手机
	
	/**
	 * 需要自动订票的信息
	 */
	public static List<String[]> queryList = new ArrayList<String[]>();
	static{
		queryList.add(new String[]{"北京西","南昌","z133","硬卧"});
		queryList.add(new String[]{"北京西","南昌","z65","硬卧"});
		queryList.add(new String[]{"北京西","南昌","z67","硬卧"});
		queryList.add(new String[]{"北京西","南昌","t107","硬卧"});
	}
	
	/**
	 * 请求订单参数
	 */
	public static Map<String,String> queryOrderParams = new HashMap<String, String>();
	static{
		queryOrderParams.put("round_start_time_str", "00:00--24:00");
		queryOrderParams.put("start_time_str", "00:00--24:00");
		queryOrderParams.put("round_train_date",today);
		queryOrderParams.put("train_date", query_train_date);
		queryOrderParams.put("train_class_arr", "QB#D#Z#T#K#QT#");
		queryOrderParams.put("train_pass_type", "QB");
		queryOrderParams.put("single_round_type", "1");
		queryOrderParams.put("include_student", "00");
	}
	/**
	 * 请求订单参数名称
	 */
	public static String[] queryOrderParamNames = {
		"station_train_code","lishi","train_start_time","trainno","from_station_telecode","to_station_telecode","arrive_time","from_station_name","from_station_telecode_name","to_station_name","to_station_telecode_name","ypInfoDetail"
	};
	/**
	 * 请求订单参数索引
	 */
	public static int[] queryOrderParamIndexs = {0,1,2,3,4,5,6,7,7,8,8,9};
	
	
	/**
	 * 提交订单参数
	 */
	public static Map<String,String> submitOrderParams = new HashMap<String, String>();
	static{
		submitOrderParams.put("oldPassengers", name+",1,"+cardid);
		submitOrderParams.put("passenger_1_cardno", cardid);
		submitOrderParams.put("passenger_1_cardtype", "1");
		submitOrderParams.put("passenger_1_mobileno", mobile);
		submitOrderParams.put("passenger_1_name", name);
		submitOrderParams.put("passenger_1_ticket", "1");
		submitOrderParams.put("include_student", "00");
		submitOrderParams.put("orderRequest.reserve_flag", "A");
		
		//3 是硬卧
		submitOrderParams.put("passengerTickets", "3,1,"+name+",1,"+cardid+","+mobile+",Y");
		submitOrderParams.put("passenger_1_seat", "3");
	}
}

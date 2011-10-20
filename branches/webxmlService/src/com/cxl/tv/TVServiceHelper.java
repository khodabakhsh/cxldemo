package com.cxl.tv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

public class TVServiceHelper {
	public static void main(String[] args) {
		TVServiceHelper helper = new TVServiceHelper();
		for (String area : helper.getAreas()) {
			System.out.println(area);
		}
		/**
		 * 香港
		 */
		
		System.out.println("=====================香港=======================");
		for (String tvStation : helper.getTVstationString("32")) {
			System.out.println(tvStation);
		}
		System.out.println("=====================TVB=======================");
		for (String tvStation : helper.getTVchannelString("162")) {
			System.out.println(tvStation);
		}
		System.out.println("=====================翡翠台=======================");
		for (String tvStation : helper.getTVprogramString("537","2011-10-20")) {
			System.out.println(tvStation);
		}
		/**
		 * 广东
		 */
		System.out.println("=====================广东=======================");
		for (String tvStation : helper.getTVstationString("19")) {
			System.out.println(tvStation);
		}
		System.out.println("=====================广东电视台=======================");
		for (String tvStation : helper.getTVchannelString("55")) {
			System.out.println(tvStation);
		}
		System.out.println("=====================广州新闻频道=======================");
		for (String tvStation : helper.getTVprogramString("337","2011-10-20")) {
			System.out.println(tvStation);
		}
	}

	//WSDL文档中的命名空间
	private static final String targetNameSpace = "http://WebXml.com.cn/";
	//WSDL文档中的URL
	private static final String WSDL = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx?wsdl";

	//[第一步] 获得支持的省市（地区）和分类电视名称 String()
	private static final String getAreaString = "getAreaString";

	//[第二步] 通过省市ID或分类电视ID获得电视台名称 String()
	private static final String getTVstationString = "getTVstationString";

	//[第三步] 通过电视台ID获得该电视台频道名称 String()
	private static final String getTVchannelString = "getTVchannelString";

	//[第四步] 通过频道ID获得该频道节目 String()
	private static final String getTVprogramString = "getTVprogramString";

	/**
	 * [第一步] 获得支持的省市（地区）和分类电视id和名称 String()
	 */
	public List<String> getAreas() {
		List<String> areas = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(targetNameSpace, getAreaString);
		//request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;

		AndroidHttpTransport httpTranstation = new AndroidHttpTransport(WSDL);
		//或者HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
		try {

			httpTranstation.call(targetNameSpace + getAreaString, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			//下面对结果进行解析，结构类似json对象
			//str=(String) result.getProperty(6).toString();

			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				areas.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		}
		return areas;
	}

	/**
	*  [第二步] 通过省市ID或分类电视ID获得电视台id和名称String()
	*/
	public List<String> getTVstationString(String theAreaID) {
		List<String> TVstations = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(targetNameSpace, getTVstationString);
		soapObject.addProperty("theAreaID", theAreaID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(targetNameSpace + getTVstationString, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				TVstations.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		}
		return TVstations;
	}

	/**
	 * [第三步] 通过电视台ID获得该电视台频道id和名称 String()
	*/
	public List<String> getTVchannelString(String theTVstationID) {
		List<String> TVchannels = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(targetNameSpace, getTVchannelString);
		soapObject.addProperty("theTVstationID", theTVstationID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(targetNameSpace + getTVchannelString, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				TVchannels.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		}
		return TVchannels;
	}

	/**
	 *[第四步] 通过频道ID获得该频道节目 String()
	*/
	public List<String> getTVprogramString(String theTVchannelID,String theDate) {
		List<String> TVprograms = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(targetNameSpace, getTVprogramString);
		soapObject.addProperty("theTVchannelID ", theTVchannelID);
		soapObject.addProperty("theDate ", theDate);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(targetNameSpace + getTVprogramString, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				TVprograms.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		}
		return TVprograms;
	}
	//
	//	/**
	//	* 解析返回的结果
	//	* @param soapObject
	//	*/
	//	protected WeatherBean parserWeather(SoapObject soapObject) {
	//		WeatherBean bean = new WeatherBean();
	//
	//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	//
	//		Map<String, Object> map = new HashMap<String, Object>();
	//
	//		//城市名
	//		bean.setCityName(soapObject.getProperty(1).toString());
	//		//城市简介
	//		bean.setCityDescription(soapObject.getProperty(soapObject.getPropertyCount() - 1).toString());
	//		//天气实况+建议
	//		bean.setLiveWeather(soapObject.getProperty(10).toString() + "\n" + soapObject.getProperty(11).toString());
	//
	//		//其他数据
	//		//日期，
	//		String date = soapObject.getProperty(6).toString();
	//		//---------------------------------------------------
	//		String weatherToday = "今天：" + date.split(" ")[0];
	//		weatherToday += "\n天气：" + date.split(" ")[1];
	//		weatherToday += "\n气温：" + soapObject.getProperty(5).toString();
	//		weatherToday += "\n风力：" + soapObject.getProperty(7).toString();
	//		weatherToday += "\n";
	//
	//		List<Integer> icons = new ArrayList<Integer>();
	//
	//		icons.add(parseIcon(soapObject.getProperty(8).toString()));
	//		icons.add(parseIcon(soapObject.getProperty(9).toString()));
	//
	//		map.put("weatherDay", weatherToday);
	//		map.put("icons", icons);
	//		list.add(map);
	//
	//		//-------------------------------------------------
	//		map = new HashMap<String, Object>();
	//		date = soapObject.getProperty(13).toString();
	//		String weatherTomorrow = "明天：" + date.split(" ")[0];
	//		weatherTomorrow += "\n天气：" + date.split(" ")[1];
	//		weatherTomorrow += "\n气温：" + soapObject.getProperty(12).toString();
	//		weatherTomorrow += "\n风力：" + soapObject.getProperty(14).toString();
	//		weatherTomorrow += "\n";
	//
	//		icons = new ArrayList<Integer>();
	//
	//		icons.add(parseIcon(soapObject.getProperty(15).toString()));
	//		icons.add(parseIcon(soapObject.getProperty(16).toString()));
	//
	//		map.put("weatherDay", weatherTomorrow);
	//		map.put("icons", icons);
	//		list.add(map);
	//		//--------------------------------------------------------------
	//		map = new HashMap<String, Object>();
	//
	//		date = soapObject.getProperty(18).toString();
	//		String weatherAfterTomorrow = "后天：" + date.split(" ")[0];
	//		weatherAfterTomorrow += "\n天气：" + date.split(" ")[1];
	//		weatherAfterTomorrow += "\n气温：" + soapObject.getProperty(17).toString();
	//		weatherAfterTomorrow += "\n风力：" + soapObject.getProperty(19).toString();
	//		weatherAfterTomorrow += "\n";
	//
	//		icons = new ArrayList<Integer>();
	//		icons.add(parseIcon(soapObject.getProperty(20).toString()));
	//		icons.add(parseIcon(soapObject.getProperty(21).toString()));
	//
	//		map.put("weatherDay", weatherAfterTomorrow);
	//		map.put("icons", icons);
	//		list.add(map);
	//		//--------------------------------------------------------------
	//
	//		bean.setList(list);
	//		return bean;
	//	}
	//
	//	//解析图标字符串
	//	private int parseIcon(String data) {
	//		// 0.gif，返回名称0,
	//		int resID = 32;
	//		String result = data.substring(0, data.length() - 4).trim();
	//		// String []icon=data.split(".");
	//		// String result=icon[0].trim();
	//		// Log.e("this is the icon", result.trim());
	//
	//		if (!result.equals("nothing")) {
	//			resID = Integer.parseInt(result.trim());
	//		}
	//		return resID;
	//		//return ("a_"+data).split(".")[0];
	//	}
}

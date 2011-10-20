package com.cxl.tvservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import android.R.string;

public class TVServiceHelper {
	//	public static void main(String[] args) {
	//		TVServiceHelper helper = new TVServiceHelper();
	//		for (KeyValuePair area : helper.getAreas()) {
	//			System.out.println(area);
	//		}
	//		/**
	//		 * 香港
	//		 */
	//
	////		System.out.println("=====================香港=======================");
	////		for (String tvStation : helper.getTVstationString("32")) {
	////			System.out.println(tvStation);
	////		}
	////		System.out.println("=====================TVB=======================");
	////		for (String tvStation : helper.getTVchannelString("162")) {
	////			System.out.println(tvStation);
	////		}
	////		System.out.println("=====================翡翠台=======================");
	////		for (String tvStation : helper.getTVprogramString("537", "2011-10-20")) {
	////			System.out.println(tvStation);
	////		}
	////		/**
	////		 * 广东
	////		 */
	////		System.out.println("=====================广东=======================");
	////		for (String tvStation : helper.getTVstationString("19")) {
	////			System.out.println(tvStation);
	////		}
	////		System.out.println("=====================广东电视台=======================");
	////		for (String tvStation : helper.getTVchannelString("55")) {
	////			System.out.println(tvStation);
	////		}
	////		System.out.println("=====================广东新闻频道=======================");
	////		for (String tvStation : helper.getTVprogramString("337", "2011-10-20")) {
	////			System.out.println(tvStation);
	////		}
	//	}

	/**
	 * 地区spinner的index
	 */
	public static int currentAreaSpinnerIndex = 0;

	/**
	 * 广东省
	 */
	private static final String GuangDong = "广东省";

	/**
	 *电视台spinner的index
	 */
	public static int currentTVstationSpinnerIndex = 0;

	/**
	 * 广东电视台
	 */
	private static final String GuangDongTVStation = "广东电视台";

	/**
	 *频道spinner的index
	 */
	public static int currentTVchannelSpinnerIndex = 0;

	/**
	 *广东新闻频道
	 */
	private static final String GuangZhouNewsChannel = "广东新闻频道";

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
	public static List<KeyValuePair> getAreas() {
		List<KeyValuePair> areas = new ArrayList<KeyValuePair>();
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
			String[] splitArray = null;
			for (int index = 0; index < count; index++) {
				splitArray = result.getProperty(index).toString().split("@");
				if (splitArray.length >= 2) {
					if (GuangDong.equals(splitArray[1])) {
						currentAreaSpinnerIndex = index;
					}
					areas.add(new KeyValuePair(splitArray[0], splitArray[1]));
				}
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
	public static List<KeyValuePair> getTVstationString(String theAreaID) {
		List<KeyValuePair> TVstations = new ArrayList<KeyValuePair>();
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
			String[] splitArray = null;
			for (int index = 0; index < count; index++) {
				splitArray = result.getProperty(index).toString().split("@");
				if (splitArray.length >= 2) {
					if (GuangDongTVStation.equals(splitArray[1])) {
						currentTVstationSpinnerIndex = index;
					}
					TVstations.add(new KeyValuePair(splitArray[0], splitArray[1]));
				}
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
	public static List<KeyValuePair> getTVchannelString(String theTVstationID) {
		List<KeyValuePair> TVchannels = new ArrayList<KeyValuePair>();
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
			String[] splitArray = null;
			for (int index = 0; index < count; index++) {
				splitArray = result.getProperty(index).toString().split("@");
				if (splitArray.length >= 2) {
					if (GuangZhouNewsChannel.equals(splitArray[1])) {
						currentTVchannelSpinnerIndex = index;
					}
					TVchannels.add(new KeyValuePair(splitArray[0], splitArray[1]));
				}
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
	public static List<String> getTVprogramString(String theTVchannelID, String theDate) {
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

	/**
	 *[第四步] 通过频道ID获得该频道节目 String()
	*/
	public static String getTVprogramDetail(String theTVchannelID, String theDate) {
		StringBuilder builder = new StringBuilder("");
		List<String> list = getTVprogramString(theTVchannelID, theDate);
		String[] array = null;
		for (String item : list) {
			array = item.replace("(AM)", "").replace("(PM)", "").split("@@@");
			if(array.length>=2){
				builder.append(array[0]+"【"+array[1]+"】\n");
			}
		}
		return builder.toString();
	}
}

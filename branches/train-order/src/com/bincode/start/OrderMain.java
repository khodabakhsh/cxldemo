package com.bincode.start;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bincode.util.DateUtils;
import com.bincode.util.HttpUtils;
import com.bincode.util.JavaUtils;
import com.bincode.util.MailUtils;
import com.bincode.util.TrainDataUtils;

/**
 * 在线自动抢订火车票-控制台版本
 * @author bincode
 * @email	5235852@qq.com
 */
public class OrderMain {
//	private static String cookie = "JSESSIONID=D47CE3067663B545FD3DA7ECAA30E097; BIGipServerotsweb=2885091594.36895.0000; CiVlkRggf2=MDAwM2IyNTI5ODgwMDAwMDAwMjMwcG8MLykxMzI1NjI3ODg5; VjbXN9QDqv=MDAwM2IyNTRmZWMwMDAwMDAwMDMwXyBSYkIxMzI1NjA2NjMz";
	private static String cookie = null;
	private static String queryUrlTemp = "https://dynamic.12306.cn/otsweb/order/querySingleAction.do?method=queryLeftTicket&orderRequest.train_date=${train_date}&orderRequest.from_station_telecode=${from_station_telecode}&orderRequest.to_station_telecode=${to_station_telecode}&orderRequest.train_no=&trainPassType=QB&trainClass=QB%23D%23Z%23T%23K%23QT%23&includeStudent=00&seatTypeAndNum=&orderRequest.start_time_str=00%3A00--24%3A00";
	
	public static void main(String[] args) throws Exception {
		waitSetSubmitCode();
		login();
		for(final String[] queryStrs : TrainDataUtils.queryList){
			final Thread thred = new Thread(){
				public void run(){
					String from_station_telecode = TrainDataUtils.stationNames.get(queryStrs[0]);//出发站点代码
					String to_station_telecode = TrainDataUtils.stationNames.get(queryStrs[1]);//到达站点代码
					String queryUrl = queryUrlTemp.replace("${train_date}", TrainDataUtils.query_train_date)
										.replace("${from_station_telecode}", from_station_telecode)
										.replace("${to_station_telecode}", to_station_telecode);
					
					String seatValue = TrainDataUtils.seatNameValueMap.get(queryStrs[3]);//座位在官网提交数据中的值
					int seatIndex = TrainDataUtils.seatNameIndexMap.get(queryStrs[3]);//座位在官网搜索数据中所在的序号
					next : while(true){
						try{
							String queryBody = "";
							 while(true){
								queryBody = HttpUtils.doGetBody(queryUrl,cookie);
								if(queryBody.indexOf("系统维护中") != -1){
									System.out.println("--系统维护中, 一分钟再重新搜索--");
									Thread.sleep(60000);
									continue;
								}
								
								if(queryBody.indexOf("登录名：") != -1){
									login();
									continue;
								}
								if(queryBody.indexOf("getSelected") == -1){
									continue next;
								}
								break;
							}
							
							for(String train :StringUtils.split(queryBody, "\\n")){
								String[] trainInfos = train.split(",");
								if(trainInfos[1].indexOf(queryStrs[2].toUpperCase()) == -1){
									continue;
								}
								if(trainInfos[seatIndex].indexOf("--") == -1  && trainInfos[seatIndex].indexOf("无") == -1){
									String[] matchs = JavaUtils.match(trainInfos[16], "getSelected\\('(.*?)'\\)");
									String[] orderInfo = matchs[1].split("#");
									
									Map<String,String> orderParam = new HashMap<String, String>();
									orderParam.putAll(TrainDataUtils.queryOrderParams);
									
									//设置座位信息
									orderParam.put("passengerTickets", seatValue+",1,"+TrainDataUtils.name+",1,"+TrainDataUtils.cardid+","+TrainDataUtils.mobile+",Y");
									orderParam.put("passenger_1_seat", seatValue);
									
									for(int i = 0; i < TrainDataUtils.queryOrderParamIndexs.length; i++){
										orderParam.put(TrainDataUtils.queryOrderParamNames[i], orderInfo[TrainDataUtils.queryOrderParamIndexs[i]]);
									}
									String postOrder = JavaUtils.toPostParam(orderParam);
									System.out.println("--搜索到目标："+queryStrs[0]+"到"+queryStrs[1]+(queryStrs.length>2?queryStrs[2]:""));
									int count = 100;
									while(count>0){
										System.err.println("--第"+(101-count)+"次发送预订,目标:"+queryStrs[0]+"到"+queryStrs[1]+(queryStrs.length>2?queryStrs[2]:""));
										String postOrderBody = HttpUtils.doPostBody("https://dynamic.12306.cn/otsweb/order/querySingleAction.do?method=submutOrderRequest", postOrder, null, "UTF-8", true);
										if(postOrderBody.indexOf("var train_date_str_ = ") != -1){
											Document httpDoc = JavaUtils.getDocument(postOrderBody);
											String lastThreadsXpath = "//FORM[@method='post']//INPUT";
											NodeList threads = XPathAPI.selectNodeList(httpDoc,lastThreadsXpath);
											
											Map<String,String> postOrderParam = new HashMap<String, String>();
											for(int i = 0; i < threads.getLength(); i++){
												Node node = threads.item(i);
												String name = JavaUtils.getNodeValue(node, "name");
												if(name != null && name.length() > 0){
													String value = JavaUtils.getNodeValue(node, "value");
													if(value == null)
														value = "";
													postOrderParam.put(name, value);
												}
											}
											postOrderParam.putAll(TrainDataUtils.submitOrderParams);
											
											String train_date_str_ = JavaUtils.match(postOrderBody, "var train_date_str_ = \"(.*?)\"")[1];
											String station_train_code_ = JavaUtils.match(postOrderBody, "var station_train_code_ = \"(.*?)\"")[1];
											String start_time_str_ = JavaUtils.match(postOrderBody, "var start_time_str_ = \"(.*?)\"")[1];
											String arrive_time_str_ = JavaUtils.match(postOrderBody, "var arrive_time_str_ = \"(.*?)\"")[1];
			//								String lishi_ = JavaUtils.match(postOrderBody, "var lishi_ = \"(.*?)\"")[1];
											
											postOrderParam.put("orderRequest.train_date",train_date_str_);
											postOrderParam.put("orderRequest.start_time",start_time_str_);
											postOrderParam.put("orderRequest.end_time",arrive_time_str_);
											postOrderParam.put("orderRequest.station_train_code",station_train_code_);
											postOrderParam.put("randCode", submitCode);
											String param = JavaUtils.toPostParam(postOrderParam);
											String body = HttpUtils.doPostBody("https://dynamic.12306.cn/otsweb/order/confirmPassengerAction.do?method=confirmPassengerInfoSingle", param, null, "UTF-8", true);
//											System.out.println(body);
											if(body.indexOf("45分钟") != -1){
												Date now = new Date();
												MailUtils.sendMail(TrainDataUtils.query_train_date+"_订票成功",
														TrainDataUtils.query_train_date +" "+
														queryStrs[0]+"到"+queryStrs[1]+(queryStrs.length>2?queryStrs[2]:"")+" --订票时间："
														+DateUtils.getDateTime("HH:mm:ss", now)
												);
												TrainDataUtils.queryList.remove(queryStrs);
												break next;
											}
											if(body.indexOf("没有足够的票") != -1){
												System.err.println("--没有足够的票,重新搜索--!");;
												count=0;
											}
											if(body.indexOf("验证码不正确") != -1){
												waitSetSubmitCode();
											}
											if(body.indexOf("系统忙！") != -1){
												System.err.println("--请求失败:系统忙!");;
											}
											if(body.indexOf("当前提交订单用户过多！") != -1){
												System.err.println("--当前提交订单用户过多!");;
											}
											count--;
										}
									};
								}
							}
						}catch(Exception e){
						}
					}
				}
			};
			Date now = new Date();
			System.out.println(DateUtils.getDateTime("HH:mm:ss", now)+"--开始搜索："+ queryStrs[0]+"到"+queryStrs[1]);
			thred.start();
		}
	}

	private static boolean firstPostOrderCode = true;//第一次输入验证码时，不需要发邮件
	private static String submitCode = "reload";
	private static Object submitCodeLock = new Object[0];
	private static int submitCodeSerialNum = 0;
	private static ThreadLocal<Integer> waitSubmitCodeSerialNum = new ThreadLocal<Integer> () {
        protected synchronized Integer initialValue() {
            return 0;
        }
    };
	/**
	 * 等待请求输入提交订单的验证码，只需要有一个线程来请求提交订单的验证码就可以
	 * @throws Exception
	 */
	private static void waitSetSubmitCode() throws Exception {
		synchronized(submitCodeLock){
			if(submitCodeSerialNum == waitSubmitCodeSerialNum.get()){
				submitCodeSerialNum++;
				waitSubmitCodeSerialNum.set(submitCodeSerialNum);
			}else{
				waitSubmitCodeSerialNum.set(submitCodeSerialNum);
				return;
			}
			if(!firstPostOrderCode)
				MailUtils.sendMail("重新输入订单验证码","请输入订单验证码");
			firstPostOrderCode = false;
			submitCode = "reload";
			while(submitCode.equals("reload")){
				File file = HttpUtils.doGetFile("https://dynamic.12306.cn/otsweb/passCodeAction.do?rand=randp", cookie);
				File codeFile = new File(TrainDataUtils.saveSumbitCodePath);
				if(!codeFile.exists())
					codeFile.createNewFile();
				FileUtils.copyFile(file, codeFile);
				submitCode = readString("请输入订单验证码");
				System.out.println("--验证码："+submitCode+"--");
			}
		}
	}
	
	private static Object loginLock = new Object[0];
	private static boolean firstLogin = true;//第一次登录输入验证码时，不需要发邮件
	private static int loginCodeSerialNum = 0;
	private static ThreadLocal<Integer> waitLoginCodeSerialNum = new ThreadLocal<Integer> () {
        protected synchronized Integer initialValue() {
            return 0;
        }
    };
    /**
     * 登录系统，在多线程中，只需要有一个线程来请求登录就可以
     * @throws Exception
     */
	private static void login() throws Exception{
		synchronized(loginLock){
			if(loginCodeSerialNum == waitLoginCodeSerialNum.get()){
				loginCodeSerialNum++;
				waitLoginCodeSerialNum.set(loginCodeSerialNum);
			}else{
				waitLoginCodeSerialNum.set(loginCodeSerialNum);
				return;
			}
			System.out.println("--正在登录--");
			String params = "nameErrorFocus=&passwordErrorFocus=&randErrorFocus=";
			params += "&loginUser.user_name="+TrainDataUtils.username;
			params += "&user.password="+TrainDataUtils.password;
			
			String postParams = "";
			
			boolean hasSendMail = false;
			String body = "请输入正确的验证码";
			do{
				if(body.indexOf("请输入正确的验证码") != -1){
					File file = HttpUtils.doGetFile("https://dynamic.12306.cn/otsweb/passCodeAction.do?rand=lrand", null);
					File codeFile = new File(TrainDataUtils.saveLoginCodePath);
					if(!codeFile.exists())
						codeFile.createNewFile();
					FileUtils.copyFile(file, codeFile);
					if(!firstLogin && !hasSendMail){
						MailUtils.sendMail("重新输入登录验证码","请输入登录验证码");
						hasSendMail = true;
					}
				    String code = readString("请输入登录验证码");
					postParams = params+"&randCode="+code;
					firstLogin = false;
				}
				System.out.println("--发送登录请求--");
				body = HttpUtils.doPostBody("https://dynamic.12306.cn/otsweb/loginAction.do?method=login", postParams, null, null, false);
				if(StringUtils.isEmpty(body)){
					System.err.println("官方服务没有响应.");
					continue;
				}
				if(body.indexOf("当前访问用户过多，请稍后重试") != -1){
					System.err.println("登录失败：当前访问用户过多！");
				}
			}while(body.indexOf("请输入正确的验证码") != -1 || body.indexOf("当前访问用户过多，请稍后重试") != -1 || body.indexOf("您最后一次登录时间为") == -1);;
			body = HttpUtils.doGetBody("https://dynamic.12306.cn/otsweb/order/querySingleAction.do?method=init", null);
			System.out.println("--登录成功--");
		}
	}
	
	/**
	 * 多控制台读取验证码
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	private static String readString(String msg) throws Exception{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try{
			System.out.print(msg+": ");
			return bufferedReader.readLine();
		}catch(Exception e){
		}
		return "1245";
	}
}

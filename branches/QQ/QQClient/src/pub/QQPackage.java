package pub;

import java.io.Serializable;

/**
 * 封装数据的包，它现了java.io.Serializable接口
 * @author Devon
 *
 */
public class QQPackage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 消息发送者
	 */
	private String from;
	
	/**
	 * 消息接收者
	 */
	private String to;
	
	/**
	 * 包类型
	 */
	private PackType packType;
	
	/**
	 * 数据包
	 */
	private Object data;
	
	/**
	 * 获得消息发送者
	 * @return		消息发送者
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * 设置消息发送者
	 * @return		消息发送者
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * 获得消息接收者
	 * @return		消息接收者
	 */
	public String getTo() {
		return to;
	}
	
	/**
	 * 设置消息接收者
	 * @param 		消息接收者
	 */
	public void setTo(String to) {
		this.to = to;
	}
	
	/**
	 * 获得数据包
	 * @return		数据包
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * 设置数据包
	 * @param		 数据包
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * 获得包类型
	 * @return		包类型
	 */
	public PackType getPackType() {
		return packType;
	}
	
	/**
	 * 设置包类型
	 * @param 		包类型
	 */
	public void setPackType(PackType packType) {
		this.packType = packType;
	}
}

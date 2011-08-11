package com.terry.weddingphoto.constants;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-3-16 下午05:45:07
 */
public class Constants {
	public static final String IP_COUNT_CACHE = "ip-count-cache";
	public static final String PHOTOS_COUNT_CACHE = "photos-count-cache";
	public static final String UPLOAD_SESSION_CACHE = "upload-session-cache";
	
	public static final int PHOTO_BYTES_LIMIT = 1024 * 1024;
	public static final int IP_CACHE_SESSION_TIME = 3600;// 1个小时
	public static final int UPLOAD_SESSION_TIME = 3600 * 12;// 12Hours
	public static final int COMMENT_LIMIT = 5;
	
	public static byte[] PHOTO_DELETED_DATA = null;
	
	public static String[] COMMENT_NOTIFICATION_EMAILS = null;
}

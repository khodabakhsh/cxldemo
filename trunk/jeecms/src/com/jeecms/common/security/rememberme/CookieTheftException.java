package com.jeecms.common.security.rememberme;

/**
 * @author Luke Taylor
 * @version $Id: CookieTheftException.java,v 1.1 2010/03/23 06:30:36 liufang Exp $
 */
@SuppressWarnings("serial")
public class CookieTheftException extends RememberMeAuthenticationException {
	public CookieTheftException(String message) {
		super(message);
	}
}

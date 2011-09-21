/*
 * @(#)$Id: script_cookie.js,v 1.3 2011/01/12 14:01:27 cvsroot Exp $
 * 
 * [JavaCenter Home] Copyright 2007-2011 JspRun! Inc. All rights reserved.
 * This is NOT a freeware, Use is subject to license terms.
 */

Cookie = {	

	/** Get a cookie's value
	 *
	 *  @param integer	key		The token used to create the cookie
	 *  @return void
	 */
	get: function(key) {
		// Still not sure that "[_a-zA-Z0-9.()=|%/]+($|;)" match *all* allowed characters in cookies
		tmp =  document.cookie.match((new RegExp(key +'=[_a-zA-Z0-9.()=|%/]+($|;)','g')));
		if(!tmp || !tmp[0]) return null;
		else return unescape(tmp[0].substring(key.length+1,tmp[0].length).replace(';','')) || null;
		
	},	
	
	/** Set a cookie
	 *
	 *  @param integer	key		The token that will be used to retrieve the cookie
	 *  @param string	value	The string to be stored
	 *  @param integer	ttl		Time To Live (hours)
	 *  @param string	path	Path in which the cookie is effective, default is "/" (optional)
	 *  @param string	domain	Domain where the cookie is effective, default is '' (optional)
	 *  @param boolean 	secure	Use SSL or not, default false (optional)
	 * 
	 *  @return setted cookie
	 */
	set: function(key, value, ttl, path, domain, secure) {
		cookie = [key+'='+    escape(value),
		 		  'path='+    (path  ? path : '/')];
		if (ttl)         cookie.push('expires='+Cookie.hoursToExpireDate(ttl));
		if (domain)      cookie.push('domain='+domain);
		if (secure)      cookie.push('secure');
		document.cookie = cookie.join('; ');
	},
	
	/** Unset a cookie
	 *
	 *  @param integer	key		The token that will be used to retrieve the cookie
	 *  @param string	path	Path used to create the cookie (optional)
	 *  @param string	domain	Domain used to create the cookie, default is null (optional)
	 *  @return void
	 */
	unset: function(key, path, domain) {
		path   = (!path   || typeof path   != 'string') ? '' : path;
        domain = (!domain || typeof domain != 'string') ? '' : domain;
		if (Cookie.get(key)) Cookie.set(key, '', 'Thu, 01-Jan-70 00:00:01 GMT', path, domain);
	},

	/** Return GTM date string of "now" + time to live
	 *
	 *  @param integer	ttl		Time To Live (hours)
	 *  @return string
	 */
	hoursToExpireDate: function(ttl) {
		if (parseInt(ttl) == 'NaN' ) return '';
		else {
			now = new Date();
			now.setTime(now.getTime() + (parseInt(ttl) * 60 * 60 * 1000));
			return now.toGMTString();			
		}
	},

	/** Return true if cookie functionnalities are available
	 *
	 *  @return boolean
	 */
	test: function() {
		Cookie.set('b49f729efde9b2578ea9f00563d06e57', 'true');
		if (Cookie.get('b49f729efde9b2578ea9f00563d06e57') == 'true') {
			Cookie.unset('b49f729efde9b2578ea9f00563d06e57');
			return true;
		}
		return false;
	},
	
	/** If Firebug JavaScript console is present, it will dump cookie string to console.
	 * 
	 *  @return void
	 */
	dump: function() {
		if (typeof console != 'undefined') {
			console.log(document.cookie.split(';'));
		}
	}
}
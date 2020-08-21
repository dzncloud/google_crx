/**
 * 
 */
package com.haici.googlecrx.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author John
 *
 */
public class CookieInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String cookie;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
	public Map<String, String> getCookieMap(){
		Map<String, String> cookies = new HashMap<String,String>();
		if(cookie== null || cookie.equals("")) {
			return null;
		}
		String[] cs =cookie.split(";");
		for(String c : cs) {
			String [] tc = c.split("=");
			cookies.put(tc[0], tc[1]);
		}
		return cookies;
	}
	
}

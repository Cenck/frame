/**
 * 
 */
package com.cengel.starbucks.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author galaxy
 *
 *         2017年3月14日上午11:38:50
 */
public class IpUtils {

	public final static String	X_FORWARDED_FOR		= "x-forwarded-for";
	public final static String	PROXY_CLIENT_IP		= "x-forwarded-for";
	public final static String	WL_PROXY_CLIENT_IP	= "WL-Proxy-Client-IP";
	public final static String	UNKNOWN				= "unknown";

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(X_FORWARDED_FOR);
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(PROXY_CLIENT_IP);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(WL_PROXY_CLIENT_IP);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		String[] ipArr = ip.split(",");
		if (ipArr.length == 0) {
			return ip;
		} else {
			return ipArr[0];
		}
	}
}

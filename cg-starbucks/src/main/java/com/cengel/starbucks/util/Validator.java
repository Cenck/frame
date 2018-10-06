package com.cengel.starbucks.util;

import java.util.regex.Pattern;

/**
 * @Name Validator
 * @Description :
 * Created by Chen on 2017/4/17.
 */
public class Validator {
	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	//纯字母
	public static final String REGEX_LETTER = "^[a-zA-Z]+$";

	/**
	 * 正则表达式：日期2017-09-01
	 */
	public static final String REGEX_DATELINE = "^\\d{4}-\\d{2}-\\d{2}$";
	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE   = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";

	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	public static final String REGEX_URI = "^/[a-zA-Z]{1}[a-zA-Z0-9/]+$";

	/**
	 * 正则表达式：验证IP地址
	 */
	//    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	public static final String REGEX_IP_ADDR = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

	/**
	 * 校验用户名
	 *
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername(String username) {
		return testMatches(REGEX_USERNAME, username);
	}

	/**
	 * 校验密码
	 *
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return testMatches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验手机号
	 *
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return testMatches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 *
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return testMatches(REGEX_EMAIL, email);
	}

	/**
	 * 校验汉字
	 *
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese(String chinese) {
		return testMatches(REGEX_CHINESE, chinese);
	}

	/**
	 * 校验身份证
	 *
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		return testMatches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 校验URL
	 *
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {
		return testMatches(REGEX_URL, url);
	}

	public static boolean isUri(String uri) {
		return testMatches(REGEX_URI, uri);
	}

	/**
	 * 校验IP地址
	 *
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String ipAddr) {
		return testMatches(REGEX_IP_ADDR, ipAddr);
	}

	/**
	 * 事先测试string是否为空等
	 *
	 * @return
	 */
	private static boolean testMatches(String reg, String str) {
		if (isBlank(str)) return false;
		return Pattern.matches(reg, str);
	}

	public static boolean isLetter(String string) {
		return testMatches(REGEX_LETTER, string);
	}

	/**
	 * 判断字符是否为空
	 *
	 * @param string
	 * @return
	 */
	public static boolean isBlank(String string) {
		if (string == null || string.length() == 0 || string.trim().length() == 0) {
			return true;
		}
		return false;
	}

	//是否所有都空
	public static boolean isAllBlank(String... string) {
		for (String s : string) {
			if (isNotBlank(s)) { //只要有一个非空就不成立
				return false;
			}
		}
		return true;
	}

	//TODO 是否所有都非空
	public static boolean isAllNotBlank(String... string) {
		for (String s : string) {
			if (isBlank(s)) { //只要有一个为空，就不成立
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String string) {
		return !isBlank(string);
	}

	/**
	 * 判断是否为数字
	 */
	public static boolean isNumber(String str) {
		if (isBlank(str)) return false;
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isLong(String str) {
		if (isBlank(str)) return false;
		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否为i位数字
	 */
	public static boolean isNumber(String str, int i) {
		if (isBlank(str)) return false;
		if (str.length() != i) return false;
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否为日期
	 */
	public static boolean isDateLine(String dateLine) {
		if (Validator.isBlank(dateLine)) return false;
		return testMatches(REGEX_DATELINE, dateLine);
	}

	/**
	 * 路径包含不安全字符
	 *
	 * @param path
	 * @return
	 */
	public static boolean isUseablePath(String path) {
		if (!isBlank(path) && !path.contains("../")) {
			return true;
		}
		return false;
	}

	public static boolean isPk(String pk) {
		if (isNotBlank(pk) && testMatches("^[A-Z0-9]{20}$", pk)) {
			return true;
		}
		return false;
	}

	public static String getStrNoNull(String str) {
		if (isNotBlank(str)) {
			return "";
		}
		return str;
	}

}

package com.cengel.starbucks.model.user;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/21 - 19:37
 * @Version V1.0
 **/
public class UserHolder {
	private static ThreadLocal<OnlineUser> holderContext = new ThreadLocal<>();

	public static void setUser(OnlineUser user) {
		holderContext.set(user);
	}

	public static OnlineUser getUser() {
		return holderContext.get();
	}

	/********************|| fast  ||********************/
	public static Integer getId() {
		return getUser().getId();
	}

	public static String getAccount(){
		return getUser().getAccount();
	}
	public static boolean isOnline() {
		return getUser() != null && getUser().getId() != null && getUser().getId() > 0;
	}

	public static void clear(){
		holderContext.remove();
	}
}

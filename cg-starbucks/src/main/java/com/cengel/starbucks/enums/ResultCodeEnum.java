package com.cengel.starbucks.enums;

/**
 * API返回码枚举
 * 
 * @author HLH
 *
 */
public enum ResultCodeEnum {

	/********************|| 用户 ||********************/
	/** 账户锁定 */
	LOCKED("LOCKED", 8000, "您的账号已经被锁定"),
	USR_NO_LOGIN("USR_NO_LOGIN", 502, "用户未登录"),
	/** 用户未激活 */
	USR_NOT_ACTIVE("USR_NOT_ACTIVE", 1012, "用户未激活"),
	/** 用户已注销 */
	USR_CLOSED("USR_CLOSED", 1013, "用户已注销"),
	/** 密码不正确 */
	USE_PWD_ERROR("USE_PWD_ERROR", 1014, "密码不正确"),
	/** 用户不存在 */
	USER_NOT_EXISTED("USER_NOT_EXISTED", 1016, "用户不存在"),
	/** 用户登录失效，请重登录 */
	USER_LOGIN_EXPIERD("USER_LOGIN_EXPIERD", 1017, "用户登录失效，请重登录"),
	/** 签名错误 */
	SIGN_CHECK_ERROR("SIGN_CHECK_ERROR", 1018, "签名错误"),
	/** 签名不能为空 */
	SIGN_NOT_NULL("SIGN_NOT_NULL", 1019, "签名不能为空"),
	/** 认证失败 */
	AUTHORIZE_FAILURE("AUTHORIZE_FAILURE", 1007, "用户认证失败"),



	/********************|| 消息 ||********************/
	CODE_FAIL("CODE_FAIL", 500, "验证码错误"),
	MESSAGE_FAILURE("MESSAGE_FAILURE", 301, "消息错误"),

	/********************|| 数据异常 ||********************/
	/** 数据不存在 */
	NOT_EXISTED("NOT_EXISTED", 1011, "访问资源不存在"),
	// 共享资源被占用 (乐观锁,悲观锁)
	SYS_BUSY("SYS_BUSY", 1015, "[系统繁忙] 共享资源被占用"),
	/** 系统错误 */
	SYSTEM_FAILURE("SYSTEM_FAILURE", 1001, "系统错误，稍后再试"),


	/********************|| 通用 ||********************/
	/** 参数为空 */
	NULL_ARGUMENT("NULL_ARGUMENT", 1002, "参数为空"),
	/** 新增的参数已经存在(唯一性约束) */
	DUPLICATE_KEY("DUPLICATE_KEY", 1003, "新增的参数已经存在(唯一性约束)"),
	/** 参数不正确 */
	ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", 1004, "参数不正确"),
	/** 逻辑错误 */
	LOGIC_ERROR("LOGIC_ERROR", 1005, "逻辑错误"),
	/** 外部接口调用失败 */
	DEPEND_FAILURE("DEPEND_FAILURE", 1006, "依赖外部接口失败"),
	/** 连接超时 */
	SESSION_TIMEOUT("SESSION_TIMEOUT", 1008, "会话连接超时"),
	/** 校验失败 */
	VALIDATE_FAILURE("VALIDATE_FAILURE", 1009, "校验失败"),
	/** 重复提交错误 */
	RESUBMIT_ERROR("RESUBMIT_ERROR", 1010, "重复提交错误"),





	/** 请求超时 */
	REQUEST_TIME_OUT("REQUEST_TIME_OUT", 1020, "请求超时"), ;

	/**
	 * 枚举值
	 */
	private final String	code;

	/**
	 * 数值型错误码
	 */
	private final int		codeNumber;

	/**
	 * 枚举信息
	 */
	private final String	description;

	/**
	 * 构造函数
	 *
	 * @param code
	 *            枚举值
	 * @param codeNumber
	 *            数值型错误码
	 * @param description
	 *            枚举信息
	 */
	ResultCodeEnum(String code, int codeNumber, String description) {
		this.code = code;
		this.codeNumber = codeNumber;
		this.description = description;
	}

	/**
	 * 根据code获取枚举
	 *
	 * @param code
	 * @return
	 */
	public static ResultCodeEnum getByCode(String code) {
		for (ResultCodeEnum item : values()) {
			if (code.equals(item.getCode())) {
				return item;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public int getCodeNumber() {
		return codeNumber;
	}

	@Override
	public String toString() {
		return code;
	}
}

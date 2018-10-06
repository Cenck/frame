package com.cengel.starbucks.exception;

/**
 * 业务异常对象。
 * 
 * @author liwei
 */
public class BusinessException extends BaseException {

	private static final long	serialVersionUID	= 5151669876695986575L;

	private Integer				code				= 200;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}

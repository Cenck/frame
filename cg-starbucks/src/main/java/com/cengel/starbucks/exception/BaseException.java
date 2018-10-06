package com.cengel.starbucks.exception;

/**
 * 业务异常对象。
 *
 * @author liwei
 */
public abstract class BaseException extends RuntimeException {

	private static final long	serialVersionUID	= 5151669876695986575L;

	private Integer				code				= 200;

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public BaseException(Integer code, String message, Throwable cause) {
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

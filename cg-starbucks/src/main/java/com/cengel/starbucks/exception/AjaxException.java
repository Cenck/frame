package com.cengel.starbucks.exception;

/**
 * 业务异常对象。
 * 
 * @author liwei
 */
public class AjaxException extends BaseException {

	private static final long	serialVersionUID	= 5151669876695986575L;

	private Integer				code				= 200;

	public AjaxException() {
		super();
	}

	public AjaxException(String message) {
		super(message);
	}

	public AjaxException(String message, Throwable cause) {
		super(message, cause);
	}

	public AjaxException(Throwable cause) {
		super(cause);
	}

	public AjaxException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public AjaxException(Integer code, String message, Throwable cause) {
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

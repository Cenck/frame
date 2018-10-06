package com.cengel.starbucks.model.obj;

import com.cengel.starbucks.enums.ResultCodeEnum;
import com.cengel.starbucks.mapper.JsonMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Response implements Serializable {

	private final static String  KEY_DATA     = "data";
	private final static Integer SUCCESS_CODE = 200;

	private Boolean             success;
	private Integer             code;
	private String              message;
	private Map<String, Object> data = new HashMap<String, Object>();

	public Response add(String key, Object value) {
		this.data.put(key, value);
		return this;
	}

	public Response addData(Object value) {
		this.data.put(KEY_DATA, value);
		return this;
	}
	public <T> T getObj() {
		return (T) this.data.get(KEY_DATA);
	}

	public Response putAll(Map<String, Object> attrs) {
		this.data.putAll(attrs);
		return this;
	}

	public <T> T get(String key) {
		return (T) this.data.get(key);
	}

	/**
	 * 创建Response对象。
	 *
	 * @return
	 */
	public static Response success() {
		return new Response(true);
	}

	/**
	 * 创建Response对象。
	 *
	 * @param message
	 * @return
	 */
	public static Response success(String message) {
		return new Response(true, message);
	}

	/**
	 * 创建Response对象。
	 *
	 * @return
	 */
	public static Response error() {
		return new Response(false);
	}

	/**
	 * 创建Response对象。
	 *
	 * @param message
	 * @return
	 */
	public static Response error(String message) {
		return new Response(false, message);
	}

	public static Response code(ResultCodeEnum codeEnum) {
		return new Response(false, codeEnum.getCodeNumber(), codeEnum.getDescription());
	}

	/**
	 * 创建Response对象。
	 *
	 * @param message
	 * @return
	 */
	public static Response error(Integer code, String message) {
		return new Response(false, code, message);
	}

	private Response(boolean success) {
		this.setSuccess(success);
	}

	private Response(boolean success, String message) {
		this.setSuccess(success);
		this.setCode(SUCCESS_CODE);
		this.setMessage(message);
	}

	private Response(boolean success, Integer code, String message) {
		this.setSuccess(success);
		if (code != null && code != 0) this.setCode(code);
		this.setMessage(message);
	}

	@Override
	public String toString() {
		return JsonMapper.getMapper()
				.toJson(this);
	}
}

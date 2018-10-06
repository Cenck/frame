package com.cengel.starbucks.model.entity;


import com.cengel.starbucks.util.DateUtil;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class BaseOrderEntity<E extends Serializable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1004811470690311256L;
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	protected static final String TIME_FORMAT = "HH:mm:ss";
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	private E id;
	private Timestamp modifiedTime;
	private Timestamp createTime;
	private boolean deleted;
	private Timestamp now;

	public static String date2String(Date date, String dateFormat) {
		return DateUtil.format(date, dateFormat);
	}

	public static <T extends Date> T string2Date(String dateString, String dateFormat, Class<T> targetResultType) {
		return DateUtil.parse(dateString, dateFormat, targetResultType);
	}

	public BaseOrderEntity() {
		this.modifiedTime = new Timestamp(new Date().getTime());
		this.createTime = new Timestamp(new Date().getTime());
	}

	public E getId() {
		return this.id;
	}

	public void setId(E id) {
		this.id = id;
	}

	public Timestamp getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Timestamp timestamp) {
		this.modifiedTime = timestamp;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp timestamp) {
		this.createTime = timestamp;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Timestamp getNow() {
		return this.now;
	}

	public void setNow(Timestamp now) {
		this.now = now;
	}
}

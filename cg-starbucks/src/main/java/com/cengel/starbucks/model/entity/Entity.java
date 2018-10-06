package com.cengel.starbucks.model.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 实体类的基类，定义共用属性。
 */
public abstract class Entity<PK extends java.io.Serializable> implements java.io.Serializable {

	private static final long serialVersionUID = 2178081897141735436L;

	public static final String PROP_ID = "id";

	// 数据标识
	@Id //@Column的作用范围是根据@Id的作用范围来的 (字段或get方法)
	@Column(name = "ID")
	private PK id;

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

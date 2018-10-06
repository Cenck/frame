package com.cengel.starbucks.model.obj;

import com.cengel.starbucks.model.entity.BaseEntity;
import com.cengel.starbucks.model.entity.Entity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 数据传输实体基类。
 */
public abstract class BaseDto extends Entity<Integer> implements java.io.Serializable {

	private static final long serialVersionUID = -4734655069106045342L;

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

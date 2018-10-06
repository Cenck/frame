package com.cengel.starbucks.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类的基类，定义共用属性。
 */

public interface BEntity<PK> extends DeletedEntity, Serializable {

	String PROP_DELETED ="deleted";


	PK getId();
	void setId(PK id);

	String getCreateBy();

	void setCreateBy(String createBy);

	Date getCreateTime();

	void setCreateTime(Date createTime);

	String getModifiedBy();

	void setModifiedBy(String modifiedBy);

	Date getModifiedTime();

	void setModifiedTime(Date modifiedTime);


}
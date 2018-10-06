package com.cengel.starbucks.model.entity;

import java.util.Date;

public interface OperationEntity {

	public String getCreateBy();

	public void setCreateBy(String createBy);

	public String getModifiedBy();

	public void setModifiedBy(String modifiedBy);

	public Date getCreateTime();

	public void setCreateTime(Date createTime);

	public Date getModifiedTime();

	public void setModifiedTime(Date modifiedTime);

	public String getCreateAccId();

	public void setCreateAccId(String createAccId);

	public String getDepId();

	public void setDepId(String depId);

	public String getCorpGroupId();

	public void setCorpGroupId(String CorpGroupId);

}

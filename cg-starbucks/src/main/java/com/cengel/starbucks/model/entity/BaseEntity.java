package com.cengel.starbucks.model.entity;

import com.cengel.starbucks.db.helper.EntityHelper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OrderBy;
import java.util.Date;

/**
 * 实体类的基类，定义共用属性。
 */
//@JsonIgnoreProperties({ BaseEntity.PROP_CREATE_TIME, BaseEntity.PROP_MODIFIED_TIME })
@Getter
@Setter
public abstract class BaseEntity<PK extends java.io.Serializable> extends Entity<PK> implements DeletedEntity,BEntity<PK> {

	private static final  long  serialVersionUID = 1L;

	// ~~~~属性名称静态常量
	public static final String PROP_DELETED       = "deleted";
	public static final String PROP_CREATE_TIME   = "createTime";
	public static final String PROP_MODIFIED_TIME = "modifiedTime";
	public static final String PROP_CREATE_BY     = "createBy";

	// ~~~~实体属性
	// 删除标识
	@Column(name = "DELETED")
	private Boolean deleted;
	// 创建者
	@Column(name = "CREATE_BY")
	private String  createBy;
	// 创建时间
	@OrderBy("DESC")
	@Column(name = "CREATE_TIME")
	private Date    createTime;
	// 修改者
	@Column(name = "MODIFIED_BY")
	private String  modifiedBy;
	// 修改时间
	@Column(name = "MODIFIED_TIME")
	private Date    modifiedTime;

	public String getDeclaredTableName() {
		return EntityHelper.getDeclaredTableName(this.getClass());
	}
}
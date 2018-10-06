package com.cengel.starbucks.model.obj;

import com.cengel.starbucks.exception.BusinessException;
import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.model.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Map参数构造辅助类。
 */
public abstract class Params {

	public static final String			SYSDATE_KEY	= "sysdate";

	private final Map<String, Object>	data		= new HashMap<String, Object>();

	/**
	 * 增加属性。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Params add(String key, Object value) {
		if (this.canAdd(value)) {
			this.data.put(key, value);
		}
		return this;
	}

	/**
	 * 增加entity或dto,专门用于分页查询。
	 * 
	 * @param value
	 * @return
	 */
	@Deprecated
	public Params add(Object value) {
		if (value instanceof BaseEntity || value instanceof BaseDto){
			if (this.canAdd(value)) {
				this.data.put("entity", value);
			}
		}else {
			throw new BusinessException("只允许添加entity或dto");
		}

		return this;
	}

	/******************** =====zhz====== ************************/
	/**
	 * 增加entity或dto,专门用于分页查询。
	 * 2.entity和dto只能存在一个
	 * 注意：使用基于反射的page方法时
	 * 	a.只有带@Colnum注解的字段才能被基于反射的page方法识别(自写的mapper.xml则无所谓)
	 * 	b.entity必须有@Table方法注解table名，dto的父类中必须有且仅有一个类有@table注解
	 * @param value
	 * @return
	 */
	public Params addEntity(BEntity value) {
		this.data.put("entity", value);
		return this;
	}
	public <T extends BEntity> T getEntity(){
		return (T) this.data.get("entity");
	}

	/********************|| 处理起止日期 ||********************/
	/**
	 * 表头查询区 分页查询 开始时间~结束时间
	 * @param begDate
	 * @return
	 */
	public Params addBegDate(Date begDate) {
		this.data.put("_begDate", begDate);
		return this;
	}
	public Date getBegDate(){
		return (Date) this.data.get("_begDate");
	}
	public Params addEndDate(Date endDate) {
		this.data.put("_endDate", endDate);
		return this;
	}
	public Date getEndDate(){
		return (Date) this.data.get("_endDate");
	}
	/** 查询区 时间类型 */
	public Params addTimeType(String  timeType) {
		this.data.put("_timeType", timeType);
		return this;
	}
	public String  getTimeType(){
		return (String) this.data.get("_timeType");
	}
	/******************** =====zhz====== ************************/

	/**
	 * 将当前时间增加到参数列表中。
	 * @return
	 */
	public Params addSysdate() {
		this.data.put(SYSDATE_KEY, new Date());
		return this;
	}

	/**
	 * 增加模糊匹配字段，匹配方式为：左右匹配
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Params addLike(String key, String value) {
		return this.addLike(key, value, true, true);
	}

	/**
	 * 增加模糊匹配字段，匹配方式为：左匹配
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Params addLLike(String key, String value) {
		return this.addLike(key, value, true, false);
	}

	/**
	 * 增加模糊匹配字段，匹配方式为：右匹配
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Params addRLike(String key, String value) {
		return this.addLike(key, value, false, true);
	}

	/**
	 * 增加多个属性。
	 * 
	 * @param params
	 * @return
	 */
	public Params add(Map<String, Object> params) {
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (this.canAdd(entry.getValue())) {
					this.data.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return this;
	}

	public Params addDeleted(Integer deleted) {
		return this.add(BaseEntity.PROP_DELETED, deleted);
	}


	public Params addCreateBy(String createBy) {
		if (!this.data.containsKey(BaseEntity.PROP_CREATE_BY)) {
			this.add(BaseEntity.PROP_CREATE_BY, createBy);
		}
		return this;
	}


	public String toString() {
		return this.data.toString();
	}

	private Params addLike(String key, String value, boolean left, boolean right) {
		if (this.canAdd(value)) {
			if (left) {
				value = "%" + value;
			}
			if (right) {
				value = value + "%";
			}
			this.data.put(key, value);
		}
		return this;
	}

	private boolean canAdd(Object value) {
		if (value == null) {
			return false;
		}
		if (value instanceof String && StringUtils.isBlank((String) value)) {
			return false;
		}
		return true;
	}

	/**
	 * 返回参数对象
	 * 
	 * @return
	 */
	public Map<String, Object> toMap() {
		return this.data;
	}

	/**
	 * 创建一个新的对象。
	 * 
	 * @return
	 */
	public static Params create() {
		return new ParamsMap();
	}

	private static class ParamsMap extends Params implements Map<String, Object> {

		public int size() {
			return super.data.size();
		}

		public boolean isEmpty() {
			return super.data.isEmpty();
		}

		public boolean containsKey(Object key) {
			return super.data.containsKey(key);
		}

		public boolean containsValue(Object value) {
			return super.data.containsValue(value);
		}

		public Object get(Object key) {
			return super.data.get(key);
		}

		public Object put(String key, Object value) {
			return super.data.put(key, value);
		}

		public Object remove(Object key) {
			return super.data.remove(key);
		}

		public void putAll(Map<? extends String, ? extends Object> m) {
			super.data.putAll(m);
		}

		public void clear() {
			super.data.clear();
		}

		public Set<String> keySet() {
			return super.data.keySet();
		}

		public Collection<Object> values() {
			return super.data.values();
		}

		public Set<Entry<String, Object>> entrySet() {
			return super.data.entrySet();
		}
	}
}

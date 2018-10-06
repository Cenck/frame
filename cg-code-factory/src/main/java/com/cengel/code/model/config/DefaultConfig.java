package com.cengel.code.model.config;

import com.cengel.code.model.beans.DefaultConfigBean;
import com.cengel.starbucks.exception.BusinessException;
import com.cengel.starbucks.annotation.Description;
import com.cengel.starbucks.model.entity.BaseEntity;
import com.cengel.starbucks.util.Validator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhz
 * @version V1.0
 * @create 2018-03-07 17:06
 **/
public class DefaultConfig {

	private final static ThreadLocal<Map<String, Object>> holderContext = new ThreadLocal<Map<String, Object>>();

	public static DefaultConfigBean getBean() {
		DefaultConfigBean bean = getAttr("DEFAULT_CONFIG");
		if (bean == null) {
			setBean(bean = new DefaultConfigBean());
		}
		return bean;
	}

	public static void setBean(DefaultConfigBean bean) {
		setAttr("DEFAULT_CONFIG", bean);
	}

	/**
	 * 检查必要参数是否传递正确
	 */
	public static void check() {
		if (getEntityClass() == null) {
			setEntityClass(BaseEntity.class); //默认用这个
		}
		if (Validator.isBlank(getBasePackage()) || Validator.isBlank(getOutPath()) || Validator.isBlank(getStartType()) || Validator.isBlank(getTableName()) || Validator.isBlank(getWebPath())) {
			throw new BusinessException("参数传递不完整");
		}
	}

	/**
	 * 清除线程数据
	 */
	public static void clear() {
		Map<String, Object> map = holderContext.get();
		if (map != null) {
			map.clear();
		}
		holderContext.remove();
	}

	/**
	 * 根据KEY返回数据
	 *
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttr(String key) {
		Map<String, Object> map = holderContext.get();
		if (map != null) {
			return (T) map.get(key);
		}
		return null;
	}

	public static <T> void setAttr(String key, T value) {
		Map<String, Object> map = holderContext.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			holderContext.set(map);
		}
		map.put(key, value);
	}

	public static String getBasePackage() {
		return getBean().getBasePackage();
	}

	public static void setBasePackage(String basePackage) {
		getBean().setBasePackage(basePackage);
	}

	public static String getSchema() {
		return getBean().getSchema();
	}

	public static void setSchema(String schema) {
		getBean().setSchema(schema);

	}

	public static String getOutPath() {
		return getBean().getOutPath();
	}

	public static void setOutPath(String outPath) {
		getBean().setOutPath(outPath);
	}

	public static String getTableName() {
		return getBean().getTableName();
	}

	public static void setTableName(String tableName) {
		getBean().setTableName(tableName);
	}

	public static String getWebPath() {
		return getBean().getWebPath();
	}

	public static void setWebPath(String webPath) {
		getBean().setWebPath(webPath);
	}

	public static String getStartType() {
		return getBean().getStartType();
	}

	@Description("启动类型，匹配summer.codefactory.project.??")
	public static void setStartType(String startType) {
		getBean().setStartType(startType);
	}

	public static List<LinkedHashMap> getTemplates() {
		return getBean().getTemplates();
	}

	public static Class getEntityClass() {
		return getAttr("entityClass");
	}

	public static void setEntityClass(Class entityClass) {
		setAttr("entityClass", entityClass);
	}

}

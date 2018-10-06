package com.cengel.starbucks.db.helper;

import com.cengel.starbucks.util.Validator;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Title: entity工具
 * @Description:
 * @Author zhz
 * @Time 2018/8/2 - 10:59
 * @Version V1.0
 **/
public abstract class EntityHelper {

	public static String getDeclaredTableName(Class<?> entityClass) {
		String tableName = null;
		Table[] arr = entityClass.getDeclaredAnnotationsByType(Table.class);
		if (arr != null && arr.length > 0) tableName = arr[0].name();
		if (Validator.isBlank(tableName)) {
			Entity[] arr2 = entityClass.getDeclaredAnnotationsByType(Entity.class);
			if (arr2 != null && arr2.length > 0) tableName = arr2[0].name();
		}
		return tableName;
	}
	
}

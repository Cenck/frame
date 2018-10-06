package com.cengel.hibernate.trasnform;

import com.cengel.starbucks.exception.BusinessException;
import org.hibernate.transform.ResultTransformer;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/4 - 12:57
 * @Version V1.0
 **/
public class DoResultTransformer implements ResultTransformer {

	private Class<?> entityClass;

	public DoResultTransformer(Class<?> entityClass) {
		this.entityClass = entityClass;
	}




	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		try {
			return fullfillObj_recycle(entityClass, entityClass.newInstance(), tuple, aliases);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object fullfillObj_recycle(Class entityClass, Object retObj, Object[] tuple, String[] aliases) throws IllegalAccessException {
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Column.class)) {
				String tableColName = field.getAnnotation(Column.class).name();
				Object value = getColValue(tableColName, tuple, aliases);
				if (value == null) continue;
				ObjReflectSetHelper.setF(retObj, field, value);
			}
		}
		if (entityClass.isAnnotationPresent(Entity.class)) {
			//注解Entity的类实体是一个dto的有效字段最高父类
			return retObj;
		} else if (entityClass.getSuperclass()!=null){
			return fullfillObj_recycle(entityClass.getSuperclass(), retObj, tuple, aliases);
		}
		return null;
	}

	/**
	 * 根据字段的@Colnum("??") 取??的值
	 *
	 * @param tableColName 字段的数据库列名
	 * @param tuple 字段对应的数据库查询值
	 * @param aliases 字段的select 列标题
	 * @return
	 */
	private Object getColValue(String tableColName, Object[] tuple, String[] aliases) {
		for (int i = 0; i < aliases.length; i++) {
			String alias = aliases[i];
			if (tableColName.equalsIgnoreCase(alias)) {
				return tuple[i];
			}
		}
		return null;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}
}

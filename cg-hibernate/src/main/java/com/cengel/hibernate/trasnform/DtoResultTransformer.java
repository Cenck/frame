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
public class DtoResultTransformer implements ResultTransformer {

	private Class<?> entityClass;

	public DtoResultTransformer(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		try {
			Object o = entityClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				String alias = aliases[i];
				Field field = entityClass.getDeclaredField(alias);
				ObjReflectSetHelper.setF(o,field,tuple[i]);
			}
			return o;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} return null;
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

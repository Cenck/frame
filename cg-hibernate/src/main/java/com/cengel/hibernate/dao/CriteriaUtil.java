package com.cengel.hibernate.dao;

import com.cengel.starbucks.constant.BoolConstants;
import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.util.DateUtil;
import com.cengel.starbucks.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Date;

import static com.cengel.starbucks.model.entity.BEntity.PROP_DELETED;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/21 - 17:04
 * @Version V1.0
 **/
@Slf4j
public class CriteriaUtil {

	public static DetachedCriteria buildByEntity(BEntity entity)   {
		DetachedCriteria criteria = DetachedCriteria.forClass(entity.getClass());
		if (entity.getDeleted()==null) criteria.add(Restrictions.eq(PROP_DELETED,false));
		try {
			for (Field field : entity.getClass()
					.getDeclaredFields()) {
				if (!field.isAnnotationPresent(Column.class)) {
					continue;//todo 非声明数据库列字段跳过
				}
				field.setAccessible(true);
				Object value = field.get(entity);
				if (ObjectUtil.isNull(value)) {
					continue; //todo 值为空跳过
				}
				String javaName = field.getName(); //字段名
				String colName = field.getAnnotation(Column.class)
						.name()
						.toUpperCase();//数据库列名
				Class type = field.getType();
				if (String.class.isAssignableFrom(type)) {
					if (colName.toUpperCase()
							.endsWith("ID")) {
						//todo id列不模糊查询
						criteria.add(Restrictions.eq(javaName, value.toString()));
					} else {
						criteria.add(Restrictions.like(javaName, "%" + value.toString() + "%"));
					}
				} else if (Number.class.isAssignableFrom(type)) {
					criteria.add(Restrictions.eq(javaName, value.toString()));
				} else if (Date.class.isAssignableFrom(type)) {
					criteria.add(Restrictions.eq(javaName, DateUtil.format((Date) value, DateUtil.yyyy_MM_dd_HH_mm_ss)));
				} else if (Boolean.class.isAssignableFrom(type)){
					criteria.add(Restrictions.eq(javaName,value));
				}
				// ... entity中有其他字段类型，请在此扩展处理
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return criteria;
	}

}

package com.cengel.hibernate.trasnform;

import com.cengel.starbucks.util.AssertUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/30 - 21:09
 * @Version V1.0
 **/
public class ObjReflectSetHelper {

	public static void setF(Object retObj, Field field, Object value) throws IllegalAccessException {
		AssertUtil.notNull(retObj, "对象为空");
		if (value==null) return;
		field.setAccessible(true);
		if (Integer.class.isAssignableFrom(field.getType())) {
			field.set(retObj, Integer.valueOf(value.toString()));
		} else if (Long.class.isAssignableFrom(field.getType())){
			field.set(retObj,Long.valueOf(value.toString()));
		}else if (BigDecimal.class.isAssignableFrom(field.getType())) {
			field.set(retObj, new BigDecimal(value.toString()));
		} else if (String.class.isAssignableFrom(field.getType())) {
			field.set(retObj, String.valueOf(value));
		} else field.set(retObj, value);
	}
}

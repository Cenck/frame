package com.cengel.starbucks.io;

import com.cengel.starbucks.util.ObjectUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/16 - 19:47
 * @Version V1.0
 **/
public class PropUtil {

	/**
	 * 从linkedHashMap中读取并装入对象
	 *
	 * @param map
	 * @param t
	 */
	public static Object readAndFill(LinkedHashMap<String, ?> map, Object t) {
		if (ObjectUtil.isNull(map)){
			throw new RuntimeException("map不可为空");
		}
		Class tclass = t.getClass();
		Field field;
		for (String s : map.keySet()) {
			Object value = map.get(s);
			try {
				field = tclass.getDeclaredField(s);
				field.setAccessible(true);
				if (LinkedHashMap.class.isAssignableFrom(field.getType())){
					//如果对象仍为LinkHashMap 而非基本类型，则表明本字段是一个对象
					field.set(t, readAndFill((LinkedHashMap<String, ?>) value, field.getType().newInstance()));
				}else   {
					field.set(t, value);
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

}

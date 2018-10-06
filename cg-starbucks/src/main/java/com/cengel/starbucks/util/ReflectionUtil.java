package com.cengel.starbucks.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class ReflectionUtil {

	/**
	 * 获取一个类声明的泛型的class
	 *
	 * @param clazz
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class<Object> getGenericType(final Class clazz, final int index) {

		//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		//返回表示此类型实际类型参数的 Type 对象的数组。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * Get the object Field
	 *
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
	public static Field getField(String fieldName, Class<?> clazz) {
		Field field = null;
		try {
			Optional.of(clazz);
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			log.info(MessageFormat.format("Class name:{0} has no fieldName:{1}", clazz.getName(), fieldName));
		}
		return field;
	}

	/**
	 * 获取字段值
	 *
	 * @param fieldName {字段}
	 * @param srcObj 源对象(字段值取源)
	 * @param transformer 字段转换器
	 * @return
	 */
	public static Object getFiledVal(String fieldName, Object srcObj) {
		Object o = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, srcObj.getClass());
			//获得get方法
			Method getMethod = pd.getReadMethod();
			//执行get方法返回一个Object
			o = getMethod.invoke(srcObj);
		} catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
			e.printStackTrace();
			log.error("字段{}不存在", fieldName);
		}
		return o;
	}

	/**
	 * Set object field value
	 *
	 * @param field Object field
	 * @param obj ,The object to set value
	 * @param value ,The field value
	 * @return {@code true} Represent set value success
	 */
	public static void setFieldValue(Field field, Object obj, Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.info(MessageFormat.format("Set Obj:{0} field:{1} value:{2} failed", obj, field, value));
		}
	}

	/**
	 * Set Object field value
	 *
	 * @param obj , The target to set value,
	 * @param fieldMap , The field key/value map
	 */
	public static void setObjceFiled(Object obj, Map<String, Object> fieldMap) {

		if (obj == null) return;

		Iterator<String> keyIterator = fieldMap.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			Object value = fieldMap.get(key);
			Field field = getField(key, obj.getClass());
			setFieldValue(field, obj, value);
		}
	}

	public static Class<?> getUniqueMethodParamType(Method m) {
		Class<?>[] types = m.getParameterTypes();
		return types[0];
	}

	@SuppressWarnings("rawtypes")
	public static Class<?> getUniqueMethodParamType(Class classType, String methodName) {
		Class[] paramTypes = null;
		Method[] methods = classType.getDeclaredMethods();// 全部方法
		for (int i = 0; i < methods.length; i++) {
			if (methodName.equals(methods[i].getName())) {// 和传入方法名匹配
				Class[] params = methods[i].getParameterTypes();
				paramTypes = new Class[params.length];
				for (int j = 0; j < params.length; j++) {
					try {
						paramTypes[j] = Class.forName(params[j].getName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				break;
			}
		}

		if (!ArrayUtils.isEmpty(paramTypes)) return paramTypes[0];

		return null;
	}

	/**
	 * 获取具体类的第一个泛型类(泛型来源接口的情况)
	 *
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getUniqueActTypeArgInterface(Class clz) {
		Type[] type = clz.getGenericInterfaces();
		ParameterizedType parameterizedType = (ParameterizedType) type[0];
		return (Class) parameterizedType.getActualTypeArguments()[0];
	}

	public static Class getUniqueActTypeArgClass(Class clz) {
		ParameterizedType pt = (ParameterizedType) clz.getGenericSuperclass();

		return (Class) pt.getActualTypeArguments()[0];
	}

}

package com.cengel.starbucks.core.beans;

import com.cengel.starbucks.model.entity.BaseEntity;
import com.cengel.starbucks.model.obj.DataGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author simier 2013-8-9上午11:00:51
 * 
 */
public class BeanCopyUtil {

	private static final Logger logger	= LoggerFactory.getLogger(BeanCopyUtil.class);

	/**
	 * 对象拷贝操作
	 * 
	 * @param s 要拷贝的源对象
	 * @param t 要拷贝的目标对象的类型
	 * @return 返回目标对象
	 */
	public static <S, T> T copy(S s, Class<T> t) {

		if (s == null || t == null) {
			return null;
		}

		try {

			T nt = t.newInstance();

			BeanUtils.copyProperties(s, nt);

			return nt;

		} catch (InstantiationException e) {
			e.printStackTrace();
			logger.error("========= 拷贝时发生实例化对象异常 {}", e.toString());
			return null;

		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error("========= 拷贝时发生非法访问异常异常 {}", e.toString());
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("======== 对象 {} 和 {} 拷贝时发生错误 {}", new Object[] { s.getClass(), t, e.toString() });
			return null;

		}

	}

	/**
	 * 集合拷贝操作
	 * 
	 * @param s
	 *            要拷贝的源集合
	 * @param t
	 *            要拷贝的目标对象类型
	 * @return 返回目标集合
	 */
	public static <S, T> List<T> copy(List<S> s, Class<T> t) {
		List<T> lnt = new ArrayList<T>();
		if (null == s)
			return lnt;

		for (S ss : s) {

			lnt.add(copy(ss, t));

		}

		return lnt;

	}

	/**
	 * 集合拷贝操作
	 * 
	 * @param s
	 * @param t
	 * @return
	 */
	public static <S, T> List<T> copy(Collection<S> s, Class<T> t) {

		List<T> lnt = new ArrayList<T>();

		for (S ss : s) {

			lnt.add(copy(ss, t));

		}

		return lnt;

	}

	/**
	 * Map拷贝
	 * 
	 * @param s
	 *            Map集合
	 * @param t
	 *            Map集合中的value集合中的对象类型
	 * @return 返回目标Map集合
	 */
	public static <S, T, N> Map<S, List<N>> copy(Map<S, List<T>> s, Class<N> t) {

		Map<S, List<N>> map = new HashMap<S, List<N>>();

		Set<Map.Entry<S, List<T>>> set = s.entrySet();
		for (Map.Entry<S, List<T>> e : set) {
			map.put(e.getKey(), copy(e.getValue(), t));
		}

		return map;

	}

	/**
	 * 分页对象拷贝
	 * 
	 * @param dataGrid
	 * @param t
	 * @return
	 */
	public static <E, T> DataGrid<T> copy(DataGrid<E> dataGrid, Class<T> t) {
		DataGrid<T> dg = new DataGrid<T>();
		dg.setIdentified(dataGrid.getIdentified());
		dg.setNumRows(dataGrid.getNumRows());
		dg.setItems(copy(dataGrid.getItems(), t));
		dg.setLimit(dataGrid.getLimit());
		dg.setOffsetLimit(dataGrid.getOffsetLimit());
		return dg;
	}

	/**
	 * 类拷贝,只要属性相同都可以进行拷贝.不需要相同的类
	 * @param s
	 * @param t
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	public static <S, T> T copy2(S s, Class<T> t) throws InstantiationException, IllegalAccessException, SecurityException {
		StringBuffer sb = new StringBuffer();
		List<Field> fdL = new ArrayList<Field>();
		Class<?> clazz = s.getClass();
		while (!clazz.equals(BaseEntity.class) && !clazz.equals(Object.class)) {
			fdL.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		
		List<Field> fdL2 = new ArrayList<Field>();
		clazz = t;
		while (!clazz.equals(BaseEntity.class) && !clazz.equals(Object.class)) {
			fdL2.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		
		fdL.retainAll(fdL2);
		T result = t.newInstance();
		Class<?>[] parameterTypes = new Class[1]; 
		
		for (Field fi : fdL) {
			//判断get,set方法
			sb.setLength(0);
			sb.append("get");  
			sb.append(fi.getName().substring(0, 1).toUpperCase());  
			sb.append(fi.getName().substring(1));
			try {
				t.getMethod(sb.toString());
			} catch (NoSuchMethodException e) {
				continue;
			}
			
			sb.setLength(0);
			sb.append("set");  
			sb.append(fi.getName().substring(0, 1).toUpperCase());  
			sb.append(fi.getName().substring(1));
			try {
				 parameterTypes[0] = fi.getType();
				t.getMethod(sb.toString(),parameterTypes);
			} catch (NoSuchMethodException e) {
				continue;
			}
			//----------
			fi.setAccessible(true);
			fi.set(result, fi.get(s));
		}
		return result;
	}

}

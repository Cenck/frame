package com.cengel.starbucks.util;

import java.util.Collection;
import java.util.Map;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/20 - 11:30
 * @Version V1.0
 **/
public abstract class ObjectUtil {

	public static boolean isNull(Object o){return !isNotNull(o);}
	public static boolean isNotNull(Object o){
		if (o==null) return false;
		if (o instanceof Collection<?> && ((Collection) o).size()==0){
			return false;
		}
		if (o instanceof Map && ((Map) o).size()==0){
			return false;
		}
		if (o instanceof Number && Double.valueOf(o.toString()) == 0){
			return false;
		}
		return true;
	}

	//是否所有非空
	public static boolean isAllNotNull(Object ... objects){
		for (Object object : objects) {
			if (!isNotNull(object)){
				return false;
			}
		}
		return true;
	}

}

package com.cengel.starbucks.util;

import com.cengel.starbucks.exception.BusinessException;
import com.cengel.starbucks.model.methods.Callback;
import com.cengel.starbucks.model.methods.CallbackNone;

import java.util.function.Supplier;

/**
 * @Title: 断言工具
 * @Description:
 * @Author zhz
 * @Time 2018/7/20 - 13:24
 * @Version V1.0
 **/
public abstract class AssertUtil {


	//抛出空指针异常，就不执行了
	public static void ignoreNull(CallbackNone callback) {
		try {
			callback.exec();
		} catch (NullPointerException e) {
			e.fillInStackTrace();
		}
	}
	/**
	 * 方法如果不存在空指针，则正常执行，否则不执行并返回null
	 *
	 * @param callback
	 */
	public static <T> T ignoreNull(Callback callback) {
		Object o = null;
		try {
			o = callback.exec();
		} catch (NullPointerException e) {
			e.fillInStackTrace();
		}
		return (T) o;
	}
	public static void allNotNull(String message,Object ... vals){
		if (ObjectUtil.isNull(vals)) throw new BusinessException(message);
		for (Object val : vals) {
			if (ObjectUtil.isNull(val)) throw new BusinessException(message);
		}
	}
	public static void notNull(Object o ,String message){
		if (ObjectUtil.isNull(o)) throw new BusinessException(message);
	}
	public static void Assert(boolean o ,String message){
		if (o) throw new BusinessException(message);
	}
}

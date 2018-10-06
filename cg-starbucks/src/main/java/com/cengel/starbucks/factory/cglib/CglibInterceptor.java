package com.cengel.starbucks.factory.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/22 - 16:17
 * @Version V1.0
 **/
public class CglibInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
		CglibCallback cglibCallback = () -> {
			try {
				return methodProxy.invokeSuper(o, params);
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
			return null;
		};

		if (method.isAnnotationPresent(PrintMethodCostTime.class)) {
			return doPrintMethodTime(method, cglibCallback);
		}

		return cglibCallback.exec();
	}

	private static Object doPrintMethodTime(Method method, CglibCallback cglibCallback) {
		long start = System.currentTimeMillis();
		Object result = cglibCallback.exec();
		System.out.println("["+method.getName() + "方法执行计时] 消耗: --> " + (System.currentTimeMillis() - start));
		return result;
	}
}

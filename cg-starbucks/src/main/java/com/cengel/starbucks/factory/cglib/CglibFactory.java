package com.cengel.starbucks.factory.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/22 - 16:15
 * @Version V1.0
 **/
public class CglibFactory {

	private static CglibInterceptor interceptor = new CglibInterceptor();

	public static <T> T createProxy(Class<T> targetClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(interceptor);
		enhancer.setSuperclass(targetClass);
		return (T) enhancer.create();
	}

}

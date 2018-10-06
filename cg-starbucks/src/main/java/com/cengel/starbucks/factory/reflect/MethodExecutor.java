package com.cengel.starbucks.factory.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/6 - 20:10
 * @Version V1.0
 **/
public abstract class MethodExecutor {

	public static void invokeMethod(Class c){
		Scanner scanner = new Scanner(System.in);
		String s = scanner.next();
		System.out.println(s);
		Method m= null;
		try {
			m = c.getDeclaredMethod(s);
			m.invoke(null,null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}

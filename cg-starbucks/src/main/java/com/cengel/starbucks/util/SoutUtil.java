package com.cengel.starbucks.util;

import com.cengel.starbucks.factory.string.StringFormat;
import com.cengel.starbucks.mapper.JsonMapper;
import com.cengel.starbucks.constant.Symbol;

import java.util.Collection;

/**
 * @author zhz
 * @version V1.0
 * @create 2018-06-05 18:23
 **/
public abstract class SoutUtil {

	public static void printMethodName() {
		System.out.println("\n=====>: CurrentMethodName:  " + Thread.currentThread().getStackTrace()[2].getClassName() + Symbol.DOT + Thread.currentThread().getStackTrace()[2].getMethodName());
	}

	/**
	 * 格式化以下语句 : 今天是{}，周{},心情{}. 传入3个参数，按顺序填充
	 * @param o
	 * @param params
	 */
	public static void print(String  o,Object ... params) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String className = element.getClassName();
		String methodName = className + Symbol.DOT + element.getMethodName() + "======";
		o = StringFormat.formatBracket(o,params);
		System.out.println("--->: (" + methodName + "):\n" + o);
	}


	public static void print(Object o) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String className = element.getClassName();
		String methodName = className + Symbol.DOT + element.getMethodName() + "======";
		System.out.println("--->: (" + methodName + "):" + o);
	}

	public static void info(String info, Object... objects) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String className = element.getClassName();
		String methodName = className + Symbol.DOT + element.getMethodName() + "======";
		System.out.println("--->: (" + methodName + "):  " + info);
		if (objects != null) for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			System.out.println("\t\tinfo_" + i + ":\t" + JsonMapper.getMapper().toJson(object));
		}
	}

	public static void play(String type, String... objects) {
		play(type, 1000, objects);
	}

	public static void play(String type, int interval, String... objects) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String className = element.getClassName();
		String methodName = className + Symbol.DOT + element.getMethodName();
		if (objects != null) for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			System.out.println("--->: [" + type + "] (" + methodName + "):  " + object);
			try {
				//每打印一行，停1秒
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printArr(int[] arr) {
		System.out.print("[");
		for (int o1 : arr) {
			System.out.print(o1 + ", ");
		}
		System.out.println("]");
	}

	public static void printArr(Object o) {
		System.out.print("[");
		if (o instanceof Iterable) {
			Iterable collection = (Iterable) o;
			for (Object obj : collection) {
				if (obj != null) System.out.print(obj.toString()+",");
			}
		} else {
			Object[] arr = (Object[]) o;
			for (Object o1 : arr) {
				if (o1 != null) System.out.print(o1.toString() + ", ");
			}
		}
		System.out.println("]");
	}

}

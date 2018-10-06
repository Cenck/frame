package com.cengel.starbucks.factory.cglib;

import com.cengel.starbucks.model.methods.CallbackNone;

/**
 * @Title: 方法执行计时器
 * @Description:
 * @Author zhz
 * @Time 2018/8/23 - 18:09
 * @Version V1.0
 **/
public class MethodTimer {
	public static void run(CallbackNone callback) {
		CglibFactory.createProxy(MTObj.class).test(callback);
	}
}

class MTObj {
	@PrintMethodCostTime
	public void test(CallbackNone callback) {
		callback.exec();
		while (Thread.activeCount() > 1) {

		}
	}
}

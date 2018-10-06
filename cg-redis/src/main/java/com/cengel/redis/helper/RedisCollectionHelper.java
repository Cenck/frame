package com.cengel.redis.helper;

import java.io.Serializable;
import java.util.List;

public interface RedisCollectionHelper {

	/********************|| List ||********************/
	Long lPush(String key, Serializable... values); //先创建后添加
	Long rPush(String key, Serializable... values);
	<T> List<T> lRange(String key, long start, long end);

	Serializable lPop(String key);

	Serializable rPop(String key);

	Long lLen(String key);
}

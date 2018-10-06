package com.cengel.redis.helper;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface RedisHelper extends RedisCollectionHelper {
	String REDIS_HELPER_BEAN_NAME = "redisHelper";

	<T> T get(final String key);
	void set(final String key, final Serializable value);
	void del(final String key);
	<T> T hget(final String key, final String field);
	void hset(final String key, final String field, final Serializable value);
	void hdel(final String key, final String field);

	//查找key
	List<String> find(final String query);

	/** hget后放入map*/
	Map<String, Object> hgetMap(final String key);
	//写入并续期
	void setAndExpire(final String key, final Serializable value, Long time);
	//续期
	boolean expire(final String key, final long time);

	//原子操作自增长
	Integer incr(String key);

	//按规则清除一些key
	void clear(String pattern);

	RedisTemplate<String,?> getRedisTemplate();
}

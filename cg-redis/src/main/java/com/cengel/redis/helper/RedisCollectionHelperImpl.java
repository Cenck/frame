package com.cengel.redis.helper;

import com.cengel.starbucks.io.SerializationUtil;
import com.cengel.starbucks.util.AssertUtil;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/15 - 10:50
 * @Version V1.0
 **/
public class RedisCollectionHelperImpl implements RedisCollectionHelper {

	@Resource
	private RedisTemplate<String, ?> redisTemplate;

	/********************|| List ||********************/
	@Override
	public Long lPush(String key, Serializable... values) {
		AssertUtil.notNull(values, "lPush值不能为空");
		final byte[][] arr = new byte[values.length] [];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = SerializationUtil.serialize(values[i]);
		}
		return (Long) redisTemplate.execute((RedisCallback<?>) (redisConnection -> redisConnection.lPush(SerializationUtil.serializeStr(key), arr)));
	}

	@Override
	public Long rPush(String key, Serializable... values) {
		AssertUtil.notNull(values, "lPush值不能为空");
		final byte[][] arr = new byte[0][values.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = SerializationUtil.serialize(values[i]);
		}
		return (Long) redisTemplate.execute((RedisCallback<?>) (redisConnection -> redisConnection.rPush(SerializationUtil.serializeStr(key), arr)));
	}


	@Override
	public <T> List<T> lRange(String key, long start, long end) {
		AssertUtil.allNotNull("key均不能为空", key);
		List<byte[]> list = redisTemplate.execute((RedisCallback<List<byte[]>>) redisConnection -> redisConnection.lRange(SerializationUtil.serialize(key), start, end));
		List<?> retList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(list)) for (byte[] bytes : list) {
			retList.add(SerializationUtil.deserialize(bytes));
		}
		return (List<T>) retList;
	}

	@Override
	public Serializable lPop(String key) {
		return redisTemplate.execute((RedisCallback<Serializable>) redisConnection -> SerializationUtil.deserialize(Objects.requireNonNull(redisConnection.lPop(SerializationUtil.serialize(key)),key+"不存在")));
	}

	@Override
	public Serializable rPop(String key) {
		return redisTemplate.execute((RedisCallback<Serializable>) redisConnection -> SerializationUtil.deserialize(Objects.requireNonNull(redisConnection.rPop(SerializationUtil.serialize(key)),key+"不存在")));
	}

	@Override
	public Long lLen(String key) {
		return redisTemplate.execute((RedisCallback<Long>) connection -> connection.lLen(SerializationUtil.serialize(key)));
	}

}

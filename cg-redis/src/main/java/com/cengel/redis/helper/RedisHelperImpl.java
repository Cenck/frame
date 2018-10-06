package com.cengel.redis.helper;

import com.cengel.starbucks.constant.Symbol;
import com.cengel.starbucks.io.SerializationUtil;
import lombok.Getter;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/3 - 13:04
 * @Version V1.0
 **/
public class RedisHelperImpl extends RedisCollectionHelperImpl implements RedisHelper {

	@Resource
	@Getter
	private RedisTemplate<String, ?> redisTemplate;

	/**
	 * 读数据加域
	 *
	 * @param key
	 * @return
	 */
	@Override
	public <T> T get(final String key) {
		final byte[] fullkey = SerializationUtil.serializeStr(key);
		return redisTemplate.execute((RedisCallback<T>) connection -> {
			if (connection.exists(fullkey)) {
				byte[] value = connection.get(fullkey);
				return SerializationUtil.deserialize(value);
			}
			return null;
		});
	}

	;

	/**
	 * 获取短信验证码
	 *
	 * @param prefix
	 * @param telphone
	 * @return
	 */
	public String get(String prefix, String telphone) {
		Object val = this.get(prefix + Symbol.DOT + telphone);
		if (null == val) return "";
		return val + "";
	}

	/**
	 * map 取值 加域
	 *
	 * @param key
	 * @param field
	 * @return
	 */
	@Override
	public <T> T hget(final String key, final String field) {
		final byte[] keyb = SerializationUtil.serializeStr(key);
		final byte[] fieldsb = SerializationUtil.serializeStr(field);
		return redisTemplate.execute((RedisCallback<T>) connection -> {
			if (connection.exists(keyb)) {
				byte[] value = connection.hGet(keyb, fieldsb);
				if (value != null) return SerializationUtil.deserialize(value);
			}
			return null;
		});
	}

	/**
	 * 删除数据
	 *
	 * @param key
	 */
	@Override
	public void hdel(final String key, final String field) {
		final byte[] fullkey = SerializationUtil.serializeStr(key);
		redisTemplate.execute((RedisCallback<Serializable>) connection -> {
			connection.hDel(fullkey, SerializationUtil.serialize(field));
			return null;
		});
	}

	/**
	 * map 赋值 加域
	 *
	 * @param key
	 * @param field
	 * @param value
	 */
	@Override
	public void hset(final String key, final String field, final Serializable value) {
		final byte[] fullkey = SerializationUtil.serializeStr(key);
		final byte[] fullFiled = SerializationUtil.serializeStr(field);
		redisTemplate.execute((RedisCallback<Object>) connection -> {
			connection.hSet(fullkey, fullFiled, SerializationUtil.serialize(value));
			return null;
		});
	}

	/**
	 * 写数据加域
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void set(final String key, final Serializable value) {
		redisTemplate.execute((RedisCallback<Object>) connection -> {
			connection.set(SerializationUtil.serializeStr(key), SerializationUtil.serialize(value));
			return null;
		});
	}

	@Override
	public void setAndExpire(final String key, final Serializable value, Long time) {
		if (ObjectUtils.isEmpty(time)) {
			time = 30 * 60L;
		}
		this.set(key, value);
		if (time > 0) this.expire(key, time);
	}

	/**
	 * 删除数据
	 *
	 * @param key
	 */
	@Override
	public void del(final String key) {
		redisTemplate.execute((RedisCallback<Serializable>) connection -> {
			connection.del(SerializationUtil.serializeStr(key));
			return null;
		});
	}

	/**
	 * 设置过期加域
	 *
	 * @param key
	 * @param time
	 */
	@Override
	public boolean expire(final String key, final long time) {
		byte[] bytes = SerializationUtil.serializeStr(key);
		Boolean b = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.expire(bytes, time));
		if (b == null) throw new IllegalStateException("redis异常");
		return b;
	}

	/**
	 * 得到查找键
	 *
	 * @param query
	 * @return
	 */
	@Override
	public List<String> find(final String query) {
		Set<byte[]> resultB = redisTemplate.execute((RedisCallback<Set<byte[]>>) connection -> connection.keys(SerializationUtil.serializeStr(query)));
		List<String> result = new ArrayList<String>();
		for (byte[] i : resultB) {
			result.add(SerializationUtil.deserialize(i));
		}
		return result;
	}

	/**
	 * 原子操作自增长
	 */
	@Override
	public Integer incr(String key) {
		//todo该方法不会返回null值，如果没有这个key就播入为1
		return redisTemplate.execute((RedisCallback<Integer>) connection -> {
			RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
			byte[] keyb = serializer.serialize(key);
			connection.incr(keyb);
			byte[] bs1 = connection.get(keyb);
			if (bs1 == null) {
				this.set(key, 1);
				return 1;
			}
			return Integer.valueOf(Objects.requireNonNull(serializer.deserialize(bs1)));
		});
	}

	/**
	 * 取map 加域
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Map<String, Object> hgetMap(final String key) {
		final Map<String, Object> result = new HashMap<String, Object>();
		redisTemplate.execute((RedisCallback<Object>) connection -> {
			byte[] keyb = SerializationUtil.serializeStr(key);
			if (connection.exists(keyb)) {
				Map<byte[], byte[]> value = connection.hGetAll(keyb);
				for (byte[] mk : value.keySet()) {
					byte[] mv = value.get(mk);
					result.put(SerializationUtil.deserialize(mk), SerializationUtil.deserialize(mv));
				}
			}
			return null;
		});
		return result;
	}

	/**
	 * 清除域下所有信息
	 */
	@Override
	public void clear(String pattern) {
		final String key = "*" + pattern + "*";
		List<String> keys = find(key);
		for (String ki : keys) {
			del(ki);
		}
	}
}

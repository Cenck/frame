package com.cengel.redis.helper;

import com.cengel.starbucks.annotation.Description;
import com.cengel.starbucks.constant.Symbol;
import com.cengel.starbucks.util.Validator;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/3 - 13:04
 * @Version V1.0
 **/
public class RedisDomainHelperImpl extends RedisHelperImpl implements RedisHelper {

	@Description("为redis加域")
	private String domain;

	@Resource
	@Getter
	private RedisTemplate<String, ?> redisTemplate;

	public RedisDomainHelperImpl(String domain) {
		if (Validator.isBlank(domain)) this.domain = "";
		else this.domain = domain;
	}

	/**
	 * 读数据加域
	 *
	 * @param key
	 * @return
	 */
	@Override
	public <T> T get(final String key) {
		final String fullkey = domain + ":" + key;
		return super.get(fullkey);
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
		String fullkey = domain + ":" + key;
		return super.hget(fullkey, field);
	}

	/**
	 * 删除数据
	 *
	 * @param key
	 */
	@Override
	public void hdel(final String key, final String field) {
		final String fullkey = domain + ":" + key;
		super.hdel(fullkey, field);
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
		String fullkey = domain + ":" + key;
		super.hset(fullkey, field, value);
	}

	/**
	 * 写数据加域
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void set(final String key, final Serializable value) {
		final String fullkey = domain + ":" + key;
		super.set(fullkey, value);
	}

	@Override
	public void setAndExpire(final String key, final Serializable value, Long time) {
		if (ObjectUtils.isEmpty(time)) {
			time = 30 * 60L;
		}
		final String fullkey = domain + ":" + key;
		this.set(fullkey, value);
		super.expire(fullkey, time);
	}

	;

	/**
	 * 删除数据
	 *
	 * @param key
	 */
	@Override
	public void del(final String key) {
		final String fullkey = domain + ":" + key;
		super.del(fullkey);
	}

	/**
	 * 设置过期加域
	 *
	 * @param key
	 * @param time
	 */
	@Override
	public boolean expire(final String key, final long time) {
		final String fullkey = domain + ":" + key;
		return super.expire(fullkey, time);
	}

	/**
	 * 得到查找键
	 *
	 * @param query
	 * @return
	 */
	@Override
	public List<String> find(final String query) {
		final String fullQuery = domain + ":" + query;
		return super.find(fullQuery);
	}

	/**
	 * 取map 加域
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Map<String, Object> hgetMap(final String key) {
		String fullkey = domain + ":" + key;
		return super.hgetMap(fullkey);
	}

}

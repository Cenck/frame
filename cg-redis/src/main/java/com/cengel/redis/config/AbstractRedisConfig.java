package com.cengel.redis.config;

import com.cengel.redis.aop.RedisCacheAspect;
import com.cengel.redis.helper.*;
import com.cengel.redis.utils.DefSerializationRedisSerializer;
import com.cengel.starbucks.annotation.Description;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static com.cengel.redis.helper.RedisHelper.REDIS_HELPER_BEAN_NAME;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/10 - 15:57
 * @Version V1.0
 **/
public abstract class AbstractRedisConfig {

	@Description("redis的域")
	abstract String getRedisDomain();

	@Bean
	public RedisTemplate<String, ?> redisTemplate(JedisConnectionFactory connectionFactory) {
		RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setEnableTransactionSupport(true);//允许事务
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new DefSerializationRedisSerializer());
		redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager redisCacheManager(JedisConnectionFactory connectionFactory) {
		//初始化一个RedisCacheWriter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(3 * 24 * 60 * 60L)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new DefSerializationRedisSerializer()));
		//初始化RedisCacheManager
		return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
	}

	@Bean(name = REDIS_HELPER_BEAN_NAME)
	@ConditionalOnMissingBean(RedisHelper.class)
	public RedisHelper redisHelper() {
		return new RedisHelperImpl();
	}

	@Bean()
	@ConditionalOnMissingBean(RedisSessionHelper.class)
	public RedisSessionHelper RedisSessionHelper() {
		return new RedisSessionHelperImpl();
	}

	@Bean()
	@ConditionalOnMissingBean(RedisCodeHelper.class)
	public RedisCodeHelper RedisCodeHelper() {
		return new RedisCodeHelperImpl();
	}

	@Bean
	@ConditionalOnMissingBean(RedisCacheAspect.class)
	public RedisCacheAspect redisCacheAspect(){
		return new RedisCacheAspect();
	}


}

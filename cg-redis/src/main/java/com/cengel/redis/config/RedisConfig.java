package com.cengel.redis.config;

import com.cengel.starbucks.io.YamlReader;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/3 - 10:00
 * @Version V1.0
 **/
@EnableCaching
public class RedisConfig extends AbstractRedisConfig {

	private RedisConfigProp prop;

	protected RedisConfigProp getRedisConfigProp() {
		if (prop == null) {
			try {
				prop = new YamlReader(new ClassPathResource("properties.yml").getURL().getPath()).readAndFill("redis", new RedisConfigProp());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		RedisConfigProp prop = getRedisConfigProp();
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(prop.getMaxIdle());
		jedisPoolConfig.setMinIdle(prop.getMinIdle());
		jedisPoolConfig.setMaxTotal(prop.getMaxTotal());
		jedisPoolConfig.setMaxWaitMillis(prop.getMaxWait());
		jedisPoolConfig.setTestOnBorrow(prop.getTestOnBorrow());
		return jedisPoolConfig;
	}

	@Bean
	public JedisConnectionFactory connectionFactory(JedisPoolConfig poolConfig) {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		RedisConfigProp prop = getRedisConfigProp();
		configuration.setHostName(prop.getHost());
		configuration.setDatabase(prop.getDatabase());
		configuration.setPassword(RedisPassword.of(prop.getPassword()));
		configuration.setPort(prop.getPort());
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration);
		connectionFactory.setPoolConfig(poolConfig);
		return connectionFactory;
	}

	@Override
	String getRedisDomain() {
		return getRedisConfigProp().getDomain();
	}
}

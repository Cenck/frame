package com.cengel.redis.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/10 - 16:00
 * @Version V1.0
 **/
public class SpringBootRedisConfig extends AbstractRedisConfig {

	@Value("${cengel.redis.domain}")
	private String domain;

	@Override
	String getRedisDomain() {
		return domain;
	}
}

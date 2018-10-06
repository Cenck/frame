package com.cengel.code.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.cengel.code.model.config.DefaultConfig;
import com.cengel.starbucks.io.ResourceResolver;
import com.cengel.starbucks.io.YamlReader;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/26 - 11:47
 * @Version V1.0
 **/
public class JdbcConfig {

	private final static String URL;
	private final static String PWD;
	private final static String USER;
	private final static String DRIVER_NAME;

	static {
		LinkedHashMap<String, ?> linkedHashMap  = (LinkedHashMap<String, ?>) new YamlReader(ResourceResolver.getClassPath()+"summer.yml").read("summer.datasource");
		URL = linkedHashMap.get("url").toString();
		DefaultConfig.setSchema(linkedHashMap.get("schema").toString());
		PWD = linkedHashMap.get("password").toString();
		USER = linkedHashMap.get("username").toString();
		DRIVER_NAME = linkedHashMap.get("driverName").toString();
	}

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(URL);
		dataSource.setUsername(USER);
		dataSource.setPassword(PWD);
		dataSource.setDriverClassName(DRIVER_NAME);
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}

package com.cengel.hibernate.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.cengel.starbucks.io.YamlReader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/10 - 15:49
 * @Version V1.0
 **/
public class HibernateConfig extends AbstractHibernateConfig {

	@Override
	String[] getEntityScanPkgs() {
		List<String> list =  getProp().getEntityScanPkgs();
		return list.toArray(new String[list.size()]);
	}

	private HibernateConfigProp prop;

	//必须用子类继承此类，实现本方法
	protected HibernateConfigProp getProp() {
		if (prop == null) {
			YamlReader yamlReader = null;
			try {
				yamlReader = new YamlReader(new ClassPathResource("properties.yml").getURL().getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			prop = yamlReader.readAndFill("hibernate", new HibernateConfigProp());
		}
		return prop;
	}

	@Bean
	public DataSource dataSource() {
		HibernateConfigProp prop = getProp();
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(prop.getUrl());
		dataSource.setDriverClassName(prop.getDriverClass());
		dataSource.setUsername(prop.getUsername());
		dataSource.setPassword(prop.getPassword());
		return dataSource;
	}

}

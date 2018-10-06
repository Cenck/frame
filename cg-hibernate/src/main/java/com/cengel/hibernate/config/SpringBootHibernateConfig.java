package com.cengel.hibernate.config;

import com.cengel.starbucks.util.Validator;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/10 - 15:52
 * @Version V1.0
 **/
public class SpringBootHibernateConfig extends AbstractHibernateConfig {

	@Value("${cengel.hibernate.entityScanPkgs}")
	private String entityScanPkgs;

	@Override
	String[] getEntityScanPkgs() {
		if (Validator.isNotBlank(entityScanPkgs)) {
			return entityScanPkgs.split(",");
		}
		return new String[0];
	}

}

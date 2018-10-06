package com.cengel.code.spring.config;

import com.cengel.code.CodeStarter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/26 - 10:11
 * @Version V1.0
 **/
@Configuration
@ComponentScan(basePackageClasses = CodeStarter.class)
@Import({JdbcConfig.class,TemplateConfig.class})
public class RootConfig {



}

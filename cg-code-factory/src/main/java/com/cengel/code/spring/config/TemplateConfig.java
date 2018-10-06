package com.cengel.code.spring.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/26 - 18:05
 * @Version V1.0
 **/
public class TemplateConfig {

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		/**
		 <props>
		 <prop key="template_update_delay">0</prop>
		 <prop key="default_encoding">UTF-8</prop>
		 <prop key="number_format">0.##########</prop>
		 <prop key="datetime_format">yyyy-MM-dd HH:mm:ss</prop>
		 <prop key="classic_compatible">true</prop>
		 <prop key="template_exception_handler">ignore</prop>
		 </props>
		 */
		Properties properties = new Properties();
		properties.put("template_update_delay",0);
		properties.put("default_encoding","UTF-8");
		properties.put("number_format","0.##########");
		properties.put("datetime_format","yyyy-MM-dd HH:mm:ss");
		properties.put("classic_compatible",true);
		properties.put("template_exception_handler","ignore");

		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setNumberFormat("0.##########");
		configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		configuration.setDateFormat("yyyy-MM-dd");
		configuration.setClassicCompatible(true);
		configuration.setTemplateLoader( new ClassTemplateLoader(getClass(), "/templates"));
		configuration.unsetTemplateExceptionHandler();
		freeMarkerConfigurer.setConfiguration(configuration);
		freeMarkerConfigurer.setFreemarkerSettings(properties);
		freeMarkerConfigurer.setPreferFileSystemAccess(false);//允许加载jar包中的.ftl
		freeMarkerConfigurer.setTemplateLoaderPath( "classpath:/templates");
		return freeMarkerConfigurer;
	}

}

package com.cengel.code;

import com.cengel.code.facade.CodeFacade;
import com.cengel.code.model.beans.DefaultConfigBean;
import com.cengel.code.model.config.DefaultConfig;
import com.cengel.code.spring.config.RootConfig;
import com.cengel.code.task.TypeConverter;
import com.cengel.starbucks.io.YamlReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/26 - 10:10
 * @Version V1.0
 **/
public class CodeStarter {

	static {
		DefaultConfigBean defaultConfigBean = DefaultConfig.getBean();
		String summerYaml = null;
		try {
			summerYaml = new ClassPathResource("summer.yml").getURI().getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		YamlReader YamlReader = new YamlReader(summerYaml);
		defaultConfigBean.setTemplates((List<LinkedHashMap>) YamlReader.read("summer.codefactory.project." + DefaultConfig.getStartType()));
		defaultConfigBean.setTypeConverter(new TypeConverter((Map<String, String>) YamlReader.read("summer.codefactory.typeconverter")));
		DefaultConfig.getBean().setProjectPrefix(YamlReader.read("summer.codefactory.project.name").toString());
	}

	public static void run() {
		DefaultConfig.check();
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		CodeFacade codeFacade = context.getBean(CodeFacade.class);
		codeFacade.execute();
	}


}

package com.cengel.starbucks.context;

import com.cengel.starbucks.exception.BusinessException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * spring com.weiare.bean context
 *
 * @author HLH
 */
public class BeanContext implements ApplicationContextAware {
	public static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		BeanContext.context = context;
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static <T> T getBean(Class clazz) {
		Map<String, Object> map = context.getBeansOfType(clazz);
		if (map==null ||map.size()!=1) {
			throw new BusinessException("类存在大于1个bean");
		}
		for (String s : map.keySet()) {
			return (T) map.get(s);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, ? > getBeansByType(Class clazz) {
		Map<String, ? > map = context.getBeansOfType(clazz);
		return CollectionUtils.isEmpty(map) ? null : map;
	}
}
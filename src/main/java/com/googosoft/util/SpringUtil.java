package com.googosoft.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author songyan
 * @date 2020年4月20日 下午3:58:22
 * @desc Spring工具类
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

	/**
	 * 获取配置文件中的配置信息
	 * @param property 属性名
	 * @return
	 */
	public static String getProperty(String property) {
		return applicationContext.getEnvironment().getProperty(property);
	}
	
	/**
	 * 获取配置文件中的配置信息
	 * @param property 属性名
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getProperty(String property,String defaultValue) {
		String value = null;
		try {
			value = applicationContext.getEnvironment().getProperty(property);
			if(value==null || "".equals(value)){
				value = defaultValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			value = defaultValue;
		}
		return value;
	}

}
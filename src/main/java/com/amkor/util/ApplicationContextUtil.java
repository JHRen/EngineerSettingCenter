package com.amkor.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 实现自动注入spring bean的方法,用在定时任务中
 * @author 40607
 *
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	    context = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
	    return context;
	}

	public static Object getBean(String name) {
	    return getApplicationContext().getBean(name);
	}

}

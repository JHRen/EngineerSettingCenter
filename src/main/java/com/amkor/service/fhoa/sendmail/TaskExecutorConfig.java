package com.amkor.service.fhoa.sendmail;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 为了防止启动 @Async 报错的类
 * 
 * @author 40607
 *
 */
@Configuration
@EnableAsync
public class TaskExecutorConfig implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {// 实现AsyncConfigurer接口并重写getAsyncExecutor方法，并返回一个ThreadPoolTaskExecutor，这样我们就获得了一个基于线程池TaskExecutor
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(10);   //当前线程数
		taskExecutor.setMaxPoolSize(80);    //最大线程数
		taskExecutor.setQueueCapacity(100);  //线程池所使用的缓冲队列
		taskExecutor.initialize();  //初始化
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return new MyAsyncExceptionHandler();
	}
	

}

class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler{

	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
		// TODO Auto-generated method stub
		System.out.println("-------------》》》捕获线程异常信息");
		System.out.println("Exception message - " + throwable.getMessage());
		System.out.println("Method name - " + method.getName());
	}
	
	
}
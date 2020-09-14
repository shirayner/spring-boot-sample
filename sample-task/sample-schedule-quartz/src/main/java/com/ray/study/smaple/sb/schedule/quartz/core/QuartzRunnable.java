package com.ray.study.smaple.sb.schedule.quartz.core;

import com.ray.study.smaple.sb.schedule.quartz.util.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 执行定时任务
 * @author /
 */
@Slf4j
public class QuartzRunnable implements Callable {

	private Object target;
	private Method method;
	private String params;

	QuartzRunnable(String className, String methodName, String params) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
			Class<?> clazz = Class.forName(className);
			this.target = SpringContextHolder.getBean(clazz);
			this.params = params;

			if (!StringUtils.isEmpty(params)) {
				this.method = clazz.getDeclaredMethod(methodName, String.class);
			} else {
				this.method = clazz.getDeclaredMethod(methodName);
			}

	}

	@Override
	public Object call() throws Exception {
		ReflectionUtils.makeAccessible(method);
		if (!StringUtils.isEmpty(params)) {
			method.invoke(target, params);
		} else {
			method.invoke(target);
		}
		return null;
	}
}

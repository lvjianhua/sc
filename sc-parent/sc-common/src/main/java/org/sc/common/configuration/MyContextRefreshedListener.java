package org.sc.common.configuration;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MyContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
	protected static Logger logger = LogManager.getLogger(MyContextRefreshedListener.class);

	private String methodName = "getById";
	private String methodArgs = "-1";

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Mapper mapper = event.getApplicationContext().getBean(Mapper.class);
		
		// dozer 初始化
		Map<String, ?> maps = event.getApplicationContext().getBeansWithAnnotation(FeignClient.class);
		if (!maps.isEmpty()) {
			doFeginInit(maps, mapper);

			logger.info("feign client initialized.................");
		}
	}

	private void doFeginInit(Map<String, ?> maps, Mapper mapper) {
		Iterator<String> iter = maps.keySet().iterator();
		Object obj = mapper.map(new Object(), Object.class);

		while (iter.hasNext()) {
			String key = iter.next();

			Method[] methods = maps.get(key).getClass().getMethods();

			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					method.setAccessible(true);

					try {
						obj = method.invoke(maps.get(key), methodArgs);
					} catch (Exception e) {
					}

					if (!ObjectUtils.isEmpty(obj)) {
						obj = null;
					}

					break;
				}

			}

		}
	}
}

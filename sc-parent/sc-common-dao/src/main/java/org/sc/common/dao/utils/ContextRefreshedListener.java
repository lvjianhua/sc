package org.sc.common.dao.utils;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * spring 容器加载完成之后执行的各种方法
 * @author: shawn-gfresh  create by:2017年5月5日 下午8:02:33 
 *   
 * @version V1.0
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		EntityManager em = (EntityManager) event.getApplicationContext().getBean("entityManagerPrimary");
		Set<EntityType<?>> entitys = em.getMetamodel().getEntities();
		
		Iterator<EntityType<?>> iter = entitys.iterator();
		while(iter.hasNext()){
			EntityType<?> et = iter.next();
			ClassCache.setValueByClass(et.getJavaType());
		}
	}

}

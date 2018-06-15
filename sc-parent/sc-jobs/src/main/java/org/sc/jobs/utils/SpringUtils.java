package org.sc.jobs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring bean 工具类
 *
 * @author run  create by 17/03/29
 * @version V1.0
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(SpringUtils.class);

    /**
     * spring context 对象
     */
    private static ApplicationContext applicationContext;

    /**
     * 获取spring context 对象
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 根据class 文件获取bean对象
     * create by 17/03/29
     *
     * @param clzz ，Class<T>
     * @return T
     */
    public static <T> T getBean(Class<T> clzz) {
        return applicationContext.getBean(clzz);
    }

    /**
     * 根据name 获取bean对象
     * create by 17/03/29
     *
     * @param name ，String
     * @return T
     */
    public static Object getBean(String name) {
        try {
            return applicationContext.getBean(name);
        } catch (NoSuchBeanDefinitionException e) {
            LOG.info("没有获取到bean");
            return null;
        }
    }
}



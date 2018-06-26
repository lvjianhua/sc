package cn.gfresh.zuul.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.gfresh.zuul.security.MyFilterSecurityMetadataSource;

/**
 * spring 容器加载完成之后执行的各种方法
 *
 * @version V1.0
 * @author: shawn-gfresh  create by:2017年5月5日 下午8:02:33
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        if (context.getParent().getParent() == null) {
            /*MyWebSecurityConfig myWebSecurityConfig = (MyWebSecurityConfig) event.getApplicationContext().getBean(MyWebSecurityConfig.class);
            myWebSecurityConfig.afterFilter();*/
        	MyFilterSecurityMetadataSource mfsms = event.getApplicationContext().getBean(MyFilterSecurityMetadataSource.class);
        	mfsms.loadResource();
        }
    }
}

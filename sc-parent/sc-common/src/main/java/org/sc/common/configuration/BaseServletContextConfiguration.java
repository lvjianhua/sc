package org.sc.common.configuration;

import org.sc.common.interceptor.CrosInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Base ServletContext配置
 * Created by Webb Dong on 2017/4/6.
 */
public abstract class BaseServletContextConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 允许跨域请求拦截器
        registry.addInterceptor(new CrosInterceptor()).addPathPatterns("/**");
    }
}

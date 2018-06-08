package org.sc.common.interceptor;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 跨域设置
 * 
 * @author admin
 *
 */

@Configuration
public class CrosConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                //.allowedHeaders("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                .allowCredentials(true)
                .allowedMethods("POST", "GET", "OPTIONS", "DELETE")
                ;
    }
}

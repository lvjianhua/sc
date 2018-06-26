package cn.gfresh.auth.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class CrosConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				//.allowedHeaders("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
				//.allowedHeaders("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
				//.exposedHeaders(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.ORIGIN,
				//		HttpHeaders.X_REQUESTED_WITH, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT)
				.allowCredentials(true).allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS");
	}
	
	/**
	 * 另外一种跨域设置
	 */
	/*@Bean
	public FilterRegistrationBean corsFilter(){
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		
		source.registerCorsConfiguration("/**", config);
		
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		
		//设置在最前面加载
		bean.setOrder(0);
		
		return bean;
	}*/
}

package org.sc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 配置客户端
 * 
 * @author lvjh
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigClientApp.class).run(args);
	}

}

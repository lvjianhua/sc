package org.sc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 配置jobs客户端
 * 
 * @author lvjh
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class JobsApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(JobsApplication.class).run(args);
	}

}

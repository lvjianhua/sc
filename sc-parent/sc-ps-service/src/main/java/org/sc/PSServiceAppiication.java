package org.sc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * ps服务提供者启动类
 * 
 * @author Lvjh
 * 
 * @EnableDiscoveryClient:激活对配置中心的支持
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PSServiceAppiication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(PSServiceAppiication.class).web(true).run(args);
	}
}

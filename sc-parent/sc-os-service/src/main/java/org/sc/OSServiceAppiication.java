package org.sc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * os服务提供者启动类
 * 
 * @author Lvjh
 * 
 * @EnableDiscoveryClient:激活对配置中心的支持
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OSServiceAppiication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(OSServiceAppiication.class).web(true).run(args);
	}
}

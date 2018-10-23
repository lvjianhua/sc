package org.sc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * os服务调用者启动类
 * 
 * @author Lvjh
 * 
 * @SpringBootApplication:开启springBoot项目注解
 * @EnableEurekaClient: 该注解表明应用既作为eureka实例又为eureka client 可以发现注册的服务
 * @EnableFeignClients:开启feign调用服务注解
 * @EnableCircuitBreaker:开启断路器注解
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
public class OSApiApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(OSApiApplication.class).web(true).run(args);
	}
}

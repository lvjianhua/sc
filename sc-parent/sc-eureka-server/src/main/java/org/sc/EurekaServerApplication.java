package org.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * 服务注册中心启动类
 * 
 * @author lv
 * 
 * @EnableEurekaServer: 该注解表明应用为eureka服务，有可以联合多个服务作为集群，对外提供服务注册以及发现功能
 */

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}

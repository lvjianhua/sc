package org.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关服务启动类
 * 
 * @EnableZuulProxy:注入网关的实现
 * @author lvjh
 *
 */
@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
public class ZuulApplication {
    public static void main( String[] args ){
    	SpringApplication.run(ZuulApplication.class, args);
    }
}

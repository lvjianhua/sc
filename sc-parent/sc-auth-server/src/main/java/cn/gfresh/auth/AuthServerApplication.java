package cn.gfresh.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by Webb Dong on 2017/4/5.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthServerApplication{
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}

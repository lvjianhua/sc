package org.sc.configuration;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AuthProp {
	
//	@Value("${authprop.allowOrigin}")
    private String allowOrigin = "localhost";
//    @Value("${authprop.zuulIp}")
    private String zuulIp = "localhost";
//    @Value("${authprop.zuulPort}")
    private String zuulPort = "5000";
//    @Value("${authprop.zuulDns}")
    private String zuulDns = "localhost";
//    @Value("${authprop.scheme}")
    private String  scheme = "http";
}

package cn.gfresh.auth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AuthProp {
	
    @Value("${authprop.allowOrigin}")
    private String allowOrigin;
    @Value("${authprop.zuulIp}")
    private String zuulIp;
    @Value("${authprop.zuulPort}")
    private String zuulPort;
    @Value("${authprop.zuulDns}")
    private String zuulDns;
    @Value("${authprop.scheme}")
    private String  scheme;
}

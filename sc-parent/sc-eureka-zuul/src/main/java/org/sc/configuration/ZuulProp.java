package org.sc.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by shawn on 17-7-1.
 */
@Data
@Component
public class ZuulProp {
//    @Value("${zuulprop.loginAccessUrl}")
    private String loginAccessUrl = "";
//    @Value("${zuulprop.allowOrigin}")
    private String allowOrigin = ".gfresh.cn,//gfresh.cn,.igfresh.com";
//    @Value("${zuulprop.oAuthTimes}")
    private Integer oAuthTimes = 5;
//    @Value("${zuulprop.scheme}")
    private String scheme = "HTTP";
}
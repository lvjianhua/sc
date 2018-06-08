package org.sc.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.sc.controller.Response;

/**
 * Created by admin on 2017/5/10.
 */
@Order(0)
@FeignClient(name = "ps-service")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/privilege/getRolePrivilege")
    public Response getRolePrivilege();
}

package org.sc.common.service;

import org.sc.common.model.vo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 用户业务接口
 */
@FeignClient(value = "sc-ps-service", path = "/User")
public interface BaseUserService extends BaseLogicService<User> {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    User login(@RequestBody User User);
}

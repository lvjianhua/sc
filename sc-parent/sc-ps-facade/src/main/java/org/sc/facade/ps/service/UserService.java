package org.sc.facade.ps.service;

import java.util.List;

import org.sc.facade.ps.model.table.User;
import org.sc.facade.ps.urls.UserUrls;
import org.sc.common.service.BaseLogicService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User的声明式调用类
 * 
 * @author lvjh
 *
 */
@FeignClient(value = "ps-service",path = "user")
public interface UserService  extends BaseLogicService<User>{
	
	@RequestMapping(value="/getUsers",method = RequestMethod.GET)
	List<User> getUsers();

	@RequestMapping(value="/findByUserName",method = RequestMethod.GET)
	User findByUserName(@RequestParam(value="userName")String userName);
	
    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = UserUrls.LOGIN, method = {RequestMethod.POST})
    User login(@RequestBody User user);
	
}

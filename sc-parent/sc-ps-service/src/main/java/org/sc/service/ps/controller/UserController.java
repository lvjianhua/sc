package org.sc.service.ps.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.sc.facade.ps.model.table.User;
import org.sc.common.model.vo.Response;
import org.sc.facade.ps.service.UserService;
import org.sc.common.utils.web.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User服务提供者控制层
 * 
 * @author Lvjh
 *
 */
@Api(value = "user", description = "用户相关服务")
@RequestMapping(value = "/user")
@RestController
public class UserController {
	@Autowired
	UserService UserService;
	
	@ApiOperation(value = "获取User列表")
	@RequestMapping(value = "/getUsers" ,method = RequestMethod.GET)
	private Response getUsers(){
		List<User> result = UserService.getUsers();
		return ResponseUtil.ok(result);
	}
	
	@ApiOperation(value = "通过用户名称获取信息用户信息")
	@RequestMapping(value = "/findByUserName" ,method = RequestMethod.GET)
	private Response findByUserName(@ApiParam(value = "用户名称", name = "userName", required = true)
    @RequestParam String userName){
		User result = UserService.findByUserName(userName);
		return ResponseUtil.ok(result);
	}
}

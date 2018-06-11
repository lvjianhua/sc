package org.sc.api.ps.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.sc.facade.ps.model.table.User;
import org.sc.common.model.vo.Response;
import org.sc.api.ps.controller.urls.UserInfoUrls;
import org.sc.api.ps.enums.ServiceErrorCode;
import org.sc.api.ps.model.param.Login;
import org.sc.api.ps.service.UserApiService;
import org.sc.common.utils.StringUtils;
import org.sc.common.utils.web.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User控制层
 * 
 * @author lvjh
 *
 */
@Api(value = "user",description = "用户模块api")
@RestController
@RequestMapping(value = "/api/ps/user")
public class UserController {
	
	@Autowired
	UserApiService userApiService;
	
	@ApiOperation(value = "获取User列表")
	@RequestMapping(value="/getUsers",method = RequestMethod.GET)
	private Response getUsers(){
		List<User> result = userApiService.getUsers();
		return  ResponseUtil.ok(result);
	}
	
    @ApiOperation(value = "登录", httpMethod = "POST")
    @RequestMapping(value = UserInfoUrls.LOGIN, method = {RequestMethod.POST})
    public Response login(@RequestBody Login login) {
        if (StringUtils.isBlank(login.getUserName())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        User returnUser = userApiService.login(login);
        return ResponseUtil.ok(returnUser);
    }
	
}


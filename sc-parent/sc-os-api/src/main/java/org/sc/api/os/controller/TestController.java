package org.sc.api.os.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.sc.api.os.service.TestApiService;
import org.sc.common.model.vo.Response;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.facade.os.model.table.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test控制层
 * 
 * @author lvjh
 *
 */
@Api(value = "test",description = "用户模块api")
@RestController
@RequestMapping(value = "/api/os/test")
public class TestController {
	
	@Autowired
	TestApiService testApiService;

	@ApiOperation(value = "通过用户名称获取信息用户信息")
	@RequestMapping(value = "/findByName" ,method = RequestMethod.GET)
	private Response findByName(@ApiParam(value = "用户名称", name = "name", required = true)
    @RequestParam String name){
		Test result = testApiService.findByName(name);
		return ResponseUtil.ok(result);
	}
	
	@ApiOperation(value = "获取所有的数据")
	@RequestMapping(value = "/findAll" ,method = RequestMethod.GET)
	private Response findAll(){
		return ResponseUtil.ok(testApiService.findAll());
	}
	
}


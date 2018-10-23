package org.sc.service.os.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.sc.common.model.vo.Response;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.facade.os.model.table.Test;
import org.sc.facade.os.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test服务提供者控制层
 * 
 * @author Lvjh
 *
 */
@Api(value = "test", description = "用户相关服务")
@RequestMapping(value = "/test")
@RestController
public class TestController{
	@Autowired 
	TestService testService;

	@ApiOperation(value = "获取所有用户信息")
	@RequestMapping(value = "/findAll" ,method = RequestMethod.GET)
    public Response findAll() {                
    	return ResponseUtil.ok(testService.findAll());
    }

	@ApiOperation(value = "通过用户名称获取信息用户信息")
	@RequestMapping(value = "/findByName" ,method = RequestMethod.GET)
	private Response findByName(@ApiParam(value = "用户名称", name = "name", required = true)
    @RequestParam String name){
		Test result = testService.findByName(name);
		return ResponseUtil.ok(result);
	}
	
	@ApiOperation(value = "新增Test")
	@RequestMapping(value = "/saveTest" ,method = RequestMethod.POST)
	private Response saveTest(@ApiParam(value = "test", name = "test", required = true)
    @RequestBody Test test){
		return ResponseUtil.ok(testService.saveTest(test));
	}
	
	@ApiOperation(value = "编辑Test")
	@RequestMapping(value = "/updateTest" ,method = RequestMethod.POST)
	private Response updateTest(@ApiParam(value = "test", name = "test", required = true)
    @RequestBody Test test){
		return ResponseUtil.ok(testService.updateTest(test));
	}
	
	@ApiOperation(value = "根据id删除Test")
	@RequestMapping(value = "/removeById" ,method = RequestMethod.GET)
	private Response removeById(@ApiParam(value = "id", name = "id", required = true)
    @RequestParam String id){
		return ResponseUtil.ok(testService.removeById(id));
	}
	
	@ApiOperation(value = "通过id获取信息用户信息")
	@RequestMapping(value = "/findById" ,method = RequestMethod.GET)
	private Response findById(@ApiParam(value = "id", name = "id", required = true)
    @RequestParam String id){
		Test result = testService.findById(id);
		return ResponseUtil.ok(result);
	}
	
	@ApiOperation(value = "分页用户信息")
	@RequestMapping(value = "/pageTest" ,method = RequestMethod.GET)
    public Response pageTest(
    		@ApiParam(value = "当前页码", name = "pageInde", required = true)
    		@RequestParam int pageInde,
    		@ApiParam(value = "每页条数", name = "pageSize", required = true)
    		@RequestParam int pageSize) {                
    	return ResponseUtil.ok(testService.pageTest(pageInde,pageSize));
    }
	
}

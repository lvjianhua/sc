package org.sc.facade.os.service;

import java.util.List;

import org.sc.facade.os.model.table.Test;
import org.sc.common.service.BaseLogicService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Test的声明式调用类
 * 
 * @author lvjh
 *
 */
@FeignClient(value = "os-service",path = "/test")
public interface TestService{
	@RequestMapping(value="/findByName",method = RequestMethod.GET)
	Test findByName(@RequestParam(value="name")String name);

	@RequestMapping(value="/findAll",method = RequestMethod.GET)
	List<Test> findAll();

	@RequestMapping(value="/saveTest",method = RequestMethod.POST)
	Test saveTest(@RequestBody Test test);

	@RequestMapping(value="/updateTest",method = RequestMethod.POST)
	Test updateTest(@RequestBody Test test);

	@RequestMapping(value="/removeById",method = RequestMethod.GET)
	String removeById(@RequestParam(value="id")String id);
	
	@RequestMapping(value="/findById",method = RequestMethod.GET)
	Test findById(@RequestParam(value="id")String id);

	@RequestMapping(value="/pageTest",method = RequestMethod.GET)
	List<Test> pageTest(@RequestParam(value="pageInde")int pageInde,@RequestParam(value="pageSize") int pageSize);
	
}

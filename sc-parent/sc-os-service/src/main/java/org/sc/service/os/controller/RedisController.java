package org.sc.service.os.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.sc.common.model.vo.Response;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.service.os.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lv on 2018/10/17.
 */
@Api(value = "redis", description = "redis相关服务")
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisService redisService;

    /**
     * 指定缓存失效时间
     * 
     * @param key 键
     * @param time 失效时间
     * @return
     */
    @ApiOperation(value = "指定缓存失效时间")
    @RequestMapping(value = "expire",method = RequestMethod.POST)
    public Response expire(String key, long time){
        boolean result = redisService.expire(key, time);
    	if(!result){
    		 return ResponseUtil.error();
    	}
    	return ResponseUtil.ok();
    }
    
    /**
     * 操作redis里的字符串类型
     * 
     * @param key
     * @param val
     * @return
     */
    @ApiOperation(value = "操作redis里的字符串类型")
    @RequestMapping(value = "set",method = RequestMethod.POST)
    public Response set(String key, String val){
        boolean result = redisService.set(key, val);
    	if(!result){
    		 return ResponseUtil.error();
    	}
    	return ResponseUtil.ok(result);
    }
    
    /**
     * 根据键获取redis里面的值
     * 
     * @param key
     * @return
     */
    @ApiOperation(value = "根据键获取redis里面的值")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public Response get(String key){
    	return ResponseUtil.ok(redisService.get(key));
    }



}
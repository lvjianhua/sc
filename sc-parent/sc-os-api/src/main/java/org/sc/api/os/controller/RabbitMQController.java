package org.sc.api.os.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.sc.api.os.utils.rabbitMQ.sender.DelaySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RabbitMQ测试控制层
 * 
 * @author lvjh
 *
 */
@Api(value = "rabbit",description = "rabbit模块api")
@RestController
@RequestMapping(value = "/api/os/rabbit")
public class RabbitMQController {
	
    @Autowired
    private DelaySender firstSender;

/*    @ApiOperation(value = "发送普通消息")
    @RequestMapping(value = "/sendMessage",method=RequestMethod.POST)
    public Object sendMessage(String content) {
    	secondSender.send(content);
        return "ok";
    }*/
    
    @ApiOperation(value = "发送延迟消息")
    @RequestMapping(value = "/sendYanchiMessage",method=RequestMethod.POST)
    public Object sendYanchiMessage(String content,String time) {       
        firstSender.publish(content,time);
        return "ok";
    }
	
}


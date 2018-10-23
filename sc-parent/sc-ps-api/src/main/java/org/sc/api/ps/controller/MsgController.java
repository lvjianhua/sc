package org.sc.api.ps.controller;

import com.aliyun.openservices.ons.api.Message;

import org.sc.api.ps.utils.alions.BusMqConfig;
import org.sc.api.ps.utils.alions.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;

import java.util.Random;

/**
 * Created by lv on 2018/9/21.
 */
@Api(value = "MsgController", description = "消息队列api")
@RestController
@RequestMapping(value = "/api/ps/msg")
public class MsgController {

    @Autowired
    MqProducer mqProducer;

    @Autowired
    BusMqConfig busMqConfig;

    @PostMapping(value = "/send")
    public Message msg(@RequestBody String msgbody, HttpServletRequest request){
        //System.out.println(msgbody);
        Message msg = new Message(
                // Message 所属的 Topic
                busMqConfig.getTopic(),
                // Message Tag,
                // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                "TagA",
                // Message Body
                // 任何二进制形式的数据，MQ 不做任何干预，需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                msgbody.getBytes());

        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发。
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_" + Math.round(Math.random()*8999+1000));
        mqProducer.sentMessage(msg);
        return msg;
    }
}
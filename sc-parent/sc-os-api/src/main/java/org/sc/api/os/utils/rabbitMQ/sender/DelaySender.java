package org.sc.api.os.utils.rabbitMQ.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.sc.api.os.utils.rabbitMQ.config.RabbitDelayConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
 
/**
 * 延迟消息发送  生产者1
 * 
 * @author lv
 * @date 2018/10/17
 */
@Slf4j
@Component
public class DelaySender {
 
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 延迟消息放入延迟队列中
     * 
     * @param msg
     * @param expiration
     */
    public void publish(String msg, String expiration) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("消息发送时间:"+sdf.format(new Date()));
        // 添加延时队列
        rabbitTemplate.convertAndSend(RabbitDelayConfig.REGISTER_DELAY_EXCHANGE, RabbitDelayConfig.DELAY_ROUTING_KEY, msg, message -> {
            // TODO 第一句是可要可不要,根据自己需要自行处理
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,msg);
            // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(expiration);
            return message;
        });   	
    }
    
}

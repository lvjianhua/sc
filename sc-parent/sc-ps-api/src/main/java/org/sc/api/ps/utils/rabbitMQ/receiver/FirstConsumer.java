package org.sc.api.ps.utils.rabbitMQ.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
/**
 * 消息消费者1
 * 
 * @author lv
 * @date 2018/10/17
 */
@Component
public class FirstConsumer {
 
    @RabbitListener(queues = {"lv_queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("消息接收时间:"+sdf.format(new Date()));
        System.out.println("sc-ps-api----------->FirstConsumer {} handleMessage :"+message);
    }
}

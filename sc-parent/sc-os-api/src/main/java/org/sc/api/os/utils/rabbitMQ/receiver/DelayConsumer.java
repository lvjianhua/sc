package org.sc.api.os.utils.rabbitMQ.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.sc.api.os.utils.rabbitMQ.config.RabbitDelayConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
/**
 * 消息延迟消费者
 * 
 * @author lv
 * @date 2018/10/17
 */
@Component
public class DelayConsumer {
	
    @RabbitListener(queues = {RabbitDelayConfig.REGISTER_QUEUE_NAME})
    public void handleMessage(String message) throws Exception {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("消息接收时间:"+sdf.format(new Date())+"---->message:"+message);
    }
}

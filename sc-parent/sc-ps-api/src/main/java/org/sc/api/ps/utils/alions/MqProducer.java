package org.sc.api.ps.utils.alions;


import com.aliyun.openservices.ons.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 * 
 * Created by lv on 2018/9/21.
 */
@Component
public class MqProducer implements InitializingBean,DisposableBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqProducer.class);

    @Autowired
    BusMqConfig busMqConfig;

    // 消息生产者接口
    private Producer producer;
    
    // 初始化消息生产者
    @Override
    public void afterPropertiesSet() throws Exception {
        //producer = ONSFactory.createProducer(busMqConfig.getProducerProperties());
        //producer.start();
    }

    // 发送消息
    public void sentMessage(Message message){
            producer.sendAsync(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info(sendResult.getTopic()+"-----"+sendResult.getMessageId());
                }

                @Override
                public void onException(OnExceptionContext context) {
                    LOGGER.error(context.getTopic()+"-----"+context.getMessageId()+":error="+context.getException());
                }
            });
    }

    @Override
    public void destroy() throws Exception {
            producer.shutdown();
    }
}
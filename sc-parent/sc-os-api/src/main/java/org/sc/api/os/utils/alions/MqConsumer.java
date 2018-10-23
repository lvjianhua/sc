package org.sc.api.os.utils.alions;

import com.aliyun.openservices.ons.api.*;

import org.sc.api.os.service.TestApiService;
import org.sc.api.os.utils.listener.MyMessageListener;
import org.sc.facade.os.model.table.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * 
 * Created by lv on 2018/9/21.
 * Spring bean 通过实现 InitializingBean ,DisposableBean 接口实现初始化方法和销毁前操作
 */
@Component
@ComponentScan
public class MqConsumer implements InitializingBean, DisposableBean{
    @Autowired
    BusMqConfig busMqConfig;
    
    private Consumer busConsumer;

    @Autowired
	TestApiService testApiService;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        //System.out.println("消费者初始化");
        //busConsumer = ONSFactory.createConsumer(busMqConfig.getConsumerProperties());     
        //busConsumer.start();
        //busConsumer.subscribe(busMqConfig.getTopic(), busMqConfig.getSubExpression(), new MyMessageListener());
        //System.out.println("消费者初始化完成");
    }

    // 消费者启动
    public void start(){
        busConsumer.start();
    }
    
    // 此处可以写具体业务逻辑，body是具体发送的内容
    public void onMessage(){
        busConsumer.subscribe(busMqConfig.getTopic(), busMqConfig.getSubExpression(), new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext context) {
                // System.out.println(JSON.toJSONString(message));
                // System.out.println("Receive: " + message);
                System.out.println("接收到的消息: " + new String(message.getBody()));
                Test user = testApiService.findByName("张三");
                System.out.println("Test："+user);
                return Action.CommitMessage;
            }
        });
    }
    
    @Override
    public void destroy() throws Exception {
        busConsumer.shutdown();
        System.out.println("停止");
    }

}

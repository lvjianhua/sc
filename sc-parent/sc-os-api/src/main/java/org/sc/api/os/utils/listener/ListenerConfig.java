package org.sc.api.os.utils.listener;

import org.sc.api.os.utils.alions.MqConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 监听并开始消费消息
 * 
 * Created by lv on 2018/9/21.
 */
@Component
public class ListenerConfig implements CommandLineRunner{
    @Autowired
    MqConsumer mqConsumer;
    
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("开始消费");
        //mqConsumer.start();
        //mqConsumer.onMessage();
    }
}

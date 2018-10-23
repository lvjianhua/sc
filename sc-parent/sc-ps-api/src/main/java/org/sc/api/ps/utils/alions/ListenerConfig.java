package org.sc.api.ps.utils.alions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 * 
 * Created by lv on 2018/9/21.
 */
@Component
public class ListenerConfig implements CommandLineRunner{
    @Autowired
    MqConsumer mqConsumer;
    @Override
    public void run(String... strings) throws Exception {
        //System.out.println("开始消费");
        //mqConsumer.start();
        //mqConsumer.onMessage();
    }
}

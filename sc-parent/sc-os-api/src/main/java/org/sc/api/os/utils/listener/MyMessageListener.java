package org.sc.api.os.utils.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

import org.sc.api.os.service.impl.TestServiceImpl;
import org.sc.common.utils.SpringContextUtils;
import org.sc.facade.os.model.table.Test;
/**
 * 接收阿里ons消息并进行业务处理
 * 
 * Created by lv on 2018/10/12.
 */
public class MyMessageListener implements MessageListener {
	
    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
    	System.out.println("接收到的消息: " + new String(message.getBody()));
        TestServiceImpl testServiceImpl = (TestServiceImpl) SpringContextUtils.getBean(TestServiceImpl.class);
        Test test = testServiceImpl.findByName("张三");
        System.out.println("msgBody is : " + test);
        return Action.CommitMessage;
    }
}

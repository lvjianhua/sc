package org.sc.common.alimns;

import com.aliyun.mns.client.AsyncResult;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.model.Message;

/**
 * Created by Webb Dong on 2017/6/27.
 */
public class ConsumeTask extends TaskBase implements Runnable {

    public CloudQueue mQueue;

    public ConsumeTask(CloudQueue queue, int receiveNum) {
        mQueue = queue;
        mNum = receiveNum;
    }

    @Override
    public void run() {
        int receiveMsgNum = 0;
        while (receiveMsgNum++ < mNum) {
            ReceiveDeleteAsyncCallback<Message> cb = new ReceiveDeleteAsyncCallback<Message>(
                    this);
            AsyncResult<Message> asyncPopMsgResult = mQueue
                    .asyncPopMessage(cb);
            if (asyncPopMsgResult == null) {
                System.out.println("AsyncPopMessage Fail!");
            }
        }
        waitComplete();
    }

}

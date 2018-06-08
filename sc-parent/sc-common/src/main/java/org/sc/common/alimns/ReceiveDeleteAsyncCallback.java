package org.sc.common.alimns;

import com.aliyun.mns.client.AsyncCallback;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;

/**
 * Created by Webb Dong on 2017/6/27.
 */
public class ReceiveDeleteAsyncCallback<T> implements AsyncCallback<T> {

    private ConsumeTask mTask;
    private MessageStage mStage;
    private Message mMessage;

    public ReceiveDeleteAsyncCallback(ConsumeTask task) {
        mTask = task;
        mStage = MessageStage.ReceiveStage;
    }

    @Override
    public void onSuccess(T msg) {
        if (mStage == MessageStage.ReceiveStage) {
            mMessage = (Message) msg;
            System.out.println("Receive Message " + mMessage.getMessageId()
                    + ",message body:" + mMessage.getMessageBody() + ",thread:"
                    + Thread.currentThread().getName());

            doDelete();
        } else if (mStage == MessageStage.DeleteStage) {
            System.out.println("Delete Message " + mMessage.getMessageId());

            mTask.updateCompleteCount();
        }
    }

    @Override
    public void onFail(Exception ex) {
        System.out.println("Operate Message Fail.");
        if (ex instanceof ServiceException
                && mTask.mQueue.isMessageNotExist((ServiceException) ex)) {
            System.out.println("Stage:" + mStage);

            if (mStage == MessageStage.ReceiveStage) {
                System.out.println("Continue to receive message.");
                doReceive();
            } else {
                if (mStage == MessageStage.DeleteStage) {
                    System.out.println("Message does not exist when deleting.");
                }

                mTask.updateCompleteCount();
            }
        } else {
            ex.printStackTrace();

            mTask.updateCompleteCount();
        }
    }

    public void doReceive() {
        mStage = MessageStage.ReceiveStage;
        mTask.mQueue.asyncPopMessage((ReceiveDeleteAsyncCallback<Message>) this);
    }

    public void doDelete() {
        mStage = MessageStage.DeleteStage;
        mTask.mQueue.asyncDeleteMessage(mMessage.getReceiptHandle(),
                (ReceiveDeleteAsyncCallback<Void>) this);
    }

}

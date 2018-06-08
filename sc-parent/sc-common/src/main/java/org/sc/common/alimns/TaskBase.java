package org.sc.common.alimns;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Webb Dong on 2017/6/27.
 */
public class TaskBase {

    private final AtomicInteger mCount = new AtomicInteger(0);
    private final ReentrantLock mLock = new ReentrantLock();
    private final Condition mCondition = mLock.newCondition();
    private final AtomicBoolean mFinished = new AtomicBoolean(false);
    public int mNum;

    public void waitComplete() {
        mLock.lock();
        if (!mFinished.get()) {
            try {
                mCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mLock.unlock();
    }

    public int updateCompleteCount() {
        int num = mCount.incrementAndGet();
        if (num >= mNum) {
            mLock.lock();
            mFinished.set(true);
            mCondition.signal();
            mLock.unlock();
        }
        return num;
    }

}

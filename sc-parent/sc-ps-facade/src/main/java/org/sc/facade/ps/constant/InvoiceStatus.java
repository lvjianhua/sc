package org.sc.facade.ps.constant;

/**
 * Created by Sonic Wang on 2017/6/26.
 */
public interface InvoiceStatus {

    /**
     * 已认证
     */
    int AUTHENTICATED = 2;

    /**
     * 认证中
     */
    int IN_AUTHENTICATION = 1;

    /**
     * 未认证
     */
    int UNAUTHORIZED = 0;

    /**
     * 认证失败
     */
    int FAILED_AUTHENTICATION = -1;
}

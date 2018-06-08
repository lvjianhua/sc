package org.sc.api.ps.contant;

/**
 * Created by Sonic Wang on 2018/3/19.
 */
public interface CouponStatus {

    /**
     * 未使用
     */
    int NOT_USED = 0;

    /**
     * 已使用
     */
    int USED = 1;

    /**
     * 已过期
     */
    int OVERDUE = 2;


    /**
     * 有效
     */
    int VALID = 3;
    /**
     * 无效（包含已使用、已过期）
     */
    int INVALID = 4;
}

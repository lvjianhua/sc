package org.sc.facade.ps.constant;

/**
 * 用户状态
 * Created by Sonic Wang on 2017/4/14.
 */
public interface UserStatus {

    /**
     * 正常
     */
    int NORMAL = 0;
    /**
     * 冻结或删除
     */
    int FROZEN = -1;

    /**
     * 待验证邮箱
     */
    int NO_MAIL = -2;
}

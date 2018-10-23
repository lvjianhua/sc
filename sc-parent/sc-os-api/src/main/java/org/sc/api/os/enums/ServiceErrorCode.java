package org.sc.api.os.enums;

import org.sc.common.enmus.ErrorCodeInterface;


/**
 * Created by Sonic Wang on 2017/5/2.
 */
public enum ServiceErrorCode implements ErrorCodeInterface {

    WRONG_DATA(101, "非法参数"),

    ERROR_QUOTE(102, "库存不足"),

    WRONG_BANKCARD(103, "开户行和卡号不匹配"),

    FAILED_MOBILE_RECEIPT(104,"操作失败，请检查您的信息！"),

    OUT_AUTHENTICATION(105,"该付款人帐号已存在"),

    FAIL_COMMIT(106,"受理失败，请检查您的信息"),

    WRONG_DATE(107,"日期格式错误"),

    WRONG_MESSAGE_CODE(108,"验证码错误！"),

    FAILED_SEND_MAIL(109,"邮件发送失败！"),

    CANNOT_OPERATE(110,"不能执行该操作！"),

    NO_USER(111,"没有该用户！"),

    REPEAT_PHONE(112,"手机号已存在！"),

    EXCEPTION_ROLE(113,"角色不存在或异常！"),

    REPEAT_MOBILE_CARD(114,"该代扣卡已被使用！"),

    WRONG_PAY_PASSWORD(119, "支付密码错误"),

    NO_STATUS_ACCOUNT(120, "无法使用该卡");

    private int code;
    private String message;

    ServiceErrorCode(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

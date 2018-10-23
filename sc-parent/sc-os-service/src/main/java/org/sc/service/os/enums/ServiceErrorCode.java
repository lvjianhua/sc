package org.sc.service.os.enums;

import org.sc.common.enmus.ErrorCodeInterface;


/**
 * Created by Sonic Wang on 2017/5/2.
 */
public enum ServiceErrorCode implements ErrorCodeInterface {

    WRONG_DATA(101, "非法参数"),

    NO_ROOT(102, "没有权限"),

    WRONG_ADD(103, "添加失败"),

    WRONG_UPDATE(104, "更新失败"),

    REPEAT_DATA(105, "重复数据"),

    REPEAT_USERNAME(106, "用户名重复"),

    REPEAT_PHONE(107, "手机号重复"),

    REPEAT_EMAIL(108, "邮箱重复"),

    NO_VALID_DATA(109, "参数验证不通过"),

    REPEAT_INVOICE_TITLE(111, "发票抬头重复"),

    ERROR_OBJECT_BY_ID(112, "通过id找不到对象"),

    NO_INVOICE(113, "找不到该发票"),

    WRONG_LOGIN(114, "用户名密码错误"),

    ERROR_OBJECT(115, "对象为空"),

    NO_USER(116, "没有该用户"),

    REPEAT_COLLECTION(117, "您已收藏！"),

    WRONG_NUM(118, "错误的数字类型"),

    WRONG_ACCOUNT(119, "账户异常"),

    FROZEN_ACCOUNT(120, "账户被冻结"),

    NO_ADDRESS(121, "找不到该地址"),

    ERROR_MONEY(122, "账户资金不足"),

    NEED_CHECK_EMAIL(123, "请去邮箱激活账号！"),

    NO_SHOP_OWNER(124, "找不到店主！"),

    REPEAT_PAYORDERCODE(125, "payOrderCode已存在！"),

    NO_COUPON(126, "您没有该优惠券"),

    USER_HAVE_CODE(127, "用户已分配专属号！");

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

package org.sc.facade.ps.model.attribute;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class UserExtendAttribute {

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 微信openid
     */
    private String wxOpenId;

    /**
     * 极光推送注册id
     */
    private String registionId;

    /**
     * 会员等级id
     */
    private String vipLevelId;

//    /**
//     * 是否是供应商
//     */
//    private Boolean isSupplier;

}

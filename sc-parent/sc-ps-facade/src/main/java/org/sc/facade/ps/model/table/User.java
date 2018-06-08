package org.sc.facade.ps.model.table;

import org.sc.common.model.vo.BaseEntity;
import org.sc.facade.ps.constant.InvoiceStatus;
import org.sc.facade.ps.constant.UserStatus;
import org.sc.facade.ps.constant.UserType;
import org.sc.facade.ps.model.attribute.UserExtendAttribute;
import org.sc.facade.ps.model.attribute.Wallet;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户表
 */
@Entity(name = "User")
@Table(name = "\"user\"")
@DynamicUpdate(true)
@DynamicInsert(true)
@Data
public class User extends BaseEntity {

    /**
     * 用户名，登陆名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 国家id
     */
    private String countryId;

    /**
     * 密码
     */
    private String password;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 状态 {@link UserStatus}
     */
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer status;

    /**
     * 用户类型 {@link UserType}
     */
    private String userType;

    /**
     * 用户扩展属性
     */
    @Column
    @Type(type = "JsonDataUserType")
    private UserExtendAttribute extendAttribute;

    /**
     * 账户资金
     */
    @Column
    @Type(type = "JsonDataUserType")
    private Wallet wallet;

}
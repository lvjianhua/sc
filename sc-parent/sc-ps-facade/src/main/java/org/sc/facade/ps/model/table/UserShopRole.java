package org.sc.facade.ps.model.table;

import org.sc.common.model.vo.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户角色表
 * Created by Sonic Wang on 2017/4/26.
 */
@Entity(name = "UserShopRole")
@Table(name = "user_shop_role")
@DynamicUpdate(true)
@DynamicInsert(true)
@Data
public class UserShopRole extends BaseEntity {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 角色id
     */
    private String roleId;

}

package org.sc.facade.ps.model.table;

import org.sc.common.model.vo.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色权限表
 * Created by Sonic Wang on 2017/4/26.
 */
@Entity(name = "PrivilegeRole")
@Table(name = "privilege_role")
@DynamicUpdate(true)
@DynamicInsert(true)
@Data
public class PrivilegeRole extends BaseEntity {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String privilegeId;

}

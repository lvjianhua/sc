package org.sc.facade.ps.model.table;

import org.sc.common.model.vo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色表
 * Created by Sonic Wang on 2017/4/26.
 */
@Entity(name = "Role")
@Table(name = "role")
@DynamicUpdate(true)
@DynamicInsert(true)
@Data
public class Role extends BaseEntity {

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 上级角色id
     */
    @ApiModelProperty(value = "上级角色id")
    private String parentId;

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号")
    private String roleCode;
}

package org.sc.facade.ps.model.table;

import lombok.Data;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.sc.common.model.vo.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限表
 * Created by Sonic Wang on 2017/4/26.
 */
@Entity(name = "Privilege")
@Table(name = "privilege")
@DynamicUpdate(true)
@DynamicInsert(true)
@Data
public class Privilege extends BaseEntity {

    /**
     * 权限名
     */
    private String privilegeName;

    /**
     * 链接
     */
    private String url;

    /**
     * 父权限id
     */
    private String parentPrivilegeId;

    /**
     * 备注
     */
    private String remark;
}

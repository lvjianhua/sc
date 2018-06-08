package org.sc.client;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色表
 * Created by Sonic Wang on 2017/4/26.
 */
@Data
public class Role{

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
    

    private String id;

    /** 创建时间 */
    private Date createTime;

    /** 删除类型*/
    private Integer del;
}

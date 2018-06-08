package org.sc.client;

import java.util.Date;

import lombok.Data;

/**
 * 权限表
 * Created by Sonic Wang on 2017/4/26.
 */
@Data
public class Privilege{

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
    

    private String id;

    /** 创建时间 */
    private Date createTime;

    /** 删除类型*/
    private Integer del;
}

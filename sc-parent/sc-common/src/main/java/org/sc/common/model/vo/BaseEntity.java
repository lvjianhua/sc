package org.sc.common.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * TODO
 * 
 * @author souler create by 17/04/20
 *
 * @version V1.0
 */
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	
	public static final String ID = "id";

    /**
     * serialVersionUID:TODO(主键id).
     * 
     * @since jdk1.8
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @ApiModelProperty(value = "编号")
    protected String id;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @Column(nullable = false, columnDefinition = "timestamp default now()")
    protected Date createTime;

    //** 修改时间 *//*
    @ApiModelProperty(value = "修改时间")
    @Column(nullable = false, columnDefinition = "timestamp default now()")
    protected Date updateTime;

    /** 删除类型{@link org.sc.common.utils.DelEnum} */
    @ApiModelProperty(value = "是否有效：0：有效，1：无效")
    @Column(nullable = false, columnDefinition = "integer default 0")
    protected Integer del;

}

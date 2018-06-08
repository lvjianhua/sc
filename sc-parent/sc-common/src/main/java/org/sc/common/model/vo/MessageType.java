/**
 * Project Name:ocean
 * File Name:FreightMode.java
 * Package Name:com.gfresh.ocean.module.table
 * Date:2017年4月12日上午11:23:10
 * Copyright (c) 2017, 上海极鲜网电子商务有限公司. All Rights Reserved.
 *
 */

package org.sc.common.model.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Embeddable;

import lombok.Data;

/**
 * 
 * 短信调用参数
 * 
 * @author: Souler create by:2017年7月12日 上午9:48:18
 * 
 * @version V1.0
 */
@Data
@Embeddable
public class MessageType {

    /** 发送方式：0短信，1邮件，2推送 */
    @ApiModelProperty(value = "发送方式")
    private Integer type;

    /** 用户id*/
    @ApiModelProperty(value = "用户id")
    private String userId;

    /** 信息发送码 ：0手机号，1邮箱，2推送码 */
    @ApiModelProperty(value = "信息吗")
    private String messageCode;

}

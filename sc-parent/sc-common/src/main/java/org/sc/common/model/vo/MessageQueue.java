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
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class MessageQueue {

    /** 场景id*/
    @ApiModelProperty(value = "场景id")
    private String senceId;

    /** 发送用户 */
    @ApiModelProperty(value = "发送用户")
    private List<String> users;

    /** 发送标题 */
    @ApiModelProperty(value = "场景id")
    private String title;

    /** 发送内容*/
    @ApiModelProperty(value = "场景id")
    private String content;

    /** 信息发送时间 */
    @ApiModelProperty(value = "信息码")
    private Date postTime;

    /** 信息发送参数 */
    @ApiModelProperty(value = "信息发送参数")
    private Map<String, String> messageMap;

}

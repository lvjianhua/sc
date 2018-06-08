/**
 * Project Name:ocean
 * File Name:FreightGradient.java
 * Package Name:com.gfresh.ocean.module.table
 * Date:2017年4月12日上午11:25:36
 * Copyright (c) 2017, 上海极鲜网电子商务有限公司. All Rights Reserved.
 *
 */

package org.sc.common.model.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 运费梯度
 * 
 * @author: tanqingbing create by:2017年4月12日 上午9:48:18
 * 
 * @version V1.0
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageConfig extends BaseEntity {

    /** 场景名 */
    @ApiModelProperty(value = "场景名")
    private String sceneName;

    /** 场景代码 */
    @ApiModelProperty(value = "场景代码")
    private String sceneCode;

    /** 场景类型：0其他类,1余额类,2物流类 */
    @ApiModelProperty(value = "场景类型：0余额，1物流")
    private Integer sceneType;

    /** 信息类型：0短信，1邮件，2推送 */
    @ApiModelProperty(value = "信息类型")
    private List<Integer> types;

    /** 发送标题 */
    @ApiModelProperty(value = "发送标题")
    private String title;

    /** 发送内容 */
    @ApiModelProperty(value = "发送内容")
    private String content;

    /** 模板签名 */
    @ApiModelProperty(value = "模板签名")
    private String signName;

    /** 模板内容 */
    @ApiModelProperty(value = "模板内容")
    private String template;

    /** 模板编号 */
    @ApiModelProperty(value = "模板编号")
    private String templateCode;

    /** 使用状态：0启用，1停用 */
    @ApiModelProperty(value = "使用状态")
    private Integer status;
}

package org.sc.common.alimns;

import lombok.Data;

/**
 * 阿里云消息服务，消息实体
 * Created by Webb Dong on 2017/6/19.
 */
@Data
public class AliMnsMessage {

    /**
     * 主题拥有者
     */
    private String topicOwner;

    /**
     * 主题名称
     */
    private String topicName;

    /**
     * 订阅者
     */
    private String subscriber;

    /**
     * 订阅名称
     */
    private String subscriptionName;

    private String msgId;

    /**
     * 消息内容
     */
    private String msgContent;

    private String msgMD5;

    /**
     * 发布时间
     */
    private String publishTime;

    private String msgTag;

}

package org.sc.common.model.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.sc.common.model.vo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * 订单费用表
 */

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class MessageLog extends BaseEntity {
	/** 消息的ID */
	private String messageId;
	/** 消息的类型(1:订单消息,2:产品消息,3:物流消息,4:发票消息,5:用户消息,6:店铺消息,7:团购消息) */
	private Integer messageType;
	/** */
	private Integer subType;
	/** 消息对象 */
	private Object messageBody;
}

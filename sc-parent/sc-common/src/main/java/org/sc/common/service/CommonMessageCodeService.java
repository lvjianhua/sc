package org.sc.common.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.sc.common.model.vo.MessageVo;

/**
 * 
 * @author shawn-gfresh create by:2017年11月30日 下午3:37:59
 * 
 * @version V1.0
 */
@FeignClient(name = "message-service", path = "/message/messageCode")
public interface CommonMessageCodeService {
	/**
	 * 发送消息
	 * 
	 * @param messageVo
	 * @return Boolean
	 * @author shawn-gfresh
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sendMessage")
	public Boolean sendMessage(@RequestBody MessageVo messageVo);
}

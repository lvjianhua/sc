package org.sc.common.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.sc.common.model.vo.MessageConfig;

/**
 * 
 * @author shawn-gfresh create by:2017年11月30日 下午3:36:58
 * 
 * @version V1.0
 */
@FeignClient(name = "message-service", path = "/message/messageConfig")
public interface CommonMessageConfigService {
	/**
	 * 查询对应的消息模板
	 * 
	 * @param sceneCode
	 * @return MessageConfig
	 * @author shawn-gfresh
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/queryMessageConfigByCode")
	public MessageConfig queryMessageConfigByCode(@RequestParam(value = "sceneCode", required = true) String sceneCode);
}

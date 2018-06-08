package org.sc.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.sc.common.model.vo.MessageConfig;
import org.sc.common.model.vo.MessageType;
import org.sc.common.model.vo.MessageVo;

import org.sc.common.service.CommonMessageCodeService;
import org.sc.common.service.CommonMessageConfigService;

public class SendEmailUtils {

	private static CommonMessageConfigService commonMessageConfigService;

	private static CommonMessageCodeService commonMessageCodeService;

	/**
	 * 发送邮件统一方法
	 * 
	 * @param emailTitle
	 *            邮件主题
	 * @param emailBody
	 *            邮件内容，可以试html格式的代码
	 * @param receiver
	 *            邮件接收人
	 * @return
	 * @throws IOException
	 */
	public static boolean sendEmail(String emailTitle, String emailBody, List<String> receiver) {
		if (commonMessageConfigService == null) {
			commonMessageConfigService = (CommonMessageConfigService) SpringContextUtils
					.getBean(CommonMessageConfigService.class);
		}

		if (commonMessageCodeService == null) {
			commonMessageCodeService = (CommonMessageCodeService) SpringContextUtils
					.getBean(CommonMessageCodeService.class);
		}
		// 发短信
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("emailBody", emailBody);
		// 获取信息部门的用户id
		MessageConfig configByCode = commonMessageConfigService.queryMessageConfigByCode("sms019");
		MessageVo messageVo = new MessageVo();
		messageVo.setSenceId(configByCode.getId());
		messageVo.setTitle(emailTitle);
		messageVo.setContent(emailBody);

		List<MessageType> messageList = new ArrayList<MessageType>();
		for (String email : receiver) {
			MessageType messageType = new MessageType();
			messageType.setType(1);
			messageType.setMessageCode(email);
			messageList.add(messageType);
		}
		messageVo.setMessageList(messageList);

		if (!commonMessageCodeService.sendMessage(messageVo)) {
			System.out.println("邮件发送失败>>>>>>>>>>>>邮件主题是:" + emailTitle);
		}

		return true;
	}

}

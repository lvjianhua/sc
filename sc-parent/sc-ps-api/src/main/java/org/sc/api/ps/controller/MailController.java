package org.sc.api.ps.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.sc.api.ps.model.Email;
import org.sc.api.ps.service.IMailService;
import org.sc.common.model.vo.Response;
import org.sc.common.utils.web.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "email",description = "发送邮件")
@RestController
@RequestMapping(value = "/api/ps/email")
public class MailController {
	@Autowired
	private IMailService mailService;
	
	@ApiOperation(value = "发送邮件测试")
	@RequestMapping(value="/send",method = RequestMethod.GET)
	public Response send(Email mail) {
		try {
			// 1.简单文本邮件发送:文字,html
			//mailService.sendTextWithHtml(mail);
			
			// 2.邮件中发送,嵌套图片
			//mailService.sendTextWithImg(mail);
			
			// 3.邮件中发送附件
			//mailService.sendWithAttament(mail);
			
			// 4.邮件中发送图片,文档
		    //mailService.sendWithAll(mail);
			
			// 5.邮件发送模板
			mailService.sendFreemarker(mail);
			
		} catch (Exception e) {
			e.printStackTrace();
			return  ResponseUtil.error();
		}
		return  ResponseUtil.ok();
	}
}

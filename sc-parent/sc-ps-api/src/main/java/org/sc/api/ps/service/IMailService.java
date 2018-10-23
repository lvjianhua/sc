package org.sc.api.ps.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

import org.sc.api.ps.model.Email;

import freemarker.template.TemplateException;

public interface IMailService {
	 /**
	  * 简单文本邮件发送
	  * 
	  * @param mail
	 * @throws MessagingException 
	  * @throws Exception
	  */
	 public void sendTextWithHtml(Email mail) throws MessagingException;
	 
	 /**
	  * 邮件中发送图片
	  * 
	  * @param mail
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	  */
	public void sendTextWithImg(Email mail) throws MessagingException, FileNotFoundException;

	/**
	 * 邮件中发送附件
	 * 
	 * @param mail
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 */
	public void sendWithAttament(Email mail) throws MessagingException, FileNotFoundException;

	/**
	 * 邮件中发送图片,附件
	 * 
	 * @param mail
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 */
	public void sendWithAll(Email mail) throws MessagingException, FileNotFoundException;

	/**
	 * 邮件发送模板
	 * 
	 * @param mail
	 * @throws TemplateException 
	 * @throws IOException 
	 * @throws MessagingException 
	 */
	public void sendFreemarker(Email mail) throws IOException, TemplateException, MessagingException;

}

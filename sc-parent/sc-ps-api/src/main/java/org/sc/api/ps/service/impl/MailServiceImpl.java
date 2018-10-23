package org.sc.api.ps.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.sc.api.ps.model.Email;
import org.sc.api.ps.service.IMailService;
import org.sc.api.ps.utils.email.Constants;
import org.sc.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailServiceImpl implements IMailService {
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	public static final String DEFALUT_ENCODING = "UTF-8";
	@Autowired
	private JavaMailSender sender;//执行者
	
	@Autowired
	public Configuration configuration;//freemarker
	
	@Value("${spring.mail.username}")
	public String USER_NAME;//发送者
	@Value("${server.path}")
	public String PATH;//发送者

	@Override
	public void sendTextWithHtml(Email mail) throws MessagingException{
		logger.info("发送邮件：{}",mail.getContent());
		// 创建邮件发送服务器
		MimeMessage mailMessage = sender.createMimeMessage();
		String[] ss = getEmailPage(mail.getEmail());
		if(ss != null){
			initMimeMessageHelper(mailMessage, ss, USER_NAME, mail.getSubject(), mail.getContent());
			sender.send(mailMessage);
			logger.info("发送邮件成功!");
		}
	}

	/**
	 * 封装邮件接收方
	 * 
	 * @param email：邮件实体对象
	 * @return
	 */
	private String[] getEmailPage(String email) {
		String[] str = new String[email.split(",").length];
		if(StringUtils.isEmpty(email)){
			return null;
		}	
		str = email.split(",");
		for(String ce:str){
			if(!isEmail(ce)){
				return null;	
			}
		}
		return str;
	}
	
	/**
	 * 校验邮箱的合法性
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

	private static MimeMessageHelper initMimeMessageHelper(MimeMessage mailMessage, String[] tos, 
			String from,String subject, String text) throws MessagingException {
		return initMimeMessageHelper(mailMessage, tos, from, subject, text, true, false, DEFALUT_ENCODING);
	}
	
	private static MimeMessageHelper initMimeMessageHelper(MimeMessage mailMessage, String[] tos, String from,
			String subject, String text, boolean isHTML, boolean multipart, String encoding) throws MessagingException {
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, multipart, encoding);
		messageHelper.setTo(tos);
		messageHelper.setFrom(from);
		messageHelper.setSubject(subject);
		// true 表示启动HTML格式的邮件
		messageHelper.setText(text, isHTML);
		return messageHelper;
	}
	
	@Override
	public void sendTextWithImg(Email mail) throws MessagingException, FileNotFoundException {
		logger.info("发送邮件：{}",mail.getContent());	
		// 创建邮件发送服务器
		MimeMessage mailMessage = sender.createMimeMessage();
		String[] ss = getEmailPage(mail.getEmail());
		if(ss != null){
			MimeMessageHelper messageHelper = initMimeMessageHelper(mailMessage, ss, USER_NAME, mail.getSubject(), mail.getContent(),true, true, "GBK");
			// 发送图片
			File file = ResourceUtils.getFile("classpath:static"
					+ Constants.SF_FILE_SEPARATOR + "image"
					+ Constants.SF_FILE_SEPARATOR + "springcloud.png");
			FileSystemResource img = new FileSystemResource(file);
			messageHelper.addInline("springcloud", img);
			sender.send(mailMessage);
			System.out.println("邮件发送成功..");
		}
	}
	
	@Override
	public void sendWithAttament(Email mail) throws MessagingException, FileNotFoundException {
		logger.info("发送邮件：{}",mail.getContent());
		// 创建邮件发送服务器
		MimeMessage mailMessage = sender.createMimeMessage();
		String[] ss = getEmailPage(mail.getEmail());
		if(ss != null){
			MimeMessageHelper messageHelper = initMimeMessageHelper(mailMessage, ss, USER_NAME, mail.getSubject(), mail.getContent(),true, true, "GBK");
			// 发送附件
			File file = ResourceUtils.getFile("classpath:static"
					+ Constants.SF_FILE_SEPARATOR + "file"
					+ Constants.SF_FILE_SEPARATOR + "abc.zip");
			FileSystemResource img = new FileSystemResource(file);
			messageHelper.addInline("springcloud", img);
			sender.send(mailMessage);
			System.out.println("邮件发送成功..");
		}
		
	}

	@Override
	public void sendWithAll(Email mail) throws MessagingException, FileNotFoundException {
		logger.info("发送邮件：{}",mail.getContent());
		MimeMessage mailMessage = sender.createMimeMessage();
		String[] ss = getEmailPage(mail.getEmail());
		if(ss != null){
			MimeMessageHelper messageHelper = initMimeMessageHelper(mailMessage, ss, USER_NAME, mail.getSubject(), mail.getContent(),true, true, DEFALUT_ENCODING);
			// 插入图片
			File fileImg = ResourceUtils.getFile("classpath:static"
					+ Constants.SF_FILE_SEPARATOR + "image"
					+ Constants.SF_FILE_SEPARATOR + "6.jpg");
			FileSystemResource img = new FileSystemResource(fileImg);
			messageHelper.addInline("image", img);
			// 插入附件
			File upFile = ResourceUtils.getFile("classpath:static"
					+ Constants.SF_FILE_SEPARATOR + "file"
					+ Constants.SF_FILE_SEPARATOR + "工作日志.docx");
			FileSystemResource file = new FileSystemResource(upFile);
			messageHelper.addAttachment("工作日志.docx", file);
			// 发送邮件
			sender.send(mailMessage);
			System.out.println("邮件发送成功..");
		}
	}

	@Override
	public void sendFreemarker(Email mail) throws IOException, TemplateException, MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		//这里可以自定义发信名称比如：SC-测试邮件模板
		helper.setFrom(USER_NAME,"SC-测试邮件模板");
		helper.setTo(mail.getEmail());
		helper.setSubject(mail.getSubject());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("mail", mail);
		model.put("path", PATH);
		Template template = configuration.getTemplate(mail.getTemplate()+".flt");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		helper.setText(text, true);	
		// 插入图片1
		File fileImg1 = ResourceUtils.getFile("classpath:static"
				+ Constants.SF_FILE_SEPARATOR + "image"
				+ Constants.SF_FILE_SEPARATOR + "4.jpg");
		FileSystemResource img1 = new FileSystemResource(fileImg1);
		helper.addInline("image1", img1);
		
		// 插入图片2
		File fileImg2 = ResourceUtils.getFile("classpath:static"
				+ Constants.SF_FILE_SEPARATOR + "image"
				+ Constants.SF_FILE_SEPARATOR + "5.jpg");
		FileSystemResource img2 = new FileSystemResource(fileImg2);
		helper.addInline("image2", img2);
		
		sender.send(message);
		mail.setContent(text);
	}
}


package org.sc.common.alimns;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sc.common.utils.SendEmailUtils;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.model.Message;

import lombok.Data;

/**
 * Created by Webb Dong on 2017/6/27.
 */
@Data
public class AliMnsQueueRunnable implements Runnable {

	private CloudQueue queue;

	private PopMessageCallback callback;

	protected final Logger logger = LogManager.getLogger(this.getClass());

	protected static List<String> userids = new ArrayList<String>();

	static {
		userids.add("itserver@gfresh.com");
	}

	@Override
	public void run() {
		String emailBody = "";
		String messageHd = "";
		String profileActive = System.getProperty("spring.profiles.active");
		boolean isProduction = false;
		if (profileActive.contains("production")) {
			isProduction = true;
		}
		while (true) {
			Integer curCount = 0;

			try {
				Message message = queue.popMessage();
				if (message != null) {
					curCount = message.getDequeueCount();
					emailBody = "消息处理失败,消息是：" + message.getMessageBody();
					messageHd = message.getReceiptHandle();

					boolean b = callback.onPopMessage(message);
					if (b) {
						// 删除已消费的队列
						queue.deleteMessage(message.getReceiptHandle());
					} else {						
						// 处理6000次（48小时以上了）还没有成功的话，就删除消息并发送邮件给相关人员
						if (curCount > 6000) {
							if (isProduction) {
								try {
									if (!SendEmailUtils.sendEmail("消息处理失败通知", emailBody, userids)) {
										logger.error("调用发送短信消息队列失败>>>>>>>>>>>>消息内容:" + emailBody);
									}
								} catch (Exception e1) {
									logger.error("调用发送短信消息队列失败>>>>>>>>>>>>消息内容:" + emailBody);
								}
							}
							queue.deleteMessage(messageHd);
						}
					}
				}
			} catch (Exception e) {
				logger.error("队列消费异常", e);
				// 处理6000次（48小时以上了）还没有成功的话，就删除消息并发送邮件给相关人员				
				if (curCount > 6000) {
					if (isProduction) {
						try {
							if (!SendEmailUtils.sendEmail("消息处理失败通知", emailBody, userids)) {
								logger.error("调用发送短信消息队列失败>>>>>>>>>>>>消息内容:" + emailBody);
							}
						} catch (Exception e1) {
							logger.error("调用发送短信消息队列失败>>>>>>>>>>>>消息内容:" + emailBody);
						}
					}
					queue.deleteMessage(messageHd);
				}
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public interface PopMessageCallback {
		boolean onPopMessage(Message message);
	}

}

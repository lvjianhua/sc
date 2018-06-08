package org.sc.common.interceptor;



import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;




import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * aop拦截日志
 * 
 * @author: shawn-gfresh create by:2017年4月19日 下午7:04:26
 * 
 * @version V1.0
 */
@Aspect
@Component
public class WebLogIntercept {
	private Logger logger = LogManager.getLogger(getClass());

	ThreadLocal<Long> startTime = new ThreadLocal<Long>();

	// 针对controller进行日志拦截
	@Pointcut("execution(public * cn.gfresh.*.*.controller..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("gfresh_anonymous")) {
				logger.info("USER_ID : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			}
		}
		logger.info("URL : " + request.getRequestURL().toString());
		try {
			if (!ObjectUtils.isEmpty(SecurityContextHolder.getContext())) {
				logger.info("USER_ID : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			}
		} catch (Exception e) {
			logger.info("USER_ID : ");
		}

		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		logger.debug("RESPONSE : " + ret);

		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
	}
}

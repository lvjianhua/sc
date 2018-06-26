package cn.gfresh.zuul.configuration;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cn.gfresh.zuul.controller.Response;
import cn.gfresh.zuul.utils.RequestCorsUtils;

/**
 * 
 * @author shawn-gfresh create by:2017年12月7日 下午7:45:43
 * 
 * @version V1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private Logger logger = LogManager.getLogger(getClass());

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		logger.error("the zuul server body:" + body + "httpStatus" + status);
		logger.error("the zuul server exception!", ex);

		ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, status, request);

		response.ok(RequestCorsUtils.DEFAULT_ERROR_MESSAGE);

		return response;
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Response jsonHandler(HttpServletRequest request, Exception e) {
		log(e, request);

		return new Response(RequestCorsUtils.DEFAULT_ERROR_MESSAGE, 500);
	}

	private void log(Exception ex, HttpServletRequest request) {
		logger.error("************************异常开始*******************************");
		logger.error(ex);
		logger.error("请求地址：" + request.getRequestURL());
		Enumeration enumeration = request.getParameterNames();
		logger.error("请求参数");
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement().toString();
			logger.error(name + "---" + request.getParameter(name));
		}

		StackTraceElement[] error = ex.getStackTrace();
		for (StackTraceElement stackTraceElement : error) {
			logger.error(stackTraceElement.toString());
		}
		logger.error("************************异常结束*******************************");
	}

}

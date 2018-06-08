package org.sc.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.sc.common.model.vo.Response;
import org.sc.common.utils.web.ResponseUtil;

/**
 * 错误跳转
 */
@RestController
public class GfreshErrorController implements ErrorController {
	protected static final Logger logger = LogManager.getLogger(GfreshErrorController.class);
	protected static final String ERROR_PATH = "/error";
	
	
	@ResponseBody
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public Response error(HttpServletRequest request, HttpServletResponse response) {
		logger.info("zuul filter exception:" + request.getAttribute("javax.servlet.error.exception") + ", message:"
				+ request.getAttribute("javax.servlet.error.message"));

		return ResponseUtil.error();
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}

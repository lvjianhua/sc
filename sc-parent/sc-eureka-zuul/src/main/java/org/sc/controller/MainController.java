package org.sc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sc.utils.RequestCorsUtils;

@Controller
public class MainController {
	public static final String DEFAULT_ERROR_MESSAGE = "您的网络不太给力啊!";

	private Logger logger = LogManager.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/loginOk", method = RequestMethod.GET)
	public Response login(HttpServletRequest request, HttpServletResponse response) {
		RequestCorsUtils.convertCorsHead(request, response);
		// 输出json数据 表示登录成功
		return new Response("login success!", 0);
	}

	@ResponseBody
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public Response error(HttpServletRequest request, HttpServletResponse response) {
		logger.info("zuul filter exception:" + request.getAttribute("javax.servlet.error.exception") + ", message:"
				+ request.getAttribute("javax.servlet.error.message"));

		RequestCorsUtils.convertCorsHead(request, response);

		return new Response(DEFAULT_ERROR_MESSAGE, 500);
	}

	@ResponseBody
	@RequestMapping(value = "/serverMaintenance", method = RequestMethod.POST)
	public synchronized Response serverMaintenance(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String password, @RequestParam String actionType) {
		RequestCorsUtils.convertCorsHead(request, response);

		if (password.equals("qwe123Gfresh")) {
			if (actionType.equals("start")) {
				RequestCorsUtils.IS_MAINTENANCE = true;
			} else if (actionType.equals("end")) {
				RequestCorsUtils.IS_MAINTENANCE = false;
			}
		}

		return new Response("success", 0);
	}
}

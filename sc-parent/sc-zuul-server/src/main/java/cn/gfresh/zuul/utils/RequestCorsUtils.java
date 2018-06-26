package cn.gfresh.zuul.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.gfresh.zuul.configuration.ZuulProp;
import cn.gfresh.zuul.controller.MainController;
import cn.gfresh.zuul.controller.Response;

public class RequestCorsUtils {
	public static final String DEFAULT_ERROR_MESSAGE = "您的网络不太给力啊!";

	// 是否处于维护状态 true:是 false:否
	public static Boolean IS_MAINTENANCE = false;

	public static void convertCorsHead(HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Origin"))) {
			if (StringUtils.isEmpty(request.getHeader("Origin"))) {
				response.setHeader("Access-Control-Allow-Origin", "*");
			} else if (checkOrigin(getOrigins(), request.getHeader("Origin"))) {
				System.out.println("origin is=====================" + request.getHeader("Origin"));
				response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			}
		}
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Max-Age"))) {
			response.setHeader("Access-Control-Max-Age", (30 * 24 * 60 * 60) + "");
		}
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Methods"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		}
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Headers"))) {
			response.setHeader("Access-Control-Allow-Headers",
					"Origin, X-Requested-With, Content-Type, Accept, accept-encoding, origin, accept-language");
		}
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Credentials"))) {
			response.setHeader("Access-Control-Allow-Credentials", "true");
		}
		if (!StringUtils.isEmpty(request.getHeader("Accept")) && request.getHeader("Accept").equals("*/*")) {
			response.setHeader("Accept", "application/json, text/plain, */*");
		}
	}

	public static void out(HttpServletRequest request) {
		Enumeration<String> enums = request.getHeaderNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement();
			System.out.println("i am get the request heads=========================" + key + "++++++++++++++++++++++"
					+ request.getHeader(key));
		}
	}

	public static void out(HttpServletResponse response) {
		Collection<String> headerNames = response.getHeaderNames();
		Iterator<String> iter = headerNames.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println("i am get the response heads=========================" + key + "++++++++++++++++++++++"
					+ response.getHeader(key));
		}
	}

	public static Integer oAuthTimes() {
		ZuulProp zuulProp = (ZuulProp) SpringContextUtils.getBean(ZuulProp.class);
		return zuulProp.getOAuthTimes();
	}

	public static String[] getOrigins() {
		ZuulProp zuulProp = (ZuulProp) SpringContextUtils.getBean(ZuulProp.class);
		return zuulProp.getAllowOrigin().split(",");
	}

	public static boolean checkOrigin(String[] origins, String origin) {
		for (String s : origins) {
			if (origin.contains(s)) {
				return true;
			}
		}

		return false;
	}

	public static String getScheme(String url) {
		String ip = "\\w.*://";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(url);

		while (matcher.find()) {
			return matcher.group(0).replaceAll("://", "");
		}
		return "";
	}

	public static void outWhInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 服务器处于维护状态
		if (IS_MAINTENANCE) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");

			RequestCorsUtils.convertCorsHead(request, response);
			Response redirectResponse = new Response("", "服务器维护中,请稍后重试", -3);
			response.getWriter().println(JSON.toJSONString(redirectResponse));

			return;
		}
	}

	public static void main(String[] args) {
		String s = ".gfresh.cn,//gfresh.cn";

		String[] ss = s.split(",");

		System.out.println(checkOrigin(ss, "https://zuul.m.gfresh.cn"));
	}
}

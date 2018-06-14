/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;

import org.sc.configuration.ZuulProp;
import org.sc.controller.Response;
import org.sc.utils.RequestCorsUtils;
import org.sc.utils.SpringContextUtils;

/**
 * Simple implementation of <tt>RedirectStrategy</tt> which is the default used
 * throughout the framework.
 * 
 * RedirectStrategy是Spring提供的一个客户端跳转的工具类。
 * 
 * @author Luke Taylor
 * @since 3.0
 */
public class DefaultRedirectStrategy implements RedirectStrategy {

	protected final Log logger = LogFactory.getLog(getClass());

	private boolean contextRelative;

	private static ZuulProp zuulProp;

	/**
	 * Redirects the response to the supplied URL.
	 * <p>
	 * If <tt>contextRelative</tt> is set, the redirect value will be the value
	 * after the request context path. Note that this will result in the loss of
	 * protocol information (HTTP or HTTPS), so will cause problems if a redirect is
	 * being performed to change to HTTPS, for example.
	 */
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
		redirectUrl = response.encodeRedirectURL(redirectUrl);

		if (logger.isDebugEnabled()) {
			logger.debug("Redirecting to '" + redirectUrl + "'");
		}

		if (ObjectUtils.isEmpty(zuulProp)) {
			zuulProp = (ZuulProp) SpringContextUtils.getBean(ZuulProp.class);
		}

		// 替换协议
		if (RequestCorsUtils.getScheme(redirectUrl) != "") {
			redirectUrl = redirectUrl.replaceAll(RequestCorsUtils.getScheme(redirectUrl) + "://",
					zuulProp.getScheme() + "://");
			if (redirectUrl.endsWith("/login")) {
				response.setCharacterEncoding("UTF-8"); 
				response.setContentType("application/json; charset=utf-8"); 
				
				RequestCorsUtils.convertCorsHead(request, response);
				Response redirectResponse = new Response("", "need login", -1);
				response.getWriter().println(JSON.toJSONString(redirectResponse));

				return;
			}
			logger.debug("the 302 url is :" + redirectUrl);
		}

		RequestCorsUtils.convertCorsHead(request, response);

		logger.debug("redirect url --------------------------:" + redirectUrl);

		response.sendRedirect(redirectUrl);
	}

	protected String calculateRedirectUrl(String contextPath, String url) {
		if (!UrlUtils.isAbsoluteUrl(url)) {
			if (isContextRelative()) {
				return url;
			} else {
				return contextPath + url;
			}
		}

		// Full URL, including http(s)://

		if (!isContextRelative()) {
			return url;
		}

		// Calculate the relative URL from the fully qualified URL, minus the last
		// occurrence of the scheme and base context.
		url = url.substring(url.lastIndexOf("://") + 3); // strip off scheme
		url = url.substring(url.indexOf(contextPath) + contextPath.length());

		if (url.length() > 1 && url.charAt(0) == '/') {
			url = url.substring(1);
		}

		return url;
	}

	/**
	 * If <tt>true</tt>, causes any redirection URLs to be calculated minus the
	 * protocol and context path (defaults to <tt>false</tt>).
	 */
	public void setContextRelative(boolean useRelativeContext) {
		this.contextRelative = useRelativeContext;
	}

	/**
	 * Returns <tt>true</tt>, if the redirection URL should be calculated minus the
	 * protocol and context path (defaults to <tt>false</tt>).
	 */
	protected boolean isContextRelative() {
		return contextRelative;
	}
}

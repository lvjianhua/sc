/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. */
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
import org.sc.configuration.AuthProp;
import org.sc.utils.RequestCorsUtils;
import org.sc.utils.SpringContextUtils;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


/**
 * Simple implementation of <tt>RedirectStrategy</tt> which is the default used
 * throughout the framework.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public class DefaultRedirectStrategy implements RedirectStrategy {

	protected final Log logger = LogFactory.getLog(getClass());

	private boolean contextRelative;

	private static AuthProp authPro;

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
			logger.debug("Redirecting to '" + redirectUrl + "'=========" + request.getRequestURL());
		}

		if (ObjectUtils.isEmpty(authPro)) {
			authPro = (AuthProp) SpringContextUtils.getBean(AuthProp.class);
		}

		if (redirectUrl.contains(authPro.getZuulIp() + ":" + authPro.getZuulPort())) {
			redirectUrl = redirectUrl.replaceAll(authPro.getZuulIp() + ":" + authPro.getZuulPort(),
					authPro.getZuulDns());
		}
		if (redirectUrl.contains(authPro.getZuulIp())) {
			redirectUrl = redirectUrl.replaceAll(authPro.getZuulIp(), authPro.getZuulDns());
		}

		String redirectIp = RequestCorsUtils.isContainIp(redirectUrl);
		if (!StringUtils.isEmpty(redirectIp)) {
			redirectUrl = redirectUrl.replaceAll(redirectIp, authPro.getZuulDns());
		} else {
			redirectIp = RequestCorsUtils.isContainPortIp(redirectUrl);

			if (!StringUtils.isEmpty(redirectIp)) {
				redirectUrl = redirectUrl.replaceAll(redirectIp, authPro.getZuulDns());
			}
		}

		if (!redirectUrl.startsWith("http")) {
			redirectUrl = request.getScheme() + "://" + authPro.getZuulDns() + redirectUrl;
		}

		// 替换协议
		if (RequestCorsUtils.getScheme(redirectUrl) != "") {
			redirectUrl = redirectUrl.replaceAll(RequestCorsUtils.getScheme(redirectUrl) + "://",
					authPro.getScheme() + "://");
		}

		/*
		 * if(StringUtils.isEmpty(request.getHeader("Origin"))){
		 * System.out.println("origin is null");
		 * response.setHeader("Access-Control-Allow-Origin", "*"); }else{
		 * System.out.println("origin is=====================" +
		 * request.getHeader("Origin"));
		 * response.setHeader("Access-Control-Allow-Origin",
		 * request.getHeader("Origin")); }
		 * response.setHeader("Access-Control-Allow-Methods",
		 * "POST, GET, OPTIONS, DELETE");
		 * response.setHeader("Access-Control-Allow-Headers",
		 * "Origin, X-Requested-With, Content-Type, Accept");
		 * response.setHeader("Access-Control-Allow-Credentials","true");
		 */

		RequestCorsUtils.convertCorsHead(request, response);

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

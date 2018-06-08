package org.sc.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;

import org.sc.configuration.ZuulProp;
import org.sc.utils.RequestCorsUtils;
import org.sc.utils.SpringContextUtils;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

	private ZuulProp zuulProp;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// 清除授权信息
		if (authentication instanceof OAuth2Authentication) {
			((OAuth2Authentication) authentication).eraseCredentials();
		}

		if (ObjectUtils.isEmpty(zuulProp)) {
			zuulProp = (ZuulProp) SpringContextUtils.getBean(ZuulProp.class);
		}
		// 清空登录信息
		new SecurityContextLogoutHandler().logout(request, response, null);

		request.getSession().invalidate();

		RequestCorsUtils.convertCorsHead(request, response);

		Cookie cook2 = new Cookie("_c_a_", "");
		cook2.setPath("/uaa/");
		if (zuulProp.getScheme().equalsIgnoreCase("https")) {
			cook2.setSecure(true);
		}

		response.addCookie(cook2);

		response.getWriter().println("{\"code\":0,\"message\":\"logout success\",\"data\":{}}");
	}

}

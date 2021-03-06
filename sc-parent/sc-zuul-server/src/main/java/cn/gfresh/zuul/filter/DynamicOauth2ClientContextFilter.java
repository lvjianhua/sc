package cn.gfresh.zuul.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import cn.gfresh.zuul.configuration.ZuulProp;
import cn.gfresh.zuul.controller.MainController;
import cn.gfresh.zuul.utils.RequestCorsUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Webb Dong on 2017/4/7.
 */
public class DynamicOauth2ClientContextFilter extends OAuth2ClientContextFilter {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private ZuulProp zuulProp;

	@Override
	protected void redirectUser(UserRedirectRequiredException e, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (RequestCorsUtils.IS_MAINTENANCE) {
			RequestCorsUtils.outWhInfo(request, response);
			return;
		}

		String redirectUri = e.getRedirectUri();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(redirectUri);
		Map<String, String> requestParams = e.getRequestParams();
		for (Map.Entry<String, String> param : requestParams.entrySet()) {
			builder.queryParam(param.getKey(), param.getValue());
		}

		if (e.getStateKey() != null) {
			builder.queryParam("state", e.getStateKey());
		}

		String url = getBaseUrl(request) + builder.build().encode().toUriString();

		RequestCorsUtils.convertCorsHead(request, response);

		if (request.getMethod().toUpperCase().equals("OPTIONS")) {
			response.getWriter().println();
			return;
		}

		// 替换协议
		if (url.startsWith("http://")) {
			url = url.replaceAll("http://", zuulProp.getScheme() + "://");
		}

		this.redirectStrategy.sendRedirect(request, response, url);
	}

	@Override
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	private String getBaseUrl(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();

		return url.substring(0, url.length() - request.getRequestURI().length() + request.getContextPath().length());
	}
}

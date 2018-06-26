package cn.gfresh.zuul.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import cn.gfresh.zuul.utils.RequestCorsUtils;

/**
 * Created by admin on 2017/5/11.
 */
@Component
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
	private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

	@Autowired
	private FilterInvocationSecurityMetadataSource myFilterSecurityMetadataSource;
	@Autowired
	private MyAccessDecisionManager myAccessDecisionManager;
	@Autowired
	private AuthenticationManager authenticationManager;

	private boolean observeOncePerRequest = true;

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.myFilterSecurityMetadataSource;
	}

	@PostConstruct
	public void init() {
		super.setAuthenticationManager(authenticationManager);
		super.setAccessDecisionManager(myAccessDecisionManager);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(req, res, chain);

		if (RequestCorsUtils.IS_MAINTENANCE && !fi.getHttpRequest().getRequestURI().endsWith("/serverMaintenance")) {
			RequestCorsUtils.outWhInfo(fi.getHttpRequest(), fi.getHttpResponse());
			return;
		}

		invoke(fi);
	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
				&& observeOncePerRequest) {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} else {
			if (fi.getRequest() != null) {
				fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}

			InterceptorStatusToken token = super.beforeInvocation(fi);

			try {
				fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			} finally {
				super.finallyInvocation(token);
			}

			super.afterInvocation(token, null);
		}

	}

	@Override
	public void destroy() {
		System.out.println("filter===========================end");
	}

	public SecurityMetadataSource getMyFilterSecurityMetadataSource() {
		return myFilterSecurityMetadataSource;
	}

	public void setMyFilterSecurityMetadataSource(SecurityMetadataSource myFilterSecurityMetadataSource) {
		this.myFilterSecurityMetadataSource = (MyFilterSecurityMetadataSource) myFilterSecurityMetadataSource;
	}
}

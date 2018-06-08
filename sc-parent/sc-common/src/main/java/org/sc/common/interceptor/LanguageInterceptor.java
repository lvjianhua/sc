package org.sc.common.interceptor;
import org.apache.commons.lang.StringUtils;
import org.sc.common.enmus.LanguageEnum;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String languageGroup = request.getHeader("accept-language");

		String language = "";
		if (StringUtils.isNotBlank(languageGroup)) {
			language = languageGroup.split(",")[0];
		}

		if (StringUtils.isBlank(language)) {
			language = LanguageEnum.en.language;
		}

		language.replaceAll("-","_");
		request.getSession().setAttribute("language", language);
		
		return super.preHandle(request, response, handler);
	}
}

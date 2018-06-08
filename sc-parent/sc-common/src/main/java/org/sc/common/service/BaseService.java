package org.sc.common.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.sc.common.enmus.LanguageEnum;
import org.sc.common.utils.web.WebContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Webb Dong on 2017/3/28.
 */
public abstract class BaseService {
    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    protected Mapper mapper;
    
    protected String getLoginUserId() {
    	String profileActive = "test";//System.getProperty("spring.profiles.active");
		this.logger.info("profileActive:" + profileActive);
		if ("development".equals(profileActive) || "test".equals(profileActive)) {
			return "1";
		} else {
			try {
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("gfresh_anonymous")) {
					return null;
				}
				return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
			} catch (Exception e) {
				return null;
			}
		}
    }

	protected String getLanguage() {
		String language = (String) WebContextUtils.getSession().getAttribute("language");

		try {
			LanguageEnum languageEnum = Enum.valueOf(LanguageEnum.class, language);
		} catch (Exception e) {
			return LanguageEnum.en.language;
		}
		return language;
	}
}

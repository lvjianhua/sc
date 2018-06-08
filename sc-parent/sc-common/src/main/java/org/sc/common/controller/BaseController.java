package org.sc.common.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sc.common.enmus.ErrorCode;
import org.sc.common.enmus.LanguageEnum;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.common.model.vo.Response;
import org.sc.common.model.vo.User;
import org.sc.common.service.BaseUserService;
import org.sc.common.utils.I18nMessageSource;
import org.sc.common.utils.SpringContextUtils;
import org.sc.common.utils.StringUtils;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.common.utils.web.WebContextUtils;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.http.StringSplitUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 */
public abstract class BaseController {
    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Prop国际化工具类
     */
    @Autowired
    protected I18nMessageSource i18nMessageSource;

    protected BaseUserService baseUserService;

    /**
     * 统一异常处理
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Response exceptionHandler(HttpServletRequest request, Exception ex) {
        logger.error("捕获异常,method: " + request.getRequestURI(), ex);
        if (ex instanceof ServiceRunTimeException) {
            if (((ServiceRunTimeException) ex).getErrorCode() != null) {
                ServiceRunTimeException srte = (ServiceRunTimeException) ex;
                return ResponseUtil.error(srte.getErrorCode());
            } else {
                logger.error(ex.getMessage(), ex);
            }
            return ResponseUtil.error(ex.getMessage());
        }
        if (ex instanceof HystrixRuntimeException) {
            if (ex.getCause().getMessage().contains("Load balancer does not have available server for client")
                    || ex.getCause().getMessage().contains("Connection refused")) {
                return ResponseUtil.error(ErrorCode.WRONG_ONSERVER);
            }
            return ResponseUtil.error(ex.getCause().getMessage());
        }
        return ResponseUtil.error(ErrorCode.WRONG_ONSERVER);
    }

    public String getLoginUserId() {
        String profileActive = System.getProperty("spring.profiles.active");
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

    public String getLoginUserId(String openId) {
        // 根据openId查询用户模块 返回用户id
        if (ObjectUtils.isEmpty(baseUserService)) {
            baseUserService = (BaseUserService) SpringContextUtils.getBean(BaseUserService.class);
        }

        User user = new User();
        user.setUserName(openId);

        user = baseUserService.login(user);

        if (ObjectUtils.isEmpty(user)) {
            return null;
        }

        return user.getId();
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

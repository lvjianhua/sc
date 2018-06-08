package org.sc.common.utils.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * web环境工具类，可以获取HttpServletRequest，HttpSession，ServletContext的对象
 * @author lvjh
 */
public class WebContextUtils {
	/**
	 * 当前登录用户的Session标识Key
	 */
	public static final String CURRENT_SESSION_USER_IDENTITY = "CURRENT_SESSION_USER";

	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return getServletRequestAttributes().getRequest();
	}
	
	/**
	 * 获取ServletContext对象
	 * @return
	 */
	public static ServletContext getServletContext() {
		return getServletRequestAttributes().getRequest().getSession().getServletContext();
	}
	
	/**
	 * 获取HttpSession对象
	 * @return
	 */
	public static HttpSession getSession() {
		return getServletRequestAttributes().getRequest().getSession();
	}
	
	/**
	 * 获取当前登录的用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCurrentSessionUser() {
		return (T) getSession().getAttribute(CURRENT_SESSION_USER_IDENTITY);
	}
	
	/**
	 * 移除当前登录的用户
	 */
	public static void removeCurrentSessionUser() {
		getSession().removeAttribute(CURRENT_SESSION_USER_IDENTITY);
	}
	
	/**
	 * 将当前登录的用户存入session中
	 * @param user
	 */
	public static void putCurrentUserToSession(Object user) {
		getSession().setAttribute(CURRENT_SESSION_USER_IDENTITY, user);
	}
	
	private WebContextUtils() {}
	
	/**
	 * 获取ServletRequestAttributes对象
	 * @return
	 */
	private static ServletRequestAttributes getServletRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}
}


package cn.gfresh.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gfresh.auth.configuration.Response;

/**
 * Created by Webb Dong on 2017/4/11.
 */
@Controller
public class LoginApiController {
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Response login(HttpServletRequest request, CsrfToken token) {
		Response response = new Response(token, "need login",  -1);
		Object obj = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if(!ObjectUtils.isEmpty(obj)){
			response.setMessage(((AuthenticationException)obj).getMessage());
			
			// 返回完错误后 清空
			request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		
		return response;
	}
	
	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("X-Frame-Options", "ALLOW-FROM " + request.getRequestURI());
		return "login";
	}*/

	/*@RequestMapping(value = "/oauth/confirm_access", method = RequestMethod.GET)
	public String confirmAccess() {
		return "redirect_post";
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/oauth/confirm_access", method = RequestMethod.GET)
	public Response confirmAccess(HttpServletRequest request, CsrfToken token) {
		
		Response response = new Response(token, "need login",  -2);
		Object obj = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if(!ObjectUtils.isEmpty(obj)){
			response.setMessage(((AuthenticationException)obj).getMessage());
		}
		
		return response;
	}
}

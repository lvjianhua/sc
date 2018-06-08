package org.sc.security;

import org.sc.configuration.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;




import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by admin on 2017/5/10.
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
        //SysUser userDetails = (SysUser) authentication.getPrincipal();
       /* Set<SysRole> roles = userDetails.getSysRoles();*/
        //输出登录提示信息
        //System.out.println("管理员 " + userDetails.getName() + " 登录");

        System.out.println("login success!");
        //输出json数据 表示登录成功
        Response res = new Response("login success!", 0);
        
        if(StringUtils.isEmpty(request.getHeader("Origin"))){
			System.out.println("origin is null");
			response.setHeader("Access-Control-Allow-Origin", "*");
        }else{
        	System.out.println("origin is=====================" + request.getHeader("Origin"));
        	response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }
        
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials","true");
        
        redirectStrategy.sendRedirect(request, response, "../loginOk");
    }
}

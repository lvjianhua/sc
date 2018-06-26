package org.sc.security;



import java.util.Collection;

import org.sc.client.UserClient;
import org.sc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * AuthenticationProvider 提供用户UserDetails的具体验证方式，在这里可以自定义用户密码的加密、验证方式等等。
 * Created by admin on 2017/5/11.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private UserClient userClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        //password加密 和验证码验证等信息可以在此处体现
        User loginUser = new User();
        loginUser.setUserName(username);
        loginUser.setPassword(password);
        Object returnObj = userClient.login(loginUser);
        
        String tmpStr = JSON.toJSONString(returnObj);
        
        JSONObject json = JSON.parseObject(tmpStr);
        UserDetails user = null;
        
        if(!ObjectUtils.isEmpty(json.getInteger("code")) && json.getInteger("code") == 0){
        	String data = json.getString("data");
        	JSONObject tmpJson = JSON.parseObject(data);
        	System.out.println("login is is ===================" + tmpJson.getString("id"));
        	//加载用户权限列表
            user = myUserDetailsService.loadUserByUsername(tmpJson.getString("id"));
        }else{
        	throw new BadCredentialsException(json.getString("message"));
        }
        
        /*if(user == null){
            throw new BadCredentialsException("Username not found.");
        }

        //加密过程在这里体现
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }*/

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}

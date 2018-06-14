package org.sc.configuration;


import org.sc.security.LoginSuccessHandler;
import org.sc.security.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * oauth核心配置类:权限配置类
 * 
 */
@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider gfreshAuthenticationProvider;
    /*@Autowired
    private MyCorsFilter myCorsFilter;*/
    
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().disable();
        http//.addFilterBefore(myCorsFilter, FilterSecurityInterceptor.class)
                .authorizeRequests().anyRequest().permitAll().and()
                //.authorizeRequests().antMatchers("/oauth/**").permitAll().anyRequest().permitAll().and()
        		.formLogin().loginPage("/login").permitAll()
                .and()
                .headers().frameOptions().disable()
                //.successHandler(loginSuccessHandler())
                //.and()
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 重写此方法 对请求的参数方法进行过滤
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(gfreshAuthenticationProvider);
        //auth.parentAuthenticationManager(mySecurityFilter.getAuthenticationManager());
    }

    /**
     * 登录成功之后的处理handler
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}

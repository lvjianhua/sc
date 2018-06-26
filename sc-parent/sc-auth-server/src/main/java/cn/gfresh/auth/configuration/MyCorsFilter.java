package cn.gfresh.auth.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import cn.gfresh.auth.utils.RequestCorsUtils;

/**
 * 跨域请求过滤
 * @author shawn
 *
 */
@Component
public class MyCorsFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		/*if(StringUtils.isEmpty(request.getHeader("Origin"))){
			System.out.println("origin is null");
			response.setHeader("Access-Control-Allow-Origin", "*");
        }else{
        	System.out.println("origin is=====================" + request.getHeader("Origin"));
        	response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials","true");*/
		
		RequestCorsUtils.convertCorsHead(request, response);
        
        chain.doFilter(req, res);
	}
	
	@Override
	public void destroy() {
		
	}
}

package cn.gfresh.auth.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gfresh.auth.configuration.Response;


/**
 * 
 * @ClassName:  GlobalExceptionHandler   
 * @Description:全局异常处理
 * @date:   2017年3月15日 上午10:46:25   
 *
 */
//@ControllerAdvice
class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";
    private Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);
    
    @ResponseBody
    @ExceptionHandler(value = MyLoginException.class)
    public Response defaultErrorHandler(HttpServletRequest req, MyLoginException e) throws Exception {
        LOG.error(e.getMessage(),e);
        return new Response(e.getMessage(), 1);
    }
}

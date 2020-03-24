package com.xsh.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : xsh
 * @create : 2020-02-06 - 21:50
 * @describe: @ControllerAdvice 拦截所有@Controller注解的控制类
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);
        //判断异常是否自定义被指定，如果被指定则不为空，抛出被指定异常
        if (AnnotationUtils.findAnnotation(e.getClass(),
                ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mv=new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("Exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}

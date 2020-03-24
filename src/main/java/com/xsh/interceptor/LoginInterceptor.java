package com.xsh.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : xsh
 * @create : 2020-02-08 - 1:09
 * @describe: 登录拦截器，防止未登录状态下直接输入地址访问后台
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //判断session中是否有user,如果为空则是未登录状态
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin"); //重定向到admin首页
            return false;
        }
        return true;
    }
}

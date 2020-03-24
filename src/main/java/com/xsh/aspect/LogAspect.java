package com.xsh.aspect;

import com.xsh.util.GetAddressByIpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author : xsh
 * @create : 2020-02-06 - 23:32
 * @describe: 日志切面类
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.xsh.web.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("------doBefore------");
        //获取访问者ip地址,访问的url,访问的方法和参数

        //todo
/*        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        String address = GetAddressByIpUtils.ipToAddress(ip);
        Object[] args =joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
        logger.info("访问者地址：{}",address);
        logger.info("Request : {}",requestLog);*/
    }

    @After("log()")
    public void doAfter(){
        logger.info("------doAfter------");
    }

    @AfterReturning(pointcut = "log()",returning = "result")
    public void doAfterReturn(Object result){
        logger.info("Result : {}",result);
    }

    public class RequestLog{
        private String url;
        private String ip;
        private String ClassMethod;
        private  Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            ClassMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", ClassMethod='" + ClassMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

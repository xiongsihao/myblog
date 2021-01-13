package com.xsh.web;

import com.alibaba.fastjson.JSONObject;
import com.xsh.annotations.CheckRepeatRequest;
import com.xsh.util.GetAddressByIpUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.xsh.util.GetAddressByIpUtils.getClientIPForNginx;

/**
 * @author : xsh
 * @create : 2021-01-12 - 20:53
 * @describe:
 */
@RestController
public class TestController {

    @RequestMapping("/testRepeatRequest")
    @CheckRepeatRequest
    public Object testRepeatRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        //String key = sessionId + "-" + request.getServletPath();//session可以伪造，不使用session作为key
        String ip = getClientIPForNginx(request);
        String ipInfo = GetAddressByIpUtils.getInfoByIp(ip);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("sessionId",sessionId);
        jsonObject.put("ip",ip);
        //JSONObject ipInfoJson = JSONObject.parseObject(new String(ipInfo));
        jsonObject.put("ipInfo",ipInfo);
        jsonObject.put("requestPath",request.getServletPath());
        jsonObject.put("message","访问成功");
        return jsonObject;
    }

    @RequestMapping("/isRepeatRequest")
    @CheckRepeatRequest
    public Object isRepeatRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        //String key = sessionId + "-" + request.getServletPath();//session可以伪造，不使用session作为key
        String ip = getClientIPForNginx(request);
        String ipInfo = GetAddressByIpUtils.getInfoByIp(ip);
        String response = "因短时间内频繁访问，ip已被拉黑，拒绝访问;"+ipInfo;

        return response;
    }
}

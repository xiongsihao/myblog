package com.xsh.web;

import com.alibaba.fastjson.JSONObject;
import com.xsh.handler.StateErrorException;
import com.xsh.pojo.QQInfo;
import com.xsh.service.QqService;
import com.xsh.service.QqServiceImpl;
import com.xsh.util.QQHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author xsh
 * @create 2020-03-15 20:32
 */
@Controller
public class QQController {

    @Autowired
    private QqService qqService;

    @Value("${qq.oauth.http}")
    private String http;
    @Value("${qq.appid}")
    private String APPID;
    @Value("${qq.appkey}")
    private String APPKEY;
    @Value("${cookie.time}")
    private Integer time;
    /**
     * 发起请求
     * @param session
     * @return
     */
    @GetMapping("/qq/oauth")
    public String qq(HttpSession session){
        //QQ互联中的回调地址
        String backUrl = http + "/qq/callback";

        //用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        session.setAttribute("state",uuid);

        //Step1：获取Authorization Code
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code"+
                "&client_id=" + APPID +
                "&redirect_uri=" + URLEncoder.encode(backUrl) +
                "&state=" + uuid;

        return "redirect:" + url;
    }

    /**
     * QQ回调
     * @param request
     * @return
     */
    @GetMapping("/qq/callback")
    public String qqcallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        //qq返回的信息：http://graph.qq.com/demo/index.jsp?code=9A5F************************06AF&state=test
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String uuid = (String) session.getAttribute("state");
        if(uuid != null){
            if(!uuid.equals(state)){
                throw new StateErrorException("QQ,state错误");
            }
        }

        //Step2：通过Authorization Code获取Access Token
        String backUrl = http + "/qq/callback";
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code"+
                "&client_id=" + APPID +
                "&client_secret=" + APPKEY  +
                "&code=" + code +
                "&redirect_uri=" + backUrl;

        String access_token = QQHttpClient.getAccessToken(url);

        //Step3: 获取回调后的 openid 值
        url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;
        String openid = QQHttpClient.getOpenID(url);

        //Step4：获取QQ用户信息
        url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                "&oauth_consumer_key="+APPID +
                "&openid=" + openid;

        JSONObject jsonObject = QQHttpClient.getUserInfo(url);

        //解决关闭浏览器就退出登录的bug
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath(request.getContextPath()+"/");
        cookie.setMaxAge(time);//设置cookie的生命周期为十天，单位秒。 60*60*24*10
        response.addCookie(cookie);

        //也可以放到Redis和mysql中
        session.setAttribute("openid",openid);  //openid,用来唯一标识qq用户
        session.setAttribute("nickname",removeNonBmpUnicode((String)jsonObject.get("nickname"))); //QQ名，去除网名中的特殊表情
        session.setAttribute("avatar",(String)jsonObject.get("figureurl_qq")); //QQ头像URL
        session.setAttribute("gender",(String)jsonObject.get("gender"));
        session.setAttribute("year",(String)jsonObject.get("year"));
        session.setAttribute("province",(String)jsonObject.get("province"));
        session.setAttribute("city",(String)jsonObject.get("city"));
        session.setAttribute("loginStatus","true");
        session.setAttribute("loginType",1);//登录方式,1 QQ，2 github
        //session.setAttribute("total",jsonObject.toJSONString());
        //System.out.println(jsonObject.toJSONString());

        /*        for(String str:jsonObject.keySet()){
            System.out.println(str + ":" +jsonObject.get(str));
        }*/
        QQInfo queryInfo = qqService.findQQuser(openid);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SS毫秒 Ea z时区");
        if(queryInfo == null){
            session.setAttribute("firstLoginTime", simpleDateFormat.format(new Date()));
        }else{
            //存在此用户，数据从数据库中取而不是取QQ回调的数据
            session.setAttribute("firstLoginTime",queryInfo.getLogintime());
            session.setAttribute("avatar",queryInfo.getAvatar());
        }
        return "redirect:/qqinfo";
    }

    @GetMapping("/qqinfo")
    public String home(HttpServletRequest request,HttpSession session, Model model){
            String loginStatus = (String) session.getAttribute("loginStatus");
            if (loginStatus != null) {
                String openid = (String) session.getAttribute("openid");
                String nickname = (String) session.getAttribute("nickname");
                String avatar = (String) session.getAttribute("avatar");
                String gender = (String) session.getAttribute("gender");
                String year = (String) session.getAttribute("year");
                String province = (String) session.getAttribute("province");
                String city = (String) session.getAttribute("city");
                String total = (String) session.getAttribute("total");
                String firstLoginTime =(String)session.getAttribute("firstLoginTime");

                model.addAttribute("openid",openid);
                model.addAttribute("nickname",nickname);
                model.addAttribute("avatar",avatar);
                model.addAttribute("gender",gender);
                model.addAttribute("year",year);
                model.addAttribute("province",province);
                model.addAttribute("city",city);
                model.addAttribute("total",total);
                model.addAttribute("firstLoginTime",firstLoginTime);

                return "qqinfo";
            }
            /*如果未登录，重定向到/logoutindex控制器*/
        return "redirect:/index";
    }

    /**
     * 处理掉QQ网名中的特殊表情
     * @param str
     * @return
     */
    public String removeNonBmpUnicode(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
        if ("".equals(str)) {
            str = "(* _ *)";
        }
        return str;
    }

    @PostMapping("/saveQQInfo")
    public String post(HttpSession session,QQInfo qqInfo, Model model) {
        QQInfo queryInfo = qqService.findQQuser(qqInfo.getOpenid());
        if(queryInfo == null){
            //查无此人，第一次登录，创建用户
            qqService.save(qqInfo);
            return "qqinfo";
        }
        return "qqinfo";
    }

    @GetMapping("/qqlogout")
    public String logout(HttpSession session){
        session.removeAttribute("loginStatus"); //退出登录，将session中的user信息清除
        session.removeAttribute("avatar");
        session.removeAttribute("nickname");
        session.removeAttribute("openid");
        session.removeAttribute("loginType");
        return "redirect:/index";
    }
}

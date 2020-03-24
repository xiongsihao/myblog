package com.xsh.web;

import com.xsh.dao.GithubRepository;
import com.xsh.pojo.Github;
import com.xsh.pojo.QQInfo;
import com.xsh.util.GetAddressByIpUtils;
import com.xsh.util.HttpClientUtils;
import com.xsh.vo.GitHubConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
/**
 * @author : xsh
 * @create : 2020-03-10 - 15:05
 * @describe:
 */
@Controller
public class GithubController {

    @Autowired
    private GithubRepository githubRepository;
    @Value("${cookie.time}")
    private Integer time;


    @GetMapping("/github/oauth")
    public String github(HttpSession session){

        //随机uuid,用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        session.setAttribute("state",uuid);

        //Step1：获取Authorization Code
        String url = "https://github.com/login/oauth/authorize?"+
                "client_id=" + GitHubConstant.CLIENT_ID +
                "&redirect_uri=" + URLEncoder.encode(GitHubConstant.CALLBACK) +
                "&state="+uuid;
        return "redirect:" + url;
    }

    //回调地址
    @RequestMapping("/github/callback")
    public String callback(String code, String state, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{

        if(!StringUtils.isEmpty(code)&&!StringUtils.isEmpty(state)){
            HttpSession session = request.getSession();
            //解决关闭浏览器就退出登录的bug
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setPath(request.getContextPath()+"/");
            cookie.setMaxAge(time);//设置cookie的生命周期为十天，单位秒。 60*60*24*10
            response.addCookie(cookie);
            //拿到我们的code,去请求token
            //发送一个请求到
            String token_url = GitHubConstant.TOKEN_URL.replace("CODE", code);
            //得到的responseStr是一个字符串需要将它解析放到map中
            String responseStr = HttpClientUtils.doGet(token_url);
            // 调用方法从map中获得返回的--》 令牌
            String token = HttpClientUtils.getMap(responseStr).get("access_token");

            //根据token发送请求获取登录人的信息  ，通过令牌去获得用户信息
            String userinfo_url = GitHubConstant.USER_INFO_URL.replace("TOKEN", token);
            responseStr = HttpClientUtils.doGet(userinfo_url);//json
            Map<String, String> responseMap = HttpClientUtils.getMapByJson(responseStr);
            session.setAttribute("node_id",responseMap.get("node_id"));  //node_id,用来唯一标识用户
            session.setAttribute("nickname",responseMap.get("login")); //github名
            /*github回调的头像url地址大概率加载不出(加载慢)，不使用github账号头像图片*/
            //session.setAttribute("avatar",responseMap.get("avatar_url")); //github头像URL

            session.setAttribute("loginStatus","true");//登录状态
            session.setAttribute("loginType",2);//登录方式,1 QQ，2 github

            Github g_user = githubRepository.findGithubuser(responseMap.get("node_id"));
            if(g_user == null){//无此node_id记录，创建新github用户记录
                int Min = 1;
                int Max = 20;
                int result = Min + (int)(Math.random() * ((Max - Min) + 1));//随机生成一个1到20的数字
                String avatarUrl="http://cdn.xiongsihao.com/GithubAvatar"+result+".png";//图片从CDN服务器上的20张头像库里面随机取
                Github github=new Github();
                github.setAvatar(avatarUrl);
                github.setCreatedTime(responseMap.get("created_at"));
                github.setNickname(responseMap.get("login"));
                github.setIndexUrl(responseMap.get("html_url"));
                github.setNodeId(responseMap.get("node_id"));
                github.setPublicRepos(responseMap.get("public_repos"));
                github.setNickname(responseMap.get("login"));
                github.setSubscriptions(responseMap.get("subscriptions_url"));
                github.setUpdatedTime(responseMap.get("updated_at"));
                github.setGithubId(responseMap.get("id"));
                github.setReceivedEventsUrl(responseMap.get("received_events_url"));
                github.setLoginTime(new Date().toString());

                githubRepository.save(github);
            }
            // 成功则登陆，跳转资料页面
            return "redirect:/githubInfo";
        }
        // 失败返回到首页
        return "redirect:/index";
    }

    @GetMapping("/githubInfo")
    public String post(HttpSession session,Model model) throws InterruptedException {
        String node_id = (String) session.getAttribute("node_id");
        Github user = githubRepository.findGithubuser(node_id);
        session.setAttribute("avatar",user.getAvatar());//更新session中头像信息
        model.addAttribute("github",user);
        return "githubInfo";
    }

    @PostMapping("/saveIp")
    public String post(HttpSession session,Github github) {
        String node_id = (String) session.getAttribute("node_id");
        Github githubuser = githubRepository.findGithubuser(node_id);
        if(githubuser.getCip() == null){//ip信息为空，第一次登录，将前端传入的ip信息存入
            githubRepository.addIpaddress(github.getCip(),github.getCid(),github.getCname(),node_id);
        }
        return "githubInfo";
    }

    @GetMapping("/githublogout")
    public String logout(HttpSession session){
        session.removeAttribute("loginStatus"); //退出登录，将session中的user信息清除
        session.removeAttribute("avatar");
        session.removeAttribute("nickname");
        session.removeAttribute("node_id");
        session.removeAttribute("loginType");
        return "redirect:/index";
    }
}

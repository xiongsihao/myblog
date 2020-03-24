package com.xsh.web;

import com.xsh.service.BlogService;
import com.xsh.service.TagService;
import com.xsh.service.TypeService;
import com.xsh.util.GetAddressByIpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : xsh
 * @create : 2020-02-06 - 21:13
 * @describe:
 */
@Controller
public class IndexController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/zeroError")
    public String zeroError(){
        int a=9/0;
/*        String blog=null;
        if(blog==null){
            throw new NotFoundException("博客不存在");
        }*/
        System.out.println("----------index方法执行----------");
        return "index";
    }

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        Model model,
                        @RequestHeader(value = "User-Agent",required=true) String userAgent){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        /*使用定义好的正则表达式获取请求头信息的设备信息*/
 /*       Matcher matcher = pattern.matcher(userAgent);
        String deviceMessage = null;
        if (matcher.find()) {
            deviceMessage = matcher.group(1).trim();
            logger.info("通过userAgent解析出访问者机型：" + deviceMessage);
        }*/

 //todo
        //防止进网页未获取到ip地址卡顿 注释掉
/*        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         HttpServletRequest request=attributes.getRequest();
        String ip=request.getRemoteAddr();
        String address = GetAddressByIpUtils.ipToAddress(ip);
        logger.info("访问者设备信息：" + userAgent);
        logger.info("访问者ip地址：" + ip);
        logger.info("访问者实际地址：" + address);*/

        return "index";
    }
    /*退出登录跳转页面*/
    @GetMapping("/index")
    public String index1(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        Model model,
                        @RequestHeader(value = "User-Agent",required=true) String userAgent){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));

        return "index";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listNewBlog(3));
        return "_fragments :: newblogList";
    }


}

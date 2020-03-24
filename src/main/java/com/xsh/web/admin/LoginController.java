package com.xsh.web.admin;

import com.xsh.pojo.User;
import com.xsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author : xsh
 * @create : 2020-02-08 - 0:20
 * @describe:
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){ //如果user!=null说明数据库有这条数据，用户名密码正确
            user.setPassword(null); //将密码置为空，防止密码传到前端
            session.setAttribute("user",user); //将user存入session中表示已登录
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名和密码错误"); //使用重定向，如果要传参只能使用RedirectAttributes，不能使用Model
            return "redirect:/admin"; //登录失败，重定向执行/admin控制层方法
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user"); //退出登录，将session中的user信息清除
        return "redirect:/admin";
    }

}

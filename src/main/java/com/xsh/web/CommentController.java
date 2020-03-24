package com.xsh.web;

import com.xsh.pojo.Comment;
import com.xsh.pojo.User;
import com.xsh.service.BlogService;
import com.xsh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author : xsh
 * @create : 2020-02-15 - 0:14
 * @describe:
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @Value("${admin.openid}")
    private String adminOpenid;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        String loginStatus = (String) session.getAttribute("loginStatus");
        if (loginStatus != null) {
            String avatar = (String) session.getAttribute("avatar");
            String openid = (String) session.getAttribute("openid");
            comment.setAvatar(avatar);
            comment.setAdminComment(1);
            /*判断是否为管理员评论*/
            if(adminOpenid.equals(openid)){
                comment.setAdminComment(2);
            }
            System.out.println(openid);
        } else {
            comment.setAdminComment(0);
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}

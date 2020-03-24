package com.xsh.web;

import com.xsh.pojo.Message;
import com.xsh.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : xsh
 * @create : 2020-03-18 - 15:37
 * @describe:
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    private static final String PAGE_SIZE = "10";
    @Value("${comment.avatar}")
    private String avatar;

    @Value("${admin.openid}")
    private String adminOpenid;

    @GetMapping("/messageBoard")
    public String messageBoard(){

        return "message_board";
    }
    @PostMapping("/POSTMessages")
    public String post(Message message, HttpSession session) {
        String loginStatus = (String) session.getAttribute("loginStatus");
        if (loginStatus != null) {
            String avatar = (String) session.getAttribute("avatar");
            String openid = (String) session.getAttribute("openid");//QQ标识
            String node_id = (String) session.getAttribute("node_id");//github标识
            message.setAvatar(avatar);
            message.setAdminMessage(1);
            if(StringUtils.isNotBlank(node_id)){
                message.setAdminMessage(3);
            }
            /*判断是否为管理员评论*/
            if(adminOpenid.equals(openid)){
                message.setAdminMessage(2);
            }
            System.out.println(openid);
        } else {
            message.setAdminMessage(0);
            message.setAvatar(avatar);
        }
        messageService.saveMessage(message);
        messageService.findParentMessage(message);

        return "redirect:/Message";
    }
    @RequestMapping(value ="/Messages" ,method = RequestMethod.GET)
    public String Message_board(@RequestParam(value = "key",required = false)String key,
                                @RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "rows",defaultValue = "8")Integer rows,
                                @RequestParam(value = "sortBy",defaultValue = "createTime")String sortBy,
                                @RequestParam(value = "desc",required = false)Boolean desc,
                                Model model) {
        Pageable pageable = new PageRequest(page-1,rows, Sort.Direction.DESC,sortBy);

        Page<Message> messages = messageService.listMessage(pageable);
        boolean a = messages.hasPrevious(); //判断是否为首页
        boolean b = messages.hasNext();//判断是否为尾页
        model.addAttribute("a",a);
        model.addAttribute("b",b);
        model.addAttribute("messages",messageService.listMessage(pageable));
        return "message_board";
    }

    @RequestMapping(value ="/Message" ,method = RequestMethod.GET)
    public String message(@RequestParam(value = "key",required = false)String key,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "rows",defaultValue = "10")Integer rows,
                          @RequestParam(value = "sortBy",defaultValue = "createTime")String sortBy,
                          @RequestParam(value = "desc",required = false)Boolean desc,
                                    Model model) {
        Pageable pageable = new PageRequest(page-1,rows, Sort.Direction.DESC,sortBy);
        Page<Message> messages = messageService.listMessage(pageable);
        boolean a = messages.hasPrevious(); //判断是否为首页
        boolean b = messages.hasNext();//判断是否为尾页
        model.addAttribute("a",a);
        model.addAttribute("b",b);
        model.addAttribute("messages",messageService.listMessage(pageable));
        return "message_board :: messageList";//将数据返回message_board页面的th:fragment="messageList"片段，实现局部刷新
    }


}

package com.xsh.service;

import com.xsh.dao.MessageRepository;
import com.xsh.pojo.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author : xsh
 * @create : 2020-03-18 - 15:31
 * @describe: 留言板
 */
@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

    private Executor executor = Executors.newFixedThreadPool(10);

    @Transactional
    @Override
    public Message saveMessage(Message message) {
        Long parentMessageId = message.getParentMessage().getId();
        if (parentMessageId != -1) {
            message.setParentMessage(messageRepository.findOne(parentMessageId));
        } else {
            message.setParentMessage(null);
        }
        message.setCreateTime(new Date());
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> listMessage(Pageable pageable) {

        Page<Message> messagePageEntity = messageRepository.findAll(pageable);
        /*List转Page*/
        List<Message> messagePageList = eachMessage(messagePageEntity.getContent());
        long totalCount=messagePageEntity.getTotalElements();
        List<Message> messageList=new ArrayList<>();

        for (Message message: messagePageList) {
            Message messageVO=new Message();
            BeanUtils.copyProperties(message,messageVO);
            messageList.add(messageVO);
        }
        return new PageImpl(messageList,pageable,totalCount);
    }
    /**
     * 循环每个顶级的评论节点
     * @param messages
     * @return
     */
    private List<Message> eachMessage(List<Message> messages) {
        List<Message> messagesView = new ArrayList<>();
        for (Message message : messages) {
            Message c = new Message();
            BeanUtils.copyProperties(message,c);
            messagesView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(messagesView);
        return messagesView;
    }

    /**
     *
     * @param messages root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Message> messages) {

        for (Message message : messages) {
            List<Message> replys1 = message.getReplyMessages();
            for(Message reply1 : replys1) {
                tempReplys.add(reply1);//顶节点添加到临时存放集合
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            message.setReplyMessages(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Message> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param message 被迭代的对象
     * @return
     */
    private void recursively(Message message) {
        if (message.getReplyMessages().size()>0) {
            List<Message> replys = message.getReplyMessages();
            for (Message reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyMessages().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
    /**
     * 根据留言查找父留言
     * @param message
     * @return
     */
    @Override
    public void findParentMessage(Message message) {
        Message parentMessage = message.getParentMessage();
        if(parentMessage == null){
            return;
        }else{
            Message parent = messageRepository.findOne(parentMessage.getId());
            Boolean flag = parent.getReplyInform();//查看父评论是否开启回复邮件通知
            if(flag){//开启邮件通知，给父留言邮箱发送短信
                Runnable task = new Runnable() {//异步发送邮件通知
                    @Override
                    public void run() {
                        try {
                            //创建邮件正文
                            Context context = new Context();
                            //设置邮件模板内可变参数
                            context.setVariable("parentMessage", parent);
                            context.setVariable("message", message);
                            //解决表情显示问题：
                            String content1 = parent.getContent();
                            String content2 = message.getContent();
                            String replace1 = content1.replace("/images", "http://xiongsihao.com/images");
                            String replace2 = content2.replace("/images","http://xiongsihao.com/images");
                            context.setVariable("replace1", replace1);
                            context.setVariable("replace2", replace2);
                            context.setVariable("sendTime", new Date());
                            String emailContent = templateEngine.process("emailTemplate", context);
                            mailService.sendHtmlMail(parent.getEmail(),"你收到一条回复(XSH_博客)",emailContent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                executor.execute(task);
            }else {
                return;
            }
        }
    }
}

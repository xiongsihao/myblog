package com.xsh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


//@Component
@Service("mailServiceImpl")
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * 发送文本邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        logger.info("发送文本邮件开始：{},{},{}", to, subject, content);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单文本已经发送。");
        } catch (Exception e) {
            logger.error("发送文本邮件时发生异常！", e);
        }

    }

    /**
     * 发送html邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容模板
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //logger.info("发送html邮件开始：{},{},{}", to, subject, content);
        logger.info("发送html邮件开始：{},{}", to, subject);
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }


    /**
     * 发送带附件的邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容
     * @param filePath 附件
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); //true代表邮件内容支持多组件，如附件，图片等

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file); //添加附件，可多次调用该方法添加多个附件
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容
     * @param imgPath 图片资源地址，也可以是其它静态资源
     * @param rscId 图片ID，用于在<img>标签中使用，从而显示图片
     */
    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String imgPath, String rscId){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(imgPath));
            helper.addInline(rscId, res); //重复调用addInline可添加多个图片

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
}

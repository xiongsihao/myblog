//package com.xsh.mailtest;
//
//import com.xsh.service.MailService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
///**
// * @author : xsh
// * @create : 2020-03-15 - 16:50
// * @describe: 发送邮件测试类
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MailServiceTest {
//
//    @Autowired
//    private MailService mailService;
//
//    @Autowired
//    private TemplateEngine templateEngine;
//
//    @Test
//    public void testSimpleMail() throws Exception {
//        mailService.sendSimpleMail("3248884841@qq.com","test simple mail"," hello this is simple mail");
//        //mailService.sendSimpleMail("xsh2668028614@qq.com","test simple mail"," hello this is simple mail");
//        //mailService.sendSimpleMail("xiongsh@easyinker.com","test simple mail"," hello this is simple mail");
//    }
//
//    @Test
//    public void testHtmlMail() throws Exception {
//        String content="<html>\n" +
//                "<body>\n" +
//                "    <h3 style=\"background-color:red\">hello world ! 这是一封html邮件!</h3>\n" +
//                "</body>\n" +
//                "</html>";
//        mailService.sendHtmlMail("3248884841@qq.com","test Html mail",content);
//    }
//
//    @Test
//    public void sendAttachmentsMail() {
//        String filePath="D:\\b.txt";
//        mailService.sendAttachmentsMail("3248884841@qq.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
//    }
//
//
//    @Test
//    public void sendInlineResourceMail() {
//        String rscId = "test006";
//        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
//        String imgPath = "C:\\Users\\Administrator\\Desktop\\myblog\\图片管理\\firstPicture\\blogFirstPicture10.jpg";
//
//        mailService.sendInlineResourceMail("3248884841@qq.com", "主题：这是有图片的邮件", content, imgPath, rscId);
//    }
//
//
//    @Test
//    public void sendTemplateMail() {
//        //创建邮件正文
//        Context context = new Context();
//        //context.setVariable("id", "006");
//        String emailContent = templateEngine.process("emailTemplate", context);
//
//        mailService.sendHtmlMail("xsh2668028614@qq.com","主题：这是模板邮件",emailContent);
//    }
//}

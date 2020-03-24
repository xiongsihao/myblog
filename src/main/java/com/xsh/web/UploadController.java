package com.xsh.web;

import com.xsh.dao.GithubRepository;
import com.xsh.pojo.Github;
import com.xsh.pojo.QQInfo;
import com.xsh.service.FileService;
import com.xsh.service.QqService;
import com.xsh.util.UUidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : xsh
 * @create : 2020-03-17 - 2:28
 * @describe:https://blog.csdn.net/qq_33924360/article/details/89153493?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
 */
@RestController
@RequestMapping("/upload")
public class UploadController {


    @Autowired
    private FileService fileService;
    @Autowired
    private QqService qqService;
    @Autowired
    private GithubRepository githubRepository;

    @Value("${baseUploadUrl}")
    private String url;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/uploadQQImg")
    public Map<String,Object> uploadQQImg(@RequestParam(value = "file") MultipartFile upfile, HttpSession session) throws IOException {
        Map<String,Object> map = new HashMap<>();
        String fileName = upfile.getOriginalFilename();
        File file = new File(url + fileName);
        try{
            //将MulitpartFile文件转化为file文件格式
            upfile.transferTo(file);

            //根据用户id和UUID组合成文件名
            String openid = (String) session.getAttribute("openid");
            QQInfo qquser = qqService.findQQuser(openid);
            String uploadFileName="QQavatar_userid"+qquser.getId()+"_"+ UUidUtils.getUUID();
            logger.info("上传的文件名为："+uploadFileName);

            Map response = fileService.uploadFile(file,uploadFileName);
            String imageUrl = (String)response.get("imageUrl");
            //上传图片之后更新对应用户的头像链接并将session中的头像信息更新
            qqService.updateAvatar(imageUrl,qquser.getId());
            session.setAttribute("avatar",imageUrl);
            map.put("url",imageUrl);
            map.put("state","SUCESS");
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping(value = "/uploadGithubImg")
    public Map<String,Object> uploadGithubImg(@RequestParam(value = "file") MultipartFile upfile, HttpSession session) throws IOException {
        Map<String,Object> map = new HashMap<>();
        String fileName = upfile.getOriginalFilename();
        File file = new File(url + fileName);
        try{
            //将MulitpartFile文件转化为file文件格式
            upfile.transferTo(file);

            //根据用户id和UUID组合成文件名
            String node_id = (String) session.getAttribute("node_id");
            Github g_user = githubRepository.findGithubuser(node_id);
            String uploadFileName="GithubAvatar_id"+g_user.getGithubId()+"_"+ UUidUtils.getUUID();
            logger.info("上传的文件名为："+uploadFileName);

            Map response = fileService.uploadFile(file,uploadFileName);
            String imageUrl = (String)response.get("imageUrl");
            //上传图片之后更新对应用户的头像链接并将session中的头像信息更新
            githubRepository.updateAvatar(imageUrl,g_user.getId());
            map.put("url",imageUrl);
            map.put("state","SUCESS");
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}

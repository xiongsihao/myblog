package com.xsh.test.Controller;

import com.xsh.test.Service.ImageService;
import com.xsh.test.Service.RenameService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : xsh
 * @create : 2020-02-30 - 9:57
 * @describe:
 */
@Controller
public class HelloWorldController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private RenameService renameService;
    @RequestMapping(path="test/{workId}")
    public String HelloWorld(@PathVariable("workId") Integer workId, Model model) throws Exception {
        Map<Integer, String> content = imageService.getContent(workId);
        //定义一个Map存放每张图片的真实名字
        Map<Integer, String> name = new HashMap<Integer, String>();
        for(int i=1;i<=content.size();i++){
            name.put(i,imageService.getName(workId, content.get(i)));
        }
/*      name.put(1,imageService.getName(workId, content.get(1)));
        name.put(2,imageService.getName(workId, content.get(2)));
        name.put(3,imageService.getName(workId, content.get(3)));
        name.put(4,imageService.getName(workId, content.get(4)));
        name.put(5,imageService.getName(workId, content.get(5)));
        name.put(6,imageService.getName(workId, content.get(6)));
        name.put(7,imageService.getName(workId, content.get(7)));
        name.put(8,imageService.getName(workId, content.get(8)));
        name.put(9,imageService.getName(workId, content.get(9)));
        name.put(10,imageService.getName(workId, content.get(10)));
        name.put(11,imageService.getName(workId, content.get(11)));
        name.put(12,imageService.getName(workId, content.get(12)));
        name.put(13,imageService.getName(workId, content.get(13)));*/

        System.out.println("第三张图名字为："+name.get(3));
        model.addAttribute("imageNames", name);
        return "test";
    }

    @RequestMapping(path="test1/{workId}")
    public String HelloWorld1(@PathVariable("workId") Integer workId, Model model) throws Exception {
        Map<Integer, String> content = imageService.getContent(workId);
        //定义一个Map存放每张图片的真实名字
        Map<Integer, String> name = new HashMap<Integer, String>();
        for(int i=1;i<=content.size();i++){
            name.put(i,imageService.getName(workId, content.get(i)));
            System.out.println("第"+i+"张图片名字为："+name.get(i));
        }
/*        name.put(1,imageService.getName(workId, content.get(1)));
        name.put(2,imageService.getName(workId, content.get(2)));
        name.put(3,imageService.getName(workId, content.get(3)));
        name.put(4,imageService.getName(workId, content.get(4)));
        name.put(5,imageService.getName(workId, content.get(5)));
        name.put(6,imageService.getName(workId, content.get(6)));
        name.put(7,imageService.getName(workId, content.get(7)));
        name.put(8,imageService.getName(workId, content.get(8)));
        name.put(9,imageService.getName(workId, content.get(9)));
        name.put(10,imageService.getName(workId, content.get(10)));
        name.put(11,imageService.getName(workId, content.get(11)));
        name.put(12,imageService.getName(workId, content.get(12)));
        name.put(13,imageService.getName(workId, content.get(13)));*/

        //System.out.println("第三张图名字为："+name.get(3));
        Map<Integer, String> name2 = new HashMap<Integer, String>();
        for(int ii=1;ii<=content.size();ii++){
            name2.put(ii,renameService.reName(name.get(ii)));
        }
        Set<String> repeat = renameService.findRepeat(name2);
        String repeatResult= StringUtils.join(repeat.toArray(), ";");//set转字符串
        String path="C:\\Users\\Administrator\\Desktop\\"+workId;
        String[] fileName = renameService.getFileName(path);
        System.out.println("当前文件夹内图片数量："+fileName.length);
        if(!name2.get(1).equals("无上传图片")){
            renameService.renameFile(path,name2.get(1),1+".jpg");
        }
        for (int j=2;j <=13;j++){
            renameService.renameFile(path,name2.get(j),j+".jpg");
        }
        model.addAttribute("imageNames", name);
        model.addAttribute("repeatResult", repeatResult);
        return "test";
    }
}

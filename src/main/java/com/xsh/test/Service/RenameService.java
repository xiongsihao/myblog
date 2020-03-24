package com.xsh.test.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @author : xsh
 * @create : 2020-02-30 - 10:31
 * @describe:
 */
@Service
public class RenameService {
    public String [] getFileName(String path) {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }

    public  void renameFile(String path,String oldname,String newname){
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(path+"\\"+oldname);
            File newfile=new File(path+"\\"+newname);
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists()){//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname+"已经存在！");
            }else{
                oldfile.renameTo(newfile);
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    //去除tmp异常图片前面的路径，只留下文件名
    public String reName(String name){
        boolean flag = name.contains("tmp");
        if(flag){
            return name.substring(name.indexOf("tmp"));
        }
        return name;
    }

    //找出map中重复的数据
    public Set<String> findRepeat(Map<Integer,String> map){
        //建立一个数组用来存储map的数值
        List all = new ArrayList();
        //遍历map的值，然后赋值到all
        Collection values = map.values();
        for(Iterator iterator1 = values.iterator(); iterator1.hasNext();){
            all.add((String) iterator1.next());
        }
        System.out.println(all.size());
        //找到重复值str1，根据索引查找重复数据，当第一个匹配的数据和最后一个匹配的数据索引不同时则存在重复数据
        Object str1 = null;
        Set<String> result=new HashSet<String>();//存放结果，因为可能有多个重复图片，所以用Set存放
        for(int i=0;i<all.size()-1;i++){
            Object object1 = all.get(i);
            int index1 = all.indexOf(object1)+1;
            int index2 = all.lastIndexOf(object1)+1;
            if(index1!=index2){
                str1=all.get(index1);
                result.add("重复图片："+str1+"。第"+index1+"张图片与第"+index2+"张图片重复");
            }
        }
        if(result.isEmpty()){//如果结果为空则没有重复图片
            result.add("无重复图片");
        }
        return result;
    }
}

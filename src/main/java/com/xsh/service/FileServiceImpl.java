package com.xsh.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xsh.dao.QqRepository;
import com.xsh.pojo.QQInfo;
import com.xsh.util.UUidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : xsh
 * @create : 2020-03-17 - 2:27
 * @describe:
 */
@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private Auth auth;
    @Autowired
    private QqRepository qqRepository;

    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.path}")
    private String url;

    private StringMap putPolicy;

    /**
     *
     * @param file 上传的文件对象
     * @param filename 指定的上传文件名
     * @return
     * @throws QiniuException
     */
    @Override
    public Map uploadFile(File file,String filename) throws QiniuException {
        Map map = new HashMap();
        Response response = this.uploadManager.put(file,filename,getUploadToken());
        //解析上传的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);

        //String imageName = putRet.hash;
        //System.out.println("putRet.hash："+putRet.hash);
        String imageName = putRet.key;//上传之后的文件名
        String imageUrl=url+"/"+filename;//上传之后的文件外链
        System.out.println("putRet.key："+putRet.key);
        System.out.println("imageUrl："+imageUrl);
        int retry = 0;
        while(response.needRetry() && retry < 3){
            response = this.uploadManager.put(file,null,getUploadToken());
        }
        map.put("response",response);
        map.put("imageUrl",imageUrl);
        return map;
    }


    private String getUploadToken(){
        return this.auth.uploadToken(bucket,null,3600,putPolicy);
    }
}
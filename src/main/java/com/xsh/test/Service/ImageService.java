package com.xsh.test.Service;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : xsh
 * @create : 2020-02-30 - 15:21
 * @describe:
 */
/*
@Service
public class ImageService {

    public CloseableHttpClient httpClient= HttpClients.createDefault();
    public Map<Integer, String> getContent(Integer workId) throws IOException {
        String workUrl="http://oss.handyprint.cn/works/"+workId+"/content.json";
        HttpGet request = new HttpGet(workUrl);
        String response = httpClient.execute(request, new BasicResponseHandler());
        System.out.println("完整响应："+response);
        //字符串转换成json对象
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonA= jsonObject.getJSONObject("DOCATTRIBUTES");
        JSONArray pageids = jsonA.getJSONArray("PAGEIDS"); //获取JSON中的PAGEIDS数组并遍历
        //定义一个Map存放每张图片的信息
        Map<Integer, String> m1 = new HashMap<Integer, String>();
        for(int i=0,lengths=pageids.length();i<lengths;i++) {
            String o = (String) pageids.get(i);
            System.out.println(i + 1 + ":" + o);
            m1.put(i + 1,o);
        }
        System.out.println("第三张图content为："+m1.get(3));
        return m1;
    }

    public String getName(Integer workId,String name) throws IOException {
        String imageUrl="http://oss.handyprint.cn/works/"+workId+"/"+name+".json";
        HttpGet request1 = new HttpGet(imageUrl);
        String response1 = httpClient.execute(request1, new BasicResponseHandler());
        boolean flag = response1.contains("wxfile://");
        String end=null;
        if(flag){
            String result=response1.substring(response1.indexOf("wxfile://"));
            end=null;//存放名字
            String[] strArr = result.split(",");
            String b=strArr[0];
            System.out.println(b.substring(b.indexOf("wxfile://"), b.lastIndexOf("\"")));
            end=b.substring(b.indexOf("wxfile://"), b.lastIndexOf("\""));
        }else{
             end="正常图片";
        }
        return end;
    }
}
*/
@Service
public class ImageService {

    public CloseableHttpClient httpClient= HttpClients.createDefault();
    public Map<Integer, String> getContent(Integer workId) throws IOException {
        String workUrl="http://oss.handyprint.cn/works/"+workId+"/content.json";
        HttpGet request = new HttpGet(workUrl);
        String response = httpClient.execute(request, new BasicResponseHandler());
        System.out.println("完整响应："+response);
        //字符串转换成json对象
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonA= jsonObject.getJSONObject("DOCATTRIBUTES");
        JSONArray pageids = jsonA.getJSONArray("PAGEIDS"); //获取JSON中的PAGEIDS数组并遍历
        //定义一个Map存放每张图片的信息
        Map<Integer, String> m1 = new HashMap<Integer, String>();
        for(int i=0,lengths=pageids.length();i<lengths;i++) {
            String o = (String) pageids.get(i);
            System.out.println(i + 1 + ":" + o);
            m1.put(i + 1,o);
        }
        System.out.println("第三张图content为："+m1.get(3));
        return m1;
    }

    public String getName(Integer workId,String name) throws IOException {
        String imageUrl="http://oss.handyprint.cn/works/"+workId+"/"+name+".json";
        HttpGet request1 = new HttpGet(imageUrl);
        String response1 = httpClient.execute(request1, new BasicResponseHandler());
        boolean flag = response1.contains("\"REPLACE\":true");
        String end=null;
        if(flag){
            String response2 = spiltRtoL(response1); //将指定的字符串进行倒转,从后向前查找
            String result=response2.substring(response2.indexOf("eurt:\"ECALPER\""));
            String[] strArr = result.split(",");
            String b=strArr[3];//查找出名字字段PFILE，逆序
            String c = spiltRtoL(b);
            String removeStr1="\"PFILE\":\"";//定义需要去除的字符串
            String removeStr2="\"";//定义需要去除的字符串
            String d=c.replace(removeStr1,"");
            end=d.replace(removeStr2,"");

            //System.out.println(b.substring(b.indexOf("wxfile://"), b.lastIndexOf("\"")));
        }else {
            end="无上传图片";
        }
        return end;
    }

    /*将字符串逆序返回*/
    public static String spiltRtoL(String s) {

        StringBuffer sb = new StringBuffer();
        int length = s.length();
        char[] c = new char[length];
        for (int i = 0; i < length; i++) {
            c[i] = s.charAt(i);
        }
        for (int i = length - 1; i >= 0; i--) {
            sb.append(c[i]);
        }

        return sb.toString();
    }

}

package com.xsh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : xsh
 * @create : 2020-02-27 - 17:20
 * @describe:调用淘宝接口根据ip地址查询实际地址(淘宝接口已失效)
 * 使用搜狐查看ip地址
 */
public class GetAddressByIpUtils {
    public static void main(String[] args) {
        //String s = ipToAddress("39.97.98.246");
        //System.out.println(s);
        getIpBysohu();
    }
    public static Map<Object,Object> getIpBysohu() {
        String str = getJsonContent("http://pv.sohu.com/cityjson?ie=utf-8");//返回数据格式var returnCitySN = {"cip": "101.45.86.135", "cid": "310000", "cname": "上海市"};
        String NewStr=str.substring(str.indexOf("{"), str.lastIndexOf("}")+1);//截取括号内的数据
        System.out.println(NewStr);
        /*JSON转Map*/
        Map<Object,Object> objectMap=new HashMap<>();
        Map maps = (Map)JSON.parse(NewStr);
        for (Object map : maps.entrySet()){
            objectMap.put(((Map.Entry)map).getKey(),((Map.Entry)map).getValue());
        }
        System.out.println((String) objectMap.get("cname"));
        return objectMap;
    }
    /*已失效*/
    public static String ipToAddress(String ip) {
        String result = "";
        try{
            String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
            System.out.println(str);
            JSONObject obj = JSONObject.parseObject(str);
            if(obj == null){
                return "获取访问者实际地址异常：null";
            }
            JSONObject obj2 =  (JSONObject) obj.get("data");
            int code = (int)obj.get("code");

            if(code==0){
                result =  obj2.get("country")+"--" +obj2.get("region")+"--" +obj2.get("city")+"--" +obj2.get("isp");
            }else{
                result =  "IP地址有误";
            }
        }catch(Exception e){

            e.printStackTrace();
            result = "获取IP地址异常："+e.getMessage();
        }
        System.out.println("result: " + result);
        return result;
    }

    public static String getJsonContent(String urlStr) {
        try
        {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            if (respCode == 200)
            {
                return ConvertStream2Json(httpConn.getInputStream());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private static String ConvertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }
}

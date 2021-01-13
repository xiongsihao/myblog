package com.xsh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xsh.pojo.HttpResult;

import javax.servlet.http.HttpServletRequest;
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


    public final static String getIpAddress(HttpServletRequest request)
            throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        return ip;
    }

    /***
     * 获取客户端IP地址;通过Nginx获取;X-Real-IP,
     * @param request
     * @return
     *
     * nginx需要增加以下配置：
     *          proxy_set_header  Host $http_host;
     * 			proxy_set_header X-Real-IP  $remote_addr;
     * 			proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
     * 			proxy_set_header REMOTE-HOST $remote_addr;
     */
    public static String getClientIPForNginx(HttpServletRequest request) {
        String fromSource = "X-Real-IP";
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
            fromSource = "X-Forwarded-For";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            fromSource = "Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            fromSource = "WL-Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            fromSource = "request.getRemoteAddr";
        }
        System.out.println("App Client IP: "+ip+", fromSource: "+fromSource);
        return ip;
    }
    //解析ip地址
    public static String getInfoByIp(String ip){
        HttpClientUtil apiService=new HttpClientUtil();
        String url = "http://whois.pconline.com.cn/ipJson.jsp";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ip",ip);//传入ip地址
        map.put("json","true");
        HttpResult httpResult = null;
        try {
            httpResult = apiService.doGet(url,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(httpResult.getCode()==200){
            System.out.println("SUCCESS");
        }
        JSONObject jsonObject = JSONObject.parseObject(httpResult.getBody());

        String pro = jsonObject.getString("pro");
        String city = jsonObject.getString("city");
        String addr = jsonObject.getString("addr");
        String ip_a = jsonObject.getString("ip");

        //String response="解析访问ip【"+ip+"】信息，解析结果："+httpResult.getBody();
        String response="解析访问ip【"+ip_a+"】信息，解析结果："+pro+addr;
        return response;
    }
}

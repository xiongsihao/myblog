package com.xsh.util;

import java.util.UUID;

/**
 * @author : xsh
 * @create : 2020-03-17 - 22:35
 * @describe: UUID工具类
 */
public class UUidUtils {
    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] retArray = new String[number];
        for(int i=0;i<number;i++){
            retArray[i] = getUUID();
        }
        return retArray;
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    /*生成UUid*/
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(uuid);
        }

        String s = UUidUtils.getUUID(5)[1];
        System.out.println(s);
    }
}

package com.xsh.pojo;

import java.io.Serializable;

/**
 * @author : xsh
 * @create : 2020-04-02 - 14:53
 * @describe:
 */
public class HttpResult implements Serializable {
    // 响应的状态码
    private int code;

    // 响应的响应体
    private String body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}

package com.xsh.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : xsh
 * @create : 2021-01-12 - 20:13
 * @describe: 接口防止重复请求标记注解
 */

@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface CheckRepeatRequest {
}


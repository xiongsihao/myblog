package com.xsh.aspect;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;


 /**
 * @author : xsh
 * @create : 2021-01-12 - 20:28
 * @describe: 内存缓存配置
 */
@Configuration
public class RequestCache {
    @Value("${repeatRequestParam.cacheExpireTime}")
    private Long cacheExpireTime;

    @Bean
    public Cache<String, Integer> getCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(cacheExpireTime, TimeUnit.SECONDS)
                .build();// 构建缓存有效期为60秒
    }
}
package com.xsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class MyblogApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){

        return application.sources(MyblogApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(MyblogApplication.class, args);
    }

    /*springboot修改session过期时间，默认为30分钟过期*/
/*     @Bean
     public EmbeddedServletContainerCustomizer containerCustomizer(){
                return container -> {
                  container.setSessionTimeout(2592000);*//*单位为S， 60*60*24*30* .//*
            };
         }*/
}

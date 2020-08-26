package com.cloudmusic.config;

import com.cloudmusic.interceptor.OriginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author simple
 * @description 配置跨域拦截器
 * @date 2018/10/30 10:30
 */
@Configuration
@EnableSwagger2
public class MyWebAppConfigurer
        implements WebMvcConfigurer {

    @Bean
    OriginInterceptor originInterceptor(){
        return new OriginInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(originInterceptor());
    }
}

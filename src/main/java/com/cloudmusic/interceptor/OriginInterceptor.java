package com.cloudmusic.interceptor;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.utils.CloudMusicUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 解决跨域问题
 * @date 2018/10/30 10:25
 */
@Component
public class OriginInterceptor implements HandlerInterceptor {

    //注入账号密码
    @Value("${application.accountInfo.username}")
    private String username;
    @Value("${application.accountInfo.password}")
    private String password;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //CloudMusicUtil.loginVipAccount(username,password,response,request);

        response.setHeader("Access-Control-Allow-Origin", "http://popps.top:7003");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return true;
    }
}

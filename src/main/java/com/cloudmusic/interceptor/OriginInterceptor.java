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
import java.util.*;

/**
 * @author simple
 * @description 解决跨域问题
 * @date 2018/10/30 10:25
 */
@Component
public class OriginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String originHeader = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //设置登录cookie信息
        String token = request.getHeader("token");
        if(token!=null && !"Not login".equals(token)){
            Cookie cookie = new Cookie("MUSIC_U", token);
            response.addCookie(cookie);
        }
        return true;
    }
}

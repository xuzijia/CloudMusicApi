package com.cloudmusic.api.controller;

import com.cloudmusic.utils.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.MusicUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：模拟登陆网易云音乐
 * Created by xuzijia
 * 2018/5/23 16:35
 */
@RestController
public class LoginController {

    /**
     * 手机号登陆接口(建议用户使用post请求)
     *
     * @param phone    *手机号 必填
     * @param password *密码 必填
     * @return 登陆信息
     * @throws Exception
     */
    @RequestMapping("/login")
    public String login(String phone, String password, HttpServletResponse response) throws Exception {
        //md5加密
        password = MusicUtil.md5(password);
        Map<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("password", password);
        data.put("rememberLogin", "true");
        return CreateWebRequest.createLoginRequest(ApiUrl.cellPhoneLoginUrl, data, new HashMap<>(), response);
    }

    /**
     * 刷新登录状态
     *
     * @return 是否刷新成功
     * @throws Exception
     */
    @RequestMapping("/login/refresh")
    public String refreshLogin(HttpServletRequest request) throws Exception {
        return CreateWebRequest.createWebPostRequest(ApiUrl.RefreshLoginUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
    }

}

package com.cloudmusic.controller.cloudMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.result.Result;
import com.cloudmusic.utils.CloudMusicUtil;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
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
    @RequestMapping("/login_phone")
    public String loginByPhone(String phone, String password, HttpServletResponse response) {
        //md5加密
        //password = CloudMusicUtil.md5(password);
        Map<String, String> data = new HashMap<>();
        data.put("phone", phone);
        data.put("password", password);
        data.put("rememberLogin", "true");
        return CreateWebRequest.createLoginRequest(CloudMusicApiUrl.cellPhoneLoginUrl, data, response);
    }
    /**
     * 邮箱登陆接口(建议用户使用post请求)
     *
     * @param email    *邮箱 必填
     * @param password *密码 必填
     * @return 登陆信息
     * @throws Exception
     */
    @RequestMapping("/login_email")
    public String loginByEmail(String email, String password, HttpServletResponse response){
        //md5加密
        //password = CloudMusicUtil.md5(password);
        Map<String, String> data = new HashMap<>();
        data.put("username", email);
        data.put("password", password);
        data.put("rememberLogin", "true");
        return CreateWebRequest.createLoginRequest(CloudMusicApiUrl.emailLoginUrl, data, response);
    }

    /**
     * 刷新登录状态
     *
     * @return 是否刷新成功
     * @throws Exception
     */
    @RequestMapping("/login/refresh")
    public String refreshLogin(HttpServletRequest request) throws Exception {
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.RefreshLoginUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
    }


    /**
     * 发送手机验证码
     *
     * @return
     * @param cellphone 手机号码
     * @param ctcode 国家区号 默认为86 即中国地区
     * @throws Exception
     */
    @RequestMapping("/login/smsSend")
    public String smsSend(String cellphone, String ctcode, HttpServletRequest request) throws Exception {
        if(cellphone==null || cellphone.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        if(StringUtils.isEmpty(ctcode)){
            ctcode="86";
        }
        data.put("ctcode", ctcode);
        data.put("cellphone", cellphone);
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.smsSendUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 验证验证码
     *
     * @return
     * @param cellphone 手机号码
     * @param ctcode 国家区号 默认为86 即中国地区
     * @param captcha 验证码
     * @throws Exception
     */
    @RequestMapping("/login/smsVerify")
    public String smsVerify(String cellphone, String ctcode, String captcha,HttpServletRequest request) throws Exception {
        if(cellphone==null || cellphone.trim().equals("") || captcha==null || captcha.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        if(StringUtils.isEmpty(ctcode)){
            ctcode="86";
        }
        data.put("ctcode", ctcode);
        data.put("cellphone", cellphone);
        data.put("captcha", captcha);
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.smsVerfiyUrl, data, CreateWebRequest.getCookie(request));
    }
    /**
     * 检测手机号是否被注册
     *
     * @return
     * @param cellphone 手机号码
     * @param ctcode 国家区号 默认为86 即中国地区
     * @throws Exception
     */
    @RequestMapping("/login/smsCheckPhone")
    public String smsCheckPhone(String cellphone, String ctcode,HttpServletRequest request) throws Exception {
        if(cellphone==null || cellphone.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        if(StringUtils.isEmpty(ctcode)){
            ctcode="86";
        }
        data.put("countrycode", ctcode);
        data.put("cellphone", cellphone);
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.smsCheckPhoneUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.logoutUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
    }


}

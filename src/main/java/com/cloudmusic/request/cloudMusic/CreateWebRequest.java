package com.cloudmusic.request.cloudMusic;

import com.cloudmusic.result.Result;
import com.cloudmusic.utils.CloudMusicUtil;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装url请求数据
 * Created by xuzijia
 * 2018/5/18 19:46
 */
public class CreateWebRequest {

    /**
     * 发送请求
     * @param url api接口地址
     * @param data 请求参数
     * @param cookie 请求cookie
     * @return 如果成功返回json结果数据
     */
    public static String createWebPostRequest(String url, Map<String, Object> data, Map<String, String> cookie) {
        try {
            //得到加密参数
            Map<String, String> arithmeticParam = CloudMusicUtil.arithmetic(data);
            //发起请求
            Document document = Jsoup.connect(url).
                    userAgent(randomUserAgent()).
                    data(arithmeticParam).
                    //解决评论接口460问题
                    cookies(cookie).cookie("_ntes_nuid","1234567890123456").
                    //header请求头
                            header("Accept", "*/*").
                            header("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4").
                            header("Connection", "keep-alive").
                            header("Content-Type", "application/x-www-form-urlencoded").
                            header("Referer", "http://music.163.com").
                            header("Host", "music.163.com").
                            ignoreContentType(true).
                            post();
            //返回结果数据
            String result = document.text();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "找不到资源")).toString();
        }
    }

    public static String createWebPostRequestByReferer(String url, Map<String, Object> data, Map<String, String> cookie,String musicId) {
        try {
            //得到加密参数
            Map<String, String> arithmeticParam = CloudMusicUtil.arithmetic(data);
            //发起请求
            Document document = Jsoup.connect(url).
                    userAgent(randomUserAgent()).
                    data(arithmeticParam).
                    //解决评论接口460问题
                            cookies(cookie).cookie("_ntes_nuid","1234567890123456").
                    //header请求头
                            header("Accept", "*/*").
                            header("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4").
                            header("Connection", "keep-alive").
                            header("Content-Type", "application/x-www-form-urlencoded").
                            header("Referer", "hhttps://music.163.com/song?id="+musicId).
                            header("Host", "music.163.com").
                            ignoreContentType(true).
                            post();
            //返回结果数据
            String result = document.text();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "找不到资源")).toString();
        }
    }

    /**
     * 发送登陆请求,登陆成功保存cookie
     * @param url api接口地址
     * @param data 请求参数
     * @param response
     * @param request
     * @return 如果成功返回json结果数据
     */
//    public static String createLoginRequest(String url, Map<String, Object> data, Map<String,String> cookie, HttpServletResponse response) {
//        try {
//            //得到加密参数
//            Map<String, String> arithmeticParam = CloudMusicUtil.arithmetic(data);
//            //发起请求
//            Connection.Response res = Jsoup.connect(url).
//                    userAgent(randomUserAgent()).
//                    data(arithmeticParam).cookies(cookie).
//                    //header请求头
//                            header("Accept", "*/*").
//                            header("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4").
//                            header("Connection", "keep-alive").
//                            header("Content-Type", "application/x-www-form-urlencoded").
//                            header("Referer", "http://music.163.com").
//                            header("Host", "music.163.com").
//                            ignoreContentType(true).method(Connection.Method.POST).execute();
//            Map<String, String> cookies = res.cookies();
//            //设置cookie
//            String token = cookies.get("MUSIC_U");
//            System.out.println(cookies.get("MUSIC_U"));
//            Cookie MUSIC_U=new Cookie("MUSIC_U",cookies.get("MUSIC_U"));
//            Cookie __csrf=new Cookie("__csrf",cookies.get("__csrf"));
//            Cookie appver=new Cookie("__csrf",cookies.get("1.5.9"));
//            Cookie os=new Cookie("__csrf",cookies.get("osx"));
//            Cookie channel=new Cookie("__csrf",cookies.get("netease"));
//            Cookie osver=new Cookie("__csrf",cookies.get("%E7%89%88%E6%9C%AC%2010.13.2%EF%BC%88%E7%89%88%E5%8F%B7%2017C88%EF%BC%89"));
//            response.addCookie(MUSIC_U);
//            response.addCookie(__csrf);
//            response.addCookie(appver);
//            response.addCookie(os);
//            response.addCookie(channel);
//            response.addCookie(osver);
//            response.addCookie(__csrf);
//            Map<String,Object> result=new HashMap<>();
//            if(token!=null&&!"".equals(token)){
//                result.put("code",200);
//                result.put("token",token);
//            }else{
//                result.put("code",500);
//                result.put("msg","获取不到token,登录失败");
//            }
//            //返回结果数据
////            return new JSONObject(result).toString();
//            return res.body();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new JSONObject(new Result(500, "找不到资源")).toString();
//        }
//    }
    public static String createLoginRequest(String url, Map<String, String> data, HttpServletResponse response, HttpServletRequest request) {
        try {
            //发起请求
            Connection.Response res = Jsoup.connect(url).
                    userAgent(randomUserAgent()).
                    data(data).ignoreContentType(true).method(Connection.Method.GET).execute();
            Map<String, String> cookies = res.cookies();
            //设置cookie
            String token = cookies.get("MUSIC_U");
            System.out.println(cookies.get("MUSIC_U"));
            Cookie MUSIC_U=new Cookie("MUSIC_U",cookies.get("MUSIC_U"));

            response.addCookie(MUSIC_U);
            MUSIC_U.setPath("/api");
            Map<String,Object> result=new HashMap<>();
            if(token!=null&&!"".equals(token)){
                result.put("code",200);
                result.put("token",token);
            }else{
                result.put("code",500);
                result.put("msg","获取不到token,登录失败");
            }
            //返回结果数据
//            return new JSONObject(result).toString();
            return res.body();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "找不到资源")).toString();
        }
    }

    /**
     * 获取cookie 需要登陆的接口需要该cookie才能访问
     * @param request
     * @return cookie信息
     */
    public static Map<String,String> getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map<String,String> cookie=new HashMap<>();
        if(cookies==null){
            return cookie;
        }
        for (Cookie c:cookies){
            cookie.put(c.getName(),c.getValue());
        }
        return cookie;
    }

    /**
     * 返回随机的UserAgent
     * @return
     */
    private static String randomUserAgent() {
        String[] userAgents = {
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
                "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_2 like Mac OS X) AppleWebKit/603.2.4 (KHTML, like Gecko) Mobile/14F89;GameHelper",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/603.2.4 (KHTML, like Gecko) Version/10.1.1 Safari/603.2.4",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A300 Safari/602.1",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)",
                "Mozilla/5.0 (Windows NT 6.3; Win64, x64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/13.10586",
                "Mozilla/5.0 (iPad; CPU OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A300 Safari/602.1"
        };
        int num = (int) Math.floor(Math.random() * userAgents.length);
        return userAgents[num];
    }

}

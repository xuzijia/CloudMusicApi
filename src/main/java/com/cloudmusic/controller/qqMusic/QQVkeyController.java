package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 获取vkey
 * @date 2019/1/17 09:39
 */
@RestController
@RequestMapping("/qq")
public class QQVkeyController {
    @RequestMapping("/getVkey")
    public String getVkey(String id){
        try {
            String url = "https://u.y.qq.com/cgi-bin/musicu.fcg?data=%7B%22req%22%3A%7B%22module%22%3A%22CDN.SrfCdnDispatchServer%22%2C%22method%22%3A%22GetCdnDispatch%22%2C%22param%22%3A%7B%22guid%22%3A%227403626864%22%2C%22calltype%22%3A0%2C%22userip%22%3A%22%22%7D%7D%2C%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%227403626864%22%2C%22songmid%22%3A%5B%22"+id+"%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%222295443695%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A2295443695%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D";
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Accept", "*/*");
            headers.put("Accept-Encoding", "gzip, deflate, sdch, br");
            headers.put("Accept-Language", "zh-CN,zh;q=0.8");
            headers.put("Content-Type", "application/json;charset=UTF-8");
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64)");
            headers.put("Referer", "https://y.qq.com/portal/player.html");
            String result = CreateQQWebRequest.createWebGetRequest(url,new HashMap<>(),headers);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

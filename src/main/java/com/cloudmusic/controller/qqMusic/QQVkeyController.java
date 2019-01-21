package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getVkey(String mid){
        if(mid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("songmid",mid);
        data.put("filename","C400"+mid+".m4a");
        data.put("guid",QQMusicApiUrl.guid);
        data.put("platform","yqq");
        data.put("loginUin","0");
        data.put("hostUin","0");
        data.put("cid","205361747");
        data.put("needNewCode","0");
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.vkeyUrl, data);
        return result;
    }
}

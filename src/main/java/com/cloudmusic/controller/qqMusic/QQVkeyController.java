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
        String guid =Math.abs((long)(Math.random() * 2147483647) * (int)(System.currentTimeMillis() * 1000) % 10000000000l)+"";
        String  filename = "C400" + mid + ".m4a";

        Map<String, String> data = new HashMap<>();
        data.put("format", "json");
        data.put("cid", "205361747");
        data.put("outCharset", "utf8");
        data.put("uin", "0");
        data.put("songmid", mid);
        data.put("filename", filename);
        data.put("guid", guid);

        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.vkeyUrl, data);
        return result;
    }
}

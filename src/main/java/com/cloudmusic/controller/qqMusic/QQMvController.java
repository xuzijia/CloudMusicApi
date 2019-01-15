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
 * @description 视频mv相关接口
 * @date 2019/1/10 16:15
 */
@RestController
@RequestMapping("/qq")
public class QQMvController {
    /**
     * 获取mv列表
     * @param lan mv分类
     * @return mv列表数据
     */
    @RequestMapping("/mv/list")
    public String getMvList(String lan){
        if(lan==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("lan",lan);
        data.put("cmd","shoubo");
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.mvListUrl, data);
        return result;
    }
}

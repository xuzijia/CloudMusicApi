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
 * @description 有关专辑相关接口
 * @date 2019/1/12 09:11
 */
@RequestMapping("/qq")
@RestController
public class QQAlbumController {
    /**
     * 获取专辑详情
     * @param albummid 专辑id 必传
     * @return 专辑详情
     */
    @RequestMapping("/album/detail")
    public String getSingerSongsList(String albummid){
        if(albummid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("albummid",albummid);
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.albumDetailUrl, data);
        return result;
    }
}

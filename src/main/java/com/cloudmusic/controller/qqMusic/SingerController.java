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
 * @description 歌手相关接口
 * @date 2019/1/10 12:41
 */
@RestController
@RequestMapping("/qq")
public class SingerController {
    /**
     * 获取歌手详情
     * @param singermid 歌手id 必传
     * @return 排行榜歌曲数据
     */
    @RequestMapping("/singer/detail")
    public String getRankDetail(String singermid){
        if(singermid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("singermid",singermid);
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.singerDetailUrl, data,false);
        return result;
    }
}

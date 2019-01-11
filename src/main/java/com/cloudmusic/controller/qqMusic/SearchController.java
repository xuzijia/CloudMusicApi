package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author simple
 * @description 搜索相关接口
 * @date 2019/1/10 16:04
 */
@RestController
@RequestMapping("/qq")
public class SearchController {
    /**
     * 获取热门搜索
     * @return 排行榜列表
     */
    @RequestMapping("/search/hotkey")
    public String getRankList(){
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.hotSearchKeyUrl, new HashMap<>(),false);
        return result;
    }
}

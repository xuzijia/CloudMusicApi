package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author simple
 * @description 首页相关接口
 * @date 2019/1/10 11:47
 */
@RestController
@RequestMapping("/qq")
public class RecommendController {
    /**
     * 获取轮播图列表数据
     * @return 轮播图列表数据
     */
    @RequestMapping("/recommend/banner")
    public String getRankList(){
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.bannerUrl, new HashMap<>(),false);
        return result;
    }
}

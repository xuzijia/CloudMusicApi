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
 * @description 有关qq音乐的歌单接口
 * @date 2019/1/10 11:18
 */
@RestController
@RequestMapping("/qq")
public class PlayListController {
    /**
     * 获取排行榜列表数据
     * @return 排行榜列表
     */
    @RequestMapping("/rank/list")
    public String getRankList(){
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.rankUrl, new HashMap<>(),true);
        return result;
    }
    /**
     * 获取排行榜中的歌曲数据
     * @param topid 排行榜id 必传
     * @return 排行榜歌曲数据
     */
    @RequestMapping("/rank/detail")
    public String getRankDetail(String topid){
        if(topid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("topid",topid);
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.rankDetailUrl, data,false);
        return result;
    }
    /**
     * 获取歌单详细信息
     * @param disstid 歌单id 必传
     * @return 歌单详细信息
     */
    @RequestMapping("/playlist/detail")
    public String getPlaylistDetail(String disstid){
        if(disstid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("disstid",disstid);
        data.put("type","1");
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.playlistDetailUrl, data,true);
        return result;
    }

}

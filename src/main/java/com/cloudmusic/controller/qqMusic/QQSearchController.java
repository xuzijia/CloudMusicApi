package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 搜索相关接口
 * @date 2019/1/10 16:04
 */
@RestController
@RequestMapping("/qq")
public class QQSearchController {
    /**
     * 获取热门搜索
     * @return 排行榜列表
     */
    @RequestMapping("/search/hotkey")
    public String getSearchKey(){
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.hotSearchKeyUrl, new HashMap<>());
        return result;
    }
    /**
     * 根据搜索关键词歌曲
     * @param keyword 关键词
     * @return 歌曲内容数据
     */
    @RequestMapping("/search/soso")
    public String Search(String keyword,
                         @RequestParam(name = "page",defaultValue = "1")Integer page,
                         @RequestParam(name = "size",defaultValue = "20")Integer size){
        if(keyword==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data= new HashMap<>();
        data.put("w",keyword);
        data.put("remoteplace","txt.yqq.top");
        data.put("cr","1");
        data.put("p",page.toString());
        data.put("n",size.toString());
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.SearchUrl,data);
        return result;
    }

    private String getTypeStr(String type) {
        if(type.equals("song")){
            return "txt.yqq.song";
        }else if(type.equals("mv")){
            return "txt.yqq.mv";
        }else if(type.equals("playlist")){
            return "txt.yqq.playlist";
        } else{
            return "txt.yqq.center";
        }
    }
}

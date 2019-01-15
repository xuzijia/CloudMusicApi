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
 * @description 有关qq音乐的歌单接口
 * @date 2019/1/10 11:18
 */
@RestController
@RequestMapping("/qq")
public class QQPlayListController {
    /**
     * 获取排行榜列表数据
     * @return 排行榜列表
     */
    @RequestMapping("/rank/list")
    public String getRankList(){
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.rankUrl, new HashMap<>());
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
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.rankDetailUrl, data);
        return result;
    }

    /**
     * 获取歌单分类标签
     * @return 歌单分类标签
     */
    @RequestMapping("/playlist/category")
    public String getCategoryList(){
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.playListCategoryUrl, new HashMap<>());
        return result;
    }

    /**
     *
     * @param categoryId 歌单分类id 默认为10000000 表示全部类型
     * @param sortId 歌单排序id 2=最新歌单 5=推荐歌单 默认为推荐歌单 5
     * @param page 当前页数 默认为第一页
     * @param size 显示条数 默认为30
     * @return
     */
    @RequestMapping("/playlist/list")
    public String getPlayList(@RequestParam(name="categoryId",defaultValue = "10000000") String categoryId,
                              @RequestParam(name = "sortId",defaultValue = "5") String sortId,
                              @RequestParam(name = "page",defaultValue = "1")Integer page,
                              @RequestParam(name = "size",defaultValue = "30")Integer size){
        Map<String,String> data=new HashMap<>();
        data.put("categoryId",categoryId);
        data.put("sortId",sortId);
        //计算显示条数
        data.put("sin",String.valueOf((page-1)*size));
        data.put("ein",String.valueOf((page-1)*size+size-1));
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.playListUrl, data);
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
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.playlistDetailUrl, data);
        return result;
    }

}

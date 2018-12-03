package com.cloudmusic.controller;

import com.cloudmusic.api.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关搜索的接口
 * Created by xuzijia
 * 2018/5/25 8:30
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class SearchController {

    /**
     * 获取搜索内容
     * @param keywords *关键词 必填
     * @param limit 搜索数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @param type 搜索类型(默认值：1) 具体类型值： 1:单曲, 10:专辑, 100:歌手, 1000:歌单, 1002:用户, 1004:Mv 1006:歌词, 1009:电台
     * @return 搜索内容
     */
    @RequestMapping("/search")
    public String getSearch(String keywords,Integer limit,Integer offset,String type){
        if (keywords == null || keywords.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        limit=limit==null?30:limit;
        offset = offset == null ? 0 : offset;
        type = type == null ? "1" : type;
        Map<String, Object> data = new HashMap<>();
        data.put("s", keywords);
        data.put("limit", limit);
        data.put("offset", offset);
        data.put("type", type);

        return CreateWebRequest.createWebPostRequest(ApiUrl.searchUrl,data,new HashMap<>());
    }

    /**
     * 获取搜索建议 即搜索框下拉显示的内容
     * @param keywords *关键词 必填
     * @return 搜索建议 包括单曲 专辑 mv 歌单等搜索建议
     */
    @RequestMapping("/search/suggest")
    public String getSearchSuggest(String keywords){
        if (keywords == null || keywords.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("s", keywords);

        return CreateWebRequest.createWebPostRequest(ApiUrl.searchSuggestUrl,data,new HashMap<>());
    }

    /**
     * 获取搜索多重匹配
     * @param keywords *关键词 必填
     * @return 搜索多重匹配
     */
    @RequestMapping("/search/multimatch")
    public String getSearchMultimatch(String keywords){
        if (keywords == null || keywords.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("s", keywords);
        data.put("type", 1);

        return CreateWebRequest.createWebPostRequest(ApiUrl.searchMultimatchUrl,data,new HashMap<>());
    }

    /**
     * 获取热搜关键词
     * @return 热搜关键词
     */
    @RequestMapping("/search/hot")
    public String getSearchHot(){
        Map<String, Object> data = new HashMap<>();
        data.put("type", 1111);//不知道是啥~

        return CreateWebRequest.createWebPostRequest(ApiUrl.searchHotUrl,data,new HashMap<>());
    }
}

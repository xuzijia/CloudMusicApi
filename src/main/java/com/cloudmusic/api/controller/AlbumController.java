package com.cloudmusic.api.controller;

import com.cloudmusic.conf.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关专辑的接口
 * Created by xuzijia
 * 2018/5/23 16:06
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class AlbumController {

    /**
     * 获取专辑内容
     * @param id *专辑id 必传
     * @return 专辑内容
     */
    @RequestMapping("/album/detail")
    public String getAlbumDetail(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        String url = ApiUrl.albumDetailUrl.replace("{id}", id);
        return CreateWebRequest.createWebPostRequest(url,new HashMap<>(),new HashMap<>());
    }

    /**
     * 获取新碟上架列表
     * @param type 新碟分类 可选值：ALL, ZH,EA,KR,JP(默认值：ALL)
     * @param limit 新碟数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @return 专辑内容
     */
    @RequestMapping("/album/new")
    public String getNewAlbum(String type,Integer limit,Integer offset){
        limit=limit==null?30:limit;
        offset = offset == null ? 0 : offset;
        type = type == null ? "ALL" : type;
        Map<String, Object> data = new HashMap<>();
        data.put("limit", limit);
        data.put("offset", offset);
        data.put("area", type);
        data.put("total",true);
        return CreateWebRequest.createWebPostRequest(ApiUrl.albumNewUrl,data,new HashMap<>());
    }

    /**
     * 获取轮播图数据
     * @param clientType 客户端类型 默认值为 "pc"
     * @return
     */
    @RequestMapping("/banner/getBanner")
    public String getBanner(@RequestParam(defaultValue = "pc") String clientType){
        return CreateWebRequest.createWebPostRequest(ApiUrl.bannerUrl.replace("{clientType}",clientType),new HashMap<>(),new HashMap<>());
    }

}

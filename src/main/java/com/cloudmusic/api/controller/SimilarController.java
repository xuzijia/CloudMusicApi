package com.cloudmusic.api.controller;

import com.cloudmusic.conf.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关相似的接口
 * Created by xuzijia
 * 2018/5/22 21:55
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class SimilarController {

    /**
     * 获取相似歌手
     * @param id *歌手id 必传
     * @return 相似歌手列表
     */
    @RequestMapping("/simi/artist")
    public String getSimiArtist(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("artistid",id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.simiArtistUrl,data,new HashMap<>());
    }

    /**
     * 获取相似歌单
     * @param id *歌曲id 必传
     * @return 相似歌单列表
     */
    @RequestMapping("/simi/playlist")
    public String getSimiPlaylist(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("songid",id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.simiPlaylistUrl,data,new HashMap<>());
    }

    /**
     * 获取相似Mv
     * @param id *Mvid 必传
     * @return 相似歌单列表
     */
    @RequestMapping("/simi/mv")
    public String getSimiMv(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("mvid",id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.simiMvUrl,data,new HashMap<>());
    }

    /**
     * 获取相似歌曲
     * @param id *歌曲id 必传
     * @return 相似歌曲列表
     */
    @RequestMapping("/simi/song")
    public String getSimiSong(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("songid",id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.simiSongUrl,data,new HashMap<>());
    }

    /**
     * 获取最近5个听了这首歌的用户
     * @param id *歌曲id 必传
     * @return 最近5个听了这首歌的用户
     */
    @RequestMapping("/user/song")
    public String getSimiUser(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("songid",id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.simiUserUrl,data,new HashMap<>());
    }
}

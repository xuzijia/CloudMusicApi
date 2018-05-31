package com.cloudmusic.api.controller;

import com.cloudmusic.utils.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关歌手的接口
 * Created by xuzijia
 * 2018/5/22 20:12
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class ArtistController {

    /**
     * 获取歌手列表
     * @param cat 歌手类型(默认值：1001 华语男歌手)
     * @param limit 歌手数量(默认值：30)
     * @param offset 偏移数量 用于分页(默认值：0)
     * @return 歌手列表数据
     */
    @RequestMapping("/artist/list")
    public String getArtistList(String cat, Integer limit, Integer offset) throws Exception {
        cat=cat==null?"1001":cat;
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        Map<String, Object> data = new HashMap<>();
        data.put("categoryCode", cat);//具体值请看最下面注释
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.artistListUrl, data, new HashMap<>());
    }

    /**
     * 获取热门歌手列表
     * @param limit 歌手数量(默认值：30)
     * @param offset 偏移数量 用于分页(默认值：0)
     * @return 热门歌手列表
     */
    @RequestMapping("/artist/hot")
    public String getHotArtistList(Integer limit, Integer offset) throws Exception {
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        Map<String, Object> data = new HashMap<>();
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.artistListHotUrl, data, new HashMap<>());
    }

    /**
     * 获取歌手热门单曲
     * @param id *歌手id 必传
     * @return 歌手热门单曲
     */
    @RequestMapping("/artist/song")
    public String getArtistSong(String id) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        String url = ApiUrl.artistSongUrl.replace("{id}", id);
        return CreateWebRequest.createWebPostRequest(url, new HashMap<>(), new HashMap<>());
    }

    /**
     * 获取歌手Mv信息
     * @param id *歌手id 必传
     * @param limit mv数量(默认值：30)
     * @param offset 偏移数量 用于分页(默认值：0)
     * @return 歌手Mv信息
     */
    @RequestMapping("/artist/mv")
    public String getArtistMv(String id, Integer limit, Integer offset) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        data.put("artistId", id);
        data.put("offset", offset);
        data.put("limit", limit);
        return CreateWebRequest.createWebPostRequest(ApiUrl.artistMvUrl, data, new HashMap<>());
    }

    /**
     * 获取歌手专辑
     * @param id *歌手id 必传
     * @param limit 专辑数量(默认值：30)
     * @param offset 偏移数量 用于分页(默认值：0)
     * @return 歌手专辑
     */
    @RequestMapping("/artist/album")
    public String getArtistAlbum(String id, Integer limit, Integer offset) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        data.put("offset", offset);
        data.put("limit", limit);
        String url = ApiUrl.artistAlbumUrl.replace("{id}", id);
        return CreateWebRequest.createWebPostRequest(url, data, new HashMap<>());
    }

    /**
     * 获取歌手描述
     * @param id *歌手id 必传
     * @return 歌手描述
     */
    @RequestMapping("/artist/desc")
    public String getArtistDesc(String id) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.artistDescUrl, data, new HashMap<>());
    }

    //---需要登陆的接口---

    /**
     * 收藏歌手(需要登陆)
     * @param id *歌手id 必传
     * @param request
     * @return 是否收藏成功
     */
    @RequestMapping("/artist/sub")
    public String ArtistSub(String id, HttpServletRequest request) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("artistId", id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.ArtistSubUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 取消收藏歌手
     * @param id *歌手id 必传
     * @param request
     * @return 是否取消收藏成功
     */
    @RequestMapping("/artist/unsub")
    public String ArtistUnsub(String id, HttpServletRequest request) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("artistId", id);
        data.put("artistIds", "["+id+"]");
        return CreateWebRequest.createWebPostRequest(ApiUrl.ArtistUnsubUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 收藏歌手列表
     * @param limit 歌手数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @param request
     * @return 收藏歌手列表
     */
    @RequestMapping("/artist/sublist")
    public String ArtistSubList(Integer limit,Integer offset,HttpServletRequest request) throws Exception {
        HashMap<String, Object> data = new HashMap<>();
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        data.put("offset", offset);
        data.put("total", "true");
        data.put("limit", limit);
        return CreateWebRequest.createWebPostRequest(ApiUrl.ArtistSublistUrl, data, CreateWebRequest.getCookie(request));
    }
}


/*
    categoryCode 取值:

    入驻歌手 5001
    华语男歌手 1001
    华语女歌手 1002
    华语组合/乐队 1003
    欧美男歌手 2001
    欧美女歌手 2002
    欧美组合/乐队 2003
    日本男歌手 6001
    日本女歌手 6002
    日本组合/乐队 6003
    韩国男歌手 7001
    韩国女歌手 7002
    韩国组合/乐队 7003
    其他男歌手 4001
    其他女歌手 4002
    其他组合/乐队 4003
 */
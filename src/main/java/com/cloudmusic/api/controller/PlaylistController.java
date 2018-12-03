package com.cloudmusic.api.controller;

import com.cloudmusic.conf.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import com.cloudmusic.utils.ResultCacheUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关歌单的接口
 * Created by xuzijia
 * 2018/5/22 22:25
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class PlaylistController {

    @Autowired
    private ResultCacheUtils resultCacheUtils;

    /**
     * 获取歌单分类
     * @return 歌单分类列表
     */
    @RequestMapping("/playlist/catlist")
    public String getPlayListCatList(){
        return CreateWebRequest.createWebPostRequest(ApiUrl.playlistCatListUrl,new HashMap<>(),new HashMap<>());
    }

    /**
     * 获取热门歌单分类
     * @return 热门歌单分类列表
     */
    @RequestMapping("/playlist/hotcatlist")
    public String getPlayListHotCatList(){
        return CreateWebRequest.createWebPostRequest(ApiUrl.playlistHotCatListUrl,new HashMap<>(),new HashMap<>());
    }

    /**
     * 获取歌单详细内容
     * @param id *歌单id 必传
     * @return 歌单详细内容
     */
    @RequestMapping("/playlist/detail")
    public String getPlayListDetail(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",id);
        data.put("n",100000);
        String url = ApiUrl.playlistDetailUrl.replace("{id}", id);
        String key="/playlist/detail/"+id;
        return resultCacheUtils.createCache(key,url,data,60*60*2);
    }


    /**
     * 获取推荐的歌单(登陆后可以获取到定制化的歌单 未登录可以或者编辑推荐的歌单)
     *
     * @param limit 歌单数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @param request
     * @return 推荐的歌单
     */
    @RequestMapping("/playlist/personalized")
    public String getPlaylistPersonalized(Integer limit,Integer offset,HttpServletRequest request) {
        limit=limit==null?30:limit;
        offset=offset==null?0:offset;
        HashMap<String, Object> data = new HashMap<>();
        data.put("n",1000);
        data.put("limit",limit);
        data.put("offset",offset);
        data.put("total",true);

        return CreateWebRequest.createWebPostRequest(ApiUrl.playlistPersonalizedUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 获取精品歌单
     * @param cat 歌单分类(默认值：全部) 该分类可从playlist/catlist获取
     * @param limit 歌单数量(默认值：20)
     * @return 精品歌单
     */
    @RequestMapping("/playlist/highquality")
    public String getPlayListHighquality(String cat,Integer limit){
        HashMap<String, Object> data = new HashMap<>();
        cat=cat==null?"全部":cat;
        limit=limit==null?20:limit;
        data.put("cat",cat);
        data.put("limit",limit);
        return CreateWebRequest.createWebPostRequest(ApiUrl.playlistHighqualityUrl,data,new HashMap<>());
    }


    /**
     * 获取网友精选碟歌单
     * @param cat 歌单分类(默认值：全部) 该分类可从playlist/catlist获取
     * @param order 可选值为 'new' 和 'hot', 分别对应最新和最热(默认值：hot)
     * @param limit 歌单数量(默认值：20)
     * @param offset 偏移量(默认值：0)
     * @return 网友精选碟歌单
     */
    @RequestMapping("/playlist/list")
    public String getPlayList(String cat,String order,Integer limit,Integer offset){
        HashMap<String, Object> data = new HashMap<>();
        cat=cat==null?"全部":cat;
        limit=limit==null?20:limit;
        offset=offset==null?0:offset;
        order=order==null?"hot":order;
        data.put("cat",cat);
        data.put("order",order);
        data.put("limit",limit);
        data.put("offset",offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.playlistListUrl,data,new HashMap<>());
    }

    /**
     * 获取指定用户歌单
     * @param id *用户id 必传
     * @return 用户歌单
     */
    @RequestMapping("/playlist/user")
    public String getPlayList(String id){
        HashMap<String, Object> data = new HashMap<>();
        data.put("uid",id);
        data.put("limit",20);//好像没用 但是又不能少这个参数~
        data.put("offset",0);
        return CreateWebRequest.createWebPostRequest(ApiUrl.userPlaylistUrl,data,new HashMap<>());
    }

    /**
     * 新建歌单 (需要登陆)
     * @param name *歌单名字 必传
     * @param request
     * @return 是否新建成功
     */
    @RequestMapping("/playlist/create")
    public String createPlaylist(String name,HttpServletRequest request) {
        if (name == null || name.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("name",name);
        return CreateWebRequest.createWebPostRequest(ApiUrl.createPlaylistUrl,data,CreateWebRequest.getCookie(request));
    }

    /**
     * 收藏/取消收藏歌单 (需要登陆)
     * @param id *歌单id 必传
     * @param type *操作类型(1:收藏 2:取消收藏) 必传
     * @param request
     * @return 是否新建成功
     */
    @RequestMapping("/playlist/sub")
    public String createPlaylist(String id,String type,HttpServletRequest request) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        if (type == null || !type.trim().equals("1") && !type.trim().equals("2")) {
            return new JSONObject(new Result(0, "type参数值错误")).toString();
        }
        type=type.equals("1")?"subscribe":"unsubscribe";
        Map<String,Object> data=new HashMap<>();
        data.put("id",id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.PlaylistSubUrl.replace("{action}",type),data,CreateWebRequest.getCookie(request));
    }

    /**
     * 对歌单添加或删除歌曲 只能操作自己的歌单(需要登陆)
     * @param id *歌单id 必传
     * @param mid *要操作的歌曲id(可多个,用逗号隔开) 必传
     * @param type *操作类型(add:添加 del:删除) 必传
     * @param request
     * @return 是否操作成功
     */
    @RequestMapping("/playlist/tracks")
    public String playlistTracks(String id,String mid,String type,HttpServletRequest request) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数:id")).toString();
        }
        if (mid == null || mid.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数:mid")).toString();
        }
        if (type == null || !type.trim().equals("add") && !type.trim().equals("del")) {
            return new JSONObject(new Result(0, "type参数值错误")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("op",type);
        data.put("pid",id);
        data.put("tracks",mid);
        JSONArray jsonArray = new JSONArray(mid.split(","));
        //将歌曲数据转成json数据
        data.put("trackIds",jsonArray.toString());
        return CreateWebRequest.createWebPostRequest(ApiUrl.PlaylistTracksUrl,data,CreateWebRequest.getCookie(request));
    }

}

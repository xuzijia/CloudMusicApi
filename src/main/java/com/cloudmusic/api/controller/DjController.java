package com.cloudmusic.api.controller;

import com.cloudmusic.conf.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关电台的接口
 * Created by xuzijia
 * 2018/5/25 10:03
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class DjController {

    /**
     * 获取推荐电台(需要登陆)
     * @param request
     * @return 推荐电台
     */
    @RequestMapping("/dj/recommend")
    public String getDjRecommend(HttpServletRequest request){
        return CreateWebRequest.createWebPostRequest(ApiUrl.djRecommendUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
    }

    /**
     * 获取电台分类
     * @return 电台分类
     */
    @RequestMapping("/dj/catelist")
    public String getDjCatelist(){
        return CreateWebRequest.createWebPostRequest(ApiUrl.djCatelistUrl,new HashMap<>(),new HashMap<>());
    }

    /**
     * 获取指定分类电台推荐
     * @param type *电台类型 必传
     * @return 指定分类电台推荐
     */
    @RequestMapping("/dj/recommendType")
    public String getDjRecommendType(String type){
        if (type == null || type.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("cateId",type);
        return CreateWebRequest.createWebPostRequest(ApiUrl.djRecommendTypeUrl,data,new HashMap<>());
    }


    /**
     * 获取指定用户电台(不是用户收藏电台)
     *
     * @param id *用户id 必传
     * @return 用户电台
     */
    @RequestMapping("/user/dj")
    public String getUserDj(String id) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("limit", 30);
        data.put("offset", 0);

        return CreateWebRequest.createWebPostRequest(ApiUrl.userDjUrl.replace("{id}",id), data, new HashMap<>());
    }


    /**
     * 获取电台的订阅列表(需要登陆)
     * @param limit 电台数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @return 电台的订阅列表
     */
    @RequestMapping("/dj/sublist")
    public String getDjSublist(Integer limit,Integer offset,HttpServletRequest request){
        HashMap<String, Object> data = new HashMap<>();
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        data.put("limit", limit);
        data.put("offset", offset);
        data.put("total", "true");
        return CreateWebRequest.createWebPostRequest(ApiUrl.djSublistUrl,data,CreateWebRequest.getCookie(request));
    }

    /**
     * 获取电台详情
     * @param id *电台id 必传
     * @return 电台详情
     */
    @RequestMapping("/dj/detail")
    public String getDjDetail(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.djDetailUrl,data,new HashMap<>());
    }

    /**
     * 获取电台节目
     * @param id *电台id 必传
     * @param limit 节目数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @return 电台节目列表
     */
    @RequestMapping("/dj/program")
    public String getDjProgram(String id,Integer limit,Integer offset){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        data.put("radioId", id);
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.djProgramUrl,data,new HashMap<>());
    }

    /**
     * 订阅或者取消订阅电台(需要登陆)
     * @param id *电台id 必传
     * @param action *1为订阅  2为取消订阅 必传
     * @param request
     * @return 是否操作成功
     */
    @RequestMapping("/dj/sub")
    public String getDjSub(String id,String action,HttpServletRequest request){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        if (action == null || action.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("id",id);

        action=action.equals("1")?"sub":"unsub";
        return CreateWebRequest.createWebPostRequest(ApiUrl.djSubUrl.replace("{action}",action),data,CreateWebRequest.getCookie(request));
    }


    /**
     * 推荐的电台
     * @return 音乐列表
     */
    @RequestMapping("/dj/personalized")
    public String getDjPersonalized(HttpServletRequest request) throws Exception {
        return CreateWebRequest.createWebPostRequest(ApiUrl.djPersonalizedUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
    }

    /**
     * 推荐的节目
     * @return 音乐列表
     */
    @RequestMapping("/program/personalized")
    public String getprogramPersonalized() throws Exception {
        Map<String,Object> data=new HashMap<>();
        data.put("type","recommend");
        return CreateWebRequest.createWebPostRequest(ApiUrl.programPersonalizedUrl,data,new HashMap<>());
    }
}

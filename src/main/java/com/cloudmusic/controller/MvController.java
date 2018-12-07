package com.cloudmusic.controller;

import com.cloudmusic.api.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关Mv的接口
 * Created by xuzijia
 * 2018/5/23 10:13
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class MvController {

    /**
     * 获取Mv排行
     * @param limit mv数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @return Mv排行数据
     */
    @RequestMapping("/mv/top")
    public String getTopMvList(Integer limit,Integer offset){
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        Map<String, Object> data = new HashMap<>();
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.topMvUrl,data,new HashMap<>());
    }

    /**
     * 获取最新mv列表
     * @param limit mv数量(默认值：30)
     * @param offset 偏移量(默认值：0)
     * @return 最新Mv数据
     */
    @RequestMapping("/mv/new")
    public String getNewMvList(Integer limit,Integer offset){
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        Map<String, Object> data = new HashMap<>();
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.MvNewUrl,data,new HashMap<>());
    }

    /**
     * todo 待完善！
     * 获取推荐mv
     * @return 推荐mv数据
     */
    @RequestMapping("/mv/personalized")
    public String getPersonalizedMvList(HttpServletRequest request){
        return CreateWebRequest.createWebPostRequest(ApiUrl.recommendMvUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
    }

    /**
     * 获取Mv信息
     * @param id *mvid 必传
     * @return Mv信息
     */
    @RequestMapping("/mv/detail")
    public String getMvDetail(String id){
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("id",id);
        return CreateWebRequest.createWebPostRequest(ApiUrl.mvDetailUrl,data,new HashMap<>());
    }
    /**
     * 获取视频信息
     * @param vid *vid 必传
     * @return Mv信息
     */
    @RequestMapping("/video/detail")
    public String getVideoDetail(String vid){
        if (vid == null || vid.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        data.put("id",vid);
        return CreateWebRequest.createWebPostRequest(ApiUrl.videoUrl,data,new HashMap<>());
    }

    /**
     * 播放mv 由于网易云音乐做了防盗链 上面获取到的链接不能直接播放 访问该地址可播放mv
     * jsoup默认请求数据大小为1M 视频文件较大 设置为300M
     * @param url *Mv播放地址 必传
     * @return Mv信息
     */
    @RequestMapping("/mv/player")
    public void playMv(String url, HttpServletResponse response) throws IOException {
        Connection.Response execute = Jsoup.connect(url).header("Referer", "http://music.163.com/").
                header("Content-Type", "video/mp4").header("Location", url).ignoreContentType(true).maxBodySize(300*1024*1024).
                execute();
        response.setHeader("Content-Type","video/mp4");
        response.getOutputStream().write(execute.bodyAsBytes());

    }



}

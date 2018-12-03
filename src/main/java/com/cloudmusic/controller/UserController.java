package com.cloudmusic.controller;

import com.cloudmusic.api.ApiUrl;
import com.cloudmusic.utils.CreateWebRequest;
import com.cloudmusic.utils.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：需要登陆才能操作的API接口
 * Created by xuzijia
 * 2018/5/23 21:15
 * 该接口的所有API需要登陆后才能操作
 */
@RestController
public class UserController {

    /**
     * 获取每天推荐歌曲(需要登陆)
     *
     * @param request
     * @return 每日推荐歌曲
     */
    @RequestMapping("/user/recommend")
    public String getUserRecommend(HttpServletRequest request) {
        return CreateWebRequest.createWebPostRequest(ApiUrl.userRecommendUrl, new HashMap<>(), CreateWebRequest.getCookie(request));
    }

    /**
     * 添加或者移除歌曲到喜欢的音乐歌单(需要登陆)
     * @param id *歌曲id 必传
     * @param action 操作类型(true:添加 false:移除 默认值：true)
     * @param request
     * @return  是否操作成功
     */
    @RequestMapping("/song/like")
    public String getUserRecommend(String id,String action,HttpServletRequest request) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        action=action==null? "true":action;
        Map<String, Object> data = new HashMap<>();
        data.put("trackId", id);
        data.put("like", action);
        String url = ApiUrl.likeSongUrl.replace("{alg}","itembased").replace("{trackId}",id).replace("{time}","25");
        return CreateWebRequest.createWebPostRequest(url, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 获取每天推荐歌单(需要登陆)
     *
     * @param request
     * @return 每日推荐歌单
     */
    @RequestMapping("/playlist/recommend")
    public String getPlaylistRecommend(HttpServletRequest request) {
        return CreateWebRequest.createWebPostRequest(ApiUrl.playlistRecommendUrl, new HashMap<>(), CreateWebRequest.getCookie(request));
    }

    /**
     * 获取用户私人FM(需要登陆)
     *
     * @param request
     * @return 上一首 当前 下一首 三首歌曲信息
     */
    @RequestMapping("/user/personal_fm")
    public String getUserPersonalFm(HttpServletRequest request) {
        return CreateWebRequest.createWebPostRequest(ApiUrl.userPersonalFmUrl, new HashMap<>(), CreateWebRequest.getCookie(request));
    }


    /**
     * 把音乐从私人 FM 中移除至垃圾桶(需要登陆)
     *
     * @param id *歌曲id 必传
     * @param request
     * @return 是否移除成功
     */
    @RequestMapping("/user/fm_trash")
    public String getUserPersonalFm(String id,HttpServletRequest request) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("songId", id);
        String url = ApiUrl.fmTrashUrl.replace("{alg}","RT").replace("{songId}",id).replace("{id}","25");
        return CreateWebRequest.createWebPostRequest(url, data, CreateWebRequest.getCookie(request));
    }


    /**
     * 每日签到(需要登陆)
     *
     * @param type    0为安卓端签到  1为web/pc端签到 (默认值：0)
     * @param request
     * @return 是否签到成功
     */
    @RequestMapping("/user/signin")
    public String DailySignin(String type, HttpServletRequest request) {
        type = type == null ? "0" : type;
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        return CreateWebRequest.createWebPostRequest(ApiUrl.userDailySigninUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 获取云盘数据(需要登陆)
     *
     * @param limit   歌曲数量(默认值：20)
     * @param offset  偏移量(默认值：0)
     * @param request
     * @return 云盘数据
     */
    @RequestMapping("/user/cloud")
    public String getUserCloud(Integer limit, Integer offset, HttpServletRequest request) {
        limit = limit == null ? 20 : limit;
        offset = offset == null ? 0 : offset;
        Map<String, Object> data = new HashMap<>();
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.userCloudUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 获取指定用户信息
     *
     * @param id      *用户id 必传
     * @param request
     * @return 用户详情
     */
    @RequestMapping("/user/detail")
    public String getUserDetail(String id, HttpServletRequest request) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        return CreateWebRequest.createWebPostRequest(ApiUrl.userDetailUrl.replace("{id}", id), new HashMap<>(), CreateWebRequest.getCookie(request));
    }

    /**
     * 获取登陆用户信息, 歌单，收藏，mv, dj 数量(需要登陆)
     *
     * @param request
     * @return 用户信息, 歌单，收藏，mv, dj 数量
     */
    @RequestMapping("/user/subcount")
    public String getUserSubCount(HttpServletRequest request) {
        return CreateWebRequest.createWebPostRequest(ApiUrl.userSubcountUrl, new HashMap<>(), CreateWebRequest.getCookie(request));
    }

    /**
     * 更新用户歌单(需要登陆) todo 该接口有问题
     *
     * @param id      *歌单id 必传
     * @param name    歌单名字
     * @param desc    歌单描述
     * @param tags    歌单标签
     * @param request
     * @return 是否更新成功
     */
    @RequestMapping("/user/update_playlist")
    public String updatePlaylist(String id, String name, String desc, String tags, HttpServletRequest request) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        name = name == null ? "" : name;
        desc = desc == null ? "" : desc;
        tags = tags == null ? "" : tags;

        //封装请求数据
        data.put("/api/playlist/desc/update", "{id:" + id + ",desc:" + desc + "}");
        data.put("/api/playlist/tags/update", "{id:" + id + ",tags:" + tags + "}");
        data.put("/api/playlist/update/name", "{id:" + id + ",name:" + name + "}");

        return CreateWebRequest.createWebPostRequest(ApiUrl.userUpdatePlaylistUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 发送私信(需要登陆)
     *
     * @param ids      *用户id，多个逗号隔开 必传
     * @param msg    *要发送的消息 必传
     * @param request
     * @return 是否发送成功
     */
    @RequestMapping("/send/msg")
    public String sendMsg(String ids,String msg, HttpServletRequest request) {
        if (ids == null || ids.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        if (msg == null || msg.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("type","text");//私信类型：消息类型
        data.put("msg",msg);
        data.put("userIds","["+ids+"]");
        return CreateWebRequest.createWebPostRequest(ApiUrl.sendMsgUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 发送私信(带歌单)(需要登陆)
     *
     * @param ids      *用户id，多个逗号隔开 必传
     * @param msg    *要发送的消息 必传
     * @param playlist    *要发送的歌单
     * @param request
     * @return 是否发送成功
     */
    @RequestMapping("/send/playlist")
    public String sendMsg(String ids,String msg,String playlist, HttpServletRequest request) {
        if (ids == null || ids.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        if (msg == null || msg.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        if (playlist == null || playlist.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("type","playlist");//私信类型：歌单类型
        data.put("msg",msg);
        data.put("userIds","["+ids+"]");
        data.put("id",playlist);
        return CreateWebRequest.createWebPostRequest(ApiUrl.sendMsgUrl, data, CreateWebRequest.getCookie(request));
    }


    /**
     * 更新用户信息(需要登陆)
     *
     * @param gender    gender为0表示保密，1为男性，2为女性
     * @param birthday  生日 时间戳
     * @param nickname  用户名
     * @param province  省份(编码格式)
     * @param city      城市(编码格式)
     * @param signature 签名
     * @param request
     */
    @RequestMapping("/user/update")
    public String updateUser(String gender, String birthday, String nickname, String province, String city, String signature, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("avatarImgId", "0");//暂时不实现头像修改
        data.put("birthday", birthday);
        data.put("gender", gender);
        data.put("nickname", nickname);
        data.put("province", province);
        data.put("city", city);
        data.put("signature", signature);

        return CreateWebRequest.createWebPostRequest(ApiUrl.userUpdateUrl, data, CreateWebRequest.getCookie(request));
    }

    /**
     * 获取用户关注列表
     *
     * @param id *用户id 必传
     * @param limit   用户数量(默认值：20)
     * @param offset  偏移量(默认值：0)
     * @return 用户关注列表
     */
    @RequestMapping("/user/follows")
    public String getUserFollows(String id,Integer limit,Integer offset) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        limit = limit == null ? 20 : limit;
        offset = offset == null ? 0 : offset;
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.userFollowsUrl.replace("{id}",id), data,new HashMap<>());
    }

    /**
     * 获取用户粉丝列表
     *
     * @param id *用户id 必传
     * @param limit   粉丝数量(默认值：20)
     * @param offset  偏移量(默认值：0)
     * @return 用户粉丝列表
     */
    @RequestMapping("/user/followeds")
    public String getUserFolloweds(String id,Integer limit,Integer offset) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        limit = limit == null ? 20 : limit;
        offset = offset == null ? 0 : offset;
        data.put("userId", id);
        data.put("limit", limit);
        data.put("offset", offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.userFollowedsUrl, data,new HashMap<>());
    }

    /**
     * 获取用户动态
     *
     * @param id *用户id 必传
     * @return 用户动态
     */
    @RequestMapping("/user/event")
    public String getUserEvent(String id,Integer limit,Integer offset) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        limit = limit == null ? 20 : limit;
        offset = offset == null ? 0 : offset;
        data.put("time",-1);
        data.put("getcounts",true);
        data.put("limit",limit);
        data.put("offset",offset);
        return CreateWebRequest.createWebPostRequest(ApiUrl.userEventUrl.replace("{id}",id), data,new HashMap<>());
    }

    /**
     * 获取用户播放记录(某些用户关闭查看隐私，则无权限查看)
     *
     * @param id *用户id 必传
     * @param type  type=1 时只返回 weekData, type=0 时返回 allData(默认值：1)
     * @return 用户播放记录
     */
    @RequestMapping("/user/play_record")
    public String getUserPlayRecord(String id,String type) {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> data=new HashMap<>();
        type = type == null ? "1" : type;
        data.put("uid",id);
        data.put("type",type);
        return CreateWebRequest.createWebPostRequest(ApiUrl.userPlayRecordUrl, data,new HashMap<>());
    }

    /**
     * 获取登陆用户动态消息 (需要登陆)
     * 可获取各种动态 , 对应网页版网易云，朋友界面里的各种动态消息 ，如分享的视频，音乐，照片等！
     * @param request
     * @return 动态消息
     */
    @RequestMapping("/event")
    public String getUserPlayRecord(HttpServletRequest request) {
        Map<String,Object> data=new HashMap<>();
        return CreateWebRequest.createWebPostRequest(ApiUrl.evnetUrl,data,CreateWebRequest.getCookie(request));
    }

    /**
     * 获取登独家放送
     * @param request
     * @return 独家放送
     */
    @RequestMapping("/privatecontent")
    public String getPrivatecontentPersonalized(HttpServletRequest request) {
        Map<String,Object> data=new HashMap<>();
        return CreateWebRequest.createWebPostRequest(ApiUrl.privatecontentPersonalizedUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
    }


}

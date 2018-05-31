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
 * 接口功能：有关评论的接口
 * Created by xuzijia
 * 2018/5/18 9:37
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class CommentsController {

    private static Map<String,String> typeMap;
    static{
        //初始化评论类型
        typeMap=new HashMap<>();
        typeMap.put("0","R_SO_4_");//歌曲
        typeMap.put("1","R_MV_5_");//mv
        typeMap.put("2","A_PL_0_");//歌单
        typeMap.put("3","R_AL_3_");//专辑
        typeMap.put("4","A_DJ_1_");//电台
    }
    /**
     * 获取音乐评论
     * @param id *音乐id 必传
     * @param limit 评论数量 (默认值：20)
     * @param offset 偏移量 (默认值：0)
     * @return 音乐评论数据
     */
    @RequestMapping("/comment/music")
    public String getMusicComment(String id, Integer limit, Integer offset) throws Exception {
        return getCommentData(ApiUrl.MusicCommentUrl,id,limit,offset);
    }

    /**
     * 获取歌单评论
     * @param id *歌单id 必传
     * @param limit 评论数量 (默认值：20)
     * @param offset 偏移量 (默认值：0)
     * @return 歌单评论数据
     */
    @RequestMapping("/comment/playlist")
    public String getPlayListComment(String id,Integer limit,Integer offset) throws Exception {
        return getCommentData(ApiUrl.PlayListCommentUrl,id,limit,offset);
    }


    /**
     * 获取专辑评论
     * @param id *专辑id 必传
     * @param limit 评论数量 (默认值：20)
     * @param offset 偏移量 (默认值：0)
     * @return 专辑评论数据
     */
    @RequestMapping("/comment/album")
    public String getAlbumComment(String id, Integer limit, Integer offset) throws Exception {
        return getCommentData(ApiUrl.AlbumCommentUrl,id,limit,offset);
    }

    /**
     * 获取mv评论
     * @param id *mvid 必传
     * @param limit 评论数量 (默认值：20)
     * @param offset 偏移量 (默认值：0)
     * @return mv评论数据
     */
    @RequestMapping("/comment/mv")
    public String getMvComment(String id,Integer limit,Integer offset) throws Exception {
        return getCommentData(ApiUrl.MvCommentUrl,id,limit,offset);
    }

    /**
     * 获取电台节目评论
     * @param id *电台节目id 必传
     * @param limit 评论数量 (默认值：20)
     * @param offset 偏移量 (默认值：0)
     * @return 电台节目评论数据
     */
    @RequestMapping("/comment/dj")
    public String getDjComment(String id,Integer limit,Integer offset) throws Exception {
        return getCommentData(ApiUrl.DjCommentUrl,id,limit,offset);
    }

    /**
     * 给评论点赞或取消赞(需要登陆)
     * @param id *资源id(包括mv,电台等资源) 必传
     * @param cid *评论id 必传
     * @param  action *操作类型(1:点赞 2:取消点赞) 必传
     * @param type *资源类型(对应歌曲 , mv, 专辑 , 歌单 , 电台对应以下类型) 必传
     *              0: 歌曲1: mv2: 歌单3: 专辑4: 电台
     * @return 是否操作成功
     */
    @RequestMapping("/comment/like")
    public String CommentLike(String id, String cid, String action, String type, HttpServletRequest request) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数:id")).toString();
        }
        if (cid == null || cid.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数:cid")).toString();
        }
        if (action == null || !action.trim().equals("1") && !action.equals("2")) {
            return new JSONObject(new Result(0, "参数错误:action")).toString();
        }
        if (type == null || type.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数:type")).toString();
        }
        action=action.equals("1")?"like":"unlike";
        try {
            type=typeMap.get(type);
        }catch (Exception e){
            return new JSONObject(new Result(0, "参数值错误:type")).toString();
        }

        Map<String,Object> data=new HashMap<>();
        data.put("threadId", type+id);
        data.put("commentId",cid);

        return CreateWebRequest.createWebPostRequest(ApiUrl.CommentLikeUrl.replace("{action}",action),data,CreateWebRequest.getCookie(request));
    }


    /**
     * 获取评论
     * @param id id参数
     * @param url API地址
     * @param limit 评论数量 (默认值：20)
     * @param offset 偏移量 (默认值：0)
     * @return 评论数据
     */
    private String getCommentData(String url,String id,Integer limit,Integer offset) throws Exception {
        if(id==null || id.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        url=url.replace("{id}",id);
        //封装参数到map
        Map<String,Object> data=new HashMap();
        limit=limit==null? 20:limit;
        offset=offset==null? 0:offset;
        data.put("limit",limit);
        data.put("offset",offset);
        return CreateWebRequest.createWebPostRequest(url,data,new HashMap<>());
    }
}

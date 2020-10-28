package com.cloudmusic.controller.cloudMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.pojo.CloudCommentsPo;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.result.Result;
import com.cloudmusic.service.CloudCommentsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CloudCommentsController {
    @Autowired
    private CloudCommentsService cloudCommentsServiceImpl;


    @Value("${application.accountInfo.token}")
    private String token;



    @RequestMapping("/batchGetComment")
    public String batchGetComment(String id, HttpServletRequest request) throws Exception {
        if (id == null || id.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",id);
        data.put("n",100000);
        data.put("s",100);
        String url = CloudMusicApiUrl.playlistDetailUrl;
        Map<String, String> cookie = CreateWebRequest.getCookie(request);
        //设置黑胶vip账号cookie
        cookie.put("MUSIC_U",token);

        String returnData = CreateWebRequest.createWebPostRequest(url, data, cookie);


        JSONObject jsonObject=new JSONObject(returnData);
        if(jsonObject.getInt("code")==200){
            JSONArray jsonArray = jsonObject.getJSONObject("playlist").getJSONArray("tracks");
            if(jsonArray.length()>0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    String songName=item.getString("name");
                    String songId=String.valueOf(item.getInt("id"));
                    String singer="";
                    JSONArray ar = item.getJSONArray("ar");
                    for (int a=0;a<ar.length();a++){
                        String name = ar.getJSONObject(a).getString("name");
                        if(a!=ar.length()-1){
                            singer=singer+name+", ";
                        }else{
                            singer=singer+name;
                        }
                    }
                    JSONObject albumItem = item.getJSONObject("al");
                    String cover=albumItem.getString("picUrl");
                    String albumName=albumItem.getString("name");

                    //获取评论
                    String commentData = getCommentData(CloudMusicApiUrl.MusicCommentUrl, songId, 20, 0);
                    JSONObject commentDataItem = new JSONObject(commentData);
                    List<CloudCommentsPo> saveData=new ArrayList<>();
                    if(commentDataItem.getInt("code")==200){
                        JSONArray hotComments = commentDataItem.getJSONArray("hotComments");
                        for (int y=0;y<hotComments.length();y++){
                            JSONObject commentItem = hotComments.getJSONObject(y);
                            String commentUser = commentItem.getJSONObject("user").getString("nickname");
                            String commentDate= String.valueOf(commentItem.getLong("time"));
                            Long likedCount = commentItem.getLong("likedCount");
                            String commentId=String.valueOf(commentItem.getInt("commentId"));
                            String comment =commentItem.getString("content");


                            CloudCommentsPo cloudCommentsPo = new CloudCommentsPo();
                            cloudCommentsPo.setId(commentId);
                            cloudCommentsPo.setAlbumName(albumName);
                            cloudCommentsPo.setCommentTime(commentDate);
                            cloudCommentsPo.setCover(cover);
                            cloudCommentsPo.setLikeCount(likedCount);
                            cloudCommentsPo.setSongId(songId);
                            cloudCommentsPo.setSongName(songName);
                            cloudCommentsPo.setComment(comment);
                            cloudCommentsPo.setCommentUser(commentUser);
                            cloudCommentsPo.setSinger(singer);
                            saveData.add(cloudCommentsPo);
                        }

                    }

                    cloudCommentsServiceImpl.saveAll(saveData);
                    Thread.sleep(200);
                }
            }
        }

        return "success";

    }


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

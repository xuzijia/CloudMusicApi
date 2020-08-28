package com.cloudmusic.controller;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.controller.pojo.vo.MusicResponseVo;
import com.cloudmusic.controller.pojo.vo.SongVo;
import com.cloudmusic.controller.qqMusic.SwitchMusicController;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.utils.Constant;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 汇集核心接口,并封装统一返回数据
 * Created by xuzijia
 * 2018/5/22 15:33
 */
@RestController
@RequestMapping("/core")
public class CoreApiController {

    @Value("${application.accountInfo.token}")
    private String token;
    /**
     * 搜索网易云音乐资源。并整合封装其资源信息
     *
     * @param keywords
     * @return
     */
    @RequestMapping("/getCloudMusicInfo")
    public MusicResponseVo getCloudMusicInfo(String keywords, Integer limit, Integer offset, String type,HttpServletRequest request) throws IOException {
        MusicResponseVo<SongVo> songVoMusicResponseVo = new MusicResponseVo<>();
        if (keywords == null || keywords.trim().equals("")) {
            songVoMusicResponseVo.setCode(Constant.paramErrorCode);
            songVoMusicResponseVo.setMessage(Constant.paramErrorMessage);
            return songVoMusicResponseVo;
        }
        limit = limit == null ? 30 : limit;
        offset = offset == null ? 0 : offset;
        type = type == null ? "1" : type;
        Map<String, Object> data = new HashMap<>();
        data.put("s", keywords);
        data.put("limit", limit);
        data.put("offset", offset);
        data.put("type", type);
        String webPostRequest = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.searchUrlV2, data, new HashMap<>());
        List<SongVo> list=new ArrayList<>();
        JSONObject jsonObject = new JSONObject(webPostRequest);
        if(jsonObject.getInt("code")==200){
            JSONArray songs = jsonObject.getJSONObject("result").getJSONArray("songs");
            if(songs!=null){
                for (int i=0;i<songs.length();i++){
                    SongVo songVo = new SongVo();
                    JSONObject songData = songs.getJSONObject(i);
                    String songName=songData.getString("name");
                    String id=String.valueOf(songData.getInt("id"));
                    String songType=Constant.CLOUD_MUSIC_TYPE;
                    String singerName="";
                    JSONArray singerData = songData.getJSONArray("ar");
                    for(int y=0;y<singerData.length();y++){
                        if(y==singerData.length()-1){
                            singerName=singerName+singerData.getJSONObject(y).getString("name");
                        }else{
                            singerName=singerName+singerData.getJSONObject(y).getString("name")+"/";
                        }
                    }
                    String imgUrl=songData.getJSONObject("al").getString("picUrl");
                    //获取歌词
                    JSONObject lyricData = new JSONObject(CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.SongLyricUrl.replace("{id}", id), new HashMap<>(), new HashMap<>()));
                    String lyric="";
                    if(lyricData.getInt("code")==200){
                        lyric=lyricData.getJSONObject("lrc").getString("lyric");
                    }
                    //获取歌曲链接
                    //获取网易黑胶vip版权音乐
                    Map<String,Object> cloudData=new HashMap<>();
                    cloudData.put("ids",id.split(","));
                    cloudData.put("br",320000);
                    Map<String, String> cookie = CreateWebRequest.getCookie(request);
                    //设置黑胶vip账号cookie
                    cookie.put("MUSIC_U",token);
                    String cloudMusicData = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.songPlayerUrl, cloudData, cookie);
                    JSONObject cloudJsonData = new JSONObject(cloudMusicData);
                    Map<String,Object> cloudResult=new HashMap<>();
                    String  songUrl = cloudJsonData.getJSONArray("data").getJSONObject(0).getString("url");
                    //获取mv信息
                    songVo.setId(id);
                    songVo.setImgUrl(imgUrl);
                    songVo.setLyric(lyric);
                    songVo.setSingerName(singerName);
                    songVo.setSongName(songName);
                    songVo.setSongUrl(songUrl);
                    songVo.setType(songType);
                    list.add(songVo);
                }
            }
        }
        songVoMusicResponseVo.setCode(200);
        songVoMusicResponseVo.setDataList(list);
        return songVoMusicResponseVo;
    }
}

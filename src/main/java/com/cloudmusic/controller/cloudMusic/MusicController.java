package com.cloudmusic.controller.cloudMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import com.cloudmusic.request.cloudMusic.ResultCacheUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关的歌曲内容
 * Created by xuzijia
 * 2018/5/22 15:54
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class MusicController {
    @Autowired
    private ResultCacheUtils resultCacheUtils;
    /**
     * 获取歌曲详细信息
     * @param id *歌曲id 必传
     * @return 歌曲详细内容
     */
    @RequestMapping("/song/detail")
    public String getSongDetail(String id) throws Exception {
        if(id==null || id.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        String url= CloudMusicApiUrl.SongDetailUrl.replace("{id}",id);
        return CreateWebRequest.createWebPostRequest(url,new HashMap<>(),new HashMap<>());
    }
    /**
     * 获取歌曲歌词
     * @param id *歌曲id 必传
     * @return 歌曲歌词
     */
//    @RequestMapping("/song/lyric")
//    public String getSongLyric(String id,HttpServletRequest request) throws Exception {
//        if(id==null || id.trim().equals("")){
//            return new JSONObject(new Result(0, "缺少必填参数")).toString();
//        }
//        String url= CloudMusicApiUrl.SongLyricUrl;
//        Map<String,Object> params=new HashMap<>();
//        params.put("id",id);
//        params.put("lv","-1");
//        params.put("kv","-1");
//        params.put("tv","-1");
//        Map<String,String> cookie = new HashMap<>();
//        cookie.put("os","pc");
//        return CreateWebRequest.createWebPostRequest(url,params,CreateWebRequest.getCookie(request));
//    }

    /**
     * 获取歌曲歌词
     * @param id *歌曲id 必传
     * @return 歌曲歌词
     */
    @RequestMapping("/song/lyric")
    public Object getSongLyricTemp(String id,HttpServletRequest request) throws Exception {
        if(id==null || id.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        String url= CloudMusicApiUrl.SongLyricUrl.replace("{id}",id);

        return CreateWebRequest.createWebPostRequest(url, new HashMap<>(), new HashMap<>());
    }


    /**
     * http://music.163.com/song/media/outer/url?id={id}.mp3
     * 获取歌曲播放地址(通过组装url方式返回)
     * @param ids *歌曲id(多个id用逗号分开) 必传
     * @return 歌曲url
     */
    @RequestMapping("/song/url")
    public String getSongUrl(String ids, HttpServletResponse response) throws Exception {
        if(ids==null || ids.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        String[] split = ids.split(",");
        for(String id:split){
            String fileName="";
            String name="";
            String singer="";
            //获取文件相关信息
            String songDetail = getSongDetail(id);
            JSONObject jsonObject = new JSONObject(songDetail);
            JSONArray songs = jsonObject.getJSONArray("songs");
            if(songs.length()>0){
                JSONObject jsonObject1 = songs.getJSONObject(0);
                name = jsonObject1.getString("name");
                JSONArray artists = jsonObject1.getJSONArray("artists");
                for (int i=0;i<artists.length();i++){
                    if(i+1==artists.length()){
                        singer+=artists.getJSONObject(i).get("name");
                    }else{
                        singer+=artists.getJSONObject(i).get("name")+",";
                    }
                }
                fileName= singer+"_"+name;
            }

            data.put(id, CloudMusicApiUrl.FinalSongUrl.replace("{id}",id));
            String url = CloudMusicApiUrl.FinalSongUrl.replace("{id}", id);
            URL urlfile = null;
            HttpURLConnection httpUrl = null;
            BufferedInputStream bis = null;
            response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(fileName,"utf-8")+".mp3" );
            response.setCharacterEncoding("utf-8");
            BufferedOutputStream bos  = new BufferedOutputStream(response.getOutputStream());
            try
            {
                urlfile = new URL(url);
                httpUrl = (HttpURLConnection)urlfile.openConnection();
                httpUrl.connect();
                bis = new BufferedInputStream(httpUrl.getInputStream());
                int len = 2048;
                byte[] b = new byte[len];
                while ((len = bis.read(b)) != -1)
                {
                    bos.write(b, 0, len);
                }
                bos.flush();
                bis.close();
                httpUrl.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    bis.close();
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }




        }
        return null;
        //return new JSONObject(data).toString();
    }


    @Value("${application.accountInfo.token}")
    private String token;

    /**
     * 获取歌曲播放地址(POST请求) 如果code=404 说明歌曲没有版权或者歌曲不存在
     * @param ids *歌曲id(多个id用逗号分开) 必传
     * @param br 码率(默认值：最大码率)
     * @return 歌曲url
     */
    @RequestMapping("/song/player")
    public String getSongPlayer(String ids, Integer br, HttpServletRequest request) throws Exception {
        if(ids==null || ids.trim().equals("")){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        //获取网易黑胶vip版权音乐
        Map<String, Object> cloudData = new HashMap<>();
        cloudData.put("ids", ids.split(","));
        cloudData.put("br", 320000);
        Map<String, String> cookie = CreateWebRequest.getCookie(request);
        //设置黑胶vip账号cookie
        cookie.put("MUSIC_U", token);
        String cloudMusicData = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.songPlayerUrl, cloudData, cookie);
        JSONObject cloudJsonData = new JSONObject(cloudMusicData);
        Map<String, Object> cloudResult = new HashMap<>();
        Object url = cloudJsonData.getJSONArray("data").getJSONObject(0).get("url");
        cloudResult.put("code", 200);
        cloudResult.put("url", url);
        return new JSONObject(cloudResult).toString();
    }

    /**
     * 推荐的新音乐
     * @return 音乐列表
     */
    @RequestMapping("/song/personalized")
    public String getSongPersonalized() throws Exception {
        Map<String,Object> data=new HashMap<>();
        data.put("type","recommend");
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.songPersonalizedUrl,data,new HashMap<>());
    }

}

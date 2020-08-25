package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 当网易云歌曲没有版权 自动切换到qq音乐源
 * @date 2019/1/17 10:03
 */
@RequestMapping("/qq")
@RestController
public class SwitchMusicController {

    @Value("${application.accountInfo.token}")
    private String token;

    /**
     *根据歌曲名+歌手名 获取qq音乐歌曲地址
     * @return
     */
    @RequestMapping("/switchSource")
    public String getMusicSource(String searchStr, String musicId, HttpServletRequest request){
        if(searchStr==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }

        //获取网易黑胶vip版权音乐
        Map<String,Object> cloudData=new HashMap<>();
        cloudData.put("ids",musicId.split(","));
        cloudData.put("br",320000);
        Map<String, String> cookie = CreateWebRequest.getCookie(request);
        //设置黑胶vip账号cookie
        cookie.put("MUSIC_U",token);
        String cloudMusicData = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.songPlayerUrl, cloudData, cookie);
        JSONObject cloudJsonData = new JSONObject(cloudMusicData);
        Map<String,Object> cloudResult=new HashMap<>();
        cloudResult.put("code",200);
        cloudResult.put("url",cloudJsonData.getJSONArray("data").getJSONObject(0).get("url"));
        return  new JSONObject(cloudResult).toString();


        //切换qq音乐
        //Map<String,String> data= new HashMap<>();
        //data.put("w",searchStr);
        //data.put("remoteplace","txt.yqq.center");
        //data.put("cr","1");
        //data.put("p","1");
        //data.put("n","1");
        //String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.SearchUrl,data);
        //JSONObject jsonObject=new JSONObject(result);
        //String mid="";
        //int code = jsonObject.getInt("code");
        //if(code==0){
        //    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("song").getJSONArray("list");
        //    if(jsonArray.length()>0){
        //        //获取音乐id
        //        String songmid = jsonArray.getJSONObject(0).getString("songmid");
        //        mid=songmid;
        //    }
        //}
        ////获取vkey
        //Map<String,String> data1=new HashMap<>();
        //data1.put("songmid",mid);
        //data1.put("filename","C400"+mid+".m4a");
        //data1.put("callback","musicJsonCallback");
        //data1.put("format","jsonp");
        //data1.put("guid",QQMusicApiUrl.guid);
        //data1.put("platform","yqq");
        //data1.put("loginUin","3051522991");
        //data1.put("cid","205361747");
        //data1.put("uin","3051522991");
        //data1.put("needNewCode","0");
        //String result1 = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.vkeyUrl, data1);
        //JSONObject jsonObject1=new JSONObject(result1);
        //String url="";
        //if(jsonObject1.getInt("code")==0){
        //    JSONObject item = jsonObject1.getJSONObject("data").getJSONArray("items").getJSONObject(0);
        //    String vkey = item.getString("vkey");
        //    String fileName=item.getString("filename");
        //    url=QQMusicApiUrl.MusicSourceUrl.replace("{1}",fileName).replace("{2}",vkey);
        //    Map<String,Object> r=new HashMap<>();
        //    r.put("code",200);
        //    r.put("url",url);
        //    return  new JSONObject(r).toString();
        //}
        //return new JSONObject(new Result(404, "没有版权")).toString();
    }
}

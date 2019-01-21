package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     *根据歌曲名+歌手名 获取qq音乐歌曲地址
     * @return
     */
    @RequestMapping("/switchSource")
    public String getMusicSource(String searchStr){
        if(searchStr==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data= new HashMap<>();
        data.put("w",searchStr);
        data.put("remoteplace","txt.yqq.center");
        data.put("cr","1");
        data.put("p","1");
        data.put("n","1");
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.SearchUrl,data);
        JSONObject jsonObject=new JSONObject(result);
        String mid="";
        int code = jsonObject.getInt("code");
        if(code==0){
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("song").getJSONArray("list");
            if(jsonArray.length()>0){
                //获取音乐id
                String songmid = jsonArray.getJSONObject(0).getString("songmid");
                mid=songmid;
            }
        }
        //获取vkey
        Map<String,String> data1=new HashMap<>();
        data1.put("songmid",mid);
        data1.put("filename","C400"+mid+".m4a");
        data1.put("guid",QQMusicApiUrl.guid);
        data1.put("platform","yqq");
        data1.put("loginUin","0");
        data1.put("hostUin","0");
        data1.put("cid","205361747");
        data1.put("needNewCode","0");
        String result1 = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.vkeyUrl, data1);
        JSONObject jsonObject1=new JSONObject(result1);
        String url="";
        if(jsonObject1.getInt("code")==0){
            JSONObject item = jsonObject1.getJSONObject("data").getJSONArray("items").getJSONObject(0);
            String vkey = item.getString("vkey");
            String fileName=item.getString("filename");
            url=QQMusicApiUrl.MusicSourceUrl.replace("{1}",fileName).replace("{2}",vkey);
            Map<String,Object> r=new HashMap<>();
            r.put("code",200);
            r.put("url",url);
            return  new JSONObject(r).toString();
        }
        return new JSONObject(new Result(404, "没有版权")).toString();
    }
}

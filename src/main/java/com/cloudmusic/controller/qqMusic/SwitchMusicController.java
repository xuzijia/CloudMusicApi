package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import com.cloudmusic.utils.ScriptEngineUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
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
        Object url = cloudJsonData.getJSONArray("data").getJSONObject(0).get("url");
        if(url==null){
            return new JSONObject(new Result(404, "没有版权")).toString();
            //说明网易云木有这首歌的版权了。切换到qq音乐


        }else{
            cloudResult.put("code",200);
            cloudResult.put("url",url);
            return  new JSONObject(cloudResult).toString();
        }


    }

    @RequestMapping("/getMusicUrl")
    public Object getMusicUrl(String musicId) throws IOException {
        if(musicId==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> resultData=new HashMap<>();
        String data=String.format(QQMusicApiUrl.MusicRequestParamData,musicId);
        String sign = ScriptEngineUtil.getSecuritySign(data);
        String url=QQMusicApiUrl.MusicUrlApi+"?data="+ URLEncoder.encode(data,"UTF-8")+"&sign="+sign;
        String result = CreateQQWebRequest.createWebGetRequest(url, new HashMap<>());
        JSONObject jsonObject = new JSONObject(result);
        resultData.put("code",404);
        if(jsonObject!=null && jsonObject.getInt("code")==0){
            JSONObject req_0 = jsonObject.has("req_0")==true?jsonObject.getJSONObject("req_0"):null;
            if(req_0!=null){
                String urlSuffix = req_0.getJSONObject("data").getJSONArray("midurlinfo").getJSONObject(0).getString("purl");
                if(urlSuffix!=null && !urlSuffix.equals("")){
                    //拼接url
                    String musicUrl=QQMusicApiUrl.urlPrefixStr+urlSuffix;
                    resultData.put("code",200);
                    resultData.put("url",musicUrl);
                }
            }
        }
        return resultData;
    }


}

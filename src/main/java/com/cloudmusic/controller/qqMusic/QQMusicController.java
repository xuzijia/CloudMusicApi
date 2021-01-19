package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.controller.pojo.vo.MvDetilsVo;
import com.cloudmusic.controller.pojo.vo.MvVo;
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
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author simple
 * @description qq音乐相关接口
 * @date 2019/1/17 10:03
 */
@RequestMapping("/qq")
@RestController
public class QQMusicController {

    @Value("${application.accountInfo.token}")
    private String token;

    /**
     * 根据歌曲名+歌手名 获取qq音乐歌曲地址
     *
     * @return
     */
    @RequestMapping("/switchSource")
    public Object getMusicSource(String searchStr, String musicId, String musicType, HttpServletRequest request) throws IOException {
        if (searchStr == null) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        if (musicType != null && musicType.equals("qq")) {
            //返回qq音乐信息
            return getMusicUrl(musicId);
        }
        //获取网易黑胶vip版权音乐
        Map<String, Object> cloudData = new HashMap<>();
        cloudData.put("ids", musicId.split(","));
        cloudData.put("br", 320000);
        Map<String, String> cookie = CreateWebRequest.getCookie(request);
        //设置黑胶vip账号cookie
        cookie.put("MUSIC_U", token);
        String cloudMusicData = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.songPlayerUrl, cloudData, cookie);
        JSONObject cloudJsonData = new JSONObject(cloudMusicData);
        Map<String, Object> cloudResult = new HashMap<>();
        Object url = cloudJsonData.getJSONArray("data").getJSONObject(0).get("url");
        if (url == null || url.toString().equals("null") || url.toString().equals("")) {
            //说明网易云木有这首歌的版权了。切换到qq音乐
            Map<String, String> data = new HashMap<>();
            data.put("w", searchStr);
            data.put("remoteplace", "txt.yqq.center");
            String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.SearchUrl, data);
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("code") == 0) {
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("song").getJSONArray("list");
                if (jsonArray != null && jsonArray.length() > 0) {
                    String songmid = jsonArray.getJSONObject(0).getString("songmid");
                    return getMusicUrl(songmid);
                }
            }
            return new JSONObject(new Result(404, "没有版权")).toString();

        } else {
            cloudResult.put("code", 200);
            cloudResult.put("url", url);
            return new JSONObject(cloudResult).toString();
        }


    }

    @RequestMapping("/getMusicUrl")
    public Object getMusicUrl(String musicId) throws IOException {
        if (musicId == null) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> resultData = new HashMap<>();
        String data = String.format(QQMusicApiUrl.MusicRequestParamData, musicId);
        String sign = ScriptEngineUtil.getSecuritySign(data);
        String url = QQMusicApiUrl.MusicUrlApi + "?data=" + URLEncoder.encode(data, "UTF-8") + "&sign=" + sign;
        String result = CreateQQWebRequest.createWebGetRequest(url, new HashMap<>());
        JSONObject jsonObject = new JSONObject(result);
        resultData.put("code", 404);
        if (jsonObject != null && jsonObject.getInt("code") == 0) {
            JSONObject req_0 = jsonObject.has("req_0") == true ? jsonObject.getJSONObject("req_0") : null;
            if (req_0 != null) {
                String urlSuffix = req_0.getJSONObject("data").getJSONArray("midurlinfo").getJSONObject(0).getString("purl");
                if (urlSuffix != null && !urlSuffix.equals("")) {
                    //拼接url
                    String musicUrl = QQMusicApiUrl.urlPrefixStr + urlSuffix;
                    resultData.put("code", 200);
                    resultData.put("url", musicUrl);
                }
            }
        }
        return resultData;
    }


    /**
     * 获取qq音乐歌词
     *
     * @param musicId
     * @return
     * @throws IOException
     */
    @RequestMapping("/getLyric")
    public String getLyric(String musicId) throws IOException {
        if (musicId == null) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, String> data = new HashMap<>();
        data.put("songmid", musicId);
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.getLyricUrl, data);
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject.getInt("code") == 0) {
            String lyric = jsonObject.getString("lyric");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(lyric);
            jsonObject.put("decodeLyric", new String(bytes));
        }
        return jsonObject.toString();
    }

    /**
     * 获取音乐相关信息
     *
     * @param musicId
     * @return
     */
    @RequestMapping("/getSongInfo")
    public String getSongInfo(String musicId) throws IOException {
        if (musicId == null) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        String data = String.format(QQMusicApiUrl.musicInfoRequestParamData, musicId, "");
        String sign = ScriptEngineUtil.getSecuritySign(data);
        String url = QQMusicApiUrl.MusicUrlApi + "?data=" + URLEncoder.encode(data, "UTF-8") + "&sign=" + sign;
        String result = CreateQQWebRequest.createWebGetRequest(url, new HashMap<>());
        return result;
    }

    /**
     * 获取mv相关信息
     *
     * @param musicId
     * @return
     */
    @RequestMapping("/getMvInfo")
    public String getMvInfo(String musicId) throws IOException {
        if (musicId == null) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        String data = String.format(QQMusicApiUrl.mvInfoRequestParamData, musicId);
        String sign = ScriptEngineUtil.getSecuritySign(data);
        String url = QQMusicApiUrl.MusicUrlApi + "?data=" + URLEncoder.encode(data, "UTF-8") + "&sign=" + sign;
        String result = CreateQQWebRequest.createWebGetRequest(url, new HashMap<>());
        return result;
    }

    /**
     * 获取mv播放链接
     *
     * @param vid
     * @return
     */
    @RequestMapping("/getMvUrl")
    public String getMvUrl(String vid, boolean dataFormat) throws IOException {
        try {
            if (vid == null) {
                return new JSONObject(new Result(0, "缺少必填参数")).toString();
            }
            String data = QQMusicApiUrl.mvUrlRequestParamData.replace("{vid}", vid);
            String musicUrlApi = QQMusicApiUrl.MusicUrlApi + "?data=" + data;
            String result = CreateQQWebRequest.createWebGetRequest(musicUrlApi, new HashMap<>());
            if (!dataFormat) {
                return result;
            } else {
                //数据格式化
                JSONObject resultJson = new JSONObject(result);
                MvVo mvVo = new MvVo();
                if (resultJson.has("code") && resultJson.getInt("code") == 0) {
                    if(resultJson.getJSONObject("getMVInfo").getJSONObject("data").has(vid)){
                        JSONObject mvInfoData =resultJson.getJSONObject("getMVInfo").getJSONObject("data").getJSONObject(vid);
                        mvVo.setCoverPic(mvInfoData.getString("cover_pic"));
                        mvVo.setId(mvInfoData.getString("vid"));
                        mvVo.setName(mvInfoData.getString("name"));
                        mvVo.setSingId(String.valueOf(mvInfoData.getInt("sid")));
                    }
                    if(resultJson.getJSONObject("getMVUrl").getJSONObject("data").has(vid)){
                        if(resultJson.getJSONObject("getMVUrl").getJSONObject("data").getJSONObject(vid).has("mp4")){
                            JSONArray mvUrlJson = resultJson.getJSONObject("getMVUrl").getJSONObject("data").getJSONObject(vid).getJSONArray("mp4");
                            if(mvUrlJson!=null){
                                List<MvDetilsVo> mvDetails= new ArrayList<>();
                                for (int i=0;i<mvUrlJson.length();i++){
                                    JSONObject jsonObject = mvUrlJson.getJSONObject(i);
                                    MvDetilsVo mvDetilsVo = new MvDetilsVo();
                                    if(jsonObject.getInt("filetype")==10){
                                        //240p
                                        mvDetilsVo.setQuality("240p");
                                    }
                                    if(jsonObject.getInt("filetype")==20){
                                        mvDetilsVo.setQuality("480p");
                                    }
                                    if(jsonObject.getInt("filetype")==30){
                                        mvDetilsVo.setQuality("720p");
                                    }
                                    if(jsonObject.getInt("filetype")==40){
                                        mvDetilsVo.setQuality("1080p");
                                    }
                                    JSONArray freeflow_url = jsonObject.getJSONArray("freeflow_url");
                                    if(freeflow_url.length()>0){
                                        mvDetilsVo.setFileSize(jsonObject.getLong("fileSize"));
                                        mvDetilsVo.setUrl(freeflow_url.getString(0));
                                        mvDetails.add(mvDetilsVo);
                                    }

                                }
                                mvVo.setMvDetails(mvDetails);
                            }
                        }
                    }

                }
                return new JSONObject(mvVo).toString();
            }

        } catch (Exception e) {
            return new JSONObject(new Result(0, "无效的MvId或者找不到相应的Mv.")).toString();
        }
    }

}

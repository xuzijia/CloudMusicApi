package com.cloudmusic.controller.cloudMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.topMvUrl,data,new HashMap<>());
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
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.MvNewUrl,data,new HashMap<>());
    }

    /**
     * todo 待完善！
     * 获取推荐mv
     * @return 推荐mv数据
     */
    @RequestMapping("/mv/personalized")
    public String getPersonalizedMvList(HttpServletRequest request){
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.recommendMvUrl,new HashMap<>(),CreateWebRequest.getCookie(request));
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
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.mvDetailUrl,data,new HashMap<>());
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
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.videoUrl,data,new HashMap<>());
    }






    /**
     * 播放mv 由于网易云音乐做了防盗链 上面获取到的链接不能直接播放 访问该地址可播放mv
     * jsoup默认请求数据大小为1M 视频文件较大 设置为300M
     * @param url *Mv播放地址 必传
     * @return Mv信息
     */
    @RequestMapping("/mv/player")
    public void playMv(String id, String quality,HttpServletResponse response) throws IOException {

        String mvDetail = getMvDetail(id);
        JSONArray jsonObject=new JSONArray(mvDetail);
        if(StringUtils.isEmpty(quality)){
            quality="480";
        }
        String mvUrl = jsonObject.getJSONObject(0).getJSONObject("data").getJSONObject("brs").getString(quality);

        String fileName="MV_";
        fileName=fileName+jsonObject.getJSONObject(0).getJSONObject("data").getString("artistName")+"_"+jsonObject.getJSONObject(0).getJSONObject("data").getString("name");


        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(fileName,"utf-8")+".mp4" );
        response.setCharacterEncoding("utf-8");
        BufferedOutputStream bos  = new BufferedOutputStream(response.getOutputStream());
        try
        {
            urlfile = new URL(mvUrl);
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



}

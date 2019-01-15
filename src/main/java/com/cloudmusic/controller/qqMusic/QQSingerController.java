package com.cloudmusic.controller.qqMusic;

import com.cloudmusic.api.QQMusicApiUrl;
import com.cloudmusic.request.qqMusic.CreateQQWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 歌手相关接口
 * @date 2019/1/10 12:41
 */
@RestController
@RequestMapping("/qq")
public class QQSingerController {

    /**
     * 获取歌手列表
     * @param area 地区 默认为全部 -100
     * @param sex 性别 默认为全部 -100
     * @param genre 风格类别 默认为全部 -100
     * @param index 英文索引 默认为热门歌手 -100
     * @param page 当前页 默认为第一页
     * @param size 显示条数 默认显示 100条
     * @return
     */
    @RequestMapping("/singer/list")
    public String getSingerList(@RequestParam(name = "area",defaultValue = "-100") Integer area,
                                @RequestParam(name = "sex",defaultValue = "-100")Integer sex,
                                @RequestParam(name = "genre",defaultValue = "-100")Integer genre,
                                @RequestParam(name = "index",defaultValue = "-100")Integer index,
                                @RequestParam(name = "page",defaultValue = "1")Integer page,
                                @RequestParam(name = "size",defaultValue = "80")Integer size){
        Map<String,Object> param=new HashMap<>();
        param.put("area",area);
        param.put("sex",sex);
        param.put("genre",genre);
        param.put("index",index);
        param.put("cur_page",page);
        param.put("sin",(page-1)*size);
        String data=parseSingersJson(param);
        return CreateQQWebRequest.createWebGetJsonRequest(QQMusicApiUrl.singerListUrl,new HashMap<>(),data);
    }


    /**
     * 获取歌手歌曲列表
     * @param singermid 歌手id 必传
     * @param page 当前页 默认为第一页
     * @param size 显示条数 默认显示30条歌曲
     * @return 歌手歌曲列表
     */
    @RequestMapping("/singer/songs")
    public String getSingerSongsList(String singermid,
                                @RequestParam(name = "page",defaultValue = "1")Integer page,
                                @RequestParam(name = "size",defaultValue = "30")Integer size){
        if(singermid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("singermid",singermid);
        data.put("begin",String.valueOf((page-1)*size));
        data.put("num",String.valueOf(size));
        data.put("order","listen");//按播放数排序
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.singerSongListUrl, data);
        return result;
    }
    /**
     * 获取歌手专辑列表
     * @param singermid 歌手id 必传
     * @param page 当前页 默认为第一页
     * @param size 显示条数 默认显示30条歌曲
     * @return 歌手专辑列表
     */
    @RequestMapping("/singer/albums")
    public String getSingerAlbumsList(String singermid,
                                @RequestParam(name = "page",defaultValue = "1")Integer page,
                                @RequestParam(name = "size",defaultValue = "30")Integer size){
        if(singermid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,Object> param=new HashMap<>();
        param.put("singermid",singermid);
        param.put("begin",(page-1)*size);
        param.put("num",size);
        param.put("order","time");//按时间排序
        param.put("exstatus",1);
        String data=parseAlbumsJson(param);
        String result = CreateQQWebRequest.createWebGetJsonRequest(QQMusicApiUrl.singerAlbumListUrl, new HashMap<>(),data);
        return result;
    }
    /**
     * 获取歌手MV列表
     * @param singermid 歌手id 必传
     * @param page 当前页 默认为第一页
     * @param size 显示条数 默认显示30条mv
     * @return 歌手MV列表
     */
    @RequestMapping("/singer/mvs")
    public String getSingerMvsList(String singermid,
                                     @RequestParam(name = "page",defaultValue = "1")Integer page,
                                     @RequestParam(name = "size",defaultValue = "30")Integer size){
        if(singermid==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("singermid",singermid);
        data.put("begin",String.valueOf((page-1)*size));
        data.put("num",String.valueOf(size));
        data.put("order","listen");//按播放数排序
        data.put("cid","205360581");//按播放数排序
        String result = CreateQQWebRequest.createWebGetRequest(QQMusicApiUrl.singerMvListUrl, data);
        return result;
    }


    /**
     * 将map参数解析成歌手列表所需的json请求参数
     * @param paramData 请求参数key/value
     * @return
     */
    private String parseSingersJson(Map<String, Object> paramData) {
        JSONObject data=new JSONObject();
        JSONObject comm=new JSONObject();
        comm.put("ct",24);
        comm.put("cv",10000);
        data.put("comm",comm);
        JSONObject singerList=new JSONObject();
        singerList.put("module","Music.SingerListServer");
        singerList.put("method","get_singer_list");
        JSONObject param=new JSONObject();
        for (String key:paramData.keySet()){
            param.put(key,paramData.get(key));
        }
        singerList.put("param",param);
        data.put("singerList",singerList);
        return data.toString();
    }
    /**
     * 将map参数解析成歌手专辑列表所需的json请求参数
     * @param paramData 请求参数key/value
     * @return
     */
    private String parseAlbumsJson(Map<String, Object> paramData) {
        JSONObject data=new JSONObject();
        JSONObject comm=new JSONObject();
        comm.put("ct",24);
        comm.put("cv",0);
        data.put("comm",comm);
        JSONObject singerAlbum=new JSONObject();
        singerAlbum.put("module","music.web_singer_info_svr");
        singerAlbum.put("method","get_singer_album");
        JSONObject param=new JSONObject();
        for (String key:paramData.keySet()){
            param.put(key,paramData.get(key));
        }
        singerAlbum.put("param",param);
        data.put("singerAlbum",singerAlbum);
        return data.toString();
    }
}

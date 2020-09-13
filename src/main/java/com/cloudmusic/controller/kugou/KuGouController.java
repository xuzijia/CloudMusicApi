package com.cloudmusic.controller.kugou;

import com.cloudmusic.api.KuGouMusicApiUrl;
import com.cloudmusic.request.kugou.CreateKuGouWebRequest;
import com.cloudmusic.result.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 酷狗音乐相关接口
 * @date 2019/1/11 11:18
 */
@RequestMapping("/kugou")
@RestController
public class KuGouController {
    /**
     * 搜索歌曲
     * @param keyword 搜索关键词
     * @param page 当前页
     * @param pagesize 显示条数
     * @return
     */
    @RequestMapping("/search")
    public String search(String keyword, @RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "pagesize",defaultValue = "20")Integer pagesize){
        if(keyword==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("keyword",keyword);
        data.put("page",page.toString());
        data.put("pagesize",pagesize.toString());
        data.put("filter","2");
        String result = CreateKuGouWebRequest.createWebGetRequest(KuGouMusicApiUrl.searchUrl, data);
        return result;
    }

    /**
     * 获取歌曲播放地址
     * @param musicHash 歌曲hash
     * @return
     */
    @RequestMapping("/getSongDetail")
    public String getSongDetail(String musicHash){
        String md5 = DigestUtils.md5Hex(musicHash+"kgcloud");
        String songPlayerUrl = KuGouMusicApiUrl.songPlayerUrl;
        songPlayerUrl=songPlayerUrl.replace("{hash}",musicHash).replace("{key}",md5);
        String result = CreateKuGouWebRequest.createWebGetRequest(songPlayerUrl, new HashMap<>());
        return result;
    }
    /**
     * 获取歌曲信息
     * @param musicHash 歌曲hash
     * @return
     */
    @RequestMapping("/getSongInfo")
    public String getSongInfo(String musicHash){
        String songPlayerUrl = KuGouMusicApiUrl.songInfoUrl;
        Map<String, String> data = new HashMap<>();
        data.put("hsah",musicHash);
        data.put("r","play/getdata");
        String result = CreateKuGouWebRequest.createWebGetRequest(songPlayerUrl, data);
        return result;
    }

    /**
     * 获取mv播放地址
     * @param mvHash 歌曲mv hash
     * @return
     */
    @RequestMapping("/getMvDetail")
    public String getMvDetail(String mvHash){
        String md5 = DigestUtils.md5Hex(mvHash.toUpperCase()+"kugoumvcloud");
        String songPlayerUrl = KuGouMusicApiUrl.getMvUrl;
        songPlayerUrl=songPlayerUrl.replace("{hash}",mvHash.toUpperCase()).replace("{key}",md5);
        String result = CreateKuGouWebRequest.createWebGetRequest(songPlayerUrl, new HashMap<>());
        return result;
    }

    @RequestMapping("/getLyric")
    public String getLyric(String musicHash){
        Map<String, String> data = new HashMap<>();
        data.put("keyword","123");
        data.put("hash",musicHash);
        data.put("timelength","250");
        String result = CreateKuGouWebRequest.createWebGetRequest(KuGouMusicApiUrl.getLyric, data);
        return result;
    }

}

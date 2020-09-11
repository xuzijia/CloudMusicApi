package com.cloudmusic.controller.migu;

import com.cloudmusic.api.KuGouMusicApiUrl;
import com.cloudmusic.api.MiGuMusicApiUrl;
import com.cloudmusic.export.ExcelUtils;
import com.cloudmusic.request.kugou.CreateKuGouWebRequest;
import com.cloudmusic.request.migu.CreateMiGuWebRequest;
import com.cloudmusic.result.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 咪咕音乐相关接口
 * @date 2020/08/27 11:18
 */
@RequestMapping("/migu")
@RestController
public class MiGuController {
    /**
     * 搜索歌曲
     * @param keyword 搜索关键词
     * @param page 当前页
     * @param pagesize 显示条数
     * @param type 搜索类型 1 歌手 2歌曲 4专辑 6 歌单
     * @return
     */
    @RequestMapping("/search")
    public String search(String keyword, @RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "pagesize",defaultValue = "20")Integer pagesize, @RequestParam(name = "type",defaultValue = "2")Integer type) throws IOException {
        if(keyword==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("keyword",keyword);
        data.put("pgc",page.toString());
        data.put("rows",pagesize.toString());
        data.put("type",type.toString());
        String result = CreateMiGuWebRequest.createWebGetRequest(MiGuMusicApiUrl.searchApi, data);
        //生成excel
        JSONObject jsonObject = new JSONObject(result);
        ExcelUtils.genMiguData(jsonObject.getJSONArray("musics"));
        return result;
    }

    /**
     * 获取mv信息
     * @param mvId
     * @return
     */
    @RequestMapping("/getMvInfo")
    public String getMvInfo(String mvId){
        if(mvId==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("cpid",mvId);
        String result = CreateMiGuWebRequest.createWebGetRequest(MiGuMusicApiUrl.getMvInfo, data);
        return result;
    }


    /**
     * 获取歌曲信息
     * @param id
     * @return
     */
    @RequestMapping("/getSongInfo")
    public String getSongInfo(String id){
        if(id==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String,String> data=new HashMap<>();
        data.put("cpid",id);
        String result = CreateMiGuWebRequest.createWebGetRequest(MiGuMusicApiUrl.getSongInfo, data);
        return result;
    }
}

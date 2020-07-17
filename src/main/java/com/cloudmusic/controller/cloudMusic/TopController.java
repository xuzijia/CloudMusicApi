package com.cloudmusic.controller.cloudMusic;

import com.cloudmusic.api.CloudMusicApiUrl;
import com.cloudmusic.request.cloudMusic.CreateWebRequest;
import com.cloudmusic.result.Result;
import com.cloudmusic.request.cloudMusic.ResultCacheUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口功能：有关排行榜的接口
 * Created by xuzijia
 * 2018/5/23 9:34
 * 为使用方便,降低门槛,所有接口直接使用了 Get 请求
 */
@RestController
public class TopController {
    @Autowired
    private ResultCacheUtils resultCacheUtils;
    /**
     * 获取歌曲排行榜数据
     *
     * @param idx *排行榜标识 必传 具体各个标识请看最下面注释
     * @return 歌曲排行榜数据
     */
    @RequestMapping("/top/data")
    public String getTopData(String idx) {

        if (idx == null || idx.trim().equals("")) {
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", topList.get(idx));
        data.put("n", 10000);
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.topDataUrl, data, new HashMap<>());

    }

    /**
     * 获取歌手排行榜数据
     *
     * @param type 歌手类型 待完善
     * @return 歌曲排行榜数据
     */
    @RequestMapping("/top/artist")
    public String getTopArtistList(String type) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);//todo 具体值还没挖掘到~
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.topArtistUrl, data, new HashMap<>());

    }
    /**
     * 获取排行榜列表
     *
     * @return 排行榜列表数据
     */
    @RequestMapping("/top/list")
    public String getTopList() {
        Map<String, Object> data = new HashMap<>();
        return CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.topListUrl, data, new HashMap<>());

    }
    /**
     * 获取排行榜内容摘要
     *
     * @return 排行榜内容摘要
     */
    @RequestMapping("/top/detail")
    public String getTopListDetail() {
        Map<String, Object> data = new HashMap<>();
        return  CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.topListDetailUrl,data,new HashMap<>());
    }



    /**
     * "0":  云音乐新歌榜,
     * "1":  云音乐热歌榜,
     * "2":  网易原创歌曲榜,
     * "3":  云音乐飙升榜,
     * "4":  云音乐电音榜,
     * "5":  UK排行榜周榜,
     * "6":  美国Billboard周榜
     * "7":  KTV嗨榜,
     * "8":  iTunes榜,
     * "9":  Hit FM Top榜,
     * "10": 日本Oricon周榜
     * "11": 韩国Melon排行榜周榜,
     * "12": 韩国Mnet排行榜周榜,
     * "13": 韩国Melon原声周榜,
     * "14": 中国TOP排行榜(港台榜),
     * "15": 中国TOP排行榜(内地榜)
     * "16": 香港电台中文歌曲龙虎榜,
     * "17": 华语金曲榜,
     * "18": 中国嘻哈榜,
     * "19": 法国 NRJ EuroHot 30周榜,
     * "20": 台湾Hito排行榜,
     * "21": Beatport全球电子舞曲榜,
     * "22": 云音乐ACG音乐榜,
     * "23": 云音乐嘻哈榜
     */
    private static Map<String, String> topList;
    //初始化排行榜标识
    static {
        topList = new HashMap<>();
        topList.put("0", "3779629");
        topList.put("1", "3778678");
        topList.put("2", "2884035");
        topList.put("3", "19723756");
        topList.put("4", "10520166");
        topList.put("5", "180106");
        topList.put("6", "60198");
        topList.put("7", "21845217");
        topList.put("8", "11641012");
        topList.put("9", "120001");
        topList.put("10", "60131");
        topList.put("11", "3733003");
        topList.put("12", "60255");
        topList.put("13", "46772709");
        topList.put("14", "112504");
        topList.put("15", "64016");
        topList.put("16", "10169002");
        topList.put("17", "4395559");
        topList.put("18", "1899724");
        topList.put("19", "27135204");
        topList.put("20", "112463");
        topList.put("21", "3812895");
        topList.put("22", "71385702");
        topList.put("23", "991319590");
    }

}


package com.cloudmusic.controller.kugou;

import com.cloudmusic.api.KuGouMusicApiUrl;
import com.cloudmusic.request.kugou.CreateKuGouWebRequest;
import com.cloudmusic.result.Result;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 酷狗音乐搜索相关接口
 * @date 2019/1/11 11:18
 */
@RequestMapping("/kugou")
@RestController
public class KuGouSearchController {
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
}

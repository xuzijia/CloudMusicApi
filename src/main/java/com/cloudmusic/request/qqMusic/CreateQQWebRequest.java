package com.cloudmusic.request.qqMusic;

import com.cloudmusic.result.Result;
import com.cloudmusic.utils.JsonpUtil;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * 封装url请求数据
 * Created by xuzijia
 * 2018/5/18 19:46
 */
public class CreateQQWebRequest {

    /**
     * 发送请求
     *
     * @param url  api接口地址
     * @param data 请求参数
     * @return 如果成功返回json结果数据
     */
    public static String createWebGetRequest(String url, Map<String, String> data,boolean flag) {
        try {
            Document post = Jsoup.connect(url).referrer("https://y.qq.com/").
                    data(data).ignoreContentType(true).get();
            String result = post.text();
            //是否需要解析jsonp
            if(flag){
                result= JsonpUtil.analysisJsonp(result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "服务器出错")).toString();
        }
    }

}

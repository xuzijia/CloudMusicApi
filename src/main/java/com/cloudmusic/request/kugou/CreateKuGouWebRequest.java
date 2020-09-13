package com.cloudmusic.request.kugou;

import com.cloudmusic.result.Result;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * 封装url请求数据
 * Created by xuzijia
 * 2018/5/18 19:46
 */
public class CreateKuGouWebRequest {

    /**
     * 发送请求
     *
     * @param url  api接口地址
     * @param data 请求参数
     * @return 如果成功返回json结果数据
     */
    public static String createWebGetRequest(String url, Map<String, String> data) {
        try {
            Document post = Jsoup.connect(url).referrer("https://wwwapi.kugou.com").
                    data(data).get();
            String result = post.text();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "服务器出错")).toString();
        }
    }

}

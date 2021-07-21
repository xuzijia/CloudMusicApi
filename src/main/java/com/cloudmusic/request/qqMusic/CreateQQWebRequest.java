package com.cloudmusic.request.qqMusic;

import com.cloudmusic.result.Result;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URLEncoder;
import java.util.HashMap;
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
    public static String createWebGetRequest(String url, Map<String, String> data) {
        data=addDefaultParam(data);
        try {
            Document post = Jsoup.connect(url).referrer("https://y.qq.com/").
                    data(data).ignoreContentType(true).get();
            return post.text();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "服务器出错")).toString();
        }
    }
    /**
     * 发送Post请求
     *
     * @param url  api接口地址
     * @param data 请求参数json字符串
     * @return 如果成功返回json结果数据
     */
    public static String createWebPostRequest(String url, String  data) {
        try {

            Document post = Jsoup.connect(url).requestBody(data).referrer("https://y.qq.com/").ignoreContentType(true).post();
            return post.text();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "服务器出错")).toString();
        }
    }

    /**
     * 发送请求
     *
     * @param url  api接口地址
     * @param header 请求头
     * @return 如果成功返回json结果数据
     */
    public static String createWebGetRequest(String url, Map<String,String> params,Map<String, String> header) {
        try {
            Connection connection = Jsoup.connect(url).referrer("https://y.qq.com/").
                    data(params);
            for (String key:header.keySet()){
                connection=connection.header(key,header.get(key));
            }
            Document document = connection.ignoreContentType(true).get();

            return document.text();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "服务器出错")).toString();
        }
    }

    /**
     * 发送包含json请求参数的请求
     *
     * @param url  api接口地址
     * @param data 普通请求参数
     * @param jsonData json请求参数
     * @return 如果成功返回json结果数据
     */
    public static String createWebGetJsonRequest(String url, Map<String, String> data,String jsonData) {
        data=addDefaultParam(data);
        data.put("data",jsonData);
        try {
            Document post = Jsoup.connect(url).referrer("https://y.qq.com/").
                    data(data).ignoreContentType(true).get();
            return post.text();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(new Result(500, "服务器出错")).toString();
        }
    }

    /**
     * qq音乐接口默认参数
     * @param param
     * @return
     */
    private static Map<String,String> addDefaultParam(Map<String,String> param){
        param.put("format","json");
        param.put("inCharset","utf-8");
        param.put("outCharset","utf-8");
        param.put("notice","0");
        param.put("format","json");
        return param;
    }

}

package com.cloudmusic.utils;

/**
 * @author simple
 * @description 解析jsonp工具类
 * @date 2019/1/10 11:28
 */
public class JsonpUtil {
    public static String analysisJsonp(String jsonpData){
        int start = jsonpData.indexOf("(");
        int end=jsonpData.lastIndexOf(")");
        if(start!=-1 && end!=-1){
            return jsonpData.substring(start+1,end);
        }else{
            return jsonpData;
        }
    }
}

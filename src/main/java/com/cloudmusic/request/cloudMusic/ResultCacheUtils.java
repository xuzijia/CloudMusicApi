package com.cloudmusic.request.cloudMusic;

import com.cloudmusic.base.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 * @description 缓存数据到redis 节省请求时间
 * @date 2018/11/17 15:24
 */
@Component
public class ResultCacheUtils {

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 判断redis是否存在数据 如果存在取出返回，如果不存在 发起请求并保存请求结果
     * @param key  redis key
     * @param url  请求地址
     * @param data 请求参数
     * @param time 缓存时间 单位：秒
     * @return
     */
    public String createCache(String key, String url, Map<String,Object> data,Integer time){
        //判断key是否存在redis
        if(redisUtil.hasKey(key)){
            return (String) redisUtil.get(key);
        }else{
            //创建请求
            String result=CreateWebRequest.createWebPostRequest(url, data, new HashMap<>());
            //保存到redis
            redisUtil.set(key,result,time);
            return result;
        }
    }

}

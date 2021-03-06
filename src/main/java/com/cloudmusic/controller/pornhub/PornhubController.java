package com.cloudmusic.controller.pornhub;

import com.cloudmusic.result.Result;
import com.cloudmusic.utils.PornhubDownloadUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取p站学习视频 下载链接
 */
@RestController
@RequestMapping("/porn")
public class PornhubController {

    @RequestMapping("getPornhubUrl")
    public Object getPornhubUrl(String url)  {
        if(url==null){
            return new JSONObject(new Result(0, "缺少必填参数")).toString();
        }
        JSONObject downloadData =new JSONObject();
        try {
            downloadData = PornhubDownloadUtils.getDownloadData(url);
        }catch (Exception e){
            downloadData.put("message","请开启vpn访问改接口。");
        }
        return downloadData.toString();
    }
}

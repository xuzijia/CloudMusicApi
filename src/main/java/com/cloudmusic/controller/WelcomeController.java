package com.cloudmusic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口首页界面
 * Created by xuzijia
 * 2018/5/22 15:33
 */
@RestController
public class WelcomeController {
    @GetMapping("/")
    public String root(){
        return "<div style='text-align:center;color:#46ce46;margin-top:300px;'><img src='/images/icon-spring-boot.svg' width=100 height:100/><h1>Welcome Use CloudMusic API</h1></div>";
    }
}

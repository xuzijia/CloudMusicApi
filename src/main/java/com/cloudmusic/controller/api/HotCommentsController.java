package com.cloudmusic.controller.api;

import com.cloudmusic.pojo.CloudCommentsPo;
import com.cloudmusic.service.impl.CloudCommentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotCommentsController {

    @Autowired
    private CloudCommentsServiceImpl cloudCommentsServiceImpl;

    @RequestMapping("/getCloudComments")
    public List<CloudCommentsPo> findList(){
        return cloudCommentsServiceImpl.findList();
    }
}

package com.cloudmusic.service;

import com.cloudmusic.pojo.CloudCommentsPo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CloudCommentsService {

    void saveAll(List<CloudCommentsPo> data);

    List<CloudCommentsPo> findList();
}

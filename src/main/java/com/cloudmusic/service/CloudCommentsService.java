package com.cloudmusic.service;

import com.cloudmusic.pojo.CloudCommentsPo;

import java.util.List;

public interface CloudCommentsService {

    void saveAll(List<CloudCommentsPo> data);
}

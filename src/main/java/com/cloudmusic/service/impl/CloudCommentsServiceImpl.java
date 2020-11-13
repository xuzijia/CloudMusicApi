package com.cloudmusic.service.impl;

import com.cloudmusic.dao.CloudCommentsDao;
import com.cloudmusic.pojo.CloudCommentsPo;
import com.cloudmusic.service.CloudCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CloudCommentsServiceImpl implements CloudCommentsService {

    @Autowired
    private CloudCommentsDao cloudCommentsDao;

    @Override
    public void saveAll(List<CloudCommentsPo> data){
        cloudCommentsDao.saveAll(data);
    }

    @Override
    public List<CloudCommentsPo> findList() {
        return cloudCommentsDao.findList();
    }

}

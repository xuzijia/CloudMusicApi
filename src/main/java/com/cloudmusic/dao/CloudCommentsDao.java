package com.cloudmusic.dao;

import com.cloudmusic.pojo.CloudCommentsPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudCommentsDao extends JpaRepository<CloudCommentsPo, String> {

}

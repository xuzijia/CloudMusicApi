package com.cloudmusic.dao;

import com.cloudmusic.pojo.CloudCommentsPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudCommentsDao extends JpaRepository<CloudCommentsPo, String> {
    @Query(value = "select * from cloud_comments order by like_count desc",nativeQuery = true)
    List<CloudCommentsPo> findList();
}

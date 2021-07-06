package com.cloudmusic.dao;

import com.cloudmusic.pojo.ApiLog;
import com.cloudmusic.pojo.PornSeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiLogDao extends JpaRepository<ApiLog,Long> {

}

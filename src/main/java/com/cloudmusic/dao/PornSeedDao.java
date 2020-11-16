package com.cloudmusic.dao;

import com.cloudmusic.pojo.PornSeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PornSeedDao extends JpaRepository<PornSeed,String> {

    PornSeed findByLinkUrl(String linkUrl);
}

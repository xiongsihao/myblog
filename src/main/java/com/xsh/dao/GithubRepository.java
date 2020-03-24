package com.xsh.dao;

import com.xsh.pojo.Github;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : xsh
 * @create : 2020-03-22 - 17:38
 * @describe:
 */
public interface GithubRepository extends JpaRepository<Github,Long> {

        @Query("select g from Github g where g.nodeId = ?1")
        Github findGithubuser(String nodeId);

        @Transactional
        @Modifying
        @Query("update Github g set g.avatar = ?1 where g.id = ?2")
        void updateAvatar(String avatar,Long id);

        @Transactional
        @Modifying
        @Query("update Github g set g.cip = ?1,g.cid = ?2,g.cname = ?3 where g.nodeId = ?4")
        void addIpaddress(String cip,String cid,String cname,String node_id);
}

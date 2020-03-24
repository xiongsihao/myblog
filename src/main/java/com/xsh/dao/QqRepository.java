package com.xsh.dao;

import com.xsh.pojo.Comment;
import com.xsh.pojo.QQInfo;
import com.xsh.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : xsh
 * @create : 2020-03-15 - 14:01
 * @describe:
 */
public interface QqRepository extends JpaRepository<QQInfo,Long> {

    @Query("select q from QQInfo q where q.openid = ?1")
    QQInfo findQQuser(String openid);

    @Transactional
    @Modifying
    @Query("update QQInfo q set q.avatar = ?1 where q.id = ?2")
    void updateAvatar(String avatar,Long id);
}

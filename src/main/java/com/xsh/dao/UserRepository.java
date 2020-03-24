package com.xsh.dao;

import com.xsh.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : xsh
 * @create : 2020-02-08 - 0:15
 * @describe:
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}

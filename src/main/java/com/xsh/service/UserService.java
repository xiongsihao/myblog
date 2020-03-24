package com.xsh.service;

import com.xsh.pojo.User;

/**
 * @author : xsh
 * @create : 2020-02-08 - 0:12
 * @describe:
 */
public interface UserService {

    User checkUser(String username, String password);
}

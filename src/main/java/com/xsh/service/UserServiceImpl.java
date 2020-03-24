package com.xsh.service;

import com.xsh.dao.UserRepository;
import com.xsh.pojo.User;
import com.xsh.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xsh
 * @create : 2020-02-08 - 0:14
 * @describe:
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}

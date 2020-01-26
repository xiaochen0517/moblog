package com.moblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moblog.dao.UserDao;
import com.moblog.domain.User;
import com.moblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String findAll() {
        //获取用户数据
        List<User> users = userDao.findAll();
        for (User user:users){
            System.out.println(user.toString());
        }
        String jsonstr = JSONObject.toJSONString(users);
        return jsonstr;
    }
}

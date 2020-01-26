package com.test.mybatis;

import com.moblog.dao.UserDao;
import com.moblog.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring.xml" })
public class TestOne {

    @Autowired
    private UserDao userDao;

    @Test
    public void test1(){
        List<User> users = userDao.findAll();
        for (User user:users){
            System.out.println(user.toString());
        }
    }

}

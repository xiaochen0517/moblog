package com.moblog.dao;

import com.moblog.domain.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@Repository
public interface UserDao {

    @Select("select * from user;")
    List<User> findAll();

}

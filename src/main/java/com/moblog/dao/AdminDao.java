package com.moblog.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
public interface AdminDao {

    @Select("select count(id) from admin where `username`=#{username} and `password`=#{password}")
    int login(@Param("username") String username, @Param("password") String password);

}

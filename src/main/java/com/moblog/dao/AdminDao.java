package com.moblog.dao;

import com.moblog.domain.Account;
import com.moblog.domain.Article;
import com.moblog.domain.admin.ReUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 功能：
 * 管理员 dao
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
public interface AdminDao {

    @Select("select count(id) from admin where `username`=#{username} and `password`=#{password}")
    int login(@Param("username") String username, @Param("password") String password);

    @Select("select u.id, u.username, a.nickname, a.address, a.email, a.tel " +
            "from account a " +
            "inner join user u " +
            "on a.uid=u.id " +
            "limit #{startItem}, 10;")
    List<ReUser> findAccountList(int startItem);

    @Select("select count(id) from account;")
    int findAccountSize();

    @Select("select * from account where id = #{userid};")
    Account findUserinfo(int userid);

    @Select("select `id`,`title`,`publisht`,`reviset`,`browse`,`like`,`sortid`,`label`" +
            " from article " +
            "where uid = #{userid} " +
            "limit #{startItem}, 10;")
    List<Article> findUserArticle(@Param("userid") int userid,@Param("startItem") int startItem);

    @Select("select name from sort where id = #{id};")
    String findSortName(int id);

    @Select("select count(`id`) from article where uid = #{userid};")
    int findUserArticleSize(int userid);

}

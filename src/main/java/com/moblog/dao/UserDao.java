package com.moblog.dao;

import com.moblog.domain.Sort;
import com.moblog.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Select("select count(id) from user where username = #{username} and password = #{password};")
    int findUserLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 查询用户名冲突
     *
     * @param username
     * @return
     */
    @Select("select count(id) from user where username = #{username}")
    int findUsername(String username);

    /**
     * 查询邮箱冲突
     *
     * @param email
     * @return
     */
    @Select("select count(id) from account where email = #{email}")
    int findEmail(String email);

    /**
     * 查询电话号码冲突
     *
     * @param phoneNum
     * @return
     */
    @Select("select count(id) from account where tel = #{phoneNum}")
    int findPhoneNum(String phoneNum);

    /**
     * 查询指定user的id
     *
     * @param username
     * @return
     */
    @Select("select id from user where username = #{username}")
    int findUserId(String username);

    /**
     * 插入user数据
     *
     * @param username
     * @param password
     * @param status
     * @return
     */
    @Insert("insert into user (username, password, status)" +
            "values (#{username}, #{password}, #{status})")
    int insertUser(@Param("username") String username, @Param("password") String password, @Param("status") boolean status);

    /**
     * 插入account数据
     *
     * @param uid
     * @param nickname
     * @param email
     * @return
     */
    @Insert("insert into account (uid, nickname, email)" +
            "values (#{uid}, #{nickname}, #{email})")
    int insertAccount(@Param("uid") int uid, @Param("nickname") String nickname, @Param("email") String email);

    /**
     * 更新电话号码
     *
     * @param uid
     * @param phoneNum
     * @return
     */
    @Update("update account set tel = #{phoneNum} where uid = #{uid}")
    int updateAccountTel(@Param("uid") int uid, @Param("phoneNum") String phoneNum);

    /**
     * 删除指定account
     *
     * @param uid
     * @return
     */
    @Delete("delete from account where uid = #{uid}")
    int deleteAccount(int uid);

    /**
     * 删除指定user
     *
     * @param id
     * @return
     */
    @Delete("delete from user where id = #{id}")
    int deleteUser(int id);

    /**
     * 获取用户状态
     *
     * @param id
     * @return
     */
    @Select("select status from user where id = #{id}")
    boolean findUserStatusi(int id);

    @Select("select status from user where username = #{username}")
    boolean findUserStatuss(String username);

    /**
     * 查询文章是否存在
     *
     * @param aid
     * @return
     */
    @Select("select count(*) from article where id = #{aid}")
    int findArticleExist(int aid);

    /**
     * 插入评论数据
     *
     * @param uid
     * @param aid
     * @param date
     * @param content
     * @param status
     * @return
     */
    @Insert("insert into comment (uid, aid, time, content, status) " +
            "values (#{uid}, #{aid}, #{date}, #{content}, #{status})")
    int insertComment(@Param("uid") int uid, @Param("aid") int aid,
                      @Param("date") String date, @Param("content") String content,
                      @Param("status") boolean status);

    /**
     * 查询是否有重复的赞
     *
     * @param uid
     * @param aid
     * @return
     */
    @Select("select count(id) from `like` where uid = #{uid} and aid = #{aid}")
    int findRepetLike(@Param("uid") int uid, @Param("aid") int aid);

    /**
     * 添加文章点赞
     *
     * @param uid
     * @param aid
     * @param time
     * @return
     */
    @Insert("insert into `like` (uid, aid, time)" +
            " values (#{uid}, #{aid}, #{time})")
    int insertLike(@Param("uid") int uid, @Param("aid") int aid, @Param("time") String time);

    /**
     * 添加文章
     * @param uid
     * @param title
     * @param publisht
     * @param reviset
     * @param sortid
     * @param label
     * @param content
     * @return
     */
    @Insert("insert into article (uid, title, publisht, reviset, sortid, label, content) " +
            "values " +
            "(#{uid}, #{title}, #{publisht}, #{reviset}, #{sortid}, #{label}, #{content});")
    int insertArticle(@Param("uid") int uid,@Param("title") String title,@Param("publisht") String publisht,
                      @Param("reviset") String reviset,@Param("sortid") int sortid,
                      @Param("label") String label,@Param("content") String content);

    /**
     * 获取分类
     * @param uid
     * @return
     */
    @Select("select * from sort where uid = #{uid}")
    List<Sort> findUserSort(int uid);

    /**
     * 插入分类数据
     * @param uid
     * @param name
     * @return
     */
    @Insert("insert into sort (uid, name, defsort) values (#{uid}, #{name}, false)")
    int insertUserSort(@Param("uid") int uid,@Param("name") String name);
}

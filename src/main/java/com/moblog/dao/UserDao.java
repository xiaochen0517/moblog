package com.moblog.dao;

import com.moblog.domain.Account;
import com.moblog.domain.Blogroll;
import com.moblog.domain.Sort;
import com.moblog.domain.User;
import com.moblog.domain.blog.ReArticleList;
import com.moblog.domain.user.ReAccount;
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
     *
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
    int insertArticle(@Param("uid") int uid, @Param("title") String title, @Param("publisht") String publisht,
                      @Param("reviset") String reviset, @Param("sortid") int sortid,
                      @Param("label") String label, @Param("content") String content);

    /**
     * 获取分类
     *
     * @param uid
     * @return
     */
    @Select("select * from sort where uid = #{uid}")
    List<Sort> findUserSort(int uid);

    /**
     * 插入分类数据
     *
     * @param uid
     * @param name
     * @return
     */
    @Insert("insert into sort (uid, name, defsort) values (#{uid}, #{name}, false)")
    int insertUserSort(@Param("uid") int uid, @Param("name") String name);

    /**
     * 删除指定用户分类
     *
     * @param id
     * @param uid
     * @return
     */
    @Delete("delete from sort where id = #{id} and uid = #{uid}")
    int deleteUserSort(@Param("id") int id, @Param("uid") int uid);

    /**
     * 计算现有分类下的文章个数
     * @param sid
     * @return
     */
    @Select("select count(*) from article where sortid = #{sid}")
    int findUserSortArticleSize(@Param("sid") int sid);

    /**
     * 修改分类名
     * @param id
     * @param uid
     * @param name
     * @return
     */
    @Update("update sort set name = #{name} where id = #{id} and uid = #{uid}")
    int updateUserSort(@Param("id") int id, @Param("uid") int uid, @Param("name") String name);

    /**
     * 添加友链
     *
     * @param name
     * @param link
     * @return
     */
    @Insert("insert into blogroll (name, link) values (#{name}, #{link});")
    int insertBlogRoll(@Param("name") String name, @Param("link") String link);

    /**
     * 修改友链
     *
     * @param id
     * @param name
     * @param link
     * @return
     */
    @Update("update blogroll set name = #{name}, link = #{link} where id = #{id}")
    int updateBlogRoll(@Param("id") int id, @Param("name") String name, @Param("link") String link);

    /**
     * 删除友链
     *
     * @param id
     * @return
     */
    @Delete("delete from blogroll where id = #{id};")
    int delBlogRoll(int id);

    /**
     * 获取用户信息
     *
     * @param uid
     * @return
     */
    @Select("select a.*, u.username from account a " +
            "inner join user u on u.id = a.uid " +
            "where a.uid = #{uid};")
    ReAccount findUserInfo(int uid);

    /**
     * 修改轮播图
     *
     * @param id
     * @param name
     * @param link
     * @return
     */
    @Update("update homephoto set name = #{name}, link = #{link} where id = #{id}")
    int updateHomePhoto(@Param("id") int id, @Param("name") String name, @Param("link") String link);

    /**
     * 添加轮播图
     *
     * @param name
     * @param link
     * @return
     */
    @Insert("insert into homephoto (name, link) values (#{name}, #{link});")
    int insertHomePhoto(@Param("name") String name, @Param("link") String link);

    /**
     * 删除轮播图
     *
     * @param id
     * @return
     */
    @Delete("delete from homephoto where id = #{id};")
    int delHomePhoto(int id);

    /**
     * 查询是否有重复的昵称
     *
     * @param nickname
     * @return
     */
    @Select("select count(id) from account where nickname = #{nickname};")
    int findNicknameRepeat(String nickname);

    /**
     * 修改昵称
     *
     * @param uid
     * @param nickname
     * @return
     */
    @Update("update account set nickname = #{nickname} where uid = #{uid}")
    int updateNickname(@Param("uid") int uid, @Param("nickname") String nickname);

    /**
     * 修改地址
     *
     * @param uid
     * @param address
     * @return
     */
    @Update("update account set address = #{address} where uid = #{uid}")
    int updateAddress(@Param("uid") int uid, @Param("address") String address);

    /**
     * 查询是否有重复的邮箱
     *
     * @param email
     * @return
     */
    @Select("select count(id) from account where email = #{email};")
    int findEmailRepeat(String email);

    /**
     * 修改邮箱
     *
     * @param uid
     * @param email
     * @return
     */
    @Update("update account set email = #{email} where uid = #{uid}")
    int updateEmail(@Param("uid") int uid, @Param("email") String email);

    /**
     * 查询电话号码是否重复
     *
     * @param tel
     * @return
     */
    @Select("select count(id) from account where tel = #{tel};")
    int findTelRepeat(String tel);

    /**
     * 修改电话号码
     *
     * @param uid
     * @param tel
     * @return
     */
    @Update("update account set tel = #{tel} where uid = #{uid}")
    int updateTel(@Param("uid") int uid, @Param("tel") String tel);

    /**
     * 编辑介绍
     *
     * @param uid
     * @param introduce
     * @return
     */
    @Update("update account set introduce = #{introduce} where uid = #{uid}")
    int updateIntroduce(@Param("uid") int uid, @Param("introduce") String introduce);

    /**
     * 修改介绍图片
     *
     * @param perphoto
     * @return
     */
    @Update("update settings set perphoto = #{perphoto} where id = 1;")
    int updatePerPhoto(String perphoto);

    /**
     * 修改介绍内容
     *
     * @param percontent
     * @return
     */
    @Update("update settings set percontent = #{percontent} where id = 1;")
    int updatePerContent(String percontent);

    /**
     * 获取指定用户文章
     *
     * @param uid
     * @param start
     * @param size
     * @return
     */
    @Select("select a.id,u.nickname username,a.title,a.publisht,a.browse,ifnull(al.`like`, 0) `like`,s.name sort,a.label,a.status from article a " +
            "left join account u on u.uid = a.uid " +
            "left join " +
            "(select count(`like`.id) `like`, article.id id from article, `like` where `like`.aid = article.id) al on a.id = al.id " +
            "left join sort s on s.id = a.sortid " +
            "where a.uid = #{uid} " +
            "order by a.publisht desc limit #{start}, #{size};")
    List<ReArticleList> findUserArticle(@Param("uid") int uid, @Param("start") int start, @Param("size") int size);

    /**
     * 获取用户文章数量
     *
     * @param uid
     * @return
     */
    @Select("select count(id) from article where uid = #{uid};")
    int findUserArticleSize(int uid);

    /**
     * 修改用户文章
     * @param aid
     * @param uid
     * @param title
     * @param reviset
     * @param sortid
     * @param label
     * @param content
     * @return
     */
    @Update("update article set title = #{title}, reviset = #{reviset}, sortid = #{sortid}, label = #{label}, content = #{content} " +
            "where id = #{aid} and uid = #{uid};")
    int updateUserArticle(@Param("aid") int aid, @Param("uid") int uid, @Param("title") String title,
                          @Param("reviset") String reviset, @Param("sortid") int sortid,
                          @Param("label") String label, @Param("content") String content);

    /**
     * 删除用户文章
     * @param aid
     * @param uid
     * @return
     */
    @Delete("delete from article where id = #{aid} and uid = #{uid};")
    int delUserArticle(@Param("aid") int aid, @Param("uid") int uid);

    /**
     * 修改管理员介绍
     * @param content
     * @return
     */
    @Update("update settings set perphoto = #{photolink}, percontent = #{content} where id = 1")
    int updateAdminRecom(@Param("photolink") String photolink, @Param("content") String content);
}

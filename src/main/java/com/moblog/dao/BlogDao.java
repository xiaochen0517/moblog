package com.moblog.dao;

import com.moblog.domain.blog.ReArticle;
import com.moblog.domain.blog.ReArticleList;
import com.moblog.domain.blog.ReComment;
import com.moblog.domain.blog.Recommend;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 功能：
 * 博客api dao层接口
 * @author MoChen
 * Date  2020/2/19
 * @version 0.1
 */
public interface BlogDao {

    /**
     * 获取主页文章列表
     *
     * @param start
     * @param size
     * @return
     */
    @Select("select a.id,u.nickname username,a.title,a.publisht,a.browse,ifnull(al.`like`, 0) `like`,s.name sort,a.label from article a " +
            "left join account u on u.uid = a.uid " +
            "left join " +
            "(select count(`like`.id) `like`, article.id id from article, `like` where `like`.aid = article.id) al on a.id = al.id " +
            "left join sort s on s.id = a.sortid " +
            "order by a.publisht desc limit #{start}, #{size};")
    List<ReArticleList> findHomePageArticle(@Param("start") int start, @Param("size") int size);

    /**
     * 获取所有文章的总条数
     *
     * @return
     */
    @Select("select count(id) from article;")
    int findAllArticleSize();

    /**
     * 搜索文章返回数据
     *
     * @param keyword 关键词
     * @param start   开始条目
     * @param size    条数
     * @return
     */
    @Select("select a.id,u.nickname username,a.title,a.publisht,a.browse,ifnull(al.`like`, 0) `like`,s.name sort,a.label from article a " +
            "left join account u on u.uid = a.uid " +
            "left join " +
            "(select count(`like`.id) `like`, article.id id from article, `like` where `like`.aid = article.id) al on a.id = al.id " +
            "left join sort s on s.id = a.sortid " +
            "where a.title like '%${keyword}%' " +
            "order by a.publisht desc limit #{start}, #{size};")
    List<ReArticleList> findSearchArticle(@Param("keyword") String keyword, @Param("start") int start, @Param("size") int size);

    /**
     * 获取搜索条目文章条数
     *
     * @param keyword 关键词
     * @return
     */
    @Select("select count(id) from article where title like '%${keyword}%';")
    int findSearchArticleSize(@Param("keyword") String keyword);

    /**
     * 获取文章信息
     * @param id 文章id
     * @return
     */
    @Select("select a.id,u.nickname username,a.title,a.publisht,a.browse,ifnull(al.`like`, 0) `like`,s.name sort,a.label,a.content from article a " +
            "left join account u on u.uid = a.uid " +
            "left join " +
            "(select count(`like`.id) `like`, article.id id from article, `like` where `like`.aid = article.id) al on a.id = al.id " +
            "left join sort s on s.id = a.sortid " +
            "where a.id = #{id};")
    ReArticle findArticle(int id);

    /**
     * 将置顶文章的浏览量加1
     * @param id
     * @return
     */
    @Update("update article set browse = browse + 1 where id = #{id};")
    int addArticleBrowse(int id);

    /**
     * 获取文章推荐
     * @return
     */
    @Select("select id, title from article order by browse limit 0, 5;")
    List<Recommend> findRecommend();

    /**
     * 获取指定文章评论数量
     * @param aid
     * @return
     */
    @Select("select count(id) from comment where aid = #{aid} ;")
    int findArticleReCommentSize(int aid);

    /**
     * 获取指定文章评论
     * @param aid
     * @param start
     * @param size
     * @return
     */
    @Select("select c.id,u.username,c.content,c.time from comment c " +
            "inner join user u on c.uid = u.id " +
            "where c.aid = #{aid} order by c.time desc limit #{start}, #{size};")
    List<ReComment> findArticleReComment(@Param("aid") int aid,@Param("start") int start,@Param("size") int size);
}

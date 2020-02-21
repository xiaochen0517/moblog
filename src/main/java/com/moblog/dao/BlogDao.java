package com.moblog.dao;

import com.moblog.domain.blog.ReArticle;
import com.moblog.domain.blog.ReArticleList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("select a.id,u.username,a.title,a.publisht,a.browse,a.like,s.name sort,a.label from " +
            "(article a inner join `user` u on u.id = a.uid) " +
            "inner join sort s on s.id = a.sortid order by a.publisht desc limit #{start}, #{size};")
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
    @Select("select a.id,u.username,a.title,a.publisht,a.browse,a.like,s.name sort,a.label from " +
            "(article a inner join `user` u on u.id = a.uid) " +
            "inner join sort s on s.id = a.sortid " +
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
    @Select("select a.id,u.username,a.title,a.publisht,a.browse,a.like,s.name sort,a.label,a.content from " +
            "(article a inner join `user` u on u.id = a.uid) " +
            "inner join sort s on s.id = a.sortid " +
            "where a.id = #{id};")
    ReArticle findArticle(int id);


}

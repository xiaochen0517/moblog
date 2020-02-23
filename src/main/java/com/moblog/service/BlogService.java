package com.moblog.service;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/19
 * @version 0.1
 */
public interface BlogService {

    /**
     * 获取主页文章列表
     * @return
     */
    String homePageArticle(int page);

    /**
     * 搜索文章
     * @return
     */
    String searchArticle(String keyword, int page);

    /**
     * 获取文章
     * @param id 文章id
     * @return
     */
    String article(int id);

    /**
     * 获取文章推荐
     * @return
     */
    String recommend();

    /**
     * 获取文章评论
     * @param aid
     * @return
     */
    String comment(int aid, int page);

}

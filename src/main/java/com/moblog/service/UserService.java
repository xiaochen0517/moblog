package com.moblog.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
public interface UserService {

    String findAll();

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    String login(String username, String password, HttpServletRequest request);

    /**
     * 用户注册
     * @param username
     * @param password
     * @param email
     * @param phonenum
     * @param request
     * @return
     */
    String register(String username, String password, String email, String phonenum, HttpServletRequest request);

    /**
     * 设置文章评论
     * @param username 用户名
     * @param content 内容
     * @param aid 文章id
     * @param request 请求
     * @return
     */
    String comment(String username, String content, int aid, HttpServletRequest request);

    /**
     * 文章点赞接口
     * @param username
     * @param aid
     * @param request
     * @return
     */
    String articleLike(String username, int aid, HttpServletRequest request);

    /**
     * 注销接口
     * @param request
     * @return
     */
    String logout(HttpServletRequest request);

    /**
     * 上传文章图片
     * @param imageFile
     * @param request
     * @param response
     * @return
     */
    String uploadArticlePhoto(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response);

    /**
     * 删除博客中的图片
     * @param fileName
     * @return
     */
    String delArticlePhoto(String fileName, HttpServletRequest request);

    /**
     * 上传文章
     * @param username
     * @param title
     * @param sortid
     * @param label
     * @param content
     * @param request
     * @return
     */
    String addArticle(String username, String title, int sortid, String label, String content, HttpServletRequest request);

    /**
     * 获取用户分类
     * @param username
     * @param request
     */
    String getUserSort(String username, HttpServletRequest request);

    /**
     * 添加用户分类
     * @param username
     * @param name
     * @param request
     * @return
     */
    String addUserSort(String username, String name, HttpServletRequest request);

    /**
     * 添加友链
     * @param name
     * @param link
     * @return
     */
    String addBlogRoll(String name, String link);

    /**
     *
     * @param id
     * @param name
     * @param link
     * @return
     */
    String editBlogRoll(int id, String name, String link);

    /**
     * 删除友链
     * @param id
     * @return
     */
    String delBlogRoll(int id);

    /**
     * 获取登录用户信息
     * @param request
     * @return
     */
    String getUserInfo(HttpServletRequest request);
}

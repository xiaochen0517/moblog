package com.moblog.service;

import javax.servlet.http.HttpServletRequest;

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

}

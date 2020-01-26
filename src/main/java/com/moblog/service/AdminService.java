package com.moblog.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @param vcode 验证码
     * @return 状态码
     */
    int login(HttpServletRequest request, String username, String password, String vcode);

}

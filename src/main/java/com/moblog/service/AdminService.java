package com.moblog.service;

import com.moblog.domain.Account;
import com.moblog.domain.admin.ReArticle;
import com.moblog.domain.admin.ReUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 获取用户列表
     * @param page 页数
     * @return
     */
    List<ReUser> userlist(int page);

    /**
     * 获取用户
     * @return
     */
    int[] getUserSize();

    /**
     * 获取用户信息
     * @param userid
     * @return
     */
    Account getUserinfo(int userid);

    /**
     * 获取用户文章列表
     * @param userid
     * @param page
     * @return
     */
    List<ReArticle> getUserArticle(int userid, int page);


    int[] getUserArticleSize(int userid);

}

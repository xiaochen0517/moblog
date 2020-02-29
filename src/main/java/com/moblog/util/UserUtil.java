package com.moblog.util;

import com.moblog.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/29
 * @version 0.1
 */
public class UserUtil {

    /**
     * 检查用户权限
     *
     * @param userdao
     * @param request
     * @return
     */
    public static boolean checkUserPermission(UserDao userdao, HttpServletRequest request) {
        // 获取用户名
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        // 获取用户权限
        return userdao.findUserStatuss(username);
    }

}

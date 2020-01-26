package com.moblog.service.impl;

import com.moblog.dao.AdminDao;
import com.moblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public int login(HttpServletRequest request, String username, String password, String vcode) {
        //验证vcode
        HttpSession session = request.getSession();
        if (!vcode.equalsIgnoreCase((String) session.getAttribute("vcode"))){
            //验证码错误
            return -1;
        }
        int result = adminDao.login(username, password);
        if (result != 1){
            //用户名或密码错误
            return -2;
        }
        return 0;
    }
}

package com.moblog.service.impl;

import com.moblog.dao.AdminDao;
import com.moblog.domain.admin.ReUser;
import com.moblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
@Component
public class AdminServiceImpl implements AdminService {

    private static final String TAG = "管理员Service接口实现类";

    private static final int pageitem = 10;

    @Autowired
    private AdminDao adminDao;

    @Override
    public int login(HttpServletRequest request, String username, String password, String vcode) {
        //验证vcode
        HttpSession session = request.getSession();
        if (vcode==null||!vcode.equalsIgnoreCase((String) session.getAttribute("vcode"))){
            //验证码错误
            return -1;
        }
        int result = adminDao.login(username, password);
        if (result != 1){
            //用户名或密码错误
            return -2;
        }
        //登录成功
        session.setAttribute("username", username);
        return 0;
    }

    @Override
    public List<ReUser> userlist(int page) {
        //获取数据
        return adminDao.findAccountList((page - 1) * pageitem);
    }

    @Override
    public int[] getUserSize() {
        int accountSize = adminDao.findAccountSize();
        int pageSize = accountSize / pageitem;
        if (accountSize%pageitem > 0){
            pageSize++;
        }
        return new int[]{accountSize, pageSize};
    }
}

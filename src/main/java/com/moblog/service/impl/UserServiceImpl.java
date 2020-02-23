package com.moblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moblog.dao.UserDao;
import com.moblog.domain.User;
import com.moblog.service.UserService;
import com.moblog.util.Log;
import com.moblog.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@Component
public class UserServiceImpl implements UserService {

    private static final String TAG = "UserServiceImpl";

    private static final String status = "status";

    @Autowired
    private UserDao userDao;

    @Override
    public String findAll() {
        //获取用户数据
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user.toString());
        }
        String jsonstr = JSONObject.toJSONString(users);
        return jsonstr;
    }

    @Override
    public String login(String username, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        } else if (userDao.findUserLogin(username, password) == 1) {  //开始登录
            // 登录成功
            map.put(status, 200);
            // 写入数据
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            // 获取用户状态
            System.out.println(userDao.findUserStatuss(username));
            if (userDao.findUserStatuss(username)){
                map.put("adminstatus", true);
            }
            //返回数据
            Log.d(TAG, "用户：" + username + "，登录成功");
            return JSONObject.toJSONString(map);
        } else {
            // 登录失败
            map.put(status, 405);
            //返回数据
            Log.d(TAG, "用户：" + username + "，登录失败");
            return JSONObject.toJSONString(map);
        }
    }

    @Override
    public String register(String username, String password, String email, String phonenum, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (username.equals("") || password.equals("") || email.equals("")) {
            // 数据错误
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 判断用户名是否重复
        if (userDao.findUsername(username) > 0) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 判断email是否重复
        if (userDao.findEmail(email) > 0) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 判断phonenum是否重复
        if (phonenum.length() > 5 && userDao.findPhoneNum(phonenum) > 0) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 开始插入数据
        int reuser = userDao.insertUser(username, password, false);
        if (reuser == 1) {
            // 获取插入的user的id
            int uid = userDao.findUserId(username);
            try {
                // 插入account
                int reaccount = userDao.insertAccount(uid, username, email);
                if (reaccount == 1) {
                    // 插入数据成功
                    if (phonenum.length() > 5) {
                        // 插入电话号码
                        userDao.updateAccountTel(uid, phonenum);
                    }
                    // 注册成功，自动登录
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);

                    map.put(status, 200);
                    return JSONObject.toJSONString(map);
                } else {
                    // 插入数据失败
                    userDao.deleteAccount(uid);
                    userDao.deleteUser(uid);
                    map.put(status, 408);
                    return JSONObject.toJSONString(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 插入数据失败
                userDao.deleteAccount(uid);
                userDao.deleteUser(uid);
                map.put(status, 408);
                return JSONObject.toJSONString(map);
            }
        } else {
            map.put(status, 408);
            return JSONObject.toJSONString(map);
        }
    }

    @Override
    public String comment(String username, String content, int aid, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断上传数据格式
        HttpSession session = request.getSession();
        if (!session.getAttribute("username").equals(username)){
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }else if(content.equals("") || content.length() < 5){
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }else if (aid < 1){
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 判断文章是否存在
        if (userDao.findArticleExist(aid) != 1){
            // 文章不存在
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 获取用户id
        int userId = userDao.findUserId(username);
        // 写入数据
        int reComment = userDao.insertComment(userId, aid, SystemUtil.getNowTime(), content, true);
        if (reComment != 1){
            // 写入错误
            map.put(status, 408);
            return JSONObject.toJSONString(map);
        }
        // 成功写入数据
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }
}

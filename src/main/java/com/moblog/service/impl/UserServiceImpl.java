package com.moblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moblog.dao.UserDao;
import com.moblog.domain.Account;
import com.moblog.domain.Sort;
import com.moblog.domain.User;
import com.moblog.domain.blog.ReArticleList;
import com.moblog.domain.user.ReAccount;
import com.moblog.service.UserService;
import com.moblog.util.Log;
import com.moblog.util.SystemUtil;
import com.moblog.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.Console;
import java.io.File;
import java.util.Calendar;
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
public class UserServiceImpl implements UserService
{

    private static final String TAG = "UserServiceImpl";

    private static final String status = "status";

    private static final int pageSize = 10;

    private static final String emailRegEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    private static final String telRegEx = "[0-9]+";

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
            if (userDao.findUserStatuss(username)) {
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
        if (!session.getAttribute("username").equals(username)) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        } else if (content.equals("") || content.length() < 5) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        } else if (aid < 1) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 判断文章是否存在
        if (userDao.findArticleExist(aid) != 1) {
            // 文章不存在
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 获取用户id
        int userId = userDao.findUserId(username);
        // 写入数据
        int reComment = userDao.insertComment(userId, aid, SystemUtil.getNowTime(), content, true);
        if (reComment != 1) {
            // 写入错误
            map.put(status, 408);
            return JSONObject.toJSONString(map);
        }
        // 成功写入数据
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String articleLike(String username, int aid, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断上传数据格式
        HttpSession session = request.getSession();
        if (!session.getAttribute("username").equals(username)) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        } else if (aid < 1) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 判断文章是否存在
        if (userDao.findArticleExist(aid) != 1) {
            // 文章不存在
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        int uid = userDao.findUserId(username);
        // 查询是否已经点赞
        if (userDao.findRepetLike(uid, aid) > 0) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 添加数据
        int reLike = userDao.insertLike(uid, aid, SystemUtil.getNowTime());
        if (reLike != 1) {
            // 点赞失败
            map.put(status, 408);
            return JSONObject.toJSONString(map);
        }
        // 点赞成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            session.removeAttribute("username");
        }
        return "{\"status\": 200}";
    }

    @Override
    public String uploadArticlePhoto(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (imageFile == null) {
                map.put(status, 404);
                return JSONObject.toJSONString(map);
            }
            // 获取用户名
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            // 获取服务器的实际路径
            String realPath = request.getSession().getServletContext().getRealPath("/");
            // 上传的路径
            String resourcePath = "images/upload/" + username;
            //文件名
            String name = imageFile.getOriginalFilename();
            // 转换后的文件名
            String filename = SystemUtil.getRandomName(name);
            File dir = new File(realPath + resourcePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //用户上传原图保存到服务器
            File file = new File(dir, filename);
            imageFile.transferTo(file);
            if (file.exists()) {
                // 上传文件成功
                map.put(status, 200);
                map.put("url", "http://127.0.0.1:8080/moblog/" + resourcePath + "/" + filename);
                return JSONObject.toJSONString(map);
            }
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
    }

    @Override
    public String delArticlePhoto(String fileName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Log.d(TAG, fileName);
        //判断数据
        if (fileName == null || fileName.length() < 32) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取用户名
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        // 获取服务器的实际路径
        String realPath = request.getSession().getServletContext().getRealPath("/");
        // 上传的路径
        String resourcePath = "images/upload/" + username;
        // 文件名
        String filename = SystemUtil.getFileName(fileName);
        // 开始删除文件
        File file = new File(realPath + resourcePath, filename);
        if (file.exists()) {
            file.delete();
            System.out.println("文件删除-->" + file.getName());
            map.put(status, 200);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 405);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String addArticle(String username, String title, int sortid, String label, String content, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //判断数据
        if (username == null || username.equals("")) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        } else if (title == null || title.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        } else if (sortid < 1) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        } else if (label == null || label.equals("")) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        } else if (content == null || content.equals("")) {
            map.put(status, 408);
            return JSONObject.toJSONString(map);
        }
        // 获取用户名
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            // 获取错误
            map.put(status, 409);
            return JSONObject.toJSONString(map);
        }
        // 写入数据
        String time = SystemUtil.getNowTime();
        int restatus = userDao.insertArticle(uid, title, time, time, sortid, label, content);
        if (restatus != 1) {
            // 写入失败
            map.put(status, 410);
            return JSONObject.toJSONString(map);
        }
        // 成功写入
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String getUserSort(String username, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //判断数据
        if (username == null || username.equals("")) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            // 获取错误
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 获取数据
        List<Sort> userSort = userDao.findUserSort(uid);
        if (userSort == null || userSort.size() < 1) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 成功获取
        map.put(status, 200);
        map.put("sorts", userSort);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String addUserSort(String username, String name, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //判断数据
        if (username == null || username.equals("")) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        } else if (name == null || name.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            // 获取错误
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 插入数据
        int restatus = userDao.insertUserSort(uid, name);
        if (restatus != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 成功获取
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String delUserSort(int id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (id < 1){
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        String username = (String) request.getSession().getAttribute("username");
        //判断数据
        if (username == null || username.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            // 获取错误
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 获取分类下的文章个数
        int articleSize = userDao.findUserSortArticleSize(id);
        Log.d(TAG, articleSize);
        if (articleSize > 0){
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 删除分类
        int restatus = userDao.deleteUserSort(id, uid);
        if (restatus != 1){
            // 删除失败
            map.put(status, 408);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editUserSort(int id, String name, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (id < 1){
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        if (name == null || name.equals("")) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        String username = (String) request.getSession().getAttribute("username");
        //判断数据
        if (username == null || username.equals("")) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            // 获取错误
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 删除分类
        int restatus = userDao.updateUserSort(id, uid, name);
        if (restatus != 1){
            // 删除失败
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String addBlogRoll(String name, String link, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (name == null || name.equals("")) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        if (link == null || link.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 添加友链
        int restatus = userDao.insertBlogRoll(name, link);
        if (restatus != 1) {
            // 添加失败
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 添加成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editBlogRoll(int id, String name, String link, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (id < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        if (name == null || name.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        if (link == null || link.equals("")) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 修改友链
        int restatus = userDao.updateBlogRoll(id, name, link);
        if (restatus != 1) {
            // 修改失败
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 修改成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String delBlogRoll(int id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (id < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 删除友链
        int restatus = userDao.delBlogRoll(id);
        if (restatus != 1) {
            // 删除失败
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 删除成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String getUserInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 获取用户名
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int userId = userDao.findUserId(username);
        ReAccount userInfo = userDao.findUserInfo(userId);
        if (userInfo == null || userInfo.getNickname().equals("")) {
            // 获取到的数据为空
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        map.put("userinfo", userInfo);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr -->" + jsonstr);
        return jsonstr;
    }

    @Override
    public String editHomePhoto(int id, String name, String link, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (id < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        if (name == null || name.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        if (link == null || link.equals("")) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 修改友链
        int restatus = userDao.updateHomePhoto(id, name, link);
        if (restatus != 1) {
            // 修改失败
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 修改成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String addHomePhoto(String name, String link, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (name == null || name.equals("")) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        if (link == null || link.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 添加友链
        int restatus = userDao.insertHomePhoto(name, link);
        if (restatus != 1) {
            // 添加失败
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 添加成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String delHomePhoto(int id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (id < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 删除友链
        int restatus = userDao.delHomePhoto(id);
        if (restatus != 1) {
            // 删除失败
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        // 删除成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editNickName(String nickname, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (nickname.length() < 2) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取用户ID
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 检查是否有重复昵称
        int nicknameRepeat = userDao.findNicknameRepeat(nickname);
        if (nicknameRepeat > 0) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 插入数据
        int i = userDao.updateNickname(uid, nickname);
        if (i != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editAddress(String address, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (address.length() < 2) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取用户ID
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 插入数据
        int i = userDao.updateAddress(uid, address);
        if (i != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editEmail(String email, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (!email.matches(emailRegEx)) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取用户ID
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 检查是否有重复昵称
        int nicknameRepeat = userDao.findEmailRepeat(email);
        if (nicknameRepeat > 0) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 插入数据
        int i = userDao.updateEmail(uid, email);
        if (i != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editTel(String tel, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (tel.length() < 5 || !tel.matches(telRegEx)) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取用户ID
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 检查是否有重复昵称
        int nicknameRepeat = userDao.findTelRepeat(tel);
        if (nicknameRepeat > 0) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 插入数据
        int i = userDao.updateTel(uid, tel);
        if (i != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editIntroduce(String introduce, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (introduce.length() < 5) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取用户ID
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 插入数据
        int i = userDao.updateIntroduce(uid, introduce);
        if (i != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editPerPhoto(String photo, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (photo == null || photo.length() < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        int i = userDao.updatePerPhoto(photo);
        if (i != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editPerContent(String percontent, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (!UserUtil.checkUserPermission(userDao, request)) {
            map.put(status, 498);
            return JSONObject.toJSONString(map);
        }
        if (percontent == null || percontent.length() < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        int i = userDao.updatePerContent(percontent);
        if (i != 1) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String getUserArticle(int page, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 判断数据
        if (page < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int uid = userDao.findUserId(username);
        //计算页数
        int pageStart = (page - 1) * pageSize;
        //获取文章条数
        int articleSize = userDao.findUserArticleSize(uid);
        //判断是否有数据
        if (articleSize < 1) {
            // 无数据
            map.put("status", 200);
            map.put("articlesize", 0);
            return JSONObject.toJSONString(map);
        }
        //获取数据
        List<ReArticleList> reArticleLists = userDao.findUserArticle(uid, pageStart, pageSize);
        //计算页面数量
        int pageSizes = (articleSize / pageSize) + (articleSize % pageSize > 0 ? 1 : 0);
        //包装json
        map.put("status", 200);
        map.put("articlesize", articleSize);
        map.put("pagesize", pageSizes);
        map.put("articlelist", reArticleLists);

        String jsonStr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr --> " + jsonStr);

        return jsonStr;
    }

    @Override
    public String editUserArticle(int id, String title, int sortid, String label, String content, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 获取username
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        //判断数据
        if (id < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        } else if (title == null || title.equals("")) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        } else if (sortid < 1) {
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        } else if (label == null || label.equals("")) {
            map.put(status, 407);
            return JSONObject.toJSONString(map);
        } else if (content == null || content.equals("")) {
            map.put(status, 408);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            // 获取错误
            map.put(status, 409);
            return JSONObject.toJSONString(map);
        }
        // 写入数据
        String time = SystemUtil.getNowTime();
        int restatus = userDao.updateUserArticle(id, uid, title, time, sortid, label, content);
        if (restatus != 1) {
            // 写入失败
            map.put(status, 410);
            return JSONObject.toJSONString(map);
        }
        // 成功写入
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String delUserArticle(int id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 获取username
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        //判断数据
        if (id < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取uid
        int uid = userDao.findUserId(username);
        if (uid < 1) {
            // 获取错误
            map.put(status, 409);
            return JSONObject.toJSONString(map);
        }
        // 删除数据
        int restatus = userDao.delUserArticle(id, uid);
        if (restatus != 1) {
            // 写入失败
            map.put(status, 410);
            return JSONObject.toJSONString(map);
        }
        // 成功删除
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }

    @Override
    public String editRecommendContent(String photoLink, String content, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (content == null || content.equals("")){
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 检查用户权限
        if (!UserUtil.checkUserPermission(userDao, request)){
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 开始修改
        int restatus = userDao.updateAdminRecom(photoLink, content);
        if (restatus != 1){
            map.put(status, 406);
            return JSONObject.toJSONString(map);
        }
        // 修改成功
        map.put(status, 200);
        return JSONObject.toJSONString(map);
    }
}

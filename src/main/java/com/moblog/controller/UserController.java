package com.moblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.moblog.dao.UserDao;
import com.moblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 * 用户登录控制类
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/findall")
    @ResponseBody
    private String findAll(){
        String jsonstr = userService.findAll();
        System.out.println(jsonstr);
        return jsonstr;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    private String login(String username, String password, HttpServletRequest request){
        return userService.login(username, password, request);
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @param email
     * @param phonenum
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    private String register(String username, String password, String email, String phonenum, HttpServletRequest request){
        return userService.register(username, password, email, phonenum, request);
    }

    /**
     * 提交文章评论
     * @param username
     * @param content
     * @param aid
     * @param request
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    private String comment(String username, String content, int aid, HttpServletRequest request){
        return userService.comment(username, content, aid, request);
    }

    /**
     * 文章点赞接口
     * @param username 点赞人
     * @param aid 所赞文章
     * @param request
     * @return
     */
    @RequestMapping(value = "/articlelike", method = RequestMethod.POST)
    @ResponseBody
    private String articleLike(String username, int aid, HttpServletRequest request){
        return userService.articleLike(username, aid, request);
    }

    /**
     * 检查用户是否在登录状态接口
     * @return
     */
    @RequestMapping(value = "/loginstatus", method = RequestMethod.GET)
    @ResponseBody
    private String loginStatus(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return JSONObject.toJSONString(map);
    }

    /**
     * 注销接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private String logout(HttpServletRequest request){
        return userService.logout(request);
    }

    /**
     * 文章图片上传
     * @param image
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/uploadap", method = RequestMethod.POST)
    @ResponseBody
    private String uploadArticlePhoto(MultipartFile image, HttpServletRequest request, HttpServletResponse response){
        return userService.uploadArticlePhoto(image, request, response);
    }

    /**
     * 删除博客中的图片
     * @param filename
     * @param request
     * @return
     */
    @RequestMapping(value = "/delap", method = RequestMethod.POST)
    @ResponseBody
    private String delArticlePhoto(String filename, HttpServletRequest request){
        return userService.delArticlePhoto(filename, request);
    }

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
    @RequestMapping(value = "/addarticle", method = RequestMethod.POST)
    @ResponseBody
    private String addArticle(String username, String title, int sortid, String label, String content, HttpServletRequest request){
        return userService.addArticle(username, title, sortid, label, content, request);
    }

    /**
     * 获取用户分类
     * @param username
     * @param request
     * @return
     */
    @RequestMapping(value = "/getsort", method = RequestMethod.GET)
    @ResponseBody
    private String getUserSort(String username, HttpServletRequest request){
        return userService.getUserSort(username, request);
    }

    /**
     * 新建分类
     * @param username
     * @param name
     * @param request
     * @return
     */
    @RequestMapping(value = "/addsort", method = RequestMethod.POST)
    @ResponseBody
    private String addUserSort(String username, String name, HttpServletRequest request){
        return userService.addUserSort(username, name, request);
    }

}

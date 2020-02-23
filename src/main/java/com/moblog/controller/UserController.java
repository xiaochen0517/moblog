package com.moblog.controller;

import com.moblog.dao.UserDao;
import com.moblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

}

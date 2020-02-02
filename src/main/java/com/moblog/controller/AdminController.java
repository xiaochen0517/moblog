package com.moblog.controller;

import com.moblog.domain.Account;
import com.moblog.domain.admin.ReArticle;
import com.moblog.domain.admin.ReUser;
import com.moblog.service.AdminService;
import com.moblog.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String TAG = "管理员登录接口";

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @param vcode 验证码
     * @param request request
     * @return view
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private ModelAndView login(String username, String password, String vcode,
                               boolean select, HttpServletRequest request, HttpServletResponse response){
        Log.d(TAG, "username-->"+username);
        Log.d(TAG, "password-->"+password);
        int result = adminService.login(request, username, password, vcode);
        ModelAndView modelAndView = new ModelAndView();
        switch (result){
            case 0:
                modelAndView.addObject("activeitem", 1);
                modelAndView.setViewName("redirect:/admin/homepage");
                if (select){
                    Cookie cookie = new Cookie("username", username);
                    cookie.setPath("/");
                    cookie.setMaxAge(60*60*24*30);
                    response.addCookie(cookie);
                    Cookie cookie1 = new Cookie("password", password);
                    cookie1.setPath("/");
                    cookie1.setMaxAge(60*60*24*30);
                    response.addCookie(cookie1);
                }else{
                    Cookie cookie = new Cookie("username", "");
                    cookie.setPath("/");
                    cookie.setMaxAge(60*60*24*30);
                    response.addCookie(cookie);
                    Cookie cookie1 = new Cookie("password", "");
                    cookie1.setPath("/");
                    cookie1.setMaxAge(60*60*24*30);
                    response.addCookie(cookie1);
                }
                return modelAndView;
            case -1:
                modelAndView.addObject("msg", "验证码错误");
                break;
            case -2:
                modelAndView.addObject("msg", "用户名或密码错误");
                break;
        }
        modelAndView.setViewName("forward:/");
        return modelAndView;
    }

    /**
     * 主页跳转
     * @return
     */
    @RequestMapping(value = "homepage", method = RequestMethod.GET)
    private ModelAndView homePage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("activeitem", 1);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    /**
     * 用户列表
     * @param page 页数
     * @return
     */
    @RequestMapping(value = "/userpage", method = RequestMethod.GET)
    private ModelAndView userPage(int page){
        Log.d(TAG, "page-->"+page);
        ModelAndView modelAndView = new ModelAndView();
        if (page<=0){
            modelAndView.setViewName("error");
            return modelAndView;
        }
        List<ReUser> reUsers = adminService.userlist(page);
        int[] pageinfo = adminService.getUserSize();

        //侧边栏，导航栏选中
        modelAndView.addObject("activeitem", 2);
        //返回列表数据
        modelAndView.addObject("userlist", reUsers);
        //返回页面数据
        modelAndView.addObject("page", page);//页数
        modelAndView.addObject("allitem", pageinfo[0]);//总条数
        modelAndView.addObject("allpage", pageinfo[1]);//总页数
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/userinfopage", method = RequestMethod.GET)
    private ModelAndView userinfoPage(int userid){
        ModelAndView modelAndView = new ModelAndView();
        //判断信息
        if (userid < 1){
            modelAndView.setViewName("error");
            return modelAndView;
        }
        //获取用户信息
        Account userinfo = adminService.getUserinfo(userid);
        modelAndView.addObject("account", userinfo);
        modelAndView.addObject("activeitem", 1);
        modelAndView.setViewName("userinfo");
        return modelAndView;
    }

    /**
     * 用户文章列表
     * @param page 页数
     * @return
     */
    @RequestMapping(value = "/articlepage", method = RequestMethod.GET)
    private ModelAndView articlePage(int userid, int page){
        ModelAndView modelAndView = new ModelAndView();
        if (userid<1 || page<1){
            modelAndView.setViewName("error");
            return modelAndView;
        }
        //获取文章数据
        List<ReArticle> userArticle = adminService.getUserArticle(userid, page);
        //获取文章数目
        int[] userArticleSize = adminService.getUserArticleSize(userid);
        modelAndView.addObject("articles", userArticle);
        modelAndView.addObject("allsize", userArticleSize[0]);
        modelAndView.addObject("allpage", userArticleSize[1]);
        modelAndView.addObject("page", page);
        modelAndView.addObject("userid", userid);
        modelAndView.addObject("activeitem", 2);
        modelAndView.setViewName("article");
        return modelAndView;
    }

}

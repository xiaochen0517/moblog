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

    /**
     * 删除指定分类
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/delsort", method = RequestMethod.POST)
    @ResponseBody
    private String delUserSort(int id, HttpServletRequest request){
        return userService.delUserSort(id, request);
    }

    /**
     * 编辑分类名
     * @param id
     * @param name
     * @param request
     * @return
     */
    @RequestMapping(value = "/editsort", method = RequestMethod.POST)
    @ResponseBody
    private String editUserSort(int id, String name, HttpServletRequest request){
        return userService.editUserSort(id, name, request);
    }

    /**
     * 添加友链
     * @param name
     * @param link
     * @return
     */
    @RequestMapping(value = "/addblogroll", method = RequestMethod.POST)
    @ResponseBody
    private String addBlogRoll(String name, String link, HttpServletRequest request){
        return userService.addBlogRoll(name, link, request);
    }

    /**
     * 修改友链
     * @param id
     * @param name
     * @param link
     * @return
     */
    @RequestMapping(value = "/editblogroll", method = RequestMethod.POST)
    @ResponseBody
    private String editBlogRoll(int id, String name, String link, HttpServletRequest request){
        return userService.editBlogRoll(id, name, link, request);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @RequestMapping(value = "/delblogroll", method = RequestMethod.POST)
    @ResponseBody
    private String delBlogRoll(int id, HttpServletRequest request){
        return userService.delBlogRoll(id, request);
    }

    /**
     * 获取已登录用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
    @ResponseBody
    private String getUserInfo(HttpServletRequest request){
        return userService.getUserInfo(request);
    }

    /**
     * 修改轮播图
     * @param id
     * @param name
     * @param link
     * @return
     */
    @RequestMapping(value = "/edithomephoto", method = RequestMethod.POST)
    @ResponseBody
    private String editHomePhoto(int id, String name, String link, HttpServletRequest request){
        return userService.editHomePhoto(id, name, link, request);
    }

    /**
     * 添加轮播图
     * @param name
     * @param link
     * @return
     */
    @RequestMapping(value = "/addhomephoto", method = RequestMethod.POST)
    @ResponseBody
    private String addHomePhoto(String name, String link, HttpServletRequest request){
        return userService.addHomePhoto(name, link, request);
    }

    /**
     * 删除主页轮播图
     * @param id
     * @return
     */
    @RequestMapping(value = "/delhomephoto", method = RequestMethod.POST)
    @ResponseBody
    private String delHomtPhoto(int id, HttpServletRequest request){
        return userService.delHomePhoto(id, request);
    }

    /**
     * 修改用户名
     * @param nickname
     * @param request
     * @return
     */
    @RequestMapping(value = "/editnickname", method = RequestMethod.POST)
    @ResponseBody
    private String editNickName(String nickname, HttpServletRequest request){
        return userService.editNickName(nickname, request);
    }

    /**
     * 修改地址信息
     * @param address
     * @param request
     * @return
     */
    @RequestMapping(value = "/editaddress", method = RequestMethod.POST)
    @ResponseBody
    private String editAddress(String address, HttpServletRequest request){
        return userService.editAddress(address, request);
    }

    /**
     * 编辑邮箱
     * @param email
     * @param request
     * @return
     */
    @RequestMapping(value = "/editemail", method = RequestMethod.POST)
    @ResponseBody
    private String editEmail(String email, HttpServletRequest request){
        return userService.editEmail(email, request);
    }

    /**
     * 编辑电话
     * @param tel
     * @param request
     * @return
     */
    @RequestMapping(value = "/edittel", method = RequestMethod.POST)
    @ResponseBody
    private String editTel(String tel, HttpServletRequest request){
        return userService.editTel(tel, request);
    }

    /**
     * 编辑介绍
     * @param introduce
     * @param request
     * @return
     */
    @RequestMapping(value = "/editintroduce", method = RequestMethod.POST)
    @ResponseBody
    private String editIntroduce(String introduce, HttpServletRequest request){
        return userService.editIntroduce(introduce, request);
    }

    /**
     * 修改介绍图片
     * @param photo
     * @param request
     * @return
     */
    @RequestMapping(value = "/editperphoto", method = RequestMethod.POST)
    @ResponseBody
    private String editPerPhoto(String photo, HttpServletRequest request){
        return userService.editPerPhoto(photo, request);
    }

    /**
     * 编辑介绍内容
     * @param percontent
     * @param request
     * @return
     */
    @RequestMapping(value = "/editpercontent", method = RequestMethod.POST)
    @ResponseBody
    private String editPerContent(String percontent, HttpServletRequest request){
        return userService.editPerContent(percontent, request);
    }

    /**
     * 获取用户文章列表
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/getuserarticle", method = RequestMethod.GET)
    @ResponseBody
    private String getUserArticle(int page, HttpServletRequest request){
        return userService.getUserArticle(page, request);
    }

    /**
     * 编辑用户文章
     * @param id
     * @param title
     * @param sortid
     * @param label
     * @param content
     * @param request
     * @return
     */
    @RequestMapping(value = "/edituserarticle", method = RequestMethod.POST)
    @ResponseBody
    private String editUserArticle(int id, String title, int sortid, String label, String content, HttpServletRequest request){
        return userService.editUserArticle(id, title, sortid, label, content, request);
    }

    /**
     * 删除用户文章
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/deluserarticle", method = RequestMethod.POST)
    @ResponseBody
    private String delUserArticle(int id, HttpServletRequest request){
        return userService.delUserArticle(id, request);
    }

    /**
     * 修改用户介绍
     * @param content
     * @param request
     * @return
     */
    @RequestMapping(value = "/editrecom", method = RequestMethod.POST)
    @ResponseBody
    private String editRecommendContent(String photolink, String content, HttpServletRequest request){
        return userService.editRecommendContent(photolink, content, request);
    }
}

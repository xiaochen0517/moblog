package com.moblog.controller;

import com.moblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.beancontext.BeanContextServiceAvailableEvent;

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

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String login(String username, String password, String vcode,
                         HttpServletRequest request){
        int result = adminService.login(request, username, password, vcode);
        switch (result){
            case 0:
                request.setAttribute("msg", "登录成功");
                return "home";
            case -1:
                request.setAttribute("msg", "验证码错误");
                break;
            case -2:
                request.setAttribute("msg", "用户名或密码错误");
                break;
        }

        return "forward:/";
    }

}
